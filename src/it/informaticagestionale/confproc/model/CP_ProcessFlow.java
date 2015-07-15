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
import java.io.*;
import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

import org.compiere.model.MOrderLine;
import org.compiere.process.*;
import org.compiere.util.*;

/**
 *	Process Flow Model
 *	
 *  @author Giorgio Cafasso & Corrado Chiodi
 *  @version $Id: CP_ProcessFlow.java,v 1.2 2008/04/21 00:51:05 gcafasso cchiodi Exp $
 */
public class CP_ProcessFlow extends X_CP_ProcessFlow {



	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param CP_ProcessFlow_ID id
	 */
	public CP_ProcessFlow (Properties ctx, int CP_ProcessFlow_ID, String trxName)
	{
		super (ctx, CP_ProcessFlow_ID, trxName);
		if (CP_ProcessFlow_ID == 0)
		{
		//	setDocumentNo (null);
		//	setAD_User_ID (0);
		//	setM_PriceList_ID (0);
		//	setM_Warehouse_ID(0);

			setTotalLines (Env.ZERO);

		}
	}	//	MRequisition

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public CP_ProcessFlow (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRequisition
	
	/** Lines						*/
	private CP_ProcessSubFlow[]		m_lines = null;
	
	/** Lines History*/
	private CP_ProcessHistory[] 	m_linesH= null;
	

	/**
	 * 	String Representation
	 *	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("CP_ProcessSubFlow[");
		sb.append(get_ID()).append("-").append(getCP_ProcessFlow_ID())
			.append(",Status=").append(getCP_ProcessFlowState_ID())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	
	/**
	 * 	Create PDF
	 *	@return File or null
	 */
	public File createPDF ()
	{
		try
		{
			File temp = File.createTempFile(get_TableName()+get_ID()+"_", ".pdf");
			return createPDF (temp);
		}
		catch (Exception e)
		{
			log.severe("Could not create PDF - " + e.getMessage());
		}
		return null;
	}	//	getPDF

	/**
	 * 	Create PDF file
	 *	@param file output file
	 *	@return file if success
	 */
	public File createPDF (File file)
	{
	//	ReportEngine re = ReportEngine.get (getCtx(), ReportEngine.INVOICE, getC_Invoice_ID());
	//	if (re == null)
			return null;
	//	return re.getPDF(file);
	}	//	createPDF


	/**
	 * 	Before Save
	 *	@param newRecord new
	 *	@return true
	 */
	protected boolean beforeSave (boolean newRecord)
	{

		return true;
	}	//	beforeSave
	
	

	
	
	/**
	 * 	Get Document Owner
	 *	@return AD_User_ID
	 */
	public int getDoc_User_ID()
	{
		return getAD_User_ID();
	}
	


	/**
	 * 	Get Document Approval Amount
	 *	@return amount
	 */
	public BigDecimal getApprovalAmt()
	{
		return getTotalLines();
	}
	
	
	
	public CP_ProcessSubFlow[] getLines()
	{
		return getLines(true);
	}	//	getLines
	
	/**
	 * get Lines 
	 * @return CP_ProcessSubFlow
	 */
	public CP_ProcessSubFlow[] getLines (boolean requery)
	{
	if (m_lines != null && !requery)
		return m_lines;
	//
	ArrayList<CP_ProcessSubFlow> list = new ArrayList<CP_ProcessSubFlow>();
	String sql = "SELECT * FROM CP_ProcessSubFlow WHERE CP_ProcessFlow_ID=?";
	PreparedStatement pstmt = null;
	try
	{
		pstmt = DB.prepareStatement (sql, get_TrxName());
		pstmt.setInt (1, getCP_ProcessFlow_ID());
		ResultSet rs = pstmt.executeQuery ();
		while (rs.next ())
		{
			list.add (new CP_ProcessSubFlow (getCtx(), rs, get_TrxName()));
		}
		rs.close ();
		pstmt.close ();
		pstmt = null;
	} catch (Exception e)
	{
		log.log(Level.SEVERE, "getLines", e);
	}
	try
	{
		if (pstmt != null)
			pstmt.close ();
		pstmt = null;
	} catch (Exception e)
	{
		pstmt = null;
	}
	
	m_lines = new CP_ProcessSubFlow[list.size ()];
	list.toArray (m_lines);
	return m_lines;
	}//getLines
	
	
	public CP_ProcessHistory[] getLinesHist(){
		return getLinesHist(true);
	}
	/**
	 * get Lines 
	 * @return CP_ProcessSubFlow
	 */
	public CP_ProcessHistory[] getLinesHist (boolean requery)
	{
	if (m_linesH != null && !requery)
		return m_linesH;
	//
	ArrayList<CP_ProcessHistory> list = new ArrayList<CP_ProcessHistory>();
	String sql = "SELECT * FROM CP_ProcessHistory WHERE CP_PROCESSFLOW_ID=? ORDER BY Line";
	PreparedStatement pstmt = null;
	try
	{
		pstmt = DB.prepareStatement (sql, get_TrxName());
		pstmt.setInt (1, getCP_ProcessFlow_ID());
		ResultSet rs = pstmt.executeQuery ();
		while (rs.next ())
		{
			list.add (new CP_ProcessHistory (getCtx(), rs, get_TrxName()));
		}
		rs.close ();
		pstmt.close ();
		pstmt = null;
	} catch (Exception e)
	{
		log.log(Level.SEVERE, "getLines", e);
	}
	try
	{
		if (pstmt != null)
			pstmt.close ();
		pstmt = null;
	} catch (Exception e)
	{
		pstmt = null;
	}
	
	m_linesH = new CP_ProcessHistory[list.size ()];
	list.toArray (m_linesH);
	return m_linesH;
	}//getLinesHist

	/**
		 * 	Import Constructor
		 *	@param impP it.informaticagestionale.confproc.process
		 */
		public CP_ProcessFlow (X_I_Process impP)
		{
			this (impP.getCtx(), 0, impP.get_TrxName());
			setClientOrg(impP);
			setUpdatedBy(impP.getUpdatedBy());
			setCUSTOMTEXT2(impP.getCustomText2());
			setCUSTOMTEXT1(impP.getCustomText1());
			setCUSTOMDATE1(impP.getCustomDate1());
			setCP_ProcessFlowState_ID(impP.getCP_ProcessFlowState_ID());
			setC_BPartner_ID(impP.getC_BPartner_ID());
			setM_Product_ID(impP.getM_Product_ID());
			setAD_User_ID(1000000);
			setDescription(impP.getDescription()); //guasto
//			setProcessing(false);
			setCP_ConfProc_ID(impP.getCP_ConfProc_ID());
		}	//	MProcess
	
	
}	//	CP_ProcessFlow

