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

import java.math.*;
import java.sql.*;
import java.util.logging.*;
import org.compiere.model.*;
import org.compiere.util.*;

import it.informaticagestionale.confproc.model.*;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;
import org.compiere.util.DB;
import org.compiere.model.*;
/**
 *	Import Process from I_Process
 *
 * 	@author 	ing. Giorgio Cafasso
 * 	@version 	$Id: ImportProcess.java,v 1.3 2006/07/30 00:51:01 jjanke Exp $
 */
public class ImportProcess extends SvrProcess
{


	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare()
	{

	}	//	prepare


	/**
	 *  Perrform it.informaticagestionale.confproc.process.
	 *  @return Message
	 *  @throws Exception
	 */
	protected String doIt() throws java.lang.Exception
	{
		StringBuffer sql = null;
		int no = 0;


		//	Set Client, Org, IaActive, Created/Updated, 	ProductType
		sql = new StringBuffer ("UPDATE I_PROCESS "
			+ "SET AD_Client_ID = COALESCE (AD_Client_ID, 1000002),"
			+ " AD_Org_ID = COALESCE (AD_Org_ID, 1000002),"
			+ " IsActive = COALESCE (IsActive, 'Y'),"
			+ " Created = COALESCE (Created, SysDate),"
			+ " CreatedBy = COALESCE (CreatedBy, 0),"
			+ " Updated = COALESCE (Updated, SysDate),"
			+ " UpdatedBy = COALESCE (UpdatedBy, 0),"
			+ " I_ErrorMsg = ' ',"
			+ " I_IsImported = 'N' "
			+ "WHERE I_IsImported<>'Y' OR I_IsImported IS NULL");
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Reset=" + no);

		//	Set Optional BPartner
		sql = new StringBuffer ("UPDATE I_PROCESS i "
			+ "SET C_BPartner_ID=(SELECT C_BPartner_ID FROM C_BPartner p"
			+ " WHERE i.BPartner_Value=p.Value AND i.AD_Client_ID=p.AD_Client_ID) "
			+ "WHERE C_BPartner_ID IS NULL"
			+ " AND I_IsImported<>'Y'");//.append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("BPartner=" + no);
		//
		sql = new StringBuffer ("UPDATE I_PROCESS "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=Invalid BPartner,' "
			+ "WHERE C_BPartner_ID IS NULL"
			+ " AND I_IsImported<>'Y'");//.append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("Invalid BPartner=" + no);


		//	****	Find Product

		//	Value
		sql = new StringBuffer ("UPDATE I_PROCESS i "
			+ "SET M_Product_ID=(SELECT M_Product_ID FROM M_Product p"
	//		+ " WHERE i.M_PRODUCT_VALUE=p.Value AND i.AD_Client_ID=p.AD_Client_ID) "
			+ " WHERE i.M_PRODUCT_VALUE=p.Value ) "
			+ "WHERE M_Product_ID IS NULL"
			+ " AND I_IsImported='N'");//.append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		log.info("Product Existing Value=" + no);


        // **** Find product Substitute.
		sql = new StringBuffer ("UPDATE I_PROCESS i "
				+ "SET M_PRODUCTSUB_ID=(SELECT M_Product_ID FROM M_Product p"
				+ " WHERE i.M_PRODUCTSUB_VALUE=p.Value AND i.AD_Client_ID=p.AD_Client_ID) "
				+ "WHERE M_PRODUCTSUB_ID IS NULL"
				+ " AND I_IsImported='N'");//.append(clientCheck);
			no = DB.executeUpdate(sql.toString(), get_TrxName());
			log.info("Product Existing Value=" + no);		



		//	Mandatory Value
		sql = new StringBuffer ("UPDATE I_PROCESS i "
			+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||'ERR=No Mandatory Value,' "
			+ "WHERE M_PRODUCT_VALUE IS NULL"
			+ " AND I_IsImported<>'Y'");//.append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		if (no != 0)
			log.warning("No Mandatory Value=" + no);


		commit();
		
		//	-------------------------------------------------------------------
		int noInsert = 0;
		int noUpdate = 0;
		int noInsertPO = 0;
		int noUpdatePO = 0;

		//	Go through Records
		log.fine("start inserting/updating ...");
		sql = new StringBuffer ("SELECT * FROM I_PROCESS WHERE I_IsImported='N'");
			//.append(clientCheck);
		try
		{

			//	Insert Process from Import


			//	Set Imported = Y
			PreparedStatement pstmt_setImported = DB.prepareStatement
				("UPDATE I_PROCESS SET I_IsImported='Y', "
				+ "Updated=SysDate, Processed='Y' WHERE I_PROCESS_ID=? and CP_ProcessFlow_ID=? ", get_TrxName());

			//
			PreparedStatement pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				X_I_Process imp = new X_I_Process(getCtx(), rs, get_TrxName());
				int I_Process_ID = imp.getI_Process_ID();
				int CP_ProcessFlow_ID = imp.getCP_ProcessFlow_ID();
				int C_BPartner_ID = imp.getC_BPartner_ID();
				boolean newProcess = CP_ProcessFlow_ID == 0;
				log.fine("I_PROCESS_ID=" + I_Process_ID + ", CP_ProcessFlow_ID=" + CP_ProcessFlow_ID 
					+ ", C_BPartner_ID=" + C_BPartner_ID);

				//	Process
				if (newProcess)			//	Insert new Process
				{
					CP_ProcessFlow process = new CP_ProcessFlow(imp);
					
					if (process.save())
					{
						CP_ProcessFlow_ID = process.getCP_ProcessFlow_ID();
						process.setCUSTOMTEXT1(imp.getCustomText1());
						process.save();
						imp.setCP_ProcessFlow_ID(CP_ProcessFlow_ID);
						imp.save();
						System.out.println("importProcess: "+CP_ProcessFlow_ID);
						log.finer("Insert Process");
						noInsert++;
					}
					else
					{
						StringBuffer sql0 = new StringBuffer ("UPDATE I_PROCESS i "
							+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||").append(DB.TO_STRING("Insert Process failed"))
							.append("WHERE I_PROCESS_ID=").append(I_Process_ID);
						DB.executeUpdate(sql0.toString(), get_TrxName());
						continue;
					}
				}
				else					//	Update Process
				{
					String sqlt = "UPDATE TEK_PROCESSO_CHIAMATA "
						+ "SET (AD_CONFPROC_ID,M_PRODUCT_ID,M_PRODUCTSUB_ID"
						+ "TEK_STATO_PROCESSO_ID,DESCRIZIONE,C_BPARTNER_ID,SERNO,ORA_APERTURA, RIFERIMENTO_CLIENTE )="

						+ "(SELECT AD_CONFPROC_ID,M_PRODUCT_ID, M_PRODUCTSUB_ID,"
						+ "TEK_STATO_PROCESSO_ID,DESCRIZIONE,C_BPARTNER_ID,SERNO,"
						+ "ORA_APERTURA, IDPROC "
						+ " FROM I_PROCESS WHERE I_PROCESS_ID="+I_Process_ID+") "
						+ "WHERE TEK_PROCESSO_CHIAMATA_ID="+CP_ProcessFlow_ID;
					PreparedStatement pstmt_updateProcess = DB.prepareStatement
						(sqlt, get_TrxName());

					try
					{
						no = pstmt_updateProcess.executeUpdate();
						log.finer("Update Process = " + no);
						noUpdate++;
					}
					catch (SQLException ex)
					{
						log.warning("Update Process - " + ex.toString());
						StringBuffer sql0 = new StringBuffer ("UPDATE I_PROCESS i "
							+ "SET I_IsImported='E', I_ErrorMsg=I_ErrorMsg||").append(DB.TO_STRING("Update Process: " + ex.toString()))
							.append("WHERE I_PROCESS_ID=").append(I_Process_ID);
						DB.executeUpdate(sql0.toString(), get_TrxName());
						continue;
					}
					pstmt_updateProcess.close();
				}
			
				//	Update I_Process
				pstmt_setImported.setInt(1, I_Process_ID);
				pstmt_setImported.setInt(2, CP_ProcessFlow_ID);
				no = pstmt_setImported.executeUpdate();
				//
				commit();
			}	//	for all I_Process
			rs.close();
			pstmt.close();

		}
		catch (SQLException e)
		{
		}

		//	Set Error to indicator to not imported
		sql = new StringBuffer ("UPDATE I_PROCESS "
			+ "SET I_IsImported='N', Updated=SysDate "
			+ "WHERE I_IsImported<>'Y'");//.append(clientCheck);
		no = DB.executeUpdate(sql.toString(), get_TrxName());
		addLog (0, null, new BigDecimal (no), "@Errors@");
		addLog (0, null, new BigDecimal (noInsert), "@CP_ProcessFlow_ID@: @Inserted@");
		addLog (0, null, new BigDecimal (noUpdate), "@CP_ProcessFlow_ID@: @Updated@");
		return "";
	}	//	doIt

}	//	ImportProduct
