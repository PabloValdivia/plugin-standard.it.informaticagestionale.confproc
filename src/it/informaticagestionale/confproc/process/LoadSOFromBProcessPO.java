/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is Compiere ERP & CRM Smart Business Solution. The Initial
 * Developer of the Original Code is Jorg Janke. Portions created by Jorg Janke
 * are Copyright (C) 1999-2005 Jorg Janke.
 * All parts are Copyright (C) 1999-2005 ComPiere, Inc.  All Rights Reserved.
 * Contributor(s): ______________________________________.
 *****************************************************************************/
package it.informaticagestionale.confproc.process;

import java.sql.*;
import java.util.logging.*;

import org.compiere.model.*;
import org.compiere.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.logging.Level;
import it.informaticagestionale.confproc.model.*;

import org.compiere.model.MInOut;
import org.compiere.model.MInOutLine;
import org.compiere.model.MRMA;
import org.compiere.model.MRMALine;
import org.compiere.process.DocAction;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.awt.geom.*;
import java.math.*;
import java.sql.*;
import java.util.logging.*;
import org.compiere.model.*;
import org.compiere.util.*;
/**
 *	Generate SO from Purchase Order
 *	not tested yet
 *  @author Giorgio Cafasso
 *  @version $Id: LoadSOFromBProcessPO.java,v 1.10 2005/03/11 20:25:57 gcafasso Exp $
 */
public class LoadSOFromBProcessPO extends SvrProcess
{
	/**	Order Date From		*/
	private Timestamp	p_DateOrdered_From;
	/**	Order Date To		*/
	private Timestamp	p_DateOrdered_To;
	/**	it.informaticagestionale.confproc.process state			*/
	private int 		p_CP_ProcessFlowState_ID;
//	private int			p_C_BPartner_ID;
	/**	Vendor				*/
//	private int			p_Vendor_ID;
	/**	Sales Order			*/
//	private int			p_C_Order_ID;
	/** Drop Ship			*/
//	private String		p_IsDropShip;
	//per contare il numero di ordini non completati
	int counter2 = 0;
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("DateOrdered"))
			{
				p_DateOrdered_From = (Timestamp)para[i].getParameter();
				p_DateOrdered_To = (Timestamp)para[i].getParameter_To();
			}
			else if (name.equals("CP_ProcessFlowState_ID"))
				p_CP_ProcessFlowState_ID = ((BigDecimal)para[i].getParameter()).intValue();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}	//	prepare

	/**
	 *  Perform it.informaticagestionale.confproc.process.
	 *  @return Message 
	 *  @throws Exception if not successful
	 */
	protected String doIt() throws Exception
	{
		log.info("DateOrdered=" + p_DateOrdered_From + " - " + p_DateOrdered_To); 

		//
		//System.out.println(p_DateOrdered_From);
		//System.out.println(p_DateOrdered_To);
		log.info("it.informaticagestionale.confproc.process flow state: "+ p_CP_ProcessFlowState_ID);
		//preparo le righe da Tek processo chiamata
		String sql2 = "SELECT * FROM CP_ProcessFlow tpc "
			+ "WHERE (tpc.CP_ProcessFlowState_ID=? )";
			
//		if (p_CP_ProcessFlowState_ID != 0)
//		sql2+= "AND EXISTS (select * from TEK_CHIAMATA_PARTI tc where ((tpc.TEK_PROCESSO_CHIAMATA_ID=tc.TEK_PROCESSO_CHIAMATA_ID) AND tc.TEK_CAT_ID=? and tc.TEK_STATO_PARTI_ID=1000003))";
//			else
//		sql2+= "AND EXISTS (select * from TEK_CHIAMATA_PARTI tc where ((tpc.TEK_PROCESSO_CHIAMATA_ID=tc.TEK_PROCESSO_CHIAMATA_ID) and tc.TEK_STATO_PARTI_ID=1000003))";
//			
//
//			
			PreparedStatement pstmt2 = null;
			
			//documento CO= completato
		//	No Duplicates
	/*	
		String sql = "SELECT * FROM C_Order o "
				+ "WHERE o.IsSOTrx='N' AND o.DOCSTATUS ='CO' "
			+ "AND NOT EXISTS (SELECT * FROM C_OrderLine ol WHERE o.C_Order_ID=ol.C_Order_ID AND ol.Ref_OrderLine_ID IS NOT NULL)"
			; 
		if (p_TEK_CAT_ID != 0)
			sql += "AND o.TEK_CAT_ID=?";

			if (p_DateOrdered_From != null && p_DateOrdered_To != null)
				sql += "AND TRUNC(o.DateOrdered) BETWEEN ? AND ?";
			else if (p_DateOrdered_From != null && p_DateOrdered_To == null)
				sql += "AND TRUNC(o.DateOrdered) >= ?";
			else if (p_DateOrdered_From == null && p_DateOrdered_To != null)
				sql += "AND TRUNC(o.DateOrdered) <= ?";

		PreparedStatement pstmt = null;
*/
		int counter = 0;
		
		try
		{
			pstmt2 = DB.prepareStatement (sql2,get_TrxName());
				int index = 0;
				if (p_CP_ProcessFlowState_ID != 0) {
					index++;
					pstmt2.setInt (1, p_CP_ProcessFlowState_ID);
				}
				
			//vediamo cosa manda in execute
			System.out.println("OrderSoCreate.doIt:sql2 "+sql2);
			//System.out.println("OrderSoCreate.doIt:sql2 Tostring "+pstmt2.toString());
									
			ResultSet rs2 = pstmt2.executeQuery ();
						
			//int RowNum=rs2.getRow();
			
		
			
			//System.out.println("OrderSoCreate.doIt:sql2 RowNum "+RowNum);
			//se � maggiore di zero entriamo		
			//if (RowNum>0)
			//{
					
		
			while ((rs2.next ()))
			{
				System.out.println("OrderSoCreate.doIt:entra prima di createSOFromTekChiamata");
				counter += createSOFromBProcessFlow(new CP_ProcessFlow (getCtx(), rs2, get_TrxName()));
				System.out.println("OrderSoCreate.doIt:createSOFromTekChiamata ordini creati " + counter );
			}
		
			//	}//if
			rs2.close ();
			pstmt2.close ();
			pstmt2 = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql2, e);
		}
	/*
		// secondo comando Sql,
		//nota bene che sql2 deve andare prima di sql
		try
		{
			pstmt = DB.prepareStatement (sql, get_TrxName());
				int index = 0;
				if (p_TEK_CAT_ID != 0) {
					index++;
					pstmt.setInt (1, p_TEK_CAT_ID);
				}
				if (p_DateOrdered_From != null && p_DateOrdered_To != null)
				{
					pstmt.setTimestamp(index++, p_DateOrdered_From);
					pstmt.setTimestamp(index++, p_DateOrdered_To);
				}
				else if (p_DateOrdered_From != null && p_DateOrdered_To == null)
					pstmt.setTimestamp(index++, p_DateOrdered_From);
				else if (p_DateOrdered_From == null && p_DateOrdered_To != null)
					pstmt.setTimestamp(index++, p_DateOrdered_To);
				
			//vediamo cosa manda in execute
			System.out.println(sql);
			//modo per disabilitare l'invio della query sql
			//uso per debug
			boolean esegui;
			esegui=true;
			
			ResultSet rs=null;
			
			if (esegui) { 
				System.out.println("OrderSoCreate.doIt:lancio la query sql per rs ");
						rs = pstmt.executeQuery ();
			}
			
			System.out.println("OrderSoCreate.doIt:sql get row "+rs.getRow());
			//System.out.println("OrderSoCreate.doIt:sql Tostring "+pstmt.toString());
			

			while ((rs.next ()))
			{
				System.out.println("OrderSoCreate.doIt:entra prima di createSOFromPO");
				counter += createSOFromPO (new MOrder (getCtx(), rs, get_TrxName()));
				System.out.println("OrderSoCreate.doIt:createSOFromPO ordini creati " + counter);
				
			}
			rs.close ();
			pstmt.close ();
			pstmt = null;
			
		}
				catch (Exception e)
		{
			log.log(Level.SEVERE, sql, e);
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
*/
		try
		{
			if (pstmt2 != null)
				pstmt2.close ();
			pstmt2 = null;
		}
		catch (Exception e)
		{
			pstmt2 = null;
		}
		
//		if (counter == 0)
//			log.fine(sql);
		return "@Created@ " + counter + " Non creati " + counter2;
	}	//	doIt
	
	/**
		 * 	Create SO From PO
		 *	@param po purchase order
		 *	@return number of SOs created
		 */
		private int createSOFromPO (CPMOrder po)
		{
			log.info(po.toString());
			System.out.println("OrderSoCreate:createSOFromPO entra!!!");
			CPMOrderLine[] poLines = po.getLines(true, null);
			if (poLines == null || poLines.length == 0)
			{
				log.warning("OrderSoCreate:createSOFromPO No Lines - " + po);
				return 0;
			}
			//
			int counter = 0;
			
			//select purchase order
			String sql = "SELECT so.C_BPartner_ID "
				+ "FROM C_Order so " 
				+ "WHERE so.C_Order_ID=? "
				+ "ORDER BY 1";
			
			PreparedStatement pstmt = null;
			CPMOrder so = null;
			try
			{
				pstmt = DB.prepareStatement (sql, get_TrxName());
				pstmt.setInt (1, po.getC_Order_ID());
				ResultSet rs = pstmt.executeQuery ();
				
				while (rs.next ())
				{
					//	New Order
					int C_BPartner_ID = rs.getInt(1);
					if (so == null || so.getBill_BPartner_ID() != C_BPartner_ID)
					{
						so = createSOForVendor(rs.getInt(1), po);
						addLog(0, null, null, so.getDocumentNo());
						counter++;
					}
	
					//	Line
					for (int i = 0; i < poLines.length; i++)
					{
	
						{
							CPMOrderLine soLine = new CPMOrderLine (so);
							soLine.setRef_OrderLine_ID(poLines[i].getC_OrderLine_ID());
							soLine.setM_Product_ID(poLines[i].getM_Product_ID());
							soLine.setM_AttributeSetInstance_ID(poLines[i].getM_AttributeSetInstance_ID());
							soLine.setC_UOM_ID(poLines[i].getC_UOM_ID());
							soLine.setQtyEntered(poLines[i].getQtyEntered());
							soLine.setQtyOrdered(poLines[i].getQtyOrdered());
							soLine.setDescription(poLines[i].getDescription());
							soLine.setDatePromised(poLines[i].getDatePromised());
							//soLine.setCP_ProcessFlow_ID(poLines[i].getCP_ProcessFlow_ID());
							soLine.setPrice();
							soLine.save();
						}
					}
				}
				rs.close ();
				pstmt.close ();
				pstmt = null;
			}
			catch (Exception e)
			{
				log.log(Level.SEVERE, sql, e);
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
			//	Set Reference to SO
			if (counter == 1 && so != null)
			{
				so.setRef_Order_ID(so.getC_Order_ID());
				so.save();
			}
			return counter;
		}	//	createSOFromPO

	/**
	 * 	Create SO From parti disponibili in Magazzino
	 *	@param po purchase order
	 *	@return number of SOs created
	 */
	private int createSOFromBProcessFlow (CP_ProcessFlow tpo) throws Exception
	{
		log.info(tpo.toString());

		//carico le linee da CP_ProcessFlow
//		CP_ProcessSubFlow[] tpoLines = tpo.getLines(true);		
//		
//		if (tpoLines == null || tpoLines.length == 0)
//		{
//			log.warning("OrderSoCreate:processFlow Lines - " + tpo);
//			return 0;
//		}
		
		int counter = 0;
		
		//pongo l'ordine a null per essere sicuro della sua deallocazione
		CPMOrder so = null;
	
		//creo l'intestazione dell'ordine
		so = createSO(tpo);
		
     	//	Line
//				for (int i = 0; i < tpoLines.length; i++)
//				{
					//entro solo se non sono state gi� importate 
//					if (!tpoLines[i].isImported()){
					{
						
//						if (tpoLines[i].getTEK_stato_parti_ID() == constStatus.PART_Disponibile)
//						{
							//se entro almeno una volta pongo l'ordine a 1
							//tanto creiamo un solo ordine per ogni CAT
							counter = 1;
//						//creo una nuova linea dell'ordine precedentemente creato
						CPMOrderLine soLine = new CPMOrderLine (so);
						//imposto il riferimento dell'ordine
						soLine.setRef_OrderLine_ID(tpo.getCP_ProcessFlow_ID());
						//metto l'id del prodotto nella linea
						soLine.setM_Product_ID(tpo.getM_Product_ID());
						//soLine.setM_AttributeSetInstance_ID(poLines[i].getM_AttributeSetInstance_ID());
						//imposto l'unit� di misura
						soLine.setC_UOM_ID(102);//day
						//setto la quantit�, visto che lavoro solo
						//con il magazzino ho gli stessi valori
						soLine.setQtyEntered(tpo.getQty());
						soLine.setQtyOrdered(tpo.getQty());
						soLine.setDescription(tpo.getDescription());
						//aggiorno il valore di updated
						soLine.setDatePromised(tpo.getUpdated());
						//imposto il log dell'ordine stesso
						//soLine.setCP_ProcessFlow_ID(tpo.getCP_ProcessFlow_ID());
						//dico al sistema di andarsi a trovare il prezzo  del prodotto
						//aggiunto nella linea
						soLine.setPrice(); 
						//setto a YES perch� la linea � stata importata
						//System.out.println("OrderSoCreate:createSOFromTekChiamata log di tpolines "+ tpoLines[i].getLog());
						//aggiorno la tpoline visto che l'ho impostata a YES
						//salvo la linea dentro l'ordine
						soLine.save();
//						}
						//mettere messaggio di avvertimento per l'utente ATTENZIONE!!!!
						    //log.warning("ATTENZIONE!! almeno uno prodotto in magazzino non � disponibile del documento N." +tpo.getDocumentNo());
						}
//					}
//				}
			
		
		//	Set Reference to SO
		if ((counter == 1) && (so != null))
		{
			so.setRef_Order_ID(so.getC_Order_ID());
			//aggiorno counter e riga, solo se il salvataggio va a buon fine
			so.save();
		}
	//riporto il numero di documenti creati
		return counter;
	}	//	createSOFromTekChiamata
	
	/**
	 *	Create SO for Vendor
	 *	@param C_BPartner_ID vendor
	 *	@param po purchase order
	 */
	
	//crea l'intestazione del purchase order
	public CPMOrder createSOForVendor(int C_BPartner_ID, CPMOrder po)
	{
		CPMOrder so = new CPMOrder (getCtx(), 0, get_TrxName());
/**
 * modifiche per Teknema
 		System.out.println(po.getTEK_CAT_ID());
		TEKCat tekCat = new TEKCat (getCtx(), po.getTEK_CAT_ID(), get_TrxName());
	*/
		so.setClientOrg(po.getAD_Client_ID(), po.getAD_Org_ID());
		so.setRef_Order_ID(po.getC_Order_ID());
		so.setIsSOTrx(true); //ordine di vendita
		so.setC_DocTypeTarget_ID();
		//
		so.setDescription(po.getDescription());
		so.setPOReference(po.getDocumentNo());
		so.setPriorityRule(po.getPriorityRule());
		so.setSalesRep_ID(po.getSalesRep_ID());
		so.setM_Warehouse_ID(po.getM_Warehouse_ID());
		
		//	Set Vendor modifiche per Teknema
//		System.out.println(tekCat.getC_BPartner_ID());
//		if (tekCat.getC_BPartner_ID()!= 0) 
//		{
//		MBPartner vendor = new MBPartner (getCtx(), tekCat.getC_BPartner_ID() , get_TrxName());
//		so.setBPartner(vendor);
//		}
//		else
//		{
			MBPartner vendor = new MBPartner (getCtx(), C_BPartner_ID , get_TrxName());
			so.setBPartner(vendor);
//			}
		//	Drop Ship
		so.setIsDropShip(so.isDropShip());
		if (so.isDropShip())
	{
			so.setShip_BPartner_ID(po.getC_BPartner_ID());
			so.setShip_Location_ID(po.getC_BPartner_Location_ID());
			so.setShip_User_ID(po.getAD_User_ID());
		}
		
		//	References
		so.setC_Activity_ID(po.getC_Activity_ID());
		so.setC_Campaign_ID(po.getC_Campaign_ID());
		so.setC_Project_ID(po.getC_Project_ID());
		so.setUser1_ID(po.getUser1_ID());
		so.setUser2_ID(po.getUser2_ID());
		//
		//invoice and delivery rule
		
		so.save();
		return so;
	}	//	createSOForVendor
	
	/**
	 *	Create createSOForTekCat
	 *	@param tpo TekChiamataprocesso
	 */
	
	public CPMOrder createSO(CP_ProcessFlow tpo)
	{
		CPMOrder so = new CPMOrder (getCtx(), 0, get_TrxName());
	//modifiche per Teknema
		//	TEKCat tekCat = new TEKCat (getCtx(), p_TEK_CAT_ID, get_TrxName());
		so.setClientOrg(tpo.getAD_Client_ID(), tpo.getAD_Org_ID());
	    //so.setRef_Order_ID(tpo.getTEK_PROCESSO_CHIAMATA_ID());
		so.setIsSOTrx(true); //ordine di vendita
		so.setC_DocTypeTarget_ID();
		//
		so.setDescription(tpo.getDescription());
		//so.setPOReference(tpo.getDocumentNo());
		so.setPriorityRule("5");//medium
		//so.setSalesRep_ID(tpo.getSalesRep_ID());
		so.setM_Warehouse_ID(Integer.parseInt( getCtx().getProperty("#M_Warehouse_ID")));//default warehouse
		so.setDescription("Created by it.informaticagestionale.confproc.process: " +tpo.getName());
		//	Set Vendor modifiche per Teknema
//		System.out.println("OrderSoCreate:createSOForTekCat Partner_ID - "+ tekCat.getC_BPartner_ID());
//		if (tekCat.getC_BPartner_ID()!= 0) 
//		{
//		MBPartner vendor = new MBPartner (getCtx(), tekCat.getC_BPartner_ID() , get_TrxName());
//		so.setBPartner(vendor);
//		}
//		//	Drop Ship
		so.setIsDropShip(so.isDropShip());
		if (so.isDropShip())
	{
		// modifiche per Teknema	so.setShip_BPartner_ID(tekCat.getC_BPartner_ID());
			//so.setShip_Location_ID(tpo.getC_BPartner_Location_ID());
			so.setShip_User_ID(tpo.getAD_User_ID());
		}
		
		//	References
		//so.setC_Activity_ID(po.getC_Activity_ID());
		//so.setC_Campaign_ID(po.getC_Campaign_ID());
		//so.setC_Project_ID(po.getC_Project_ID());
		//so.setUser1_ID(po.getUser1_ID());
		//so.setUser2_ID(po.getUser2_ID());
		//
		//invoice and delivery rule
		so.setC_BPartner_ID(tpo.getC_BPartner_ID());
		so.save();
		return so;
	}	//	createSOForTekCat
	
}	//	doIt
