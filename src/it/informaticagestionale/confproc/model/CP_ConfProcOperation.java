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