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

import it.informaticagestionale.confproc.model.X_CP_ProcessFlowState;
import org.compiere.util.Env;

/**
 *	Process Flow Model
 *	
 *  @author Giorgio Cafasso & Corrado Chiodi
 *  @version $Id: CP_ProcessFlowState.java,v 1.2 2008/04/21 00:51:05 gcafasso cchiodi Exp $
 */
public class CP_ProcessFlowState extends X_CP_ProcessFlowState {
	
	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param CP_ProcessFlow_ID id
	 */
	public CP_ProcessFlowState (Properties ctx, int CP_ProcessFlowState_ID, String trxName)
	{
		super (ctx, CP_ProcessFlowState_ID, trxName);
		if (CP_ProcessFlowState_ID == 0)
		{
		//	setDocumentNo (null);
		//	setAD_User_ID (0);
		//	setM_PriceList_ID (0);
		//	setM_Warehouse_ID(0);

			

		}
	}	//	CP_ProcessFlowState

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public CP_ProcessFlowState (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	CP_ProcessFlowState

}
