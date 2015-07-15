/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is Compiere ERP & CRM Smart Business Solution. The Initial
 * Developer of the Original Code is Jorg Janke. Portions created by Jorg Janke
 * are Copyright (C) 1999-2005 Jorg Janke.
 * All parts are Copyright (C) 1999-2005 ComPiere, Inc.  All Rights Reserved.
 * Contributor(s): ______________________________________.
 *****************************************************************************/
package it.informaticagestionale.confproc.process;

import java.sql.*;
import java.util.logging.*;

import org.compiere.model.*;
import org.compiere.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import it.informaticagestionale.confproc.model.*;

import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MRMA;
import org.compiere.model.MRMALine;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
/**
 * 	Create Requisition from business it.informaticagestionale.confproc.process 
 *	
 *	
 *  @author Giorgio Cafasso
 *  @version $Id: LoadRequisitionFromBProcess.java,v 1.4 2006/01/21 02:23:37 gcafasso Exp $
 */
public class LoadRequisitionFromBProcess extends SvrProcess
{
	/** Org					*/
	private int			p_AD_Org_ID = 0;
	/** Warehouse			*/
	private int			p_M_Warehouse_ID = 0;
	/** User				*/
	private int			p_AD_User_ID = 0;
	/** Product				*/
	private int			p_M_Product_ID = 0;
	/** Requisition			*/
//	private int 		p_M_Requisition_ID = 0;
	/**	Chiamata					 */
	private int 		p_CP_ProcessFlow_ID = 0;
	/** Consolidate			*/
	//private boolean		p_ConsolidateDocument = false;
    /** stato parti*/
	private int 		p_CP_ProcessSubFlowState_ID=0;
    /** stato chiamata*/
	private int 		p_CP_ProcessFlowState_ID=0;
	
	/** Requisition				*/
	private CPMRequisition		m_requisition = null;
	/** Requisition Line			*/
	private CPMRequisitionLine	m_requisitionLine = null;
	/** descrizione Line -modifica di Giorgio- 
	 * inserimento della storia delle azioni
	 */
	private CP_ProcessHistory		tek_descrizione = null;
	/** Processo Chiamata
	 * modifica di Giorgio
	 */
	private CP_ProcessFlow tek_processo_chiamata = null;
	
	int counter = 0;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("AD_Org_ID"))
				p_AD_Org_ID = para[i].getParameterAsInt();
			else if (name.equals("M_Warehouse_ID"))
				p_M_Warehouse_ID = para[i].getParameterAsInt();			
			else if (name.equals("AD_User_ID"))
				p_AD_User_ID = para[i].getParameterAsInt();
			else if (name.equals("M_Product_ID"))
				p_M_Product_ID = para[i].getParameterAsInt();
			else if (name.equals("CP_ProcessFlow_ID"))
				p_CP_ProcessFlow_ID = para[i].getParameterAsInt();
			else if (name.equals("CP_ProcessSubFlowState_ID"))
				p_CP_ProcessSubFlowState_ID = para[i].getParameterAsInt();
			else if (name.equals("CP_ProcessFlowState_ID"))
				p_CP_ProcessFlowState_ID = para[i].getParameterAsInt();
//			else if (name.equals("ConsolidateDocument"))
//				p_ConsolidateDocument = "Y".equals(para[i].getParameter());
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}	//	prepare
	
	/**
	 * 	Process
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt() throws Exception
	{
		
		log.info("AD_Org_ID=" + p_AD_Org_ID);
		
		if (p_CP_ProcessSubFlowState_ID==0 & p_CP_ProcessFlowState_ID==0){
			addLog("-- insert BProcess sub flow state to load BProcess sub flow product --");
			addLog("-- insert BProcess flow state to load BProcess flow product --");
			throw new AdempiereUserError("Insert it.informaticagestionale.confproc.process flow or sub-flow state");
		}
		
		//esiste parametro dello stato delle parti
		if ( p_CP_ProcessSubFlowState_ID!= 0){
		StringBuffer sql = new StringBuffer("SELECT * FROM CP_ProcessSubFlow rl ")
			.append("WHERE rl.ISACTIVE='Y'AND rl.CP_ProcessSubFlowState_ID=? ");
		if (p_AD_Org_ID != 0)
			sql.append(" AND AD_Org_ID=?");
		
		//if (p_AD_User_ID != 0)
		//	sql.append(" AND rl.AD_User_ID=?");

		if (p_M_Product_ID!=0)
		 sql.append("AND M_Product_ID=?");
		
		PreparedStatement pstmt = null;

		try
		{
			pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			int index = 1;
			
				pstmt.setInt (index++, p_CP_ProcessSubFlowState_ID);
			
			if (p_AD_Org_ID != 0)
				pstmt.setInt (index++, p_AD_Org_ID);
		//	if (p_AD_User_ID != 0)
		//		pstmt.setInt (index++, p_AD_User_ID);
			if (p_M_Product_ID != 0)
				pstmt.setInt (index++, p_M_Product_ID);
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				process (new CP_ProcessSubFlow (getCtx(), rs, get_TrxName()));
			//	counter =counter +1;
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log (Level.SEVERE, sql.toString(), e);
		}
		try
		{
			if (pstmt != null)
				pstmt.close ();
			pstmt = null;
		}
		catch (Exception e)
		{
			pstmt = null;
		}
		closeRequest();

	    if (counter == 0) throw new AdempiereUserError("sub it.informaticagestionale.confproc.process not found!!");
		
		}
		
		//esiste parametro dello stato della chiamata
		if ( p_CP_ProcessFlowState_ID!= 0){
			StringBuffer sql = new StringBuffer("SELECT * FROM CP_ProcessFlow rl ")
				.append("WHERE rl.ISACTIVE='Y'AND rl.CP_ProcessFlowState_ID=? ");
			if (p_AD_Org_ID != 0)
				sql.append(" AND AD_Org_ID=?");

		//	if (p_AD_User_ID != 0)
		//		sql.append(" AND rl.AD_User_ID=?");

			if (p_M_Product_ID!=0)
			 sql.append("AND M_Product_ID=?");
			
			PreparedStatement pstmt = null;

			try
			{
				pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
				int index = 1;
				
					pstmt.setInt (index++, p_CP_ProcessFlowState_ID);
				
				if (p_AD_Org_ID != 0)
					pstmt.setInt (index++, p_AD_Org_ID);
//				if (p_AD_User_ID != 0)
//					pstmt.setInt (index++, p_AD_User_ID);
				if (p_M_Product_ID != 0)
					pstmt.setInt (index++, p_M_Product_ID);
				ResultSet rs = pstmt.executeQuery ();
				while (rs.next ())
				{
					process2 (new CP_ProcessFlow (getCtx(), rs, get_TrxName()));
				//	counter =counter +1;
				}
				rs.close ();
				pstmt.close ();
				pstmt = null;
			}
			catch (Exception e)
			{
				log.log (Level.SEVERE, sql.toString(), e);
			}
			try
			{
				if (pstmt != null)
					pstmt.close ();
				pstmt = null;
			}
			catch (Exception e)
			{
				pstmt = null;
			}
			closeRequest();

		    if (counter == 0) throw new AdempiereUserError("it.informaticagestionale.confproc.process not found!!");
			}
		
		return "@Created@ " + counter;
		
	}	//	doit
	
	private int 		m_TEK_Processo_Chiamata_ID = 0;
	private int 		m_M_Product_ID = 0;
	private int			m_M_AttributeSetInstance_ID = 0;
	/** BPartner				*/
	private MBPartner	m_bpartner = null;
	
	/**
	 * 	Process Line
	 *	@param rLine request line
	 */
	private void process (CP_ProcessSubFlow rLine) throws Exception
	{
	
		log.info("product "+rLine.getProduct());
		if (rLine.getLine() == null) throw new AdempiereUserError("requisition not found");
		if (rLine.getM_Product_ID() == 0) 
		{
			
			log.warning("Ignored Line" + rLine.getLine() 
				+ " " + rLine.getDescription());
			return;
		}
		
			
		if (m_requisitionLine == null
			|| rLine.getM_Product_ID() != m_M_Product_ID
			|| rLine.getM_Product_ID() == m_M_Product_ID)		//	single line per charge	
			newLine(rLine);
		
		m_requisitionLine.setQty(rLine.getQty());
		m_requisitionLine.setCP_ProcessFlow_ID(rLine.getCP_ProcessFlow_ID());
		m_requisitionLine.setCP_ProcessFlow_ID(rLine.getCP_ProcessFlow_ID());
		
		if (!rLine.save())
			throw new AdempiereSystemError("Cannot update Request Line");
	}	//	it.informaticagestionale.confproc.process
	
	/**
	 * 	Create new Request
	 *	@param rLine request line
	 */
	private void newRequest(CP_ProcessSubFlow rLine, int C_BPartner_ID,int TEK_STATO_PARTE_ID) throws Exception
	{
		if (m_requisition != null)
			closeRequest();
		//	BPartner
		if (m_bpartner == null || C_BPartner_ID != m_bpartner.getC_BPartner_ID())
			m_bpartner = new MBPartner (getCtx(), C_BPartner_ID, null);

		//	Requisition
		m_requisition = new CPMRequisition(getCtx(), 0, get_TrxName());
		m_requisition.setAD_User_ID(p_AD_User_ID);
		m_requisition.setM_PriceList_ID(m_bpartner.getM_PriceList_ID());
		m_requisition.setM_Warehouse_ID(p_M_Warehouse_ID);
		m_requisition.setDescription("Import from Product Requisition");
		//	Prepare Save
		m_TEK_Processo_Chiamata_ID = rLine.getCP_ProcessFlow_ID();
		if (!m_requisition.save())
			throw new AdempiereSystemError("Cannot save Request");
	}	//	newRequest

	/**
	 * 	Close Request
	 */
	private void closeRequest() throws Exception
	{
		if (m_requisitionLine != null)
		{
			if (!m_requisitionLine.save())
				throw new AdempiereSystemError("Cannot update Requisition Line");
		}
		if (m_requisition != null)
		{
			m_requisition.load(get_TrxName());
		}
		m_requisition = null;
		m_requisitionLine = null;
	}	//	closeRequest

	
	/**
	 * 	New request Line (different Product or not)
	 *	@param rLine request line
	 */
	private void newLine(CP_ProcessSubFlow rLine) throws Exception
	{
	//	entra correttamente
		counter = counter+1;
		if (m_requisitionLine != null)
			if (!m_requisitionLine.save())
				throw new AdempiereSystemError("Cannot update Request Line");
		m_requisitionLine = null;
		MProduct product = null;

		//	Get Business Partner
		int C_BPartner_ID = rLine.getC_BPartner_ID();
		int TEK_STATO_PARTE_ID = rLine.getCP_ProcessSubFlowState_ID();
		if (C_BPartner_ID != 0)
			;

		//	Find Vendor from Produt
			product = MProduct.get(getCtx(), rLine.getM_Product_ID());
			
			MProductPO[] ppos = MProductPO.getOfProduct(getCtx(), product.getM_Product_ID(), null);
			for (int i = 0; i < ppos.length; i++)
			{
				if (ppos[i].isCurrentVendor() && ppos[i].getC_BPartner_ID() != 0)
				{
					C_BPartner_ID = ppos[i].getC_BPartner_ID();
					break;
				}
			}
			if (C_BPartner_ID == 0 && ppos.length > 0)
				C_BPartner_ID = ppos[0].getC_BPartner_ID();
			if (C_BPartner_ID == 0)
				throw new AdempiereUserError("No Vendor for " + product.getName());

		
		//	New Request - Different Vendor
		if (m_requisition == null )//|| m_requisition.getC_BPartner_ID() != C_BPartner_ID)
			newRequest(rLine, C_BPartner_ID, TEK_STATO_PARTE_ID);
		
		
		//	No Request Line
		m_requisitionLine = new CPMRequisitionLine(m_requisition);
		if (product != null)
		{
			m_requisitionLine.setProduct(product);
//			m_requisitionLine.setM_AttributeSetInstance_ID(rLine.getM_AttributeSetInstance_ID());
		}
		else
		{

		}
		m_requisitionLine.setAD_Org_ID(rLine.getAD_Org_ID());
		
		log.info("ID Line: "+rLine.getCP_ProcessSubFlow_ID());

		
		
		//	Prepare Save
		log.info("product: "+rLine.getM_Product_ID());
		m_M_Product_ID = rLine.getM_Product_ID();
		if (!m_requisitionLine.save())
			throw new AdempiereSystemError("Cannot save Request Line");

	}	//	newLine
	
	
	
	
	
	
	
	/**
	 * 	Process Line
	 *	@param rLine request line
	 */
	private void process2 (CP_ProcessFlow rLine) throws Exception
	{
	
		System.out.println(rLine.getM_Product_ID());
			
		if (m_requisitionLine == null
			|| rLine.getM_Product_ID() != m_M_Product_ID
			|| rLine.getM_Product_ID() == m_M_Product_ID)
	
			newLine2(rLine);
		
		m_requisitionLine.setQty(rLine.getQty());
		m_requisitionLine.setCP_ProcessFlow_ID(rLine.getCP_ProcessFlow_ID());
		m_requisitionLine.setCP_ProcessFlow_ID(rLine.getCP_ProcessFlow_ID());

	
		if (!rLine.save())
			throw new AdempiereSystemError("Cannot update Request Line");
	}	//	it.informaticagestionale.confproc.process
	
	/**
	 * 	Create new Request
	 *	@param rLine request line
	 */
	private void newRequest2(CP_ProcessFlow rLine, int C_BPartner_ID,int CP_ProcessFlowState_ID) throws Exception
	{
		if (m_requisition != null)
			closeRequest2();
		//	BPartner
		if (m_bpartner == null || C_BPartner_ID != m_bpartner.getC_BPartner_ID())
			m_bpartner = new MBPartner (getCtx(), C_BPartner_ID, null);

		//	Requisition
		m_requisition = new CPMRequisition(getCtx(), 0, get_TrxName());
		m_requisition.setAD_User_ID(p_AD_User_ID);
		m_requisition.setM_PriceList_ID(m_bpartner.getM_PriceList_ID());
		m_requisition.setM_Warehouse_ID(p_M_Warehouse_ID);
		m_requisition.setDescription("imported from BProcess state");
		//	Prepare Save
		m_TEK_Processo_Chiamata_ID = rLine.getCP_ProcessFlow_ID();
		if (!m_requisition.save())
			throw new AdempiereSystemError("Cannot save Request");
	}	//	newRequest

	/**
	 * 	Close Request
	 */
	private void closeRequest2() throws Exception
	{
		if (m_requisitionLine != null)
		{
			if (!m_requisitionLine.save())
				throw new AdempiereSystemError("Cannot update Requisition Line");
		}
		if (m_requisition != null)
		{
			m_requisition.load(get_TrxName());
		}
		m_requisition = null;
		m_requisitionLine = null;
	}	//	closeRequest

	
	/**
	 * 	New request Line (different Product or not)
	 *	@param rLine request line
	 */
	private void newLine2(CP_ProcessFlow rLine) throws Exception
	{
		counter = counter+1;
		if (m_requisitionLine != null)
			if (!m_requisitionLine.save())
				throw new AdempiereSystemError("Cannot update Request Line");
		m_requisitionLine = null;
		MProduct product = null;

		//	Get Business Partner
		int C_BPartner_ID = rLine.getC_BPartner_ID();
		int CP_ProcessFlowState_ID = rLine.getCP_ProcessFlowState_ID();

		//	Find Vendor from Produt
			product = MProduct.get(getCtx(), rLine.getM_Product_ID());
			
			MProductPO[] ppos = MProductPO.getOfProduct(getCtx(), product.getM_Product_ID(), null);
			for (int i = 0; i < ppos.length; i++)
			{
				if (ppos[i].isCurrentVendor() && ppos[i].getC_BPartner_ID() != 0)
				{
					C_BPartner_ID = ppos[i].getC_BPartner_ID();
					break;
				}
			}
			if (C_BPartner_ID == 0 && ppos.length > 0)
				C_BPartner_ID = ppos[0].getC_BPartner_ID();
			if (C_BPartner_ID == 0)
			{
				addLog("No Vendor for " + product.getName());
				throw new AdempiereUserError("No Vendor for " + product.getName());
			}
		
	
		//	New Request - Different Vendor
		if (m_requisition == null )//|| m_requisition.getC_BPartner_ID() != C_BPartner_ID)
			newRequest2(rLine, C_BPartner_ID, CP_ProcessFlowState_ID);
		
		
		//	No Request Line
		m_requisitionLine = new CPMRequisitionLine(m_requisition);
		if (product != null)
		{
			m_requisitionLine.setProduct(product);
		}
		else
		{
			addLog("product not available");
			throw new AdempiereSystemError("Missing product");
		}
		m_requisitionLine.setAD_Org_ID(rLine.getAD_Org_ID());

		//modifica di Mauro - riempimento campi descrizione
		System.out.println("ID Line: "+rLine.getCP_ProcessFlow_ID());

		
		
		//	Prepare Save
		System.out.print(rLine.getM_Product_ID());
		m_M_Product_ID = rLine.getM_Product_ID();
		if (!m_requisitionLine.save())
			throw new AdempiereSystemError("Cannot save Request Line");

	}	//	newLine
	//fine inserimento delle chiamate


}	//	RequisitionPOCreate
