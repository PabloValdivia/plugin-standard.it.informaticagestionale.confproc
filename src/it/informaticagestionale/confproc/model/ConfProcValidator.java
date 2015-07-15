/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2007 ADempiere, Inc. All Rights Reserved.                    *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * Adempiere, Inc. 															  * 										
 * Portions created by Giorgio Cafasso are Copyright (C) 2015 .               *
 *****************************************************************************/
package it.informaticagestionale.confproc.model;

import java.math.*;
import org.compiere.model.*;
import org.compiere.util.*;


/**
 *	Validator Implementation
 *	
 *	@author Giorgio Cafasso
 *	@version $Id: ConfProcValidator.java,v 1.2 2006/07/30 00:51:57 gcafasso Exp $
 */
public class ConfProcValidator implements ModelValidator
{
	/**
	 *	Constructor.
	 *	The class is instanciated when logging in and client is selected/known
	 */
	public ConfProcValidator ()
	{
		super ();
	}	//	ConfProcValidator
	
	/**	Logger			*/
	private static CLogger log = CLogger.getCLogger(ConfProcValidator.class);
	/** Client			*/
	private int		m_AD_Client_ID = -1;
	
	/** boolean document complete*/
	private boolean complete=false;
	
	/**	Process Message 			*/
	private String		m_ConfProcMsg = null;
	/**
	 *	Initialize Validation
	 *	@param engine validation engine 
	 *	@param client client
	 */
	public void initialize (ModelValidationEngine engine, MClient client)
	{
		m_AD_Client_ID = client.getAD_Client_ID();
		log.info(client.toString());
		log.info("Esegue validator");
		
		//	We want to be informed when X_Document is created/changed
//		engine.addModelChange(MOrder.Table_Name, this);
//		engine.addModelChange(MInOut.Table_Name, this);
//		engine.addModelChange(MRequisition.Table_Name, this);
//		engine.addModelChange(MMovement.Table_Name, this);
//		engine.addModelChange(MInvoice.Table_Name, this);

		//	We want to validate Order before completed 
		engine.addDocValidate(MOrder.Table_Name, this);
		engine.addDocValidate(MInOut.Table_Name, this);
		engine.addDocValidate(MRequisition.Table_Name, this);
		engine.addDocValidate(MMovement.Table_Name, this);
		engine.addDocValidate(MInvoice.Table_Name, this);
	}	//	initialize

    /**
     *	Model Change of a monitored Table.
     *	Called after PO.beforeSave/PO.beforeDelete
     *	when you called addModelChange for the table
     *	@param po persistent object
     *	@param type TYPE_
     *	@return error message or null
     *	@exception Exception if the recipient wishes the change to be not accept.
     */
	public String modelChange (PO po, int type) throws Exception
	{
		log.info(po.toString());
//		System.out.println("Execute modelChange");
		
//		if (po.get_TableName().equals("C_Order") && type == TYPE_CHANGE)
//		{
//			MOrder order = (MOrder)po;
//			System.out.println("m_processMsg " +m_processMsg);
//			if(!callTransiction(order))
//				return "Call Transiction failed " + m_processMsg;//invoke callTransiction
//			log.info(po.toString());
//		}
//		if (po.get_TableName().equals("C_Invoice") && type == TYPE_CHANGE)
//		{
//			MInvoice invoice = (MInvoice)po;
//			if(!callTransiction(invoice))
//				return "Call Transiction failed " + m_processMsg;//invoke callTransiction
//			log.info(po.toString());
//		}
//		if (po.get_TableName().equals("M_Requisition") && type == TYPE_CHANGE)
//		{
//			MRequisition requisition = (MRequisition)po;
//			if(!callTransiction(requisition))
//				return "Call Transiction failed " + m_processMsg;//invoke callTransiction
//			log.info(po.toString());
//		}
//		if (po.get_TableName().equals("M_Movement") && type == TYPE_CHANGE)
//		{
//			MMovement movement = (MMovement)po;
//			if(!callTransiction(movement))
//				return "Call Transiction failed " + m_processMsg;//invoke callTransiction
//			log.info(po.toString());
//		}
//		if (po.get_TableName().equals("M_InOut") && type == TYPE_CHANGE)
//		{
//			MInOut inout = (MInOut)po;
//			if(!callTransiction(inout))
//				return "Call Transiction failed " + m_processMsg;//invoke callTransiction
//			log.info(po.toString());
//		}
		return null;
	}	//	modelChange
	
	/**
	 *	Validate Document.
	 *	Called as first step of DocAction.prepareIt 
	 *	@param po persistent object
	 *	@param timing see TIMING_ constants
     *	@return error message or null
	 */
	public String docValidate (PO po, int timing)
	{
		System.out.println("Esegue DocValidate");
		log.info(po.get_TableName() + " Timing: "+timing);
		//	Ignore all after Complete events
		if (timing == TIMING_BEFORE_COMPLETE)
			return null;
		if (timing == TIMING_AFTER_COMPLETE) {
		//if (timing == TIMING_BEFORE_PREPARE) {
			if (po.get_TableName().equals(MOrder.Table_Name))
			{
				MOrder order = (MOrder)po;
				if(!callTransiction(order))
					return "Call Transiction failed "+m_ConfProcMsg;//invoke callTransiction
				log.info(po.toString());
			}
			if (po.get_TableName().equals(MInOut.Table_Name))
			{
				MInOut inout = (MInOut)po;
				if(!callTransiction(inout))
					return "Call Transiction failed "+m_ConfProcMsg;//invoke callTransiction
				log.info(po.toString());
			}
			if (po.get_TableName().equals(MMovement.Table_Name))
			{
				MMovement movement = (MMovement)po;
				if(!callTransiction(movement))
					return "Call Transiction failed "+m_ConfProcMsg;//invoke callTransiction
				log.info(po.toString());
			}
			if (po.get_TableName().equals(MRequisition.Table_Name))
			{
				MRequisition requisition = (MRequisition)po;
				if(!callTransiction(requisition))
					return "Call Transiction failed "+m_ConfProcMsg;//invoke callTransiction
				log.info(po.toString());
			}
			if (po.get_TableName().equals(MInvoice.Table_Name))
			{
				MInvoice invoice = (MInvoice)po;
				if(!callTransiction(invoice))
					return "Call Transiction failed "+m_ConfProcMsg;//invoke callTransiction
				log.info(po.toString());
			}
		}

		return null;
	}	//	docValidate
	

	
	
	/**
	 *	User Login.
	 *	Called when preferences are set
	 *	@param AD_Org_ID org
	 *	@param AD_Role_ID role
	 *	@param AD_User_ID user
	 *	@return error message or null
	 */
	public String login (int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		log.info("AD_User_ID=" + AD_User_ID);
		return null;
	}	//	login

	
	/**
	 *	Get Client to be monitored
	 *	@return AD_Client_ID client
	 */
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}	//	getAD_Client_ID

	
	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("ConfProcValidator[DocumentBeforeComplete");
		sb.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	callTransiction
	 *	@param  MOrder order
	 */
	private boolean callTransiction (MOrder order)
	{
					log.info("Doc.getStatus: "+order.getDocStatus());
					CPMOrder cpmorder = new CPMOrder(order.getCtx(), order.get_ID(), order.get_TrxName());
					CPMOrderLine[] orderLine= cpmorder.getLines();
					CP_ProcessFlow pf = null;
					log.info("callTransiction getDocStatus: "+order.getDocStatus());
	
//					if (!complete){
						complete = true;
						m_ConfProcMsg="";
						for (CPMOrderLine lines:orderLine ){
							pf = lines.getProcessFlow(cpmorder.get_ID(), lines.get_ID(), cpmorder.getC_DocType_ID());
						if (pf!=null){
						 //if (lines.getCP_ProcessFlow_ID()!=0){
						 //CP_ProcessFlow procFlow= new CP_ProcessFlow(order.getCtx(), lines.getCP_ProcessFlow_ID(), order.get_TrxName());
						 CP_ConfProc confProc= new CP_ConfProc(order.getCtx(), pf.getCP_ConfProc_ID(), order.get_TrxName());
						 MWarehouse war= new MWarehouse(order.getCtx(),order.getM_Warehouse_ID() , order.get_TrxName());
						 MLocator loc=war.getDefaultLocator();
						 //System.out.println("call_Transition param: "+lines.getCP_ProcessFlow_ID() + lines.getM_Product_ID() +loc.getM_Locator_ID()+ order.get_Table_ID());
						 int retTrans[]= confProc.call_Transition(pf.get_ID(), lines.getM_Product_ID(), loc.getM_Locator_ID(), -1, order.get_Table_ID(), order.isSOTrx());
						 log.info("next state TO" +retTrans[0]);
						 log.info("next sub-state TO" +retTrans[1]);
						  if (retTrans[0]==1){
							 order.setDocStatus(order.STATUS_Invalid);
							 m_ConfProcMsg = "Order not complete: "+confProc.m_processMsg;
							 order.save();
							 order.reverseCorrectIt();
							 log.severe("Order not complete: check condition in business process configurator");
							 return false;
							
						      }
							  //writing new record in it.informaticagestionale.confproc.process history
							  CP_ProcessHistory procHist= new CP_ProcessHistory(pf);
							  if (retTrans[0]!=1){
								  procHist.setCP_ProcessFlowState_ID(retTrans[0]);
								  procHist.setM_Product_ID(lines.getM_Product_ID());
								  procHist.setDocumentNo(order.getDocumentNo());
								  procHist.setC_DocType_ID(order.getC_DocType_ID());
								  procHist.setDescription("order completed: "+order.getDocumentNo()+" -  " +confProc.m_processMsg);
								  //update for subprocess 
								  if (retTrans[1]!=1){
									  procHist.setCP_ProcessSubFlowState_ID(retTrans[1]);
									  procHist.save();
								  }
								  procHist.save();
							  }
	
						 } //lines.getCP_ProcessFlow_ID()!=0
						 else return true;
					
						}// end for
					
//					}// if !Complete
//					else return false;
	return true;
	}	//	callTransiction(order)
	
	private boolean callTransiction(MMovement movement){
			CPMMovement cpmmovement = new CPMMovement(movement.getCtx(), movement.get_ID(), movement.get_TrxName());
			CPMMovementLine[] movementLine= cpmmovement.getLines();
			log.info("cpmmovement.getLines(): " +cpmmovement.getLines());

//			if (!complete){
			 m_ConfProcMsg="";
				complete = true;
				for (CPMMovementLine lines:movementLine ){
					log.info("movement lines in call-transiction considerations: " +lines);
				if (lines.getCP_ProcessFlow_ID()!=0){
				CP_ProcessFlow procFlow= new CP_ProcessFlow(movement.getCtx(), lines.getCP_ProcessFlow_ID(), movement.get_TrxName());
				CP_ConfProc confProc= new CP_ConfProc(movement.getCtx(), procFlow.getCP_ConfProc_ID(), movement.get_TrxName());
				int retTrans[]= confProc.call_Transition(lines.getCP_ProcessFlow_ID(), lines.getM_Product_ID(), lines.getM_Locator_ID(), lines.getM_LocatorTo_ID(), movement.get_Table_ID(), false);
				log.info("next state TO" +retTrans[0]);
				log.info("next sub-state TO" +retTrans[1]);
				 if (retTrans[0]==1){
					 movement.setDocStatus(movement.STATUS_Invalid);
					 m_ConfProcMsg = "Movement not complete: "+confProc.m_processMsg;
					 movement.save();
					 movement.rejectIt();
					 log.severe("Movement not complete: check condition in business it.informaticagestionale.confproc.process configurator");
					 return false;							
				 }
					  //writing new record in it.informaticagestionale.confproc.process history
					  CP_ProcessHistory procHist= new CP_ProcessHistory(procFlow);
					  if (retTrans[0]!=1){
						  procHist.setCP_ProcessFlowState_ID(retTrans[0]);
						  procHist.setM_Product_ID(lines.getM_Product_ID());
						  procHist.setDocumentNo(movement.getDocumentNo());
						  procHist.setC_DocType_ID(movement.getC_DocType_ID());
						  procHist.setDescription("movement completed: "+movement.getDocumentNo()+" - CA: " +confProc.m_processMsg);
						  //update for subprocess 
						  if (retTrans[1]!=1){
							  procHist.setCP_ProcessSubFlowState_ID(retTrans[1]);
							  procHist.save();
						  }
						  procHist.save();
					  }

			    } //lines.getCP_ProcessFlow_ID()!=0
				else return true;
			
			}// end for
			
//			}// if DocAction=CO
//			else return false;

		return true;
	} //callTransiction(movement)
	
	
	
	private boolean callTransiction(MInOut inout){	
		CPMInOut cpminout = new CPMInOut(inout.getCtx(), inout.get_ID(), inout.get_TrxName());
		CPMInOutLine[] inoutLine= cpminout.getLines();
		System.out.println("callTransiction getDocStatus: "+inout.getDocStatus());
		
//		if (!complete){
		 m_ConfProcMsg = "";
			complete = true;
			for (CPMInOutLine lines:inoutLine ){
			if (lines.getCP_ProcessFlow_ID()!=0){
			CP_ProcessFlow procFlow= new CP_ProcessFlow(inout.getCtx(), lines.getCP_ProcessFlow_ID(), inout.get_TrxName());
			CP_ConfProc confProc= new CP_ConfProc(inout.getCtx(), procFlow.getCP_ConfProc_ID(), inout.get_TrxName());
			MWarehouse war= new MWarehouse(inout.getCtx(),inout.getM_Warehouse_ID() , inout.get_TrxName());
			MLocator loc=war.getDefaultLocator();
			int retTrans[]= confProc.call_Transition(lines.getCP_ProcessFlow_ID(), lines.getM_Product_ID(), loc.getM_Locator_ID(), -1, inout.get_Table_ID(), inout.isSOTrx());
			log.info("next state TO" +retTrans[0]);
			log.info("next sub-state TO" +retTrans[1]);
			 if (retTrans[0]==1){
				 inout.setDocStatus(inout.STATUS_Invalid);
				 m_ConfProcMsg = "InOut not complete: "+confProc.m_processMsg;
				 inout.save();
				 inout.reverseCorrectIt();
				 log.severe("Shipment/Receive not complete: check condition in business it.informaticagestionale.confproc.process configurator");							 
				 return false;							
			 }
				  //writing new record in it.informaticagestionale.confproc.process history
				  CP_ProcessHistory procHist= new CP_ProcessHistory(procFlow);
				  if (retTrans[0]!=1){
					  procHist.setCP_ProcessFlowState_ID(retTrans[0]);
					  procHist.setM_Product_ID(lines.getM_Product_ID());
					  procHist.setDocumentNo(inout.getDocumentNo());
					  procHist.setC_DocType_ID(inout.getC_DocType_ID());
					  procHist.setDescription("shipment/receive completed: "+inout.getDocumentNo()+" - CA: " +confProc.m_processMsg);
					  //update for subprocess 
					  if (retTrans[1]!=1){
						  procHist.setCP_ProcessSubFlowState_ID(retTrans[1]);
						  procHist.save();
					  }
					  procHist.save();
				  }

		    } //lines.getCP_ProcessFlow_ID()!=0
			else return true;
		
		}// end for
		
//		}// if DocAction=CO
//		else return false;
		return true;
	}// end it.informaticagestionale.confproc.process inout

	
	private boolean callTransiction(MInvoice invoice){
	//Process_Invoice		
		CPMInvoice cpminvoice = new CPMInvoice(invoice.getCtx(), invoice.get_ID(), invoice.get_TrxName());
		CPMInvoiceLine[] invoiceLine= cpminvoice.getLines();
		System.out.println("callTransiction getDocStatus: "+invoice.getDocStatus());
		
//		if ( !complete){
		 m_ConfProcMsg = "";
		complete = true;
			for (CPMInvoiceLine lines:invoiceLine ){
			if (lines.getCP_ProcessFlow_ID()!=0){
			CP_ProcessFlow procFlow= new CP_ProcessFlow(invoice.getCtx(), lines.getCP_ProcessFlow_ID(), invoice.get_TrxName());
			CP_ConfProc confProc= new CP_ConfProc(invoice.getCtx(), procFlow.getCP_ConfProc_ID(), invoice.get_TrxName());
			int retTrans[]= confProc.call_Transition(lines.getCP_ProcessFlow_ID(), lines.getM_Product_ID(), -1, -1, invoice.get_Table_ID(), invoice.isSOTrx());
			log.info("next state TO" +retTrans[0]);
			log.info("next sub-state TO" +retTrans[1]);
			 if (retTrans[0]==1){
				 invoice.setDocStatus(invoice.STATUS_Invalid);
				 m_ConfProcMsg = "invoice not complete: "+confProc.m_processMsg;
				 invoice.save();
				 invoice.reverseCorrectIt();
				 log.severe("Invoice not complete: check condition in business it.informaticagestionale.confproc.process configurator");							 
				 return false;							
			 }
				  //writing new record in it.informaticagestionale.confproc.process history
				  CP_ProcessHistory procHist= new CP_ProcessHistory(procFlow);
				  if (retTrans[0]!=1){
					  procHist.setCP_ProcessFlowState_ID(retTrans[0]);
					  procHist.setM_Product_ID(lines.getM_Product_ID());
					  procHist.setDocumentNo(invoice.getDocumentNo());
					  procHist.setC_DocType_ID(invoice.getC_DocType_ID());
					  procHist.setDescription("invoice completed: "+invoice.getDocumentNo()+" - CA: " +confProc.m_processMsg);
					  //update for subprocess 
					  if (retTrans[1]!=1){
						  procHist.setCP_ProcessSubFlowState_ID(retTrans[1]);
						  procHist.save();
					  }
					  procHist.save();
				  }

		    } //lines.getCP_ProcessFlow_ID()!=0
			else return true;
		
		}// end for
		
//		}// if DocAction=CO
//		else return false;
		return true;
	}// end it.informaticagestionale.confproc.process invoice
	
	
	private boolean callTransiction(MRequisition requisition){ 
		
		CPMRequisition cpmrequisition = new CPMRequisition(requisition.getCtx(), requisition.get_ID(), requisition.get_TrxName());
		CPMRequisitionLine[] requisitionLine= cpmrequisition.getLines();
//		System.out.println("callTransiction getDocStatus: "+requisition.getDocStatus());
//		System.out.println("callTransiction requisitionLine: "+requisitionLine.length);
//		System.out.println("complete: "+complete);
		
//		if (!complete){
		 m_ConfProcMsg = "";
		complete=true;
			for (CPMRequisitionLine lines:requisitionLine ){
			if (lines.getCP_ProcessFlow_ID()!=0){
			CP_ProcessFlow procFlow= new CP_ProcessFlow(requisition.getCtx(), lines.getCP_ProcessFlow_ID(), requisition.get_TrxName());
			CP_ConfProc confProc= new CP_ConfProc(requisition.getCtx(), procFlow.getCP_ConfProc_ID(), requisition.get_TrxName());
			MWarehouse war= new MWarehouse(requisition.getCtx(),requisition.getM_Warehouse_ID() , requisition.get_TrxName());
			MLocator loc=war.getDefaultLocator();
			int retTrans[]= confProc.call_Transition(lines.getCP_ProcessFlow_ID(), lines.getM_Product_ID(), loc.getM_Locator_ID(), -1, requisition.get_Table_ID(), false);
			log.info("next state TO" +retTrans[0]);
			log.info("next sub-state TO" +retTrans[1]);
			 if (retTrans[0]==1){
				 requisition.setDocStatus(requisition.STATUS_Invalid);
				 m_ConfProcMsg = "Requisition not complete: "+confProc.m_processMsg;
				 requisition.save();
				 requisition.reverseCorrectIt();
				 log.severe("Requisition not complete: check condition in business it.informaticagestionale.confproc.process configurator");							 
				return false;							
			 }
				  //writing new record in it.informaticagestionale.confproc.process history
				  CP_ProcessHistory procHist= new CP_ProcessHistory(procFlow);
				  if (retTrans[0]!=1){
					  procHist.setCP_ProcessFlowState_ID(retTrans[0]);
					  procHist.setM_Product_ID(lines.getM_Product_ID());
					  procHist.setDocumentNo(requisition.getDocumentNo());
					  procHist.setC_DocType_ID(requisition.getC_DocType_ID());
					  procHist.setDescription("requisition completed: "+requisition.getDocumentNo()+" - CA: " +confProc.m_processMsg);
					  //update for subprocess 
					  if (retTrans[1]!=1){
						  procHist.setCP_ProcessSubFlowState_ID(retTrans[1]);
						  procHist.save();
					  }
					  procHist.save();
				  }

		    } //lines.getCP_ProcessFlow_ID()!=0
			else return true;
		
		}// end for
		
//		}// if DocAction=CO
//		else return false;
		return true;
	}// end it.informaticagestionale.confproc.process requisition
	
}	//	ConfProcValidator
