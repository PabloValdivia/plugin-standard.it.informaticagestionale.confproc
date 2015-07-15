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
 *  * Portions created by Giorgio Cafasso are Copyright (C) 2015 .               *
 *****************************************************************************/
package it.informaticagestionale.confproc.process;



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
 * 	Create PO from Requisition with  Business Process
 *	
 *	
 *  @author Giorgio Cafasso
 *  @version $Id: LoadOrderFromReqBProcess.java,v 1.4 2006/01/21 02:23:37 gcafasso Exp $
 */
public class LoadOrderFromReqBProcess extends SvrProcess
{
	/** Org					*/
	private int			p_AD_Org_ID = 0;
	/** Warehouse			*/
	private int			p_M_Warehouse_ID = 0;
	/**	Doc Date From		*/
	private Timestamp	p_DateDoc_From;
	/**	Doc Date To			*/
	private Timestamp	p_DateDoc_To;
	/**	Doc Date From		*/
	private Timestamp	p_DateRequired_From;
	/**	Doc Date To			*/
	private Timestamp	p_DateRequired_To;
	/** Priority			*/
	private String		p_PriorityRule = null;
	/** User				*/
	private int			p_AD_User_ID = 0;
	/** Product				*/
	private int			p_M_Product_ID = 0;
	/** Requisition			*/
	private int 		p_M_Requisition_ID = 0;

	/** Consolidate			*/
	private boolean		p_ConsolidateDocument = false;

	/** Order				*/
	private CPMOrder		m_order = null;
	/** Order Line			*/
	private CPMOrderLine	m_orderLine = null;
	
	
	
	
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
			else if (name.equals("DateDoc"))
			{
				p_DateDoc_From = (Timestamp)para[i].getParameter();
				p_DateDoc_To = (Timestamp)para[i].getParameter_To();
			}
			else if (name.equals("DateRequired"))
			{
				p_DateRequired_From = (Timestamp)para[i].getParameter();
				p_DateRequired_To = (Timestamp)para[i].getParameter_To();
			}
			else if (name.equals("PriorityRule"))
				p_PriorityRule = (String)para[i].getParameter();
			else if (name.equals("AD_User_ID"))
				p_AD_User_ID = para[i].getParameterAsInt();
			else if (name.equals("M_Product_ID"))
				p_M_Product_ID = para[i].getParameterAsInt();
			else if (name.equals("M_Requisition_ID"))
				p_M_Requisition_ID = para[i].getParameterAsInt();
			else if (name.equals("ConsolidateDocument"))
				p_ConsolidateDocument = "Y".equals(para[i].getParameter());
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
		//	Specific
		if (p_M_Requisition_ID != 0)
		{
			log.info("M_Requisition_ID=" + p_M_Requisition_ID);
			CPMRequisition req = new CPMRequisition(getCtx(), p_M_Requisition_ID, get_TrxName());
			if (!CPMRequisition.DOCSTATUS_Completed.equals(req.getDocStatus()))
				throw new AdempiereUserError("@DocStatus@ = " + req.getDocStatus());
			CPMRequisitionLine[] lines = req.getLines();
			log.info("lines.length: "+lines.length);
			for (int i = 0; i < lines.length; i++)
			{
				if (lines[i].getC_OrderLine_ID() == 0)
					process (lines[i]);
			}
			closeOrder();
			return "";
		}	//	single Requisition
		
		//	
		log.info("AD_Org_ID=" + p_AD_Org_ID
			+ ", M_Warehouse_ID=" + p_M_Warehouse_ID
			+ ", DateDoc=" + p_DateDoc_From + "/" + p_DateDoc_To
			+ ", DateRequired=" + p_DateRequired_From + "/" + p_DateRequired_To
			+ ", PriorityRule=" + p_PriorityRule
			+ ", AD_User_ID=" + p_AD_User_ID
			+ ", M_Product_ID=" + p_M_Product_ID
			+ ", ConsolidateDocument" + p_ConsolidateDocument);
		
		StringBuffer sql = new StringBuffer("SELECT * FROM M_RequisitionLine rl ")
		
			.append("WHERE rl.C_OrderLine_ID IS NULL");
		if (p_AD_Org_ID != 0)
			sql.append(" AND AD_Org_ID=?");
		if (p_M_Product_ID != 0)
			sql.append(" AND M_Product_ID=?");
		//	Requisition Header
		//sql.append("AND EXISTS (SELECT * FROM M_Requisition r WHERE rl.M_Requisition_ID=r.M_Requisition_ID")
		sql.append(" AND EXISTS (SELECT * FROM M_Requisition r WHERE rl.M_Requisition_ID=r.M_Requisition_ID")
			.append(" AND r.DocStatus='CO'");
		if (p_M_Warehouse_ID != 0)
			sql.append(" AND r.M_Warehouse_ID=?");
		//
		if (p_DateDoc_From != null && p_DateDoc_To != null)
			sql.append(" AND r.DateDoc BETWEEN ? AND ?");
		else if (p_DateDoc_From != null)
			sql.append(" AND r.DateDoc => ?");
		else if (p_DateDoc_To != null)
			sql.append(" AND r.DateDoc <= ?");
		//
		if (p_DateRequired_From != null && p_DateRequired_To != null)
			sql.append(" AND r.DateRequired BETWEEN ? AND ?");
		else if (p_DateRequired_From != null)
			sql.append(" AND r.DateRequired => ?");
		else if (p_DateRequired_To != null)
			sql.append(" AND r.DateRequired <= ?");
		//
		if (p_PriorityRule != null)
			sql.append(" AND r.PriorityRule => ?");
		if (p_AD_User_ID != 0)
	//		sql.append(" AND r.AD_User_ID=?");

		//		modifica di Giorgio 26-03-2007
//		sql.append(") GROUP BY rl.TEK_CAT_ID ");
		

		//
//		sql.append(" ORDER BY ");
		sql.append(") ORDER BY ");
		if (!p_ConsolidateDocument)
			sql.append("M_Requisition_ID, ");
		sql.append("C_BPartner_ID, M_Product_ID, C_Charge_ID, M_AttributeSetInstance_ID");
		

		
		
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			int index = 1;
			if (p_AD_Org_ID != 0)
				pstmt.setInt (index++, p_AD_Org_ID);
			if (p_M_Product_ID != 0)
				pstmt.setInt (index++, p_M_Product_ID);
			if (p_M_Warehouse_ID != 0)
				pstmt.setInt (index++, p_M_Warehouse_ID);
			if (p_DateDoc_From != null && p_DateDoc_To != null)
			{
				pstmt.setTimestamp(index++, p_DateDoc_From);
				pstmt.setTimestamp(index++, p_DateDoc_To);
			}
			else if (p_DateDoc_From != null)
				pstmt.setTimestamp(index++, p_DateDoc_From);
			else if (p_DateDoc_To != null)
				pstmt.setTimestamp(index++, p_DateDoc_To);
			if (p_DateRequired_From != null && p_DateRequired_To != null)
			{
				pstmt.setTimestamp(index++, p_DateRequired_From);
				pstmt.setTimestamp(index++, p_DateRequired_To);
			}
			else if (p_DateRequired_From != null)
				pstmt.setTimestamp(index++, p_DateRequired_From);
			else if (p_DateRequired_To != null)
				pstmt.setTimestamp(index++, p_DateRequired_To);
			if (p_PriorityRule != null)
				pstmt.setString(index++, p_PriorityRule);
			if (p_AD_User_ID != 0)
				pstmt.setInt (index++, p_AD_User_ID);
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				process (new CPMRequisitionLine (getCtx(), rs, get_TrxName()));
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
		closeOrder();
		return "";
	}	//	doit
	
	private int 		m_M_Requisition_ID = 0;
	private int 		m_M_Product_ID = 0;
	private int			m_M_AttributeSetInstance_ID = 0;
	/** BPartner				*/
	private MBPartner	m_bpartner = null;
	
	/**
	 * 	Process Line
	 *	@param rLine request line
	 */
	private void process (CPMRequisitionLine rLine) throws Exception
	{
		if (rLine.getM_Product_ID() == 0 && rLine.getC_Charge_ID() == 0)
		{
			log.warning("Ignored Line" + rLine.getLine() 
				+ " " + rLine.getDescription()
				+ " - " + rLine.getLineNetAmt());
			return;
		}
		
		if (!p_ConsolidateDocument
			&& rLine.getM_Requisition_ID() != m_M_Requisition_ID)
			closeOrder();		
		if (m_orderLine == null
			|| rLine.getM_Product_ID() != m_M_Product_ID
			//modifica di Giorgio
			|| rLine.getM_Product_ID() == m_M_Product_ID
			//fine modifica di Giorgio
			|| rLine.getM_AttributeSetInstance_ID() != m_M_AttributeSetInstance_ID
			|| rLine.getC_Charge_ID() != 0)		//	single line per charge
			newLine(rLine);

		//	Update Order Line
		m_orderLine.setQty(m_orderLine.getQtyOrdered().add(rLine.getQty()));
		//modifica effettuata da Giorgio
		if (rLine.getCP_ProcessFlow_ID()!=0)
		//m_orderLine.setCP_ProcessFlow_ID(rLine.getCP_ProcessFlow_ID());
		rLine.setC_OrderLine_ID(m_orderLine.getC_OrderLine_ID());
		if (!rLine.save())
			throw new AdempiereSystemError("Cannot update Request Line");
	}	//	it.informaticagestionale.confproc.process
	
	/**
	 * 	Create new Order
	 *	@param rLine request line
	 */
	private void newOrder(CPMRequisitionLine rLine, int C_BPartner_ID) throws Exception //modifiche per Teknema,int TEK_CAT_ID)
	{
		if (m_order != null)
			closeOrder();
		//	BPartner
		if (m_bpartner == null || C_BPartner_ID != m_bpartner.getC_BPartner_ID())
			m_bpartner = new MBPartner (getCtx(), C_BPartner_ID, null);

		//	Order

		m_order = new CPMOrder(getCtx(), 0, get_TrxName());
		m_order.setIsSOTrx(false);
		m_order.setC_DocTypeTarget_ID();
		m_order.setBPartner(m_bpartner);
		m_order.setM_PriceList_ID(rLine.getParent().getM_PriceList_ID()); //modifica di Giorgio 11-06-2007 --> listino prezzi Teknema
		
	//	default po document type
		if (!p_ConsolidateDocument)
			m_order.setDescription(Msg.getElement(getCtx(), "M_Requisition_ID") 
				+ ": " + rLine.getParent().getDocumentNo());
		
		//	Prepare Save
		m_M_Requisition_ID = rLine.getM_Requisition_ID();
		if (!m_order.save())
			throw new AdempiereSystemError("Cannot save Order");
	}	//	newOrder

	/**
	 * 	Close Order
	 */
	private void closeOrder() throws Exception
	{
		if (m_orderLine != null)
		{
			if (!m_orderLine.save())
				throw new AdempiereSystemError("Cannot update Order Line");
		}
		if (m_order != null)
		{
			m_order.load(get_TrxName());
			addLog(0, null, m_order.getGrandTotal(), m_order.getDocumentNo());
		}
		m_order = null;
		m_orderLine = null;
	}	//	closeOrder

	
	/**
	 * 	New Order Line (different Product)
	 *	@param rLine request line
	 */
	private void newLine(CPMRequisitionLine rLine) throws Exception
	{
		if (m_orderLine != null)
			if (!m_orderLine.save())
				throw new AdempiereSystemError("Cannot update Order Line");
		m_orderLine = null;
		MProduct product = null;

		//	Get Business Partner
		int C_BPartner_ID = rLine.getC_BPartner_ID();

		int CreatedBy=rLine.getCreatedBy();	
		if (C_BPartner_ID != 0)
			; //modifica di giorgio
		else if (rLine.getC_Charge_ID() != 0)
		{
			MCharge charge = MCharge.get(getCtx(), rLine.getC_Charge_ID());
			C_BPartner_ID = charge.getC_BPartner_ID();
			if (C_BPartner_ID == 0)
				throw new AdempiereUserError("No Vendor for Charge " + charge.getName());
		}
		else
		{
			//	Find Vendor from Produt
			product = MProduct.get(getCtx(), rLine.getM_Product_ID());
			System.out.println("rLine.getM_Product_ID: "+rLine.getM_Product_ID()); //modifica per controllo
			MProductPO[] ppos = MProductPO.getOfProduct(getCtx(), product.getM_Product_ID(), null);
			for (int i = 0; i < ppos.length; i++)
			{
				if (ppos[i].isCurrentVendor() && ppos[i].getC_BPartner_ID() != 0)
				{
					C_BPartner_ID = ppos[i].getC_BPartner_ID();
					System.out.println("RequisitionPOCreate->C_BPartner_ID: "+C_BPartner_ID);
					break;
				}
			}
			if (C_BPartner_ID == 0 && ppos.length > 0)
				C_BPartner_ID = ppos[0].getC_BPartner_ID();
			if (C_BPartner_ID == 0)
				throw new AdempiereUserError("No Vendor for " + product.getName());
		}

		//	New Order - Different Vendor
		if (m_order == null 
			//	|| m_order.getC_BPartner_ID() != C_BPartner_ID)
			|| m_order.getC_BPartner_ID() != C_BPartner_ID)//modifiche per Teknema || m_order.getTEK_CAT_ID()!=TEK_CAT_ID) //modifica Giorgio 16-3-2007
			newOrder(rLine, C_BPartner_ID);//modifica per Teknema, TEK_CAT_ID); //modifica di Giorgio 16-3-2007

		//	No Order Line
		m_orderLine = new CPMOrderLine(m_order);
		if (product != null)
		{
			m_orderLine.setProduct(product);
			m_orderLine.setM_AttributeSetInstance_ID(rLine.getM_AttributeSetInstance_ID());
		}
		else
		{
			m_orderLine.setC_Charge_ID(rLine.getC_Charge_ID());
			m_orderLine.setPriceActual(rLine.getPriceActual());
		}
		m_orderLine.setAD_Org_ID(rLine.getAD_Org_ID());
				
		
		//	Prepare Save
		m_M_Product_ID = rLine.getM_Product_ID();
		m_M_AttributeSetInstance_ID = rLine.getM_AttributeSetInstance_ID();
		if (!m_orderLine.save())
			throw new AdempiereSystemError("Cannot save Order Line");
	}	//	newLine
	
}	//	RequisitionPOCreate
