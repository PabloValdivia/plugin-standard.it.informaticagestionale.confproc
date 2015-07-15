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
package it.informaticagestionale.confproc.process;

import java.awt.geom.IllegalPathStateException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;

import it.informaticagestionale.confproc.model.*;

import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.model.*;

import it.informaticagestionale.confproc.custom.CP_ConfProcException;

/**
 * Process Flow Manager
 * 
 * manage state it.informaticagestionale.confproc.process for a single it.informaticagestionale.confproc.process operation or for multiple processes definited by it.informaticagestionale.confproc.process
 * flow state or it.informaticagestionale.confproc.process subFlow state
 * @author Corrado Chiodi, Giorgio Cafasso
 * @version $Id: CP_StatusManager.java,v 1.2
 * 
 */
public class CP_StatusManager extends SvrProcess {
	private int CP_ProcessFlow_ID=0;
	private int CP_ProcessFlowState_ID=0;
	private int CP_ProcessSubFlowState_ID=0;

	@Override
	protected String doIt() throws Exception {
		String sqlTotale=null;
		log.info("Processo=" + CP_ProcessFlow_ID);
		if (CP_ProcessFlow_ID == 0 &  CP_ProcessFlowState_ID==0 & CP_ProcessSubFlowState_ID==0) {
			throw new IllegalPathStateException("You need to select a it.informaticagestionale.confproc.process");
		} else {
			// String sqlTotale = manageProcess (new CP_ProcessFlow(getCtx(),
			// CP_ProcessFlow_ID, get_TrxName()));
			if (CP_ProcessFlow_ID!=0 ){
				sqlTotale = manageProcessNEW((new CP_ProcessFlow(getCtx(),CP_ProcessFlow_ID, get_TrxName())));
			}else{
			//multiple line it.informaticagestionale.confproc.process manager
			//import by subprocess flow state
			if (CP_ProcessFlowState_ID!=0){
			//	Specific
			StringBuffer sql = new StringBuffer("SELECT r.CP_ProcessFlow_ID FROM CP_ProcessFlow r ");
			     if (CP_ProcessSubFlowState_ID!=0)
				  sql.append(" , CP_ProcessSubFlow rl ");
				sql.append("WHERE r.ISACTIVE='Y' ");
				sql.append(" AND r.CP_ProcessFlowState_ID=? ");
			//	Movement Header
				if (CP_ProcessSubFlowState_ID!=0)
			sql.append(" AND rl.CP_ProcessSubFlowState_ID=? ");
			

			//
			sql.append(" ORDER BY ");

			sql.append("r.CP_ProcessFlow_ID");//priority
			
			PreparedStatement pstmt = null;
			try
			{
				pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
				int index = 1;
				if (CP_ProcessFlowState_ID != 0)
					pstmt.setInt (index++, CP_ProcessFlowState_ID);
				if (CP_ProcessSubFlowState_ID != 0)
					pstmt.setInt (index++, CP_ProcessSubFlowState_ID);

				ResultSet rs = pstmt.executeQuery ();
				while (rs.next ())
				{
					log.info("CP_ProcessFlow_ID multiple: " +rs.getInt(1));
					sqlTotale = manageProcessNEW((new CP_ProcessFlow(getCtx(),rs.getInt(1), get_TrxName())));
				}
				rs.close ();
				pstmt.close ();
				pstmt = null;
			}
			
			catch (Exception e)
			{
				log.log (Level.SEVERE, sql.toString(), e);
			}
			try
			{
				if (pstmt != null)
					pstmt.close ();
				pstmt = null;
			}
			catch (Exception e)
			{
				pstmt = null;
			}
			
			}
			//end multiple line it.informaticagestionale.confproc.process manager
			
			
			}			
			return "Sql: " + sqlTotale;

		}
	}

	@Override
	protected void prepare() {
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++) {
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("CP_ProcessFlow_ID")) {
				CP_ProcessFlow_ID = ((BigDecimal) para[i].getParameter()).intValue();
				log.info("prepare: " + CP_ProcessFlow_ID);

			} 
			else if (name.equals("CP_ProcessFlowState_ID")) {
				CP_ProcessFlowState_ID = ((BigDecimal) para[i].getParameter()).intValue();
				log.info("prepare: " + CP_ProcessFlowState_ID);

			} 
			else if (name.equals("CP_ProcessSubFlowState_ID")) {
				CP_ProcessSubFlowState_ID = ((BigDecimal) para[i].getParameter()).intValue();
				log.info("prepare: " + CP_ProcessSubFlowState_ID);

			} 						
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}

	}

	private String manageProcess(CP_ProcessFlow Processo) {
		/*
		 * Inizializzazione operatori grammatica
		 */

		/** aggiunge al from di default le tabelle interessate nelle condizioni */
		String addFrom = "";
		/** aggiunge al where di default le condizioni */
		String addWhere = "";
		/** aggiunge ai join di default le tabelle e le condizioni interessate */
		String addJoin = "";
		/** memorizza l'operazione */
		String addOp = "";
		/** memorizza l'operando */
		String Op = "";
		/** memorizza la logica */
		String Logic = "";
		/** memorizza la condizione */
		String condition = "";

		/** memorizza l'intera stringa della nuova grammatica SQL */
		String sqlTOTALE = null;

		/** memorizza CP_ConfProc_ID */
		int CP_ConfProc_ID = 0;

		/** memorizza il CP_ProcessFlow */
		int CP_ProcessFlow_ID = 0;

		/** memorizza il CP_ProcessSubFlow_ID */
		int CP_ProcessSubFlow_ID = 0;

		/** Contatore per le parti in subflow che soddisfano le condizioni */
		int contatore = 0;

		/** Contiene il commento della transazione */
		String descrizione = null;

		/** Se non soddisfo le condizioni visualizzo questo errore */
		String errore = "";
		String erroreStatic = "";

		/** Flag per la politica delle parti del subflow */
		String flagSub = "";
		log.info(Processo.toString());
		CP_ProcessSubFlow[] SubLines = Processo.getLines(true);

		/** memorizza l'ID della conf_proc_line che si sta esaminando */
		int CP_ConfProcLine_ID = 0;
		/** id stato futuro processo */
		int CP_ConfProc_ID_TO = 0;
		/** id stato futuro sub flow successivo */
		int CP_ProcessFlow_ID_TO = 0;
		/**
		 * stato parti subflowsuccessivo in caso in cui tutte le parti devono
		 * avere lo stesso stato
		 */
		int stato_sub_global = 0;

		int counter = 0;

		// output
		String out = "";

		PreparedStatement pstmt = null;

		/** Flag che segnala se è stato trovato uno stato futuro */
		boolean trovato = false;
		
		/**
		 * CPLineID
		 */

		CP_ConfProcLine CPLine=null;
		
		/** Flag che identifica se ci sono o meno condizioni */
		boolean flagcondizioni = true;

		/** risalgo al configuratore collegato */
		CP_ConfProc_ID = Processo.getCP_ConfProc_ID();

		// creo l'oggetto Configuratore
		CP_ConfProc Configuratore = new CP_ConfProc(getCtx(), CP_ConfProc_ID,get_TrxName());
		// creo l'oggetto per le linee
		CP_ConfProcLine[] ConfiguratoreLine = Configuratore.getLines();
		System.out.println("Manage");
		for (CP_ConfProcLine line : ConfiguratoreLine) {
			System.out.println("ConfProcLine");
			if (line.getCP_ProcessFlowState_ID() == Processo.getCP_ProcessFlowState_ID()) {
				System.out.println("Lo stato coincide");
				// creo l'oggetto per le condizioni
				CP_ConfProCondition[] condizioni = line.getLines();
				String sql = "Select * FROM ";
				if (condizioni != null)
					for (CP_ConfProCondition cond : condizioni) {
						MTable tabella = new MTable(getCtx(), cond.getAD_Table_ID(), get_TrxName());
						MColumn colonna = new MColumn(getCtx(), cond.getAD_Column_ID(), get_TrxName());
						MTable tabellaJoin = new MTable(getCtx(), cond.getAD_TableJoin_ID(), get_TrxName());
						MColumn colonnaJoin = new MColumn(getCtx(), cond.getAD_ColumnJoin_ID(), get_TrxName());
						CP_ConfProcOperation operazione = new CP_ConfProcOperation(getCtx(), cond.getCP_ConfProcOperation_ID(),get_TrxName());
						/** ADDFROM */
						if (tabella != null) {
//							System.out.println("c'è la tabella");
							String nometabella = tabella.getTableName();
							String nomecolonna = colonna.getColumnName();
							String nometabellajoin = tabellaJoin.getTableName();
							String nomeoperazione = operazione.getName();
							String nomecolonnajoin = colonnaJoin.getColumnName();

//							System.out.println("Ci sono condizioni");

							/**
							 * se nometabella � una tabella di default allora
							 * non aggiungere in ADDFROM
							 */
							System.out.println("nometabella:" + nometabella);
							log.log(Level.INFO, "nometabella:" + nometabella);
							/**
							 * se la tabella non � gi� presente nel from la
							 * aggiungo
							 */
							if (addFrom.length() == 0) {
								/**
								 * aggiunge a addFrom le tabelle interessate
								 * nelle condizioni
								 */
								addFrom = nometabella;
							}
							if (addFrom.indexOf(nometabella) == -1) {
								/**
								 * aggiunge a addFrom le tabelle interessate
								 * nelle condizioni
								 */
								addFrom += " ," + nometabella;
							}
							if (addFrom.indexOf(nometabellajoin) == -1) {
								/**
								 * aggiunge a addFrom le tabelle interessate
								 * nelle condizioni
								 */
								addFrom += " ," + nometabellajoin;
							}
							/** ADDJOIN */
							/** Se i valori recuperati dalla condizione esistono */
							if (colonnaJoin != null & tabellaJoin != null) {
								/**
								 * Imposto la condizione da aggiungere alla
								 * query
								 */
								String daAggiungere = " WHERE "+ nometabellajoin + "."+ nomecolonnajoin + "=" + nometabella+ "." + nomecolonnajoin + "";
								/**
								 * Se questa non � gi� presente nell'addJoin la
								 * inserisco
								 */
								if (addJoin.indexOf(daAggiungere) == -1)
									addJoin += daAggiungere;
							}
							/** Controlli */
							System.out.println("addfrom: " + addFrom);
							log.log(Level.INFO, "addfrom: " + addFrom);
							System.out.println("AddJoin: " + addJoin);
							log.log(Level.INFO, "AddJoin: " + addJoin);

							// /*Se si vuole legare al processo*/
							// if(addFrom.indexOf("CP_ProcessFlow")!=-1){
							// addWhere+="
							// CP_ProcessFlow.CP_ProcessFlow_ID="+Processo.get_ID();
							// }

							/** Individuo la condizione Logica */
							String Logica = cond.getLogic();
							System.out.println("operatore logico: " + Logica);
							log.log(Level.INFO, "operatore logico: " + Logica);

							condition = nometabella + "." + nomecolonna+ nomeoperazione;
							if (condition == null)
								condition = "";
							if (Logica == null)
								Logic = condition;
							else {
								if (Logica.compareTo("E") == 0 || Logica.compareTo("A") == 0)
									Logic = " AND " + condition;
								else if (Logica.compareTo("O") == 0)
									Logic = ") OR (" + condition;

							}
							/** Individuo il valore condizione intero e stringa */
							int valnum = cond.getValNum();
							String valstring = cond.getValString();
							System.out.println("valore NUM - STRING in condizione:"
											+ valnum + " - " + valstring);
							log.log(Level.INFO,"valore NUM - STRING in condizione:"+ valnum + " - " + valstring);

							/**
							 * Aggiungo ad Op la condizione logica che ho
							 * individuato prima
							 */
							Op += Logic;
							if (Op == null)
								Op = "";
							/**
							 * Se esiste una connessione logica AND OR
							 * <qualcosa> condition ( > = < !=)
							 */
							if (Logica != null || Logica != "") {
								/** Se il termine Ã¨ una stringa */
								if (valstring != null)
									Op += valstring;
								/** Se il termine Ã¨ un numero */
								else
									Op += valnum;
							}
							/**
							 * Aggiungo alla stringa che sarÃ  inserita nella
							 * query la nuova condizione completa AND/OR
							 * <qualcosa> <condition> VALNUM/VALSTRING
							 */
							addOp = " (" + Op + ") ";
							if (addOp == null)
								addOp = "";

							/** ADDWHERE */
							/** Se il where non contiene gi� la condizione */
							if (addWhere.indexOf(addOp) == -1)
								addWhere = " AND (" + addOp + " )";
							if (addWhere == null)
								addWhere = "";
							System.out.println("addWhere :" + addWhere);
							log.log(Level.INFO, "addWhere :" + addWhere);

							/* Se si vuole legare al processo */
							if (addFrom.indexOf("CP_ProcessFlow") != -1) {
								addWhere += " AND CP_ProcessFlow.CP_ProcessFlow_ID="
										+ Processo.get_ID();
							}

						}

					}

				System.out.println("id processo" + Processo.get_ID());
				log.log(Level.INFO, "id processo" + Processo.get_ID());
				System.out.println("stato processo attuale: "+ Processo.getCP_ProcessFlowState_ID());
				log.log(Level.INFO, "stato processo attuale: "+ Processo.getCP_ProcessFlowState_ID());
				System.out.println("id configuratore: "+ Processo.getCP_ConfProc_ID());
				log.log(Level.INFO, "id configuratore: "+ Processo.getCP_ConfProc_ID());
				System.out.println("id configuratore linea: " + line.get_ID());
				log.log(Level.INFO, "id configuratore linea: " + line.get_ID());
				System.out.println("Line :" + line.getLines().length);
				if (line.getLines().length == 0) {
					System.out.println("non ci sono condizioni");
					System.out.println("FORALLSUBPROCESS "+ line.getForAllSubProcess());
					System.out.println("NOSUBPROCESS " + line.getNoSubProcess());
					System.out.println("SubLines " + SubLines.length);
					flagcondizioni = false;
				}

				if (line.getForAllSubProcess()&& (SubLines != null && SubLines.length != 0)) {
					// per tutti i subflow (parti)
					System.out.println("Per tutti i subflow");
					int conta = 0;
					String oldAddWhere = "";
					for (CP_ProcessSubFlow linea : SubLines) {
						System.out.println("stato sottoprocesso attuale: "+ linea.getCP_ProcessSubFlowState_ID());
						log.log(Level.INFO, "stato sottoprocesso attuale: "+ linea.getCP_ProcessSubFlowState_ID());
						/* Se si vuole legare al sottoprocesso */
						if (addFrom.indexOf("CP_ProcessSubFlow") != -1) {
							if (addFrom.indexOf("CP_ProcessSubFlow.CP_ProcessSubFlow_ID") == -1) {
								oldAddWhere = addWhere;
								addWhere += "AND CP_ProcessSubFlow.CP_ProcessSubFlow_ID="+ linea.get_ID();
							}
						}
						if (flagcondizioni) {
							try {

								sql += addFrom + " " + addJoin + " " + addWhere;
								addWhere = oldAddWhere;
								System.out.println("Query finale: " + sql);
								pstmt = DB.prepareStatement(sql, get_TrxName());
								ResultSet rs = pstmt.executeQuery();
								rs.next();
								if (rs.getRow() == 0) {
									System.out.println("Condizioni non soddisfatte");
								} else {
									linea.setCP_ProcessSubFlowState_ID(line.getCP_ProcessSubFlowStateTO_ID());
									linea.save();
									out = "Condizioni soddisfatte";
									conta++;
								}
							} catch (Exception e) {
								System.out.println("Eccezzione generata dal test della condizione nel caso senza parti");
								log.log(Level.SEVERE, sql, e);
							}
						} else {
							System.out.println("Non ci sono le condizioni allora passo");
							out = "Non ci sono le condizioni allora passo";
							linea.setCP_ProcessSubFlowState_ID(line.getCP_ProcessSubFlowStateTO_ID());
							linea.save();
							conta++;
						}

					}
					System.out.println("Conta: " + conta + "Length: "+ SubLines.length);
					if (conta == SubLines.length) {
						System.out.println("Tutte le condizioni sono soddisfatte allora passa il processo...");
						Processo.setCP_ProcessFlowState_ID(line.getCP_ProcessFlowStateTO_ID());
						Processo.save();
						out = "Tutte le condizioni sono soddisfatte allora passa il processo...";
						trovato = true;
					} else {
						if (errore.compareTo("") != 0) {
							if (!trovato) {
								addLog(Msg.getMsg(getCtx(), "CondKo")+" - " + errore);
								errore = "";
							}
						}
					}

				} else if (!line.getForAllSubProcess()&& line.getNoSubProcess()&& (SubLines == null || SubLines.length == 0)) {
					// senza subflow (parti)
					System.out.println("Senza subflow");

					if (flagcondizioni) {
						try {

							sql += addFrom + " " + addJoin + " " + addWhere;
							pstmt = DB.prepareStatement(sql, get_TrxName());
							System.out.println("Query finale: " + sql);
							ResultSet rs = pstmt.executeQuery();
							rs.next();
							if (rs.getRow() == 0) {
								System.out.println("Condizioni non soddisfatte");
								out = "Condizioni non soddisfatte";
								errore = line.getErrorMsg();
								if (errore.compareTo("") != 0) {
									if (!trovato) {
										addLog(Msg.getMsg(getCtx(), "CondKo")+" - " + errore);
										errore = "";
									}
								}
							} else {
								Processo.setCP_ProcessFlowState_ID(line.getCP_ProcessFlowStateTO_ID());
								Processo.save();
								out = "Ok";
								trovato = true;
								break;
							}
						} catch (Exception e) {
							System.out.println("Eccezzione generata dal test della condizione nel caso senza parti");
							log.log(Level.SEVERE, sql, e);
						}
					} else {
						System.out.println("Non ci sono le condizioni allora passo");
						Processo.setCP_ProcessFlowState_ID(line.getCP_ProcessFlowStateTO_ID());
						Processo.save();
						trovato = true;
						out = "Non ci sono le condizioni allora passo";
						break;
					}
				} else if (!line.getForAllSubProcess()&& !line.getNoSubProcess()&& (SubLines != null && SubLines.length != 0)) {
					System.out.println("Qualche subflow");
					// qualche subflow (parte)
					boolean proc = false;
					String oldAddWhere = "";
					for (CP_ProcessSubFlow linea : SubLines) {
						System.out.println("stato sottoprocesso attuale: "+ linea.getCP_ProcessSubFlowState_ID());
						log.log(Level.INFO, "stato sottoprocesso attuale: "	+ linea.getCP_ProcessSubFlowState_ID());
						/* Se si vuole legare al sottoprocesso */
						if (addFrom.indexOf("CP_ProcessSubFlow") != -1) {
							if (addFrom.indexOf("CP_ProcessSubFlow.CP_ProcessSubFlow_ID") == -1) {
								oldAddWhere = addWhere;
								addWhere += " AND CP_ProcessSubFlow.CP_ProcessSubFlow_ID="+ linea.get_ID();
							}
						}
						if (flagcondizioni) {
							try {
								sql = addFrom + " " + addJoin + " " + addWhere;
								addWhere = oldAddWhere;
								System.out.println("Query finale: " + sql);
								pstmt = DB.prepareStatement(sql, get_TrxName());
								ResultSet rs = pstmt.executeQuery();
								rs.next();
								if (rs.getRow() == 0) {
									System.out.println("Condizioni non soddisfatte");
									out = "Condizioni non soddisfatte";
									errore += line.getErrorMsg();
								} else {
									linea.setCP_ProcessSubFlowState_ID(line.getCP_ProcessSubFlowStateTO_ID());
									linea.save();
									proc = true;
									out = "Ok";
								}
							} catch (Exception e) {
								System.out.println("Eccezzione generata dal test della condizione nel caso con qualche parte");
								log.log(Level.SEVERE, sql, e);
							}
						} else {
							System.out.println("Non ci sono le condizioni allora passo");
							linea.setCP_ProcessSubFlowState_ID(line.getCP_ProcessSubFlowStateTO_ID());
							linea.save();
							out = "Non ci sono le condizioni allora passo";
							proc = true;
						}

					}
					if (proc) {
						Processo.setCP_ProcessFlowState_ID(line.getCP_ProcessFlowStateTO_ID());
						Processo.save();
						out = "Ok";
						trovato = true;
					} else {
						if (errore.compareTo("") != 0) {
							if (!trovato) {
								addLog(Msg.getMsg(getCtx(), "CondKo")+" - " + errore);
								errore = "";
							}
						}

					}
				}

			}

		}

		return out;
	}

	private String manageProcessNEW(CP_ProcessFlow Processo) {
		String out = "";
		String errore = "";
		boolean flagcondizioni = true;
		boolean trovato = false;
		// associated subprocess lines
		CP_ProcessSubFlow[] SubLines = Processo.getLines(true);
		int CP_ConfProc_ID = Processo.getCP_ConfProc_ID();
		// new configurator object
		CP_ConfProc Configuratore = new CP_ConfProc(getCtx(), CP_ConfProc_ID,get_TrxName());
		// new lines configurator object
		CP_ConfProcLine[] ConfiguratoreLine = Configuratore.getLines();

		// flag for viewing if it.informaticagestionale.confproc.process can change status
		boolean processGo = false;
		// data structure for storing state transaction
		int statoPFut = 0;
		String desc = "";
		int contaParti = 0;
		// for all configurator lines 
		for (CP_ConfProcLine line : ConfiguratoreLine) {
			// flag true if condition found
			flagcondizioni = true;
			log.info("line.getCP_ConfProcLine_ID()" + line.getCP_ConfProcLine_ID());
			log.info("line.isCODEEXCEPTION()" + line.isCodeException());

		if (line.getCP_ProcessFlowState_ID() == Processo.getCP_ProcessFlowState_ID()) {
				log.log(Level.INFO, "it.informaticagestionale.confproc.process state ok");
				log.log(Level.INFO, "id it.informaticagestionale.confproc.process" + Processo.get_ID());
				log.log(Level.INFO, "actual it.informaticagestionale.confproc.process state: "+ Processo.getCP_ProcessFlowState_ID());
				log.log(Level.INFO, "id configurator: "+ Processo.getCP_ConfProc_ID());
				log.log(Level.INFO, "id configurator line: " + line.get_ID());
				if (line.getLines() == null || line.getLines().length == 0) {
					log.log(Level.INFO, "condition not found");
					log.log(Level.INFO, "FORALLSUBPROCESS "+ line.getForAllSubProcess());
					log.log(Level.INFO, "NOSUBPROCESS "+ line.getNoSubProcess());
					log.log(Level.INFO, "SubLines " + SubLines.length);
					flagcondizioni = false;
				}
				if (line.getForAllSubProcess()&& (SubLines != null && SubLines.length != 0)) {
					log.log(Level.INFO, "For all subflow");
					int conta = 0;
					int contaSub = 0;
					for (CP_ProcessSubFlow linea : SubLines) {
						// flag che identifica se la parte può cambiare di stato
						boolean partGo = false;
						String contesto = "";
						log.log(Level.INFO, "cont " + contaSub++);
						log.log(Level.INFO, "actual sub-it.informaticagestionale.confproc.process state: "+ linea.getCP_ProcessSubFlowState_ID());
						// if begin sub it.informaticagestionale.confproc.process state is the same as that of configurator line 
						if (linea.getCP_ProcessSubFlowState_ID() == line.getCP_ProcessSubFlowState_ID()) {
							// condition found
							if (flagcondizioni) {
								/* Se si vuole legare al sottoprocesso */
								String from = line.getFromCondition();
								String where = line.getWhereCondition();
								log.log(Level.INFO, "from " + from);
								log.log(Level.INFO, "where " + where);
								log.log(Level.INFO,"AND CP_ProcessSubFlow.CP_ProcessSubFlow_ID= "+ linea.getCP_ProcessSubFlow_ID());
								if (from != null)
									if (from.indexOf("CP_ProcessSubFlow") != -1) {
										if (where != null) {
											if (where.indexOf("CP_ProcessSubFlow.CP_ProcessSubFlow_ID") == -1) {
												contesto = "AND CP_ProcessSubFlow.CP_ProcessSubFlow_ID="+ linea.getCP_ProcessSubFlow_ID();
												log.log(Level.INFO,"Contesto : "+ contesto+ ""+ " Join : "+ from.toString()+ "Where: "+ where.toString());
											} 
											else {
												log.log(Level.INFO,"CP_ProcessSubFlow esiste già nel where");
											}
										}
										contesto = "AND CP_ProcessSubFlow.CP_ProcessSubFlow_ID="+ linea.getCP_ProcessSubFlow_ID();
									}
								if (line.evaluateCondition(contesto)) {
									out = "conditions OK";
									partGo = true;
								} else {
									out = "conditions KO";
									errore += line.getErrorMsg();
								}
							} 
							else {
								log.log(Level.INFO,"condition not found...go on");
								out = "condition not found...go on";
								partGo = true;
							}
							if (partGo) {
								conta++;
								
								//valuate Code Exception
								// check code it.informaticagestionale.confproc.process excpetion
								if (line.isCodeException() ) {
									CP_ConfProcException eccezione = new CP_ConfProcException(getCtx(), line.getCP_ConProcException_ID(),get_TrxName());
									try {
										if (eccezione.processa(Processo.get_ID()) == 0)
											log.log(Level.INFO, "code exception ok");
									} catch (Exception e) {
										log.log(Level.WARNING,"code exception error: "+ e.toString());
									}
								}
						
								
								
								
								linea.setCP_ProcessSubFlowState_ID(line.getCP_ProcessSubFlowStateTO_ID());
								linea.save();
								CP_ProcessHistory procHist = new CP_ProcessHistory(Processo);
								procHist.setCP_ProcessSubFlowState_ID(linea.getCP_ProcessSubFlowState_ID());
								procHist.setDescription("CA-Sub: "+ line.getDescription());
								procHist.setCP_ProcessFlowState_ID(Processo.getCP_ProcessFlowState_ID());
								procHist.save();
								
								
								
							}
						} else {
							log.log(Level.INFO,"sub process state not the same");
						}

					}
					log.log(Level.INFO, "Cont: " + conta + "Length: "+ SubLines.length);
					if (conta == SubLines.length) {
						log.log(Level.INFO,"all conditions met than process change status...");
						Processo.setCP_ProcessFlowState_ID(line.getCP_ProcessFlowStateTO_ID());
						Processo.save();
						out = "all conditions met than process change status...";
						trovato = true;
						
					} else {
						if (errore != null)
							if (errore.compareTo("") != 0)
								log.log(Level.INFO,"Conditions not met: "+ errore);
					}
				} else if (!line.getForAllSubProcess()&& line.getNoSubProcess()&& (SubLines == null || SubLines.length == 0)) {
					// senza subflow (parti)
					log.log(Level.INFO, "without subflow flag: "+ flagcondizioni);
					String contesto = "";
					boolean stateProc = false;
					if (flagcondizioni) {
						String from = line.getFromCondition();
						log.log(Level.INFO, "From: " + from);
						if (from != null)
							if (line.getLines() != null)
								if (from.indexOf("CP_ProcessFlow") != -1) {
									log.log(Level.INFO, "Precontext");
									if (Processo != null)
										contesto = " AND CP_ProcessFlow.CP_ProcessFlow_ID="+ Processo.get_ID();
									log.log(Level.INFO, "Context : "+ contesto + " From : " + from);
								}
						if (line.evaluateCondition(contesto)) {
							log.log(Level.INFO, "Conditions OK");
							stateProc = true;
							out = "Condition approvated";
						} else {
							log.log(Level.INFO, "Conditions KO");
							out = "Conditions not met";
							errore += line.getErrorMsg();
						}
					} else {
						log.log(Level.INFO, "conditions not found...go on");
						out = "conditions not found...go on";
						stateProc = true;
					}
					if (stateProc) {
						
						// check code it.informaticagestionale.confproc.process excpetion
						if (line.isCodeException() ) {
							CP_ConfProcException eccezione = new CP_ConfProcException(getCtx(), line.getCP_ConProcException_ID(),get_TrxName());
							try {
								if (eccezione.processa(Processo.get_ID()) == 0)
									log.log(Level.INFO, "code exception ok");
							} catch (Exception e) {
								log.log(Level.WARNING,"code exception error: "+ e.toString());
							}
						}
				
						
						Processo.setCP_ProcessFlowState_ID(line.getCP_ProcessFlowStateTO_ID());
						Processo.save();
						CP_ProcessHistory procHist = new CP_ProcessHistory(Processo);
						if (line.get_ValueAsString("Name")!=null)
							procHist.setDescription((line.get_ValueAsString("Name").isEmpty() ? " " : line.get_ValueAsString("Name")) + (line.getDescription().isEmpty() ? " " : " "+line.getDescription()));
						if (line.get_ValueAsString("Name")==null && line.getDescription()!=null)
							procHist.setDescription((line.getDescription().isEmpty() ? " " : " "+line.getDescription()));
						if (Processo.getCUSTOMTEXT1()!=null)
							procHist.setCUSTOMTEXT1(Processo.getCUSTOMTEXT1().isEmpty()?"":Processo.getCUSTOMTEXT1());
						if (Processo.getCUSTOMTEXT2()!=null)
							procHist.setCUSTOMTEXT2(Processo.getCUSTOMTEXT2().isEmpty()?"":Processo.getCUSTOMTEXT2());
						if (Processo.getCUSTOMNUM1()!=null)
							procHist.setCUSTOMNUM1(Processo.getCUSTOMNUM1().equals(Env.ZERO)?Env.ZERO:Processo.getCUSTOMNUM1());
						if (Processo.getCUSTOMNUM2()!=null)						
							procHist.setCUSTOMNUM2(Processo.getCUSTOMNUM2().equals(Env.ZERO)?Env.ZERO:Processo.getCUSTOMNUM2());
						procHist.setCUSTOMDATE1(Processo.getCUSTOMDATE1());
						procHist.setCUSTOMDATE2(Processo.getCUSTOMDATE2());
						procHist.setHelp((line.get_ValueAsString("Help").isEmpty() ? " " : line.get_ValueAsString("Help")));
						procHist.setName(Processo.getName());
						procHist.setC_BPartner_ID(Processo.getC_BPartner_ID());
						procHist.setM_Product_ID(Processo.getM_Product_ID());
						procHist.setCP_ProcessFlowState_ID(Processo.getCP_ProcessFlowState_ID());
						procHist.save();
						CP_ProcessFlowState procState = new CP_ProcessFlowState(getCtx(),Processo.getCP_ProcessFlowState_ID(),get_TrxName());
						addLog(Msg.getMsg(getCtx(), "CondOk")+" - "+ procState.getName());
						trovato = true;
						
					}
				} else if (!line.getForAllSubProcess()&& !line.getNoSubProcess()&& (SubLines != null && SubLines.length != 0)) {
					log.log(Level.INFO, "some subflow");
					// qualche subflow (parte)
					boolean proc = false;
					int nconta = 0;
					for (CP_ProcessSubFlow linea : SubLines) {
						if (contaParti == SubLines.length)
							break;
						String contesto = "";
						boolean partGO = false;
						log.log(Level.INFO, "Cont " + nconta++);
						log.log(Level.INFO, "actual sub it.informaticagestionale.confproc.process flow: "+ linea.getCP_ProcessSubFlowState_ID());
						if (linea.getCP_ProcessSubFlowState_ID() == line.getCP_ProcessSubFlowState_ID()) {
							if (flagcondizioni) {
								String from = line.getFromCondition();
								String where = line.getWhereCondition();
								/* Se si vuole legare al sottoprocesso */
								if (line.getLines() != null)
									if (from != null)
										if (from.indexOf("CP_ProcessSubFlow") != -1) {
											if (where != null) {
												if (where.indexOf("CP_ProcessSubFlow.CP_ProcessSubFlow_ID") == -1) {
													contesto = " AND CP_ProcessSubFlow.CP_ProcessSubFlow_ID="+ linea.getCP_ProcessSubFlow_ID();
												}
											}
											contesto = " AND CP_ProcessSubFlow.CP_ProcessSubFlow_ID="+ linea.getCP_ProcessSubFlow_ID();
										}

								if (line.evaluateCondition(contesto)) {
									log.log(Level.INFO,"Conditions met");
									partGO = true;
									contaParti++;
									out = "Conditions met";

								} else {
									log.log(Level.INFO,"Conditions not met");
									errore += line.getErrorMsg();
									out = "Conditions not met";
								}
							} else {
								log.log(Level.INFO,"condition not found...go on");
								partGO = true;
								contaParti++;
								out = "condition not found...go on";
							}
							if (partGO) {
								if (!processGo) {
									processGo = true;
									statoPFut = line.getCP_ProcessFlowStateTO_ID();
									desc = line.getDescription();
								}
								
								// check code it.informaticagestionale.confproc.process excpetion
								if (line.isCodeException() ) {
									CP_ConfProcException eccezione = new CP_ConfProcException(getCtx(), line.getCP_ConProcException_ID(),get_TrxName());
									try {
										if (eccezione.processa(Processo.get_ID()) == 0)
											log.log(Level.INFO, "code exception ok");
									} catch (Exception e) {
										log.log(Level.WARNING,"code exception error: "+ e.toString());
									}
								}
						
								
								
								int statoSubFlowFuturo = line.getCP_ProcessSubFlowStateTO_ID();
								linea.setCP_ProcessSubFlowState_ID(statoSubFlowFuturo);
								linea.save();
								CP_ProcessHistory procHist = new CP_ProcessHistory(Processo);
								procHist.setCP_ProcessSubFlowState_ID(linea.getCP_ProcessSubFlowState_ID());
								procHist.setDescription("CA-Sub: "+ line.getDescription());
								procHist.setCP_ProcessFlowState_ID(Processo.getCP_ProcessFlowState_ID());
								procHist.save();
							}
						} 
						else {
							log.log(Level.INFO,"sub process state does not coincide");

						}
					}

				}
			} else {
				log.log(Level.INFO, "State process does not coincide");
			}
			if (trovato)
				break;
		}
		if (processGo) {
			Processo.setCP_ProcessFlowState_ID(statoPFut);
			Processo.save();
			CP_ProcessHistory procHist = new CP_ProcessHistory(Processo);
			procHist.setDescription("CA: " + desc);
			procHist.setCP_ProcessFlowState_ID(statoPFut);
			procHist.save();
			CP_ProcessFlowState procState = new CP_ProcessFlowState(getCtx(),statoPFut,get_TrxName());
			addLog(Msg.getMsg(getCtx(), "CondOk")+ " - " + procState.getName());
			out = "Transaction succeded";
			return out;

		}
		if (errore != null)
			if (errore.compareTo("") != 0) {
				if (!trovato) {
//					addLog("Conditions not met: " + errore);
					addLog(Msg.getMsg(getCtx(), "CondKo")+ " - " +errore);
					errore = "";
				}
			}
		
		return out;
	}

}
