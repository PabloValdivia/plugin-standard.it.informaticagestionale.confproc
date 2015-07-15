package it.informaticagestionale.confproc.custom;

import java.math.BigDecimal; 
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;
import java.util.*;
import java.text.SimpleDateFormat;

import it.informaticagestionale.confproc.model.*;
import org.compiere.model.MAttributeSetInstance;
//import org.compiere.model.MStorage;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.util.AdempiereSystemError;
import org.compiere.util.DB;

/**
 *	CP_ConfProcException: customizable class for configuring exception in business it.informaticagestionale.confproc.process flow
 *	sample of configuration
 *  @author Giorgio Cafasso & Corrado Chiodi
 *  @version $Id: CP_ProcessFlow.java,v 1.2 2008/04/21 00:51:05 gcafasso cchiodi Exp $
 */

public class CP_ConfProcException extends X_CP_ConProcException {
	
	//chiamata che passa di stato 
	private CP_ProcessFlow processo;
	//chiamata candidata a passare di stato
	private CP_ProcessFlow processoCandidato;
	//prodotto Processeo 
	private int prodotto;
	//prodotto del processo candidato a passare  di stato
	private int prodottoCandidato;
	//id processo candidato
	private int idCandidato;
	//stringa SQL di ricerca del processo candidato
	private String sql;
	//Stato attuale
	private int statoAttuale;
	//Conf Proc
	private CP_ConfProc confProc;

	
	/**
	 * Costruttore forward ordinata laboratorio
	 */
	public CP_ConfProcException(Properties ctx, int CP_ConProcException_ID, String trxName){
		super (ctx, CP_ConProcException_ID, trxName);
		 processo=null;
		 processoCandidato=null;
		 confProc=null;
		 prodotto=0;
		 prodottoCandidato=0;
		 idCandidato=0;
		 sql="";
		 statoAttuale=0;

	}
	
		
	/**
	 * processa -SAMPLE
	 * in funzione di un id chiamata che va a stock (when a it.informaticagestionale.confproc.process go in state "stock") 
	 * passa nello stato successivo la prima chiamata che necessita di quel prodotto.
	 * go in the next state the first it.informaticagestionale.confproc.process that have that product
	 * @param idChiamata
	 * @return 0 ok 1 ko
	 * @throws Exception
	 */
	public int processa(int idProcesso) throws Exception{
		
		processo=new CP_ProcessFlow(getCtx(), idProcesso, get_TrxName());
		prodotto=processo.getM_Product_ID();
		statoAttuale=processo.getCP_ProcessFlowState_ID();
		CP_ProcessSubFlow sottoProcesso[]=processo.getLines();
		confProc=new CP_ConfProc(getCtx(), processo.getCP_ConfProc_ID(), get_TrxName());
		int statoSottoProcesso=getCP_ProcessSubFlowState_ID();
		int statoSottoProcessoFuturo=getCP_ProcessSubFlowStateTO_ID();
		if (getCP_ConProcException_ID()==1000000){	
			if (statoSottoProcesso!=0)
				return 1;
		}
		
		
		//Inserimento prodotto in magazzino clienti -other SAMPLE
		if (getCP_ConProcException_ID()==1000001){
			log.info("Eccezione==1000001");
			int productId=processo.getM_Product_ID();
			//int AttributeSetInstanceID=0;
			MAttributeSetInstance instance= new MAttributeSetInstance(getCtx(),0,1000000,get_TrxName());
			instance.setSerNo(processo.getCUSTOMTEXT2()); //CustomText 2 seriale
			instance.setDescription(processo.getCUSTOMTEXT2());
			System.out.println("instance.getSerNo()" +instance.getSerNo());
			instance.save();
			BigDecimal numero;
//			MStorage storage= MStorage.getCreate(getCtx(), 1000004, productId, instance.get_ID(), get_TrxName()) ; // 1000004 magazzino Clienti
//			numero=new BigDecimal(1);
//			storage.setQtyOnHand(storage.getQtyOnHand().add(numero));
//			storage.save();	
			
		}

		//assegna codice univoco parlante per i processi - other SAMPLE		
		if (getCP_ConProcException_ID()==1000002){	
			 String LOGTEK="";
			 String CODAMM=confProc.getCustomText1();
			 GregorianCalendar cal= new GregorianCalendar();
			 SimpleDateFormat sdf=new SimpleDateFormat("yy");
			 String Year=sdf.format(cal.getTime());//cal.get(cal.YEAR);
				LOGTEK= Year+ CODAMM + idProcesso;
					if(processo.getCUSTOMTEXT1().compareTo(LOGTEK)!=0){
					processo.setCUSTOMTEXT1(LOGTEK);
					processo.save();
					}
					else
						return 1;
			}
		
		//insert new business it.informaticagestionale.confproc.process in automatic way
		if (getCP_ConProcException_ID()==1000003){	
			log.info("Eccezione==1000003");
			CP_ProcessFlow CPProcessFlow = null;
			String LOGTEK="";
			String CODAMM=confProc.getCustomText1();
			int CPConfID = 0; 
			CPConfID = Integer.valueOf(this.getDescription()).intValue();
			log.info("CPConfID "+CPConfID);
		
		
		 GregorianCalendar cal= new GregorianCalendar();
		 SimpleDateFormat sdf=new SimpleDateFormat("yy");
		 String Year=sdf.format(cal.getTime());//cal.get(cal.YEAR);
		
		
		 
		 //new business it.informaticagestionale.confproc.process
			CPProcessFlow = new CP_ProcessFlow(getCtx(), 0, get_TrxName());
			log.info("CPProcessFlow "+CPProcessFlow.toString());
			CPProcessFlow.setCP_ConfProc_ID(CPConfID);
			CPProcessFlow.setDescription(this.getName() + " : " + this.getDescription());
			CPProcessFlow.setHelp(processo.getCUSTOMTEXT1()+ " <-- riferimento chiamata originale");
			log.info("CPProcessFlow.getDescription "+CPProcessFlow.getDescription());
			CPProcessFlow.setC_BPartner_ID(processo.getC_BPartner_ID());
			log.info("CPProcessFlow.getDescription "+CPProcessFlow.getDescription());
			CPProcessFlow.setCP_ProcessFlowState_ID(this.getCP_ProcessFlowStateTO_ID());
			CPProcessFlow.setM_Product_ID(prodotto);
			CPProcessFlow.setQty(processo.getQty());
			CPProcessFlow.setAD_User_ID(processo.getAD_User_ID());
			log.info("CPProcessFlow.setAD_User_ID "+processo.getAD_User_ID());
			CPProcessFlow.setCUSTOMTEXT2(processo.getCUSTOMTEXT2());
			log.info("CPProcessFlow.getCUSTOMTEXT2 "+ CPProcessFlow.getCUSTOMTEXT2());
			CPProcessFlow.save();
			
		 //LOG creation
		    LOGTEK= Year+ CODAMM + CPProcessFlow.getCP_ProcessFlow_ID();
		    log.info("LOG nuova call: "+LOGTEK);
				
			CPProcessFlow.setCUSTOMTEXT1(LOGTEK);
			CPProcessFlow.save();
					
			//	Prepare Save
			if (!CPProcessFlow.save()){
				throw new AdempiereSystemError("Cannot save new Business Process");
				}

		} //new automatic business it.informaticagestionale.confproc.process
		
		
		
		return 0 ;
	}
}

