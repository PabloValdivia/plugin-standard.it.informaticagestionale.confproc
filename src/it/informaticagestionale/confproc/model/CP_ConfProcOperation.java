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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.util.DB;
import org.compiere.util.Env;

/**
 *	Process Flow Model
 *	
 *  @author Giorgio Cafasso & Corrado Chiodi
 *  @version $Id: CP_ProcessFlow.java,v 1.2 2008/04/21 00:51:05 gcafasso cchiodi Exp $
 */
public class CP_ConfProcOperation extends X_CP_ConfProcOperation {




/**
 * 	Standard Constructor
 *	@param ctx context
 *	@param CP_ConfProcOperation id
 */
public CP_ConfProcOperation (Properties ctx, int CP_ConfProcOperation_ID, String trxName)
{
	super (ctx, CP_ConfProcOperation_ID, trxName);
	if (CP_ConfProcOperation_ID == 0){
	//	setDocumentNo (null);
	//	setAD_User_ID (0);
	//	setM_PriceList_ID (0);
	//	setM_Warehouse_ID(0);
	}
}	//	CP_ConfProcOperation_ID

/**
 * 	Load Constructor
 *	@param ctx context
 *	@param rs result set
 */
public CP_ConfProcOperation (Properties ctx, ResultSet rs, String trxName)
{
	super(ctx, rs, trxName);
}	//	CP_ConfProcOperation_ID






}