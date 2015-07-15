package it.informaticagestionale.confproc.model;
import java.io.*;
import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;


import org.compiere.model.MMovementLine;
import org.compiere.model.MLocator;
import org.compiere.process.*;
import org.compiere.util.*;

import it.informaticagestionale.confproc.custom.*;

/**
 *	Process Flow Model
 *	
 *  @author Giorgio Cafasso & Corrado Chiodi
 *  @version $Id: CP_ProcessFlow.java,v 1.2 2008/04/21 00:51:05 gcafasso cchiodi Exp $
 */
public class CP_ConfProc extends X_CP_ConfProc {



	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param CP_ProcessFlow_ID id
	 */
	public CP_ConfProc (Properties ctx, int CP_ConfProc_ID, String trxName)
	{
		super (ctx, CP_ConfProc_ID, trxName);
		statoOK =0;
		if (CP_ConfProc_ID == 0)
		{
		//	setDocumentNo (null);
		//	setAD_User_ID (0);
		//	setM_PriceList_ID (0);
		//	setM_Warehouse_ID(0);

			setTotalLines (Env.ZERO);

		}
	}	//	CP_ConfProc

	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public CP_ConfProc (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRequisition
	
	/** Lines						*/
	private CP_ConfProcLine[]		m_lines = null;
	
	/** counter								 */
	private int ContTrans=0;
	/**	Process Message 			*/
	public String		m_processMsg = null;
	private int statoOK;
	
	public CP_ConfProcLine[] getLines (){
		return getLines(true,0);
	}
    //optimized select lines
	public CP_ConfProcLine[] getLines (int DocTable){
		return getLines(true,DocTable);
	}
	
	/**************************************************************************
	 * 	Get Lines of conf Proc
	 * 	@param whereClause where clause or null (starting with AND)
	 * 	@return lines
	 */
	public CP_ConfProcLine[] getLines (String whereClause, String orderClause)
	{
		ArrayList<CP_ConfProcLine> list = new ArrayList<CP_ConfProcLine> ();
		StringBuffer sql = new StringBuffer("SELECT * FROM CP_ConfProcLine WHERE CP_ConfProc_ID=?  AND IsActive='Y' ");
		if (whereClause != null)
			sql.append(whereClause);
		if (orderClause != null)
			sql.append(" ").append(orderClause);
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			pstmt.setInt(1, getCP_ConfProc_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				CP_ConfProcLine ol = new CP_ConfProcLine(getCtx(), rs, get_TrxName());
			//	ol.setHeaderInfo (this);
				list.add(ol);
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close ();
			}
			catch (Exception e)
			{}
			pstmt = null;
		}
		//
		CP_ConfProcLine[] lines = new CP_ConfProcLine[list.size ()];
		list.toArray (lines);
		return lines;
	}	//	getLines

	
	
	public CP_ConfProcLine[] getLines (boolean requery, String orderBy)
	{
		if (m_lines != null && !requery)
			return m_lines;
		//
		String orderClause = "ORDER BY ";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;
		else
			orderClause += "Line";
		m_lines = getLines(null, orderClause);
		return m_lines;
	}	//	getLines
	

	
	
	/**
	 * 	Get Lines
	 *	@return array of lines
	 */
	public CP_ConfProcLine[] getLines (boolean requery,int DocTable)
	{
	if (m_lines != null && !requery)
		return m_lines;
	//
	ArrayList<CP_ConfProcLine> list = new ArrayList<CP_ConfProcLine>();
	String sql = "SELECT * FROM CP_ConfProcLine WHERE CP_ConfProc_ID=? ";
	if (DocTable!=0)
		sql += (" AND AD_Table_ID =? ");
	PreparedStatement pstmt = null;
	try
	{
		pstmt = DB.prepareStatement (sql, get_TrxName());
		pstmt.setInt (1, getCP_ConfProc_ID());
		if (DocTable!=0)
			pstmt.setInt (2, DocTable);
		ResultSet rs = pstmt.executeQuery ();
		while (rs.next ())
		{
			list.add (new CP_ConfProcLine (getCtx(), rs, get_TrxName()));
		}
		rs.close ();
		pstmt.close ();
		pstmt = null;
	} catch (Exception e)
	{
		log.log(Level.SEVERE, "getLines", e);
	}
	try
	{
		if (pstmt != null)
			pstmt.close ();
		pstmt = null;
	} catch (Exception e)
	{
		pstmt = null;
	}
	
	m_lines = new CP_ConfProcLine[list.size ()];
	list.toArray (m_lines);
	return m_lines;
	}//getLines

	/**
		 * call_Transition esegue la ricerca dello stato successivo, in funzione del documento o dell'azione che l'ha chiamata.
		 * @param CP_ProcessFlow_ID
		 * @param M_Product_ID
		 * @param storageFromID
		 * @param storageToID
		 * @param AD_Table_ID
		 * @return statoTo ok 1 error
		 */
		public int[] call_Transition(int CP_ProcessFlow_ID,int M_Product_ID,int locatorFromID, int locatorToID, int AD_Table_ID, boolean Trx){
			boolean valuate=true;
			m_processMsg= "";
			ContTrans++;
			log.log(Level.INFO, "number calls Call_Transiction: " +ContTrans+" times");
			
			int magazParID=0;
			int magazParToID=1;
			int magazTo=0;
	
			//virtualizzazione dei magazzini passati come parametri dai documenti 
			MLocator locPar= new MLocator (getCtx(),locatorFromID,get_TrxName());
			magazParID=locPar.getM_Warehouse_ID();
			if (locatorToID!=-1){
				MLocator locParTo= new MLocator (getCtx(),locatorToID,get_TrxName());
				magazParToID=locParTo.getM_Warehouse_ID();
			}

		
			//Case for CP_Process
			CP_ProcessFlow process=new CP_ProcessFlow(getCtx(),CP_ProcessFlow_ID,get_TrxName());
			CP_ConfProc configuratore=new CP_ConfProc(getCtx(),process.getCP_ConfProc_ID(),get_TrxName());
			int statoProcTO=1;
			int statoSubProcTO=1;
			
			// creo l'oggetto per le linee
			CP_ConfProcLine[] ConfiguratoreLine=configuratore.getLines(AD_Table_ID);
			
			//for(CP_ConfProcLine line:ConfiguratoreLine){
			for(int i=0;i< ConfiguratoreLine.length;i++){
	
				log.info("configurator line: "+i); //+ConfiguratoreLine[i].getLine().toString());
				valuate = true;
//				System.out.println("valuate " + valuate);
				String Join=ConfiguratoreLine[i].getJoinCondition();
				//System.out.println("Join "+Join);
				  if (Join==null)
					   valuate=ConfiguratoreLine[i].evaluateCondition();
				  else{ 
					  if (Join.indexOf("CP_ProcessFlow")==-1)
						  	valuate=ConfiguratoreLine[i].evaluateCondition();
				  		else
				  			valuate=ConfiguratoreLine[i].evaluateCondition("AND CP_ProcessFlow.CP_ProcessFlow_ID=" +process.get_ID());
					  }
				log.log(Level.INFO,"row confProc found");

					if (ConfiguratoreLine[i].isSOTrx()==Trx) //if trx in configurator is the same as that of completed document  	

					  if(ConfiguratoreLine[i].getM_WareHouse_ID() == magazParID | locatorFromID==-1) //if warehouse in configurator is the same as that of warehouse in completed document or warehouse is not present

						if(ConfiguratoreLine[i].getM_WareHouseTO_ID() == magazParToID | locatorToID==-1) //if warehouse dest in configurator is the same as that of warehouse dest in completed document or warehouse dest is not present

							if(ConfiguratoreLine[i].getAD_Table_ID() == AD_Table_ID) //if document table in configurator is the same as that of table completed document 
													
								if (ConfiguratoreLine[i].getNoSubProcess()){ //not sub-processFlow
									
								  if(process.getM_Product_ID() == M_Product_ID){
									  
									  if (process.getCP_ProcessFlowState_ID()==ConfiguratoreLine[i].getCP_ProcessFlowState_ID() & valuate){
										  log.log(Level.INFO,"NoSUBProcess: conditions for the state transaction met");
										  process.setCP_ProcessFlowState_ID(ConfiguratoreLine[i].getCP_ProcessFlowStateTO_ID());
										  process.save();
										  statoProcTO=ConfiguratoreLine[i].getCP_ProcessFlowStateTO_ID();
										  m_processMsg=ConfiguratoreLine[i].getDescription();
									  	}
									  	else {
									  		log.warning("Status in it.informaticagestionale.confproc.process configurator is different from Status in document transiction ");
									  		if (ConfiguratoreLine[i].getErrorMsg()!=null)
									  			m_processMsg= "No same status in it.informaticagestionale.confproc.process configurator or " + ConfiguratoreLine[i].getErrorMsg();
									  		else
									  			m_processMsg= "No same status in it.informaticagestionale.confproc.process configurator";
									  		statoProcTO=1;
									  	}
											if(ConfiguratoreLine[i].isCodeException()){
												CP_ConfProcException candidata= new CP_ConfProcException(getCtx(),ConfiguratoreLine[i].getCP_ConProcException_ID(),get_TrxName());
												try{
													if(candidata.processa(process.get_ID())==0)
														log.log(Level.INFO,"code exception successfull");
												}
												catch(Exception e){
													log.log(Level.SEVERE,"code exception error " +e.toString());
												
												}
											}//fine eccezione codice
								  	}//fine confronto prodotto
								  else {
									  System.out.println("Product in it.informaticagestionale.confproc.process configurator is different from product in document transaction");
								  	  m_processMsg= "No same product in it.informaticagestionale.confproc.process configurator";
									  log.warning("Product in it.informaticagestionale.confproc.process configurator is different from product in document transiction");
								  }
							    }
			  
								else{  // caso in cui ci sono sottoprocessi
									
									
									CP_ProcessSubFlow[] Processlines = process.getLines();
									
								for (CP_ProcessSubFlow lines:Processlines){
										if (Join==null)
											 valuate=ConfiguratoreLine[i].evaluateCondition();
										else {
											if (Join.indexOf("CP_ProcessSubFlow")==-1)
											   valuate=ConfiguratoreLine[i].evaluateCondition();
										  	else 
										  		valuate=ConfiguratoreLine[i].evaluateCondition("AND CP_ProcessFlow.CP_ProcessFlow_ID= " +process.get_ID()+" AND CP_ProcessSubFlow.CP_processSubFlow_ID= "+lines.get_ID());
											}
										
										
										//if flag NoSubProcess is disable and ForAllProcess is disable
									    if (!ConfiguratoreLine[i].getNoSubProcess() && !ConfiguratoreLine[i].getForAllSubProcess()){
										  if (lines.getM_Product_ID()==M_Product_ID){
											if (process.getCP_ProcessFlowState_ID()==ConfiguratoreLine[i].getCP_ProcessFlowState_ID() & lines.getCP_ProcessSubFlowState_ID()==ConfiguratoreLine[i].getCP_ProcessSubFlowState_ID() & valuate){ 
												log.log(Level.INFO,"Call_TRANSICTION- ok sub-it.informaticagestionale.confproc.process - NO for all sub-it.informaticagestionale.confproc.process: conditions for the state transaction met");
											process.setCP_ProcessFlowState_ID(ConfiguratoreLine[i].getCP_ProcessFlowStateTO_ID());
											lines.setCP_ProcessSubFlowState_ID(ConfiguratoreLine[i].getCP_ProcessSubFlowStateTO_ID());
											process.save();
											lines.save();
											statoProcTO=ConfiguratoreLine[i].getCP_ProcessFlowStateTO_ID();
											statoSubProcTO=ConfiguratoreLine[i].getCP_ProcessSubFlowStateTO_ID();
											m_processMsg=ConfiguratoreLine[i].getDescription();
											  }
											  else {
												  log.warning("No same it.informaticagestionale.confproc.process and subprocess status ");
											  	  if (ConfiguratoreLine[i].getErrorMsg()!=null)
												    m_processMsg="No same it.informaticagestionale.confproc.process and subprocess status in it.informaticagestionale.confproc.process configurator or " +ConfiguratoreLine[i].getErrorMsg();
											  	  else 
											  		m_processMsg="No same it.informaticagestionale.confproc.process and subprocess status in it.informaticagestionale.confproc.process configurator ";
											  	  statoProcTO=1;
												  statoSubProcTO=1;
											  }
											log.log(Level.INFO,"CallTransiction: " +statoProcTO);
											if(ConfiguratoreLine[i].isCodeException()){
												CP_ConfProcException candidata= new CP_ConfProcException(getCtx(),ConfiguratoreLine[i].getCP_ConProcException_ID(),get_TrxName());
												try{
													if(candidata.processa(process.get_ID())==0)
														log.log(Level.INFO,"Eccezione al codice andato a buon fine");
												}
												catch(Exception e){
													log.log(Level.SEVERE,"it.informaticagestionale.confproc.process flow exception error " +e.toString());
													
												}
											}
										  }//fine controllo prodotto 
										  else{
											  System.out.println("Product in it.informaticagestionale.confproc.process configurator is different from product in document transiction");
										  	  m_processMsg= "No same product in document transiction";
											  log.warning("Call_TRANSICTION caso NoSubProcess=false e ForAllProcess=false: Il prodotto del sotto-processo : "+lines.getLine()+" non corrisponde");
										  		}
										  }
									    //se il flag NoSubProcess è disattivato e ForAllProcess è attivato
									    if (!ConfiguratoreLine[i].getNoSubProcess() && ConfiguratoreLine[i].getForAllSubProcess()){
											  if (lines.getM_Product_ID()==M_Product_ID){
													if (process.getCP_ProcessFlowState_ID()==ConfiguratoreLine[i].getCP_ProcessFlowState_ID() & lines.getCP_ProcessSubFlowState_ID()==ConfiguratoreLine[i].getCP_ProcessSubFlowState_ID() & valuate){ 
														log.log(Level.INFO,"conditions for the state transaction met: ok subprocess, ok for all sub-it.informaticagestionale.confproc.process");
													lines.setCP_ProcessSubFlowState_ID(ConfiguratoreLine[i].getCP_ProcessSubFlowStateTO_ID());
													lines.save();
													statoProcTO=ConfiguratoreLine[i].getCP_ProcessFlowStateTO_ID();
													statoSubProcTO=ConfiguratoreLine[i].getCP_ProcessSubFlowStateTO_ID();
													statoOK++;
													log.log(Level.INFO,"num sub-it.informaticagestionale.confproc.process: "+statoOK);
													log.info("num sub-it.informaticagestionale.confproc.process: "+statoOK);
													log.log(Level.INFO,"num lines: "+Processlines.length);
													log.info("num lines: "+Processlines.length);
													//tutti i processi hanno lo stesso stato
													if (statoOK==Processlines.length ){
														log.log(Level.INFO,"Call_TRANSICTION ForAllProcess ok, it.informaticagestionale.confproc.process state can be updated: ");
														process.setCP_ProcessFlowState_ID(ConfiguratoreLine[i].getCP_ProcessFlowStateTO_ID());
														statoProcTO=ConfiguratoreLine[i].getCP_ProcessFlowStateTO_ID();
														statoSubProcTO=ConfiguratoreLine[i].getCP_ProcessSubFlowStateTO_ID();
														process.save();
														m_processMsg=ConfiguratoreLine[i].getDescription();	
														}// end statoOK==Processlines.length
													}
													else{
														  log.warning("No same it.informaticagestionale.confproc.process and subprocess status ");
														  if (ConfiguratoreLine[i].getErrorMsg()!=null)
														    m_processMsg="No same it.informaticagestionale.confproc.process and subprocess status in it.informaticagestionale.confproc.process configurator or " + ConfiguratoreLine[i].getErrorMsg();
														  else
															  m_processMsg="No same it.informaticagestionale.confproc.process and subprocess status in it.informaticagestionale.confproc.process configurator  ";
														  statoProcTO=1;
														  statoSubProcTO=1;
													}
													log.log(Level.INFO,"IsCodeException: " +ConfiguratoreLine[i].isCodeException());
													log.log(Level.INFO,"Id ConfLine: " +ConfiguratoreLine[i].get_ID());
														if(ConfiguratoreLine[i].isCodeException()){
															CP_ConfProcException candidata= new CP_ConfProcException(getCtx(),ConfiguratoreLine[i].getCP_ConProcException_ID(),get_TrxName());
															try{
																if(candidata.processa(process.get_ID())==0)
																	log.log(Level.INFO,"code exception successful");
															}
															catch(Exception e){
																log.log(Level.SEVERE,"it.informaticagestionale.confproc.process flow exception error " +e.toString());
																
															}
														}
											
													
												  }
												  else {
													  m_processMsg=" subprocess product: "+Processlines.length+" different to product in it.informaticagestionale.confproc.process configurator";
													  log.warning("product of sub-it.informaticagestionale.confproc.process num: "+Processlines.length+" not corresponding as that of document");
												  }
									    }
									}
								}
								
		
							else{
								m_processMsg= "No same table in it.informaticagestionale.confproc.process configurator";
								log.warning("Ad_table not the same: get: "+ConfiguratoreLine[i].getAD_Table_ID()+ " paste: "+AD_Table_ID);
							}
						else {
							m_processMsg= "different warehouse TO - cofigurator: "+ConfiguratoreLine[i].getM_WareHouseTO_ID()+ " and document transaction: "+magazParToID;
							log.warning("Locator TO different or not from standard transaction; IDWarehouse from configurator: "+ConfiguratoreLine[i].getM_WareHouseTO_ID()+ " paste Warehouse from document: "+magazParToID);
							statoProcTO=1;
						}
					else {
						m_processMsg= "different warehouse FROM - cofigurator: "+ConfiguratoreLine[i].getM_WareHouseTO_ID()+ " and document transaction: "+magazParToID;
						log.warning("Locator FROM different as that of location or warehouse; get configurator warehouse: "+ConfiguratoreLine[i].getM_WareHouse_ID()+ " paste Warehouse from document: "+magazParID);
						statoProcTO=1;
					}
				  else{
						m_processMsg= "TRX cofigurator != TRX document transaction:  " +Trx+" != " +ConfiguratoreLine[i].isSOTrx();
					  log.warning("TRX is not same as that of document: " +Trx+" != " +ConfiguratoreLine[i].isSOTrx());
					  statoProcTO=1;
				  }
			if(statoProcTO!=1)	break;
			} //fine ciclo FOR per il configuratore

			
		log.log(Level.INFO,"CallTransiction statoProcTo: "+statoProcTO);
		log.log(Level.INFO,"CallTransiction statoPartiTo: "+statoSubProcTO);
		int out[]= new int[2];
		out[0]=statoProcTO;
		out[1]=statoSubProcTO;
		return out;

		}//CallTransiction
	
		
}	//	CP_ProcessFlow

