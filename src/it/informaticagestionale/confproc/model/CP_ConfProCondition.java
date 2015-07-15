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

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.MMovement;
import org.compiere.model.MTable;
import org.compiere.model.MColumn;
import org.compiere.util.Env;

/**
 *	Process Flow Model
 *	
 *  @author Giorgio Cafasso & Corrado Chiodi
 *  @version $Id: CP_ProcessFlow.java,v 1.2 2008/04/21 00:51:05 gcafasso cchiodi Exp $
 */
public class CP_ConfProCondition extends X_CP_ConfProCondition {

	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param CP_ConfProCondition_ID id
	 */
	public CP_ConfProCondition (Properties ctx, int CP_ConfProCondition_ID, String trxName)
	{
		super (ctx, CP_ConfProCondition_ID, trxName);
		if (CP_ConfProCondition_ID == 0)
		{
		//	setDocumentNo (null);
		//	setAD_User_ID (0);
		//	setM_PriceList_ID (0);
		//	setM_Warehouse_ID(0);

			

		}
	}	//	CP_ConfProCondition

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public CP_ConfProCondition (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRequisition
	
	
	/**
	 *  Parent Constructor.
	 *  @param  order parent order
	 */
	public CP_ConfProCondition (CP_ConfProcLine confProcLine)
	{
		this (confProcLine.getCtx(), 0, confProcLine.get_TrxName());
		if (confProcLine.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");
		setCP_ConfProcLine_ID (confProcLine.getCP_ConfProcLine_ID());	//	parent
		setCP_ConfProcLine(confProcLine);
	}	//	AdConfProcLine

	/**
	 * 	Set Defaults from confProcLine.
	 * 	Does not set Parent !!
	 * 	@param confProc confProc
	 */
	public void setCP_ConfProcLine (CP_ConfProcLine confProcLine)
	{
		setClientOrg(confProcLine);
		//
	}	//	setOrder
	
	
	
	/** Parent							*/
	private CP_ConfProcLine m_parent = null;
	
	/**
	 * get Parent
	 * @return Parent Movement
	 */
	public CP_ConfProcLine getParent() 
	{
		if (m_parent == null)
			m_parent = new CP_ConfProcLine (getCtx(), getCP_ConfProcLine_ID(), get_TrxName());
		return m_parent;
	}	//	getParent
	/**
	 * getTableName
	 * @return table name in the condition
	 */
	public String getTableName(){
		MTable tabella=new MTable(getCtx(), this.getAD_Table_ID(), get_TrxName());
		return tabella.getTableName();
		
	}//getTableName
	/**
	 * getTableJoinName
	 * @return table name in join
	 */
	public String getTableJoinName(){
		MTable tabella=new MTable(getCtx(), this.getAD_TableJoin_ID(), get_TrxName());
		return tabella.getTableName();
	}//getTableJoinName
	
	/**
	 * getColumnName
	 * @return column name in condition
	 */
	public String getColumnName(){
		MColumn colonna=new MColumn(getCtx(), this.getAD_Column_ID(), get_TrxName());
		return colonna.getColumnName();
		
	}//getColumnName
	
	/**
	 * getColumnJoinName
	 * @return column name in join condition
	 */
	public String getColumnJoinName(){
		MColumn colonna=new MColumn(getCtx(), this.getAD_ColumnJoin_ID(), get_TrxName());
		return colonna.getColumnName();
	}//getColumnJoinName
	
	public String getOperationName(){
		CP_ConfProcOperation operazione= new CP_ConfProcOperation(getCtx(), this.getCP_ConfProcOperation_ID(), get_TrxName());
		return operazione.getName();
	}//getOperationName

	public int getValNum() {
		// TODO Auto-generated method stub
		return super.getValNum();
	}

	public int getAD_Table_ID() {
		// TODO Auto-generated method stub
		return super.getAD_Table_ID();
	}
	
}
