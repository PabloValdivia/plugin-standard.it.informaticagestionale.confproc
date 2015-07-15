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

import java.math.BigDecimal;
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
public class LoadOrderFromBProcess extends SvrProcess
{
	/** Org					*/
	private int			p_AD_Org_ID = 0;
	/** Warehouse			*/
	private int			p_M_Warehouse_ID = 0;
	/** User				*/
	private int			p_AD_User_ID = 0;
	/** Product				*/
	private int			p_M_Product_ID = 0;
	/** Partner				*/
	private int			p_C_BPartner_ID = 0;
	/** priceList				*/
	private int			p_M_PriceList_ID = 0;	
	/**	Process					 */
	private int 		p_CP_ProcessFlow_ID = 0;
    /** state subflow*/
	private int 		p_CP_ProcessSubFlowState_ID=0;
    /** state process*/
	private int 		p_CP_ProcessFlowState_ID=0;
    /** doc type*/
	private int 		p_C_DocType_ID=0;

	private boolean p_isSoTrx=false;
	
	/** Requisition				*/
	private CPMOrder		order = null;
	/** Requisition Line			*/
	private CPMOrderLine	orderLine = null;
	/** descrizione Line -modifica di Giorgio- 
	 * inserimento della storia delle azioni
	 */
	private CP_ProcessHistory		hs = null;

	private CP_ProcessFlow pf = null;
	
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
			else if (name.equals("M_PriceList_ID"))
				p_M_PriceList_ID = para[i].getParameterAsInt();			
			else if (name.equals("C_BPartner_ID"))
				p_C_BPartner_ID = para[i].getParameterAsInt();
			else if (name.equals("CP_ProcessFlow_ID"))
				p_CP_ProcessFlow_ID = para[i].getParameterAsInt();
			else if (name.equals("CP_ProcessSubFlowState_ID"))
				p_CP_ProcessSubFlowState_ID = para[i].getParameterAsInt();
			else if (name.equals("CP_ProcessFlowState_ID"))
				p_CP_ProcessFlowState_ID = para[i].getParameterAsInt();
			else if (name.equals("C_DocType_ID"))
				p_C_DocType_ID = para[i].getParameterAsInt();			
			else if (name.equals("IsSOTrx"))
				p_isSoTrx = para[i].getParameterAsBoolean();
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
		
		
		//exist state parameter 
		if ( p_CP_ProcessFlowState_ID!= 0){
			StringBuffer sql = new StringBuffer("SELECT * FROM CP_ProcessFlow rl ")
				.append("WHERE rl.ISACTIVE='Y'AND rl.CP_ProcessFlowState_ID=? ");
			if (p_AD_Org_ID != 0)
				sql.append(" AND AD_Org_ID=?");


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
			closeOrder();

		    if (counter == 0) throw new AdempiereUserError("process flow not found!");
			}
		

		return "@Created@ " + counter;
		
	}	//	doit
	
	private int 		m_M_Product_ID = 0;
	/** BPartner				*/
	private MBPartner	m_bpartner = null;
	
	
	
	
	
	/**
	 * 	Process Line
	 *	@param rLine request line
	 */
	private void process2 (CP_ProcessFlow rLine) throws Exception
	{
	
		log.info("Product Id: "+rLine.getM_Product_ID());
			
		if ( (orderLine == null || rLine.getM_Product_ID() != m_M_Product_ID	|| rLine.getM_Product_ID() == m_M_Product_ID))
			newLine2(rLine);
		
		orderLine.setQty(rLine.getQty());
		rLine.setIdDoc(order.getC_Order_ID());
		rLine.setC_DocType_ID(order.getC_DocTypeTarget_ID());
		
		//orderLine.setCP_ProcessFlow_ID(rLine.getCP_ProcessFlow_ID());
		if (!rLine.save())
			throw new AdempiereSystemError("Cannot update Order Line. Check if all fields are correctly compiled...");
	}	//	process
	
	/**
	 * 	Create new Request
	 *	@param rLine request line
	 */
	private void newOrder(CP_ProcessFlow rLine, int C_BPartner_ID,int CP_ProcessFlowState_ID) throws Exception
	{
		int location=0;
		if (order != null)
			closeOrder();
		//	BPartner
		if (m_bpartner == null || C_BPartner_ID != m_bpartner.getC_BPartner_ID())
			m_bpartner = new MBPartner (getCtx(), C_BPartner_ID, null);

		//	Order
		
		order = new CPMOrder(getCtx(), 0,p_isSoTrx, get_TrxName());
		order.setC_DocTypeTarget_ID(p_C_DocType_ID);
		order.setAD_User_ID(p_AD_User_ID);
		if (m_bpartner.getM_PriceList_ID()!=0)
			order.setM_PriceList_ID(m_bpartner.getM_PriceList_ID());
		else 
			if (p_M_PriceList_ID!=0) order.setM_PriceList_ID(p_M_PriceList_ID);
		else 
			addLog("Please, enter a priceList");
		if (p_M_Warehouse_ID==0)
			addLog ("Please, enter a warehouse");
		order.setM_Warehouse_ID(p_M_Warehouse_ID);
		order.setIsSOTrx(p_isSoTrx);
		order.setDescription("--imported by process flow: "+rLine.getName());
		if (m_bpartner == null){
			addLog ("Can't find a BPartner associated");
			throw new AdempiereSystemError("Cannot find BPartner");			
		}
		order.setC_BPartner_ID(m_bpartner.get_ID());
		
		MBPartnerLocation[] locs = m_bpartner.getLocations(true);
		for (int i = 0; i < locs.length; i++)
		{
			if (locs[i].isShipTo())
			{
				location = locs[i].getC_BPartner_Location_ID();
				order.setC_BPartner_Location_ID(location);
				break;
			}else{
				throw new AdempiereSystemError("Cannot find BPartner ShipTo location");
			}
		}
		
		//	Prepare Save
		if (!order.save()){
			addLog("Cannot save Order");
			throw new AdempiereSystemError("Cannot save Order");
		}
		addLog("Order created: "+ order.getDocumentInfo() + " from process: "+rLine.getName());
		counter++;
			
	}	//	newRequest

	/**
	 * 	Close Request
	 */
	private void closeOrder() throws Exception
	{
		if (orderLine != null)
		{
			if (!orderLine.save())
				throw new AdempiereSystemError("Cannot update Order Line");
		}
		if (order != null)
		{
			order.load(get_TrxName());
		}
		order = null;
		orderLine = null;
	}	//	closeOrder

	
	/**
	 * 	New order Line (different Product or not)
	 *	@param rLine request line
	 */
	private void newLine2(CP_ProcessFlow rLine) throws Exception
	{
		
		if (orderLine != null)
			if (!orderLine.save())
				throw new AdempiereSystemError("Cannot update Order Line");
		orderLine = null;
		MProduct product = null;

		//	Get Business Partner
		int C_BPartner_ID = rLine.getC_BPartner_ID();
		int CP_ProcessFlowState_ID = rLine.getCP_ProcessFlowState_ID();

		//	Find Vendor from Product
			product = MProduct.get(getCtx(), rLine.getM_Product_ID());
			if (product == null) {
				addLog("For generating an order you have to select a product...");
				throw new AdempiereSystemError ("Product not found");
			}
			MProductPO[] ppos = MProductPO.getOfProduct(getCtx(), product.getM_Product_ID(), null);
			for (int i = 0; i < ppos.length; i++)
			{
				if (ppos[i].isCurrentVendor() && ppos[i].getC_BPartner_ID() != 0 && !p_isSoTrx)
				{
					C_BPartner_ID = ppos[i].getC_BPartner_ID();
					break;
				}
			}
			if (C_BPartner_ID == 0 && ppos.length > 0)
				C_BPartner_ID = ppos[0].getC_BPartner_ID();
			if (C_BPartner_ID == 0)
			{
				addLog("No Vendor for " + product.getName()+ "or BPartner into process flow is empty");
				throw new AdempiereUserError("No Vendor for " + product.getName() + "or BPartner into process flow is empty");
			}
		
	
		//	New Order - Different Vendor
		if (order == null || order.getC_BPartner_ID()!=C_BPartner_ID)
			newOrder(rLine, C_BPartner_ID, CP_ProcessFlowState_ID);
		
		
		//	No Request Line
		orderLine = new CPMOrderLine(order);
		if (product != null)
		{
			orderLine.setProduct(product);
		}
		else
		{
			addLog("product not available");
			throw new AdempiereSystemError("Missing product");
		}
		orderLine.setAD_Org_ID(rLine.getAD_Org_ID());
		
		//	Prepare Save
		m_M_Product_ID = rLine.getM_Product_ID();
		if (!orderLine.save()){
			addLog("Cannot save Order Line");
			throw new AdempiereSystemError("Cannot save Order Line");
		}
		addLog("New order line from: "+rLine.getName());
		rLine.setIdDocLine(orderLine.getC_OrderLine_ID());
//		counter = counter+1;	

	}	//	newLine
	//fine inserimento delle processFlow


}	//	OrderPOCreate
