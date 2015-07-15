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
 *  @author Jorg Janke
 *  @version $Id: MRequisitionLine.java,v 1.6 2006/10/02 05:18:39 jjanke Exp $
 *  @version CPMRequisitionLinet.java gcafasso integration requisitionline to BProcess Flow
 */
public class CPMRequisitionLine extends MRequisitionLine
{
	
	/**	Logger	*/
	private static CLogger s_log = CLogger.getCLogger (CPMRequisitionLine.class);
	
	/**************************************************************************
	 *  Default Constructor
	 *  @param ctx context
	 *  @param  C_OrderLine_ID  order line to load
	 *  @param trxName trx name
	 */
	public CPMRequisitionLine (Properties ctx, int M_RequisitionLine_ID, String trxName)
	{
		super (ctx, M_RequisitionLine_ID, trxName);
		if (M_RequisitionLine_ID == 0)
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
			setLine (0);
		}
	}	//	MRequisitionLine
	
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
	public CPMRequisitionLine (CPMRequisition Requisition)
	{
		this (Requisition.getCtx(), 0, Requisition.get_TrxName());
		if (Requisition.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");
		setM_Requisition_ID (Requisition.getM_Requisition_ID());	//	parent
		//setRequisition(Requisition);
	}	//	MOrderLine

	/**
	 *  Load Constructor
	 *  @param ctx context
	 *  @param rs result set record
	 *  @param trxName transaction
	 */
	public CPMRequisitionLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MOrderLine

	//
	private boolean			m_IsSOTrx = true;
	/**	Product					*/
	private MProduct 		m_product = null;
	/** Parent					*/
	private CPMRequisition			m_parent = null;
	
	/**************************************************************************
	 * 	String Representation
	 * 	@return info
	 */
	public String toString ()
	{
		StringBuffer sb = new StringBuffer ("MRequisitionLine[")
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

	/**
	 * 	Set Product
	 *	@param product product
	 */
	public void setProduct (MProduct product)
	{
		m_product = product;
		if (m_product != null)
		{
			setM_Product_ID(m_product.getM_Product_ID());
		}
		else
		{
			setM_Product_ID(0);
			set_ValueNoCheck ("C_UOM_ID", null);
		}
		setM_AttributeSetInstance_ID(0);
	}	//	setProduct

	/**
	 * 	Get Product
	 *	@return product or null
	 */
	public MProduct getProduct()
	{
		if (m_product == null && getM_Product_ID() != 0)
			m_product =  MProduct.get (getCtx(), getM_Product_ID());
		return m_product;
	}	//	getProduct
	
}	//	MRequisitionLine
