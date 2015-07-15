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
 * 
 *  @author Jorg Janke
 *  @version $Id: InOutLine.java,v 1.6 2006/10/02 05:18:39 jjanke Exp $
 *  @version CPMInOutLine.java gcafasso integration inoutLine to BProcess Flow
 */
public class CPMInOutLine extends MInOutLine
{
	
	/**	Logger	*/
	private static CLogger s_log = CLogger.getCLogger (CPMInOutLine.class);
	
	/**************************************************************************
	 *  Default Constructor
	 *  @param ctx context
	 *  @param  C_OrderLine_ID  order line to load
	 *  @param trxName trx name
	 */
	public CPMInOutLine (Properties ctx, int M_InOutLine_ID, String trxName)
	{
		super (ctx, M_InOutLine_ID, trxName);
		if (M_InOutLine_ID == 0)
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
			//
			//
			setIsDescription (false);	// N
			setProcessed (false);
			setLine (0);
		}
	}	//	MInOutLine
	
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
	public CPMInOutLine (CPMInOut inout)
	{
		this (inout.getCtx(), 0, inout.get_TrxName());
		if (inout.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");
		setM_InOut_ID (inout.getM_InOut_ID());	//	parent
		//setInOut(inout);
	}	//	MOrderLine

	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 *  @param trxName transaction
	 */
	public CPMInOutLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MOrderLine

	//
	private boolean			m_IsSOTrx = true;
	/**	Product					*/
	private MProduct 		m_product = null;
	/** Parent					*/
	private CPMInOut			m_parent = null;
	
	/**************************************************************************
	 * 	String Representation
	 * 	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MOrderLine[")
			.append(get_ID()).append(",Line=").append(getLine())
			.append ("]");
		return sb.toString ();
	}	//	toString

	/**
	 * 	Set CP_ProcessFlow_ID
	 *	@param CP_ProcessFlow_ID processFlow
	 */
	public void setCP_ProcessFlow_ID(int CP_ProcessFlow_ID)
	{
		if (CP_ProcessFlow_ID < 1)
			 throw new IllegalArgumentException ("CP_ProcessFlow_ID is mandatory.");
		set_ValueNoCheck ("CP_ProcessFlow_ID", Integer.valueOf(CP_ProcessFlow_ID));
	}

	/**
	 * Get CP_ProcessFlow_ID
	 *	@param CP_ProcessFlow_ID processFlow*/
	public int getCP_ProcessFlow_ID () 
	{
		Integer ii = (Integer)get_Value("CP_ProcessFlow_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
}	//	MInOutLine
