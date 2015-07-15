package it.informaticagestionale.confproc.model;

import java.sql.ResultSet;
import java.util.Properties;

/**
 *	Process Sub Flow state Model
 *	
 *  @author Giorgio Cafasso & Corrado Chiodi
 *  @version $Id: CP_ProcessSubFlowState.java,v 1.2 2008/04/21 00:51:05 gcafasso cchiodi Exp $
 */
public class CP_ProcessSubFlowState extends X_CP_ProcessSubFlowState {
	
	public CP_ProcessSubFlowState (Properties ctx, int CP_ProcessSubFlowState_ID, String trxName)
	{
		super (ctx, CP_ProcessSubFlowState_ID, trxName);
		if (CP_ProcessSubFlowState_ID == 0){
		}
	}	//	CP_ProcessSubFlowState

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public CP_ProcessSubFlowState (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	CP_ProcessSubFlowState

}
