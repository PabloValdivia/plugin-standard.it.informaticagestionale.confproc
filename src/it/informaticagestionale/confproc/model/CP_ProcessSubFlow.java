package it.informaticagestionale.confproc.model;
import java.io.*;
import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

import org.compiere.model.MOrder;
import org.compiere.model.MProduct;
import org.compiere.process.*;
import org.compiere.util.*;

/**
 *	Process Sub Flow Model
 *	
 *  @author Giorgio Cafasso & Corrado Chiodi
 *  @version $Id: CP_ProcessSubFlow.java,v 1.2 2008/04/21 00:51:05 gcafasso cchiodi Exp $
 */
public class CP_ProcessSubFlow extends X_CP_ProcessSubFlow {



	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param CP_ProcessFlow_ID id
	 */
	public CP_ProcessSubFlow (Properties ctx, int CP_ProcessSubFlow_ID, String trxName)
	{
		super (ctx, CP_ProcessSubFlow_ID, trxName);
		if (CP_ProcessSubFlow_ID == 0)
		{

		

		}
	}	//	MRequisition

	
	
	
	/**
	 *  Parent Constructor.
	 *  @param  processFlow parent 
	 */
	public CP_ProcessSubFlow (CP_ProcessFlow processFlow)
	{
		this (processFlow.getCtx(), 0, processFlow.get_TrxName());
		if (processFlow.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");
		setCP_ProcessFlow_ID (processFlow.getCP_ProcessFlow_ID());	//	parent
		setCPProcessFlow(processFlow);
	}	//	AdConfProcLine




	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public CP_ProcessSubFlow (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRequisition
	
	/** Parent					*/
	private CP_ProcessFlow			m_parent = null;	
	
	/**
	 * 	Set Header Info
	 *	@param CP_ProcessFlow ProcessFlow
	 */
	public void setHeaderInfo (CP_ProcessFlow ProcessFlow)
	{
		m_parent = ProcessFlow;

	}	//	setHeaderInfo
	
	/**
	 * 	Get Parent
	 *	@return parent
	 */
	public CP_ProcessFlow getParent()
	{
		if (m_parent == null)
			m_parent = new CP_ProcessFlow(getCtx(), getCP_ProcessFlow_ID(), get_TrxName());
		return m_parent;
	}	//	getParent
	
	/**
	 * 	Add to Description
	 *	@param description text
	 */
	public void addDescription (String description)
	{
		String desc = getDescription();
		if (desc == null)
			setDescription(description);
		else
			setDescription(desc + " | " + description);
	}	//	addDescription
	/**
	 * 	Get Description Text.
	 * 	For jsp access (vs. isDescription)
	 *	@return description
	 */
	public String getDescriptionText()
	{
		return super.getDescription();
	}	//	getDescriptionText




	/**
	 * 	Set Defaults from processFlow.
	 * 	Does not set Parent !!
	 * 	@param CP_ProcessFlow processFlow
	 */
	public void setCPProcessFlow (CP_ProcessFlow processFlow)
	{
		setClientOrg(processFlow);
		//
		setHeaderInfo(processFlow);	//	sets m_order
	}	//	setOrder




	/**
	 * 	Set Product
	 *	@param product product
	 */
	public void setProduct (MProduct product)
	{
		MProduct m_product = product;
		if (m_product != null)
		{
			setM_Product_ID(m_product.getM_Product_ID());
		}
		else
		{
			setM_Product_ID(0);
			set_ValueNoCheck ("C_UOM_ID", null);
		}
		m_product.setM_AttributeSetInstance_ID(0);
	}	//	setProduct




	/**
	 * 	Get Product
	 *	@return product or null
	 */
	public MProduct getProduct()
	{
		MProduct m_product = null;
		if (m_product == null && getM_Product_ID() != 0)
			m_product =  MProduct.get (getCtx(), getM_Product_ID());
		return m_product;
	}	//	getProduct
	
	
	
	
}	//	CP_ProcessFlow

