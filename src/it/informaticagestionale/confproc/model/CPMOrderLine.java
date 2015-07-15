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

import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

import org.compiere.util.*;
import org.compiere.model.*;

/**
 *  Order Line Model.
 *  @author Jorg Janke
 *  @version $Id: MOrderLine.java,v 1.6 2006/10/02 05:18:39 jjanke Exp $
 *  @version CPMOrderLine.java gcafasso integration orderLine to BProcess Flow
 */
public class CPMOrderLine extends MOrderLine
{
	
	/**	Logger	*/
	private static CLogger s_log = CLogger.getCLogger (CPMOrderLine.class);
	
	/**************************************************************************
	 *  Default Constructor
	 *  @param ctx context
	 *  @param  C_OrderLine_ID  order line to load
	 *  @param trxName trx name
	 */
	public CPMOrderLine (Properties ctx, int C_OrderLine_ID, String trxName)
	{
		super (ctx, C_OrderLine_ID, trxName);
		if (C_OrderLine_ID == 0)
		{
		//	setC_Order_ID (0);
		//	setLine (0);
		//	setM_Warehouse_ID (0);	// @M_Warehouse_ID@
		//	setC_BPartner_ID(0);
		//	setC_BPartner_Location_ID (0);	// @C_BPartner_Location_ID@
		//	setC_Currency_ID (0);	// @C_Currency_ID@
		//	setDateOrdered (new Timestamp(System.currentTimeMillis()));	// @DateOrdered@
			//
		//	setC_Tax_ID (0);
		//	setC_UOM_ID (0);
			//
			setFreightAmt (Env.ZERO);
			setLineNetAmt (Env.ZERO);
			//
			setPriceEntered(Env.ZERO);
			setPriceActual (Env.ZERO);
			setPriceLimit (Env.ZERO);
			setPriceList (Env.ZERO);
			//
			setM_AttributeSetInstance_ID(0);
			//
			setQtyEntered (Env.ZERO);
			setQtyOrdered (Env.ZERO);	// 1
			setQtyDelivered (Env.ZERO);
			setQtyInvoiced (Env.ZERO);
			setQtyReserved (Env.ZERO);
			//
			setIsDescription (false);	// N
			setProcessed (false);
			setLine (0);
		}
	}	//	MOrderLine
	
	/**
	 *  Parent Constructor.
	 		ol.setM_Product_ID(wbl.getM_Product_ID());
			ol.setQtyOrdered(wbl.getQuantity());
			ol.setPrice();
			ol.setPriceActual(wbl.getPrice());
			ol.setTax();
			ol.save();
	 *  @param  order parent order
	 */
	public CPMOrderLine (CPMOrder order)
	{
		this (order.getCtx(), 0, order.get_TrxName());
		if (order.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");
		setC_Order_ID (order.getC_Order_ID());	//	parent
		setOrder(order);
	}	//	MOrderLine

	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 *  @param trxName transaction
	 */
	public CPMOrderLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MOrderLine

	//
	private boolean			m_IsSOTrx = true;
	/**	Product					*/
	private MProduct 		m_product = null;
	/** Parent					*/
	private CPMOrder			m_parent = null;
	
	/**************************************************************************
	 * 	String Representation
	 * 	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MOrderLine[")
			.append(get_ID()).append(",Line=").append(getLine())
			.append(",Ordered=").append(getQtyOrdered())
			.append(",Delivered=").append(getQtyDelivered())
			.append(",Invoiced=").append(getQtyInvoiced())
			.append(",Reserved=").append(getQtyReserved())
			.append(", LineNet=").append(getLineNetAmt())
			.append ("]");
		return sb.toString ();
	}	//	toString
	
	
	/*
	 * get processFlow from documents
	 */
	public CP_ProcessFlow getProcessFlow (int idDoc, int idDocLine, int C_DocType_ID){
		
		StringBuffer sql = new StringBuffer("SELECT * FROM CP_ProcessFlow WHERE C_DocType_ID=? and idDoc=? and idDocLine=? ");
		PreparedStatement pstmt = null;
		CP_ProcessFlow pf = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			pstmt.setInt(1, C_DocType_ID);
			pstmt.setInt(2, idDoc);
			pstmt.setInt(3, idDocLine);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				pf = new CP_ProcessFlow(getCtx(), rs, get_TrxName());
			}
 		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null; 
			pstmt = null;
		}
		//
		return pf;
		
	}
	
}	//	MOrderLine
