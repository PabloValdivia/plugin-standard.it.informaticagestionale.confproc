/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                        *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package it.informaticagestionale.confproc.model;

import java.io.File;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.print.ReportEngine;
import org.compiere.process.DocAction;
import org.compiere.process.DocumentEngine;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.model.*;

/**
 *  @author Jorg Janke
 *  @version $Id: MInvoice.java,v 1.5 2006/10/06 00:42:24 jjanke Exp $
 *  @version CPMInvoice.java gcafasso integration invoice to BProcess Flow
 */
public class CPMInvoice extends MInvoice
{
	/**************************************************************************
	 *  Default Constructor
	 *  @param ctx context
	 *  @param  C_Order_ID    order to load, (0 create new order)
	 *  @param trxName trx name
	 */
	public CPMInvoice(Properties ctx, int C_Invoice_ID, String trxName)
	{
		super (ctx, C_Invoice_ID, trxName);
		//  New
		if (C_Invoice_ID == 0)
		{
			setDocStatus(DOCSTATUS_Drafted);
			setDocAction (DOCACTION_Prepare);
			//
			//
			//
			super.setProcessed(false);
			setProcessing(false);
			setPosted(false);

			setDateAcct (new Timestamp(System.currentTimeMillis()));
			setDateOrdered (new Timestamp(System.currentTimeMillis()));
			setChargeAmt (Env.ZERO);
		}
	}	//	M_Invoice

	/**************************************************************************
	 *  Project Constructor
	 *  @param  project Project to create Order from
	 *  @param IsSOTrx sales order
	 * 	@param	DocSubTypeSO if SO DocType Target (default DocSubTypeSO_OnCredit)
	 */
	public CPMInvoice (MProject project, boolean IsSOTrx, String DocSubTypeSO)
	{
		this (project.getCtx(), 0, project.get_TrxName());
		setAD_Client_ID(project.getAD_Client_ID());
		setAD_Org_ID(project.getAD_Org_ID());
		setC_Campaign_ID(project.getC_Campaign_ID());
		setSalesRep_ID(project.getSalesRep_ID());
		//
		setC_Project_ID(project.getC_Project_ID());
		setDescription(project.getName());
		Timestamp ts = project.getDateContract();
		if (ts != null)
			setDateOrdered (ts);
		ts = project.getDateFinish();
		//
		setC_BPartner_ID(project.getC_BPartner_ID());
		setC_BPartner_Location_ID(project.getC_BPartner_Location_ID());
		setAD_User_ID(project.getAD_User_ID());
		//
		setIsSOTrx(IsSOTrx);
	}	//	MOrder

	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 *  @param trxName transaction
	 */
	public CPMInvoice (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MOrder

	/**	Order Lines					*/
	private CPMInvoiceLine[] 	m_lines = null;
	/** Force Creation of shipmentReceive		*/
	private boolean			m_forceCreation = false;
	
	/**************************************************************************
	 * 	Get Lines of Shipment/Receive
	 * 	@param whereClause where clause or null (starting with AND)
	 * 	@param orderClause order clause
	 * 	@return lines
	 */
	public CPMInvoiceLine[] getLines (String whereClause, String orderClause)
	{
		ArrayList<MInvoiceLine> list = new ArrayList<MInvoiceLine> ();
		StringBuffer sql = new StringBuffer("SELECT * FROM M_InvoiceLine WHERE C_Invoice_ID=? ");
		if (whereClause != null)
			sql.append(whereClause);
		if (orderClause != null)
			sql.append(" ").append(orderClause);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			pstmt.setInt(1, getC_Invoice_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				CPMInvoiceLine ol = new CPMInvoiceLine(getCtx(), rs, get_TrxName());
			//	ol.setHeaderInfo (this);
				list.add(ol);
			}
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; pstmt = null;
		}
		//
		CPMInvoiceLine[] lines = new CPMInvoiceLine[list.size ()];
		list.toArray (lines);
		return lines;
	}	//	getLines

	/**
	 * 	Get Lines of Order
	 * 	@param requery requery
	 * 	@param orderBy optional order by column
	 * 	@return lines
	 */
	public CPMInvoiceLine[] getLines (boolean requery, String orderBy)
	{
		if (m_lines != null && !requery) {
			set_TrxName(m_lines, get_TrxName());
			return m_lines;
		}
		//
		String orderClause = "ORDER BY ";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;
		else
			orderClause += "Line";
		m_lines = getLines(null, orderClause);
		return m_lines;
	}	//	getLines

	/**
	 * 	Get Lines of Shipment/Receipt.
	 * 	(useb by web store)
	 * 	@return lines
	 */
	public CPMInvoiceLine[] getLines()
	{
		return getLines(false, null);
	}	//	getLines
	

	
}	//	MOrder
