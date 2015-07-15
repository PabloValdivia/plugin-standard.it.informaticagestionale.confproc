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
/**
 * 	Create movement from BProcess 
 *	
 *	
 *  @author Giorgio Cafasso
 *  @version $Id: LoadMovementFromBProcess.java,v 1.4 2006/01/21 02:23:37 gcafasso Exp $
 */
public class LoadMovementFromBProcess extends SvrProcess
{
	/** Configuratore di processo	*/
	private int p_CP_ConfProc_ID=0;
	/** Stato del sottoprocesso					*/
	private int			p_CP_ProcessSubFlowState_ID = 0;
	/** CP_ProcessFlow_ID			*/
	private int		p_CP_ProcessFlow_ID = 0;
	/** Stato del processo					*/
	private int			p_CP_ProcessFlowState_ID = 0;
	/** Magazzino origine					*/
	private int			p_M_LOCATOR_ID = 0;
	/** Magazzino destinazione					*/
	private int			p_M_LOCATORTO_ID = 0;
	/** Org					*/

	/** Movement				*/
	private CPMMovement		m_movement = null;
	/** Movement Line			*/
	private CPMMovementLine	m_movementLine = null;
	/**
	 * counter
	 */
	private int counter = 0;
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

			else if (name.equals("CP_ProcessSubFlowState_ID"))
				p_CP_ProcessSubFlowState_ID = para[i].getParameterAsInt();
			else if (name.equals("CP_ProcessFlow_ID"))
				p_CP_ProcessFlow_ID = (int)para[i].getParameterAsInt();	
			else if (name.equals("CP_ProcessFlowState_ID"))
				p_CP_ProcessFlowState_ID = para[i].getParameterAsInt();
			else if (name.equals("M_Locator_ID"))
				p_M_LOCATOR_ID = para[i].getParameterAsInt();
			else if (name.equals("M_LocatorTo_ID"))
				p_M_LOCATORTO_ID = para[i].getParameterAsInt();
			else if (name.equals("CP_ConfProc_ID"))
				p_CP_ConfProc_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
	}	//	prepare
	
	/**
	 * 	Process
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt() throws Exception
	{
		if (p_M_LOCATOR_ID!=0){
			
		}
		else {
			addLog("Missing WareHouse");
			throw new AdempiereSystemError("Missing WareHouse");
		}
		
		if (p_M_LOCATORTO_ID!=0){
			
		}
		else {
			addLog("Missing dest WareHouse");
			throw new AdempiereSystemError("Missing dest WareHouse");
		}
		log.info("p_CP_ConfProc_ID: "+p_CP_ConfProc_ID);
		
		//import by subprocess flow state
		if (p_CP_ProcessSubFlowState_ID!=0){
		//	Specific
		StringBuffer sql = new StringBuffer("SELECT * FROM CP_ProcessSubFlow rl ")
			.append("WHERE rl.ISACTIVE='Y' AND rl.QTY>0 ");
			sql.append(" AND rl.CP_ProcessSubFlowState_ID=?");
			
		//	Movement Header
		sql.append("AND EXISTS (SELECT * FROM CP_ProcessFlow r WHERE rl.CP_ProcessFlow_ID=r.CP_ProcessFlow_ID ");
		if (p_CP_ConfProc_ID != 0)
		sql.append(" and rl.CP_ConfProc_ID=? ");

		//
		sql.append(") ORDER BY ");

		sql.append("M_Product_ID");
		
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			int index = 1;
			if (p_CP_ProcessSubFlowState_ID != 0)
				pstmt.setInt (index++, p_CP_ProcessSubFlowState_ID);
			if (p_CP_ProcessFlow_ID != 0)
				pstmt.setInt (index++, p_CP_ProcessFlow_ID);
			if (p_CP_ConfProc_ID != 0)
				pstmt.setInt (index++, p_CP_ConfProc_ID);

			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				counter= process (new CP_ProcessSubFlow (getCtx(), rs, get_TrxName()));
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
		closeMovement();
		}

		
		//import subprocess product by processflow ID
		if (p_CP_ProcessFlow_ID!=0){
		//	Specific
		StringBuffer sql = new StringBuffer("SELECT * FROM CP_ProcessSubFlow rl ")
			.append("WHERE rl.ISACTIVE='Y' AND rl.QTY>0 AND rl.DA_MOVIMENTARE='Y' ");
			sql.append(" AND rl.CP_ProcessFlow_ID=?");
			
		//	Movement Header
		sql.append("AND EXISTS (SELECT * FROM CP_ProcessFlow r WHERE rl.CP_ProcessFlow_ID=r.CP_ProcessFlow_ID ");
		if (p_CP_ConfProc_ID != 0)
		sql.append(" and rl.CP_ConfProc_ID=? ");

		//
		sql.append(") ORDER BY ");

		sql.append("M_Product_ID");
		
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			int index = 1;
			if (p_CP_ProcessFlow_ID != 0)
				pstmt.setInt (index++, p_CP_ProcessFlow_ID);
			if (p_CP_ConfProc_ID != 0)
				pstmt.setInt (index++, p_CP_ConfProc_ID);

			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				counter= process (new CP_ProcessSubFlow (getCtx(), rs, get_TrxName()));
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
		closeMovement();
		}
		
		
	
		
		//import by it.informaticagestionale.confproc.process
		if (p_CP_ProcessFlowState_ID!=0){
		//	Specific
		StringBuffer sql = new StringBuffer("SELECT * FROM CP_ProcessFlow rl ").append("WHERE (rl.ISACTIVE='Y' ");
		if (p_CP_ProcessFlowState_ID!= 0)
			sql.append(" AND rl.CP_ProcessFlowState_ID=?");
		if (p_CP_ConfProc_ID != 0)
			sql.append(" and rl.CP_ConfProc_ID=? ");


		sql.append(") ORDER BY ");

		sql.append("M_Product_ID");
		
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
			int index = 1;
			if (p_CP_ProcessFlowState_ID != 0)
				pstmt.setInt (index++, p_CP_ProcessFlowState_ID);
			if (p_CP_ConfProc_ID != 0)
				pstmt.setInt (index++, p_CP_ConfProc_ID);

	
			ResultSet rs = pstmt.executeQuery ();
			while (rs.next ())
			{
				counter= process2 (new CP_ProcessFlow (getCtx(), rs, get_TrxName()));
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
		closeMovement();
		}
		
		
		return "rows to move: "+counter;
	}	//	doit
	
	private int 		m_M_CP_ProcessFlow_ID = 0;
	private int 		m_M_Product_ID = 0;
	private int			m_M_AttributeSetInstance_ID = 0;
	/** BPartner				*/
	private MBPartner	m_bpartner = null;
	
	/**
	 * 	Process Line
	 *	@param rLine request line
	 */
	private int process (CP_ProcessSubFlow rLine) throws Exception
	{
		if (rLine.getM_Product_ID() == 0)
		{
			log.warning("Ignored Line" + rLine.getLine() 
				+ " " + rLine.getDescription());
		//		+ " - " + rLine.getLineNetAmt());
			return 0;
		}
		
	
		if (m_movementLine == null
			|| rLine.getM_Product_ID() != m_M_Product_ID
			//modifica di Giorgio
			|| rLine.getM_Product_ID() == m_M_Product_ID)		//	single line per charge
			newLine(rLine);
		//	Update Movement Line
		log.info("movement update");

		if (!rLine.save())
			throw new AdempiereSystemError("Cannot update Request Line");
		return counter;
	}	//	it.informaticagestionale.confproc.process
	
	/**
	 * 	Create new Movement
	 *	@param rLine request line
	 */
	private void newMovement(CP_ProcessSubFlow rLine) throws Exception
	{
		if (m_movement != null)
			closeMovement();

		//	Movement
		m_movement = new CPMMovement(getCtx(), 0, get_TrxName());

		m_movement.setDescription("mov sub-it.informaticagestionale.confproc.process"); 
				

		
		//	Prepare Save
		m_M_CP_ProcessFlow_ID = rLine.getCP_ProcessFlow_ID();
		if (!m_movement.save())
			throw new AdempiereSystemError("Cannot save Movement");
	}	//	newMovement

	/**
	 * 	Close Movement
	 */
	private void closeMovement() throws Exception
	{
		if (m_movementLine != null)
		{
			if (!m_movementLine.save())
				throw new AdempiereSystemError("Cannot update Movement Line");
		}
		if (m_movement != null)
		{
			m_movement.load(get_TrxName());
			//addLog(0, null, m_movement.getGrandTotal(), m_movement.getDocumentNo());
			//addLog(0, null, m_movement.getDocumentNo());
		}
		m_movement = null;
		m_movementLine = null;
	}	//	closeMovement

	
	/**
	 * 	New Movement Line (different Product)
	 *	@param rLine request line
	 */
	private void newLine(CP_ProcessSubFlow rLine) throws Exception
	{
		//aggiornamento counter
		counter = counter+1;
		if (m_movementLine != null)
			if (!m_movementLine.save())
				throw new AdempiereSystemError("Cannot update Movement Line");
		m_movementLine = null;
		MProduct product = null;


			//	Find Vendor from Produt
			product = MProduct.get(getCtx(), rLine.getM_Product_ID());
			MProductPO[] ppos = MProductPO.getOfProduct(getCtx(), product.getM_Product_ID(), null);
			for (int i = 0; i < ppos.length; i++)
			{
				if (ppos[i].isCurrentVendor() && ppos[i].getC_BPartner_ID() != 0)
				{
			//		C_BPartner_ID = ppos[i].getC_BPartner_ID();
					break;
				}
			}


		//	New Movement - Different Vendor
		if (m_movement == null) 
			newMovement(rLine);
		
		
		//	No Movement Line
		m_movementLine = new CPMMovementLine(m_movement);
		if (product != null) //PARTE DISPONIBILE
		{
			log.info("sub-process available");
			System.out.print("qty: "+rLine.getQty());
			m_movementLine.setCP_ProcessFlow_ID(rLine.getCP_ProcessFlow_ID());
			m_movementLine.setProduct(product);
			m_movementLine.setMovementQty(rLine.getQty());
			m_movementLine.setM_Locator_ID(p_M_LOCATOR_ID);
			m_movementLine.setM_LocatorTo_ID(p_M_LOCATORTO_ID);
	//		m_movementLine.setM_AttributeSetInstance_ID(rLine.getM_AttributeSetInstance_ID());
		}
		else
		{
			addLog("Missing product!!");
			throw new AdempiereSystemError("Missing product");


		}
		

		m_movementLine.setAD_Org_ID(rLine.getAD_Org_ID());
				
		
		//	Prepare Save
		m_M_Product_ID = rLine.getM_Product_ID();

		if (!m_movementLine.save())
			throw new AdempiereSystemError("Cannot save Movement Line");
	}	//	newLine


	/**
	 * 	Process Line
	 *	@param rLine request line
	 */
	private int process2 (CP_ProcessFlow rLine) throws Exception
	{
		if (rLine.getM_Product_ID() == 0)
		{
			log.warning("Ignored Line" + rLine.getCP_ProcessFlow_ID()
				+ " " + rLine.getDescription());
		//		+ " - " + rLine.getLineNetAmt());
			return 0;
		}
		
	
		if (m_movementLine == null
			|| rLine.getM_Product_ID() != m_M_Product_ID
			//modifica di Giorgio
			|| rLine.getM_Product_ID() == m_M_Product_ID)
			//fine modifica di Giorgio
		//	|| rLine.getC_Charge_ID() != 0)		//	single line per charge
			newLine2(rLine);
		//	Update Movement Line
		log.info("mov update");
	//	m_movementLine.setMovementQty(m_movementLine.getMovementQty().add(rLine.getMovementQty()));
		//modifica effettuata da Giorgio
//		m_movementLine.setLog(rLine.getLog());
		//fine modifica effettuata da Giorgio
		//	Update Requisition Line
		//rLine.setM_MovementLine_ID(m_movementLine.getM_MovementLine_ID());
		rLine.setIdDoc(m_movement.get_ID());
		rLine.setIdDocLine(m_movementLine.get_ID());
		if (!rLine.save())
			throw new AdempiereSystemError("Cannot update Movement Line");
		return counter;
	}	//	it.informaticagestionale.confproc.process
	
	/**
	 * 	Create new Movement
	 *	@param rLine request line
	 */
	private void newMovement2(CP_ProcessFlow rLine) throws Exception
	{
		if (m_movement != null)
			closeMovement2();
		//	BPartner
//		if (m_bpartner == null)
//			m_bpartner = new MBPartner (getCtx(), C_BPartner_ID, null);

		//	Movement
		m_movement = new CPMMovement(getCtx(), 0, get_TrxName());

			m_movement.setDescription("move product from process"); 			
		
		//	Prepare Save
		m_M_CP_ProcessFlow_ID = rLine.getCP_ProcessFlow_ID();
		if (!m_movement.save())
			throw new AdempiereSystemError("Cannot save Movement");
	}	//	newMovement

	/**
	 * 	Close Movement
	 */
	private void closeMovement2() throws Exception
	{
		if (m_movementLine != null)
		{
			if (!m_movementLine.save())
				throw new AdempiereSystemError("Cannot update Movement Line");
		}
		if (m_movement != null)
		{
			m_movement.load(get_TrxName());

		}
		m_movement = null;
		m_movementLine = null;
	}	//	closeMovement

	
	/**
	 * 	New Movement Line (different Product)
	 *	@param rLine request line
	 */
	private void newLine2(CP_ProcessFlow rLine) throws Exception
	{
		//aggiornamento counter
		counter = counter+1;
		if (m_movementLine != null)
			if (!m_movementLine.save())
				throw new AdempiereSystemError("Cannot update Movement Line");
		m_movementLine = null;
		MProduct product = null;


			//	Find Vendor from Produt
		// se per movimentazione ai clienti

	/**
	 * modifiche per Teknema
	 
		
		if (rLine.isForShipment())
			 product = MProduct.get(getCtx(), rLine.getM_ProductReal_ID());
		//altrimenti
		  else
		  
		  */	
			product = MProduct.get(getCtx(), rLine.getM_Product_ID());
			MProductPO[] ppos = MProductPO.getOfProduct(getCtx(), product.getM_Product_ID(), null);
			for (int i = 0; i < ppos.length; i++)
			{
				if (ppos[i].isCurrentVendor() && ppos[i].getC_BPartner_ID() != 0)
				{
			//		C_BPartner_ID = ppos[i].getC_BPartner_ID();
					break;
				}
			}


		//	New Movement - Different Vendor
		if (m_movement == null) 
			newMovement2(rLine);
		
		
		//	No Movement Line
		m_movementLine = new CPMMovementLine(m_movement);
		if (product != null) //PARTE DISPONIBILE
		{
			log.info("it.informaticagestionale.confproc.process product available");

			log.info("qty: "+rLine.getQty());
			if (rLine.getQty().equals(Env.ZERO)){
				addLog("Missing qty in business it.informaticagestionale.confproc.process");
				throw new AdempiereSystemError("Missing qty in business it.informaticagestionale.confproc.process ");
			}
			m_movementLine.setMovementQty(rLine.getQty());
			//m_movementLine.setMovementQty(Env.ONE);
			m_movementLine.setM_Locator_ID(p_M_LOCATOR_ID);
			m_movementLine.setM_LocatorTo_ID(p_M_LOCATORTO_ID);
			//m_movementLine.setCP_ProcessFlow_ID(rLine.getCP_ProcessFlow_ID());
			m_movementLine.setProduct(product);
			
			//selezione SerNO se esistente nel processo aziendale
			
			String sql="SELECT ma.M_ATTRIBUTESETINSTANCE_ID FROM M_ATTRIBUTESETINSTANCE ma, M_MovementLine ml, M_STORAGE ms, CP_ProcessFlow tp WHERE " +
					" tp.CP_ProcessFlow_ID=? AND tp.CUSTOMTEXT2=ma.SERNO AND " +
					" ms.M_ATTRIBUTESETINSTANCE_ID=ma.M_ATTRIBUTESETINSTANCE_ID " +
					" AND ms.QTYONHAND>0 AND ms.M_LOCATOR_ID=? GROUP by ma.M_ATTRIBUTESETINSTANCE_ID ";			
			
			
		//	PreparedStatement pstmt = DB.prepareStatement (sql, get_TrxName());

		//jz pstmt_updateProduct.setInt(1, I_Product_ID);
		//   pstmt_updateProduct.setInt(2, M_Product_ID);
			try
			{
				PreparedStatement pstmt = DB.prepareStatement (sql.toString(), get_TrxName());
				if (rLine.getCP_ProcessFlow_ID() != 0)
					pstmt.setInt (1, rLine.getCP_ProcessFlow_ID());
				pstmt.setInt (2, p_M_LOCATOR_ID);
				ResultSet rs = pstmt.executeQuery ();
				while (rs.next ())
				{
					int MInstance_ID=rs.getInt(1);
					m_movementLine.setM_AttributeSetInstance_ID(MInstance_ID);	
				}
				rs.close ();
				pstmt.close ();
				pstmt = null;
			}
			
			catch (Exception e)
			{
				log.log (Level.SEVERE, sql.toString(), e);
			}

			
		}
		else
		{
	//		
			addLog("Missing product!!");
			throw new AdempiereSystemError("Missing product");
	//		m_movementLine.setPriceActual(rLine.getPriceActual());
		}
		

		m_movementLine.setAD_Org_ID(rLine.getAD_Org_ID());
				
		
		//	Prepare Save
		m_M_Product_ID = rLine.getM_Product_ID();
	//	m_M_AttributeSetInstance_ID = rLine.getM_AttributeSetInstance_ID();
		if (!m_movementLine.save())
			throw new AdempiereSystemError("Cannot save Movement Line: ");
	}	//	newLine

	
	
	
	
}	//	RequisitionPOCreate
