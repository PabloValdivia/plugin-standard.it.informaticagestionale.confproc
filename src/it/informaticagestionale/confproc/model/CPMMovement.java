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
 * @author Jorg Janke
 *  @version $Id: MMovement.java,v 1.5 2006/10/06 00:42:24 jjanke Exp $
 *  @version CPMMovement.java gcafasso integration movement to BProcess Flow
 */
public class CPMMovement extends MMovement
{
	/**************************************************************************
	 *  Default Constructor
	 *  @param ctx context
	 *  @param  C_Order_ID    order to load, (0 create new order)
	 *  @param trxName trx name
	 */
	public CPMMovement(Properties ctx, int M_Movement_ID, String trxName)
	{
		super (ctx, M_Movement_ID, trxName);
		//  New
		if (M_Movement_ID == 0)
		{
			setDocStatus(DOCSTATUS_Drafted);
			setDocAction (DOCACTION_Prepare);
			//
			//
			//
			super.setProcessed(false);
			setProcessing(false);
			setPosted(false);


		}
	}	//	M_Movement

	/**************************************************************************
	 *  Project Constructor
	 *  @param  project Project to create Order from
	 *  @param IsSOTrx sales order
	 * 	@param	DocSubTypeSO if SO DocType Target (default DocSubTypeSO_OnCredit)
	 */
	public CPMMovement (MProject project, boolean IsSOTrx, String DocSubTypeSO)
	{
		this (project.getCtx(), 0, project.get_TrxName());
		setAD_Client_ID(project.getAD_Client_ID());
		setAD_Org_ID(project.getAD_Org_ID());
		setC_Campaign_ID(project.getC_Campaign_ID());
		//
		setC_Project_ID(project.getC_Project_ID());
		setDescription(project.getName());
		Timestamp ts = project.getDateContract();
		if (ts != null)
		ts = project.getDateFinish();
		//
	}	//	MOrder

	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 *  @param trxName transaction
	 */
	public CPMMovement (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MOrder

	/**	Movement Lines					*/
	private CPMMovementLine[] 	m_lines = null;
	/** Force Creation of shipmentReceive		*/
	private boolean			m_forceCreation = false;
	/**************************************************************************
	 * 	Get Lines of Movement
	 * 	@param whereClause where clause or null (starting with AND)
	 * 	@param orderClause order clause
	 * 	@return lines
	 */
	public CPMMovementLine[] getLines (String whereClause, String orderClause)
	{
		ArrayList<MMovementLine> list = new ArrayList<MMovementLine> ();
		StringBuffer sql = new StringBuffer("SELECT * FROM M_MovementLine WHERE M_Movement_ID=? ");
		if (whereClause != null)
			sql.append(whereClause);
		if (orderClause != null)
			sql.append(" ").append(orderClause);
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			pstmt.setInt(1, getM_Movement_ID());
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				CPMMovementLine ol = new CPMMovementLine(getCtx(), rs, get_TrxName());
				//ol.setHeaderInfo (this);
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
		CPMMovementLine[] lines = new CPMMovementLine[list.size ()];
		list.toArray (lines);
		return lines;
	}	//	getLines

	/**
	 * 	Get Lines of Movement
	 * 	@param requery requery
	 * 	@param orderBy optional order by column
	 * 	@return lines
	 */
	public CPMMovementLine[] getLines (boolean requery, String orderBy)
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
	 * 	Get Lines of Movement.
	 * 	(useb by web store)
	 * 	@return lines
	 */
	public CPMMovementLine[] getLines()
	{
		return getLines(false, null);
	}	//	getLines
	

	
}	//	MOrder
