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
 * Adempiere, Inc.                                                            *
 *****************************************************************************/
package it.informaticagestionale.confproc.process;

import java.util.logging.*;
import org.compiere.model.*;
import it.informaticagestionale.confproc.model.*;
import org.compiere.util.*;
import org.compiere.process.*;

/**
 *	Copy columns from one table to other
 *	Copy Business Process Configurator
 *  @author Giorgio Cafasso
 *  @version $Id: CopyColumnsFromTable
 */
public class CopyConfig extends SvrProcess
{
	/** Target Table		*/
	private int		p_target_CP_ConfProc_ID = 0;
	/** Source Table		*/
	private int		p_source_CP_ConfProc_ID = 0;
	
	/** Column Count	*/
	private int 	m_count = 0;
	
	/** Cond Count	*/
	private int 	m_condcount = 0;
	
	/** ConfProc */
	private int confProcID = 0;
	
	/**
	 *  Prepare - e.g., get Parameters.
	 */
	protected void prepare ()
	{
		ProcessInfoParameter[] para = getParameter();
		for (int i = 0; i < para.length; i++)
		{
			String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
				;
			else if (name.equals("CP_ConfProc_ID"))
				p_source_CP_ConfProc_ID = para[i].getParameterAsInt();
			else
				log.log(Level.SEVERE, "Unknown Parameter: " + name);
		}
		p_target_CP_ConfProc_ID = getRecord_ID();
	}	//	prepare

	/**
	 * 	Process
	 *	@return info
	 *	@throws Exception
	 */
	protected String doIt () throws Exception
	{
		if (p_target_CP_ConfProc_ID == 0)
			throw new AdempiereSystemError("@NotFound@ @AD_ConfProc_ID@ " + p_target_CP_ConfProc_ID);
		if (p_source_CP_ConfProc_ID == 0)
			throw new AdempiereSystemError("@NotFound@ @AD_ConfProc_ID@ " + p_source_CP_ConfProc_ID);
		log.info("Source AD_ConfProc_ID=" + p_source_CP_ConfProc_ID
				+ ", Target AD_ConfProc_ID=" + p_target_CP_ConfProc_ID);
		
		CP_ConfProc targetProcess = new CP_ConfProc(getCtx(), p_target_CP_ConfProc_ID, get_TrxName());
		CP_ConfProcLine[] targetLine = targetProcess.getLines(true," CP_ConfProc_ID DESC");
		if (targetLine.length > 0)
			// TODO: dictionary message
			throw new AdempiereSystemError("Target Process must not have line");
		
		CP_ConfProc sourceProcess = new CP_ConfProc(getCtx(), p_source_CP_ConfProc_ID, get_TrxName());
		CP_ConfProcLine[] sourceLine = sourceProcess.getLines(true," Line ");
		
		for (int i = 0; i < sourceLine.length; i++)
		{
			CP_ConfProcLine lineTarget = new CP_ConfProcLine(targetProcess);

			//conditions tables virtualization

			/**CONFPROCLINE*/
//			lineTarget.setAD_Client_ID(sourceLine[i].getAD_Client_ID());
//			lineTarget.setAD_CONFPROC_ID(p_target_CP_ConfProc_ID);
//			lineTarget.setAD_Org_ID(sourceLine[i].getAD_Org_ID());
			
			//msg inserting
			lineTarget.setDescription(sourceLine[i].getDescription());
			if (lineTarget.getErrorMsg()!=null | lineTarget.getErrorMsg()!="")
				lineTarget.setErrorMsg(sourceLine[i].getErrorMsg());

            //line number
			if (lineTarget.getLine()!=null | lineTarget.getErrorMsg()!="")
			lineTarget.setForAllSubProcess(sourceLine[i].getForAllSubProcess());
			
			
			
			//insert flag (all subprocess and without-subprocess) 
			lineTarget.setForAllSubProcess(sourceLine[i].getForAllSubProcess());
			lineTarget.setNoSubProcess(sourceLine[i].getNoSubProcess());
			lineTarget.setIsCodeException(sourceLine[i].isCodeException());
				
			
			//insert state it.informaticagestionale.confproc.process e stato sub-it.informaticagestionale.confproc.process
			if (sourceLine[i].getCP_ProcessFlowState_ID()!=0)
			lineTarget.setCP_ProcessFlowState_ID(sourceLine[i].getCP_ProcessFlowState_ID());
			if (sourceLine[i].getCP_ProcessFlowStateTO_ID()!=0)
			lineTarget.setCP_ProcessFlowStateTO_ID(sourceLine[i].getCP_ProcessFlowStateTO_ID());
			if (sourceLine[i].getCP_ProcessSubFlowState_ID()!=0)
			lineTarget.setCP_ProcessSubFlowState_ID(sourceLine[i].getCP_ProcessSubFlowState_ID());
			if (sourceLine[i].getCP_ProcessSubFlowStateTO_ID()!=0)
			lineTarget.setCP_ProcessSubFlowStateTO_ID(sourceLine[i].getCP_ProcessSubFlowStateTO_ID());
			
			
			//insert table and location for Call_transiction
			if (sourceLine[i].getAD_Table_ID()!=0)
			 lineTarget.setAD_Table_ID(sourceLine[i].getAD_Table_ID());
			if (sourceLine[i].getM_WareHouse_ID()!=0)
				lineTarget.setM_WareHouse_ID(sourceLine[i].getM_WareHouse_ID());
			if (sourceLine[i].getM_WareHouseTO_ID()!=0)
				lineTarget.setM_WareHouseTO_ID(sourceLine[i].getM_WareHouseTO_ID());
			
			//lineTarget.setLine(sourceLine[i].getLine());
			lineTarget.setIsActive(sourceLine[i].isActive());
			confProcID=lineTarget.getCP_ConfProc_ID();
			lineTarget.save(get_TrxName());
			
			CP_ConfProCondition[] sourceCondLine = sourceLine[i].getLines(true," Line DESC ");
			for (int j = 0; j < sourceCondLine.length; j++)
			{
			
				CP_ConfProCondition lineCondTarget = new CP_ConfProCondition(lineTarget);
	//			lineCondTarget.setAD_Client_ID(sourceCondLine[j].getAD_Client_ID());
				lineCondTarget.setCP_ConfProc_ID(confProcID);
	//			lineCondTarget.setLine(sourceCondLine[j].getLine());
				lineCondTarget.setAD_Org_ID(sourceCondLine[j].getAD_Org_ID());
				lineCondTarget.setCP_ConfProcLine_ID(lineTarget.getCP_ConfProcLine_ID());
				//condizioni
				
				if (sourceCondLine[j].getAD_Table_ID()!=0)
					lineCondTarget.setAD_Table_ID(sourceCondLine[j].getAD_Table_ID());
				if (sourceCondLine[j].getCP_ConfProcOperation_ID()!=0)
					lineCondTarget.setCP_ConfProcOperation_ID(sourceCondLine[j].getCP_ConfProcOperation_ID());
				if (sourceCondLine[j].getAD_TableJoin_ID()!=0)
					lineCondTarget.setAD_TableJoin_ID(sourceCondLine[j].getAD_TableJoin_ID());
				if (sourceCondLine[j].getAD_Column_ID()!=0)
					lineCondTarget.setAD_Column_ID(sourceCondLine[j].getAD_Column_ID());
				if (sourceCondLine[j].getAD_ColumnJoin_ID()!=0)
					lineCondTarget.setAD_ColumnJoin_ID(sourceCondLine[j].getAD_ColumnJoin_ID());
				if (sourceCondLine[j].getValString()!=null |sourceCondLine[j].getValString()!="")
					lineCondTarget.setValString(sourceCondLine[j].getValString());
				if (sourceCondLine[j].getLogic()!=null |sourceCondLine[j].getLogic()!="")
					lineCondTarget.setLogic(sourceCondLine[j].getLogic());
				if (sourceCondLine[j].getValNum()!=0)
					lineCondTarget.setValNum(sourceCondLine[j].getValNum());
			
				lineCondTarget.setIsActive(sourceCondLine[j].isActive());
				lineCondTarget.save();
				m_condcount++;
			}
			
			// TODO: Copy translations
			m_count++;
		}
		
		//
		return "# linee " + m_count + " # condizioni " + m_condcount;
	}	//	doIt

	
}	//	CopyColumnsFromTable