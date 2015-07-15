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
