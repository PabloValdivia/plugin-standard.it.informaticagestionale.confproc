/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
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
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package it.informaticagestionale.confproc.model;

import java.lang.reflect.Constructor;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.*;
import java.math.BigDecimal;
import org.compiere.util.Env;

/** Generated Model for CP_ConfProcLine
 *  @author Adempiere (generated) 
 *  @version Release 3.4.0s - $Id$ */
public class X_CP_ConfProcLine extends PO implements I_CP_ConfProcLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    /** Standard Constructor */
    public X_CP_ConfProcLine (Properties ctx, int CP_ConfProcLine_ID, String trxName)
    {
      super (ctx, CP_ConfProcLine_ID, trxName);
      /** if (CP_ConfProcLine_ID == 0)
        {
			setCP_ConfProcLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CP_ConfProcLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_CP_ConfProcLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Table getAD_Table() throws Exception 
    {
        Class<?> clazz = MTable.getClass(I_AD_Table.Table_Name);
        I_AD_Table result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (I_AD_Table)constructor.newInstance(new Object[] {getCtx(), new Integer(getAD_Table_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
    }

	/** Set Table.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get Table.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CP_ConProcException getCP_ConProcException() throws Exception 
    {
        Class<?> clazz = MTable.getClass(I_CP_ConProcException.Table_Name);
        I_CP_ConProcException result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (I_CP_ConProcException)constructor.newInstance(new Object[] {getCtx(), new Integer(getCP_ConProcException_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
    }

	/** Set ConProcException.
		@param CP_ConProcException_ID ConProcException	  */
	public void setCP_ConProcException_ID (int CP_ConProcException_ID)
	{
		if (CP_ConProcException_ID < 1) 
			set_Value (COLUMNNAME_CP_ConProcException_ID, null);
		else 
			set_Value (COLUMNNAME_CP_ConProcException_ID, Integer.valueOf(CP_ConProcException_ID));
	}

	/** Get ConProcException.
		@return ConProcException	  */
	public int getCP_ConProcException_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CP_ConProcException_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ConfProcLine.
		@param CP_ConfProcLine_ID ConfProcLine	  */
	public void setCP_ConfProcLine_ID (int CP_ConfProcLine_ID)
	{
		if (CP_ConfProcLine_ID < 1)
			 throw new IllegalArgumentException ("CP_ConfProcLine_ID is mandatory.");
		set_ValueNoCheck (COLUMNNAME_CP_ConfProcLine_ID, Integer.valueOf(CP_ConfProcLine_ID));
	}

	/** Get ConfProcLine.
		@return ConfProcLine	  */
	public int getCP_ConfProcLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CP_ConfProcLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/** Set CP_ConfProc.
	@param CP_ConfProcLine_ID CP_ConfProc	  */
	public void setCP_ConfProc_ID (int CP_ConfProc_ID)
	{
		if (CP_ConfProc_ID < 1)
			 throw new IllegalArgumentException ("CP_ConfProc_ID is mandatory.");
		set_ValueNoCheck ("CP_ConfProc_ID", Integer.valueOf(CP_ConfProc_ID));
	}
	
	/** Get CP_ConfProc.
		@return CP_ConfProc	  */
	public int getCP_ConfProc_ID () 
	{
		Integer ii = (Integer)get_Value("CP_ConfProc_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	


	/** Set CP_ProcessFlowState_ID.
	@param CP_ProcessFlowState_ID 	  */
	public void setCP_ProcessFlowState_ID(int CP_ProcessFlowState_ID)
	{
		if (CP_ProcessFlowState_ID < 1)
			 throw new IllegalArgumentException ("CP_ProcessFlowState_ID is mandatory.");
		set_ValueNoCheck ("CP_ProcessFlowState_ID", Integer.valueOf(CP_ProcessFlowState_ID));
	}
	
	/** Get CP_ProcessFlowState_ID
		@return CP_ProcessFlowState_ID	  */
	public int getCP_ProcessFlowState_ID () 
	{
		Integer ii = (Integer)get_Value("CP_ProcessFlowState_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/** Set CP_ProcessFlowStateTO_ID.
	@param CP_ProcessFlowStateTO_ID 	  */
	public void setCP_ProcessFlowStateTO_ID (int CP_ProcessFlowStateTO_ID)
	{
		if (CP_ProcessFlowStateTO_ID < 1)
			 throw new IllegalArgumentException ("CP_ProcessFlowStateTO_ID is mandatory.");
		set_ValueNoCheck ("CP_ProcessFlowStateTO_ID", Integer.valueOf(CP_ProcessFlowStateTO_ID));
	}
	
	/** Get CP_ProcessFlowStateTO_ID
		@return CP_ProcessFlowStateTO_ID	  */
	public int getCP_ProcessFlowStateTO_ID () 
	{
		Integer ii = (Integer)get_Value("CP_ProcessFlowStateTO_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/** Set CP_ProcessSubFlowState_ID.
	@param CP_ProcessSubFlowState_ID 	  */
	public void setCP_ProcessSubFlowState_ID (int CP_ProcessSubFlowState_ID)
	{
		if (CP_ProcessSubFlowState_ID < 1)
			 throw new IllegalArgumentException ("CP_ProcessSubFlowState_ID is mandatory.");
		set_ValueNoCheck ("CP_ProcessSubFlowState_ID", Integer.valueOf(CP_ProcessSubFlowState_ID));
	}
	
	/** Get CP_ProcessSubFlowState_ID
		@return CP_ProcessSubFlowState_ID	  */
	public int getCP_ProcessSubFlowState_ID() 
	{
		Integer ii = (Integer)get_Value("CP_ProcessSubFlowState_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/** Set CP_ProcessSubFlowStateTO_ID.
	@param CP_ProcessSubFlowStateTO_ID 	  */
	public void setCP_ProcessSubFlowStateTO_ID (int CP_ProcessSubFlowStateTO_ID)
	{
		if (CP_ProcessSubFlowStateTO_ID < 1)
			 throw new IllegalArgumentException ("CP_ProcessSubFlowStateTO_ID is mandatory.");
		set_ValueNoCheck ("CP_ProcessSubFlowStateTO_ID", Integer.valueOf(CP_ProcessSubFlowStateTO_ID));
	}
	
	/** Get CP_ProcessSubFlowStateTO_ID
		@return CP_ProcessSubFlowStateTO_ID	  */
	public int getCP_ProcessSubFlowStateTO_ID () 
	{
		Integer ii = (Integer)get_Value("CP_ProcessSubFlowStateTO_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/** Set NoSubProcess.
	@param NoSubProcess 
	 */
	public void setNoSubProcess (boolean noSubProcess)
	{
		set_Value ("noSubProcess", new Boolean(noSubProcess));
	}
	
	/** Get NoSubProcess.
		@return NoSubProcess
	  */
	public boolean getNoSubProcess () 
	{
		Object oo = get_Value("noSubProcess");
		if (oo != null) 
		{
		 if (oo instanceof Boolean) return ((Boolean)oo).booleanValue();
		 return "Y".equals(oo);
		}
		return false;
	}
	
	/** Set forAllSubProcess.
	@param forAllSubProcess 
	 */
	public void setForAllSubProcess (boolean forAllSubProcess)
	{
		set_Value ("forAllSubProcess", new Boolean(forAllSubProcess));
	}
	
	/** Get forAllSubProcess.
		@return ForAllSubProcess
	  */
	public boolean getForAllSubProcess () 
	{
		Object oo = get_Value("forAllSubProcess");
		if (oo != null) 
		{
		 if (oo instanceof Boolean) return ((Boolean)oo).booleanValue();
		 return "Y".equals(oo);
		}
		return false;
	}
	
	
	/** Set ErrorMsg.
	@param ErrorMsg 
	Error message
	 */
	public void setErrorMsg (String ErrorMsg)
	{
		if (ErrorMsg != null && ErrorMsg.length() > 255)
		{
			log.warning("Length > 255 - truncated");
			ErrorMsg = ErrorMsg.substring(0, 254);
		}
		set_Value ("errorMsg", ErrorMsg);
	}
	
	/** Get ErrorMsg.
		@return Error message
	  */
	public String getErrorMsg () 
	{
		return (String)get_Value("errorMsg");
	}
	
	/** Set Description.
	@param Description 
	Description message
	 */
	public void setDescription (String Description)
	{
		if (Description != null && Description.length() > 255)
		{
			log.warning("Length > 255 - truncated");
			Description = Description.substring(0, 254);
		}
		set_Value ("Description", Description);
	}

	/** Get Description.
	@return Description
  */
public String getDescription () 
{
	return (String)get_Value("Description");
}
	
	
	/** Set M_WareHouse_ID.
	@param M_WareHouse_ID 	  */
	public void setM_WareHouse_ID (int M_WareHouse_ID)
	{
		if (M_WareHouse_ID < 1)
			 throw new IllegalArgumentException ("M_WareHouse_ID is mandatory.");
		set_ValueNoCheck ("M_WareHouse_ID", Integer.valueOf(M_WareHouse_ID));
	}
	
	/** Get M_WareHouse_ID
		@return M_WareHouse_ID	  */
	public int getM_WareHouse_ID () 
	{
		Integer ii = (Integer)get_Value("M_WareHouse_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/** Set M_WareHouseTO_ID.
	@param M_WareHouseTO_ID 	  */
	public void setM_WareHouseTO_ID (int M_WareHouseTO_ID)
	{
		if (M_WareHouseTO_ID < 1)
			 throw new IllegalArgumentException ("M_WareHouseTO_ID is mandatory.");
		set_ValueNoCheck ("M_WareHouseTO_ID", Integer.valueOf(M_WareHouseTO_ID));
	}

	/** Get M_WareHouseTO_ID
		@return M_WareHouseTO_ID	  */
	public int getM_WareHouseTO_ID () 
	{
		Integer ii = (Integer)get_Value("M_WareHouseTO_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Get Sales Transaction.
	@return This is a Sales Transaction
	 */
	public boolean isSOTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSOTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
	
	/** Set IsCodeException.
	@param IsCodeException IsCodeException */
	public void setIsCodeException (boolean IsCodeException)
	{
	set_Value (COLUMNNAME_IsCodeException, new Boolean(IsCodeException));
	}
	/** Get IsCodeException.
	@return IsCodeException */
	public boolean isCodeException() 
	{
	Object oo = get_Value(COLUMNNAME_IsCodeException);
	if (oo != null) 
	{
		 if (oo instanceof Boolean) return ((Boolean)oo).booleanValue();
		 	return "Y".equals(oo);
		}
	return false;
	}

	/** Set Line No.
		@param Line 
		Unique line for this document
	  */
	public void setLine (BigDecimal Line)
	{
		set_Value (COLUMNNAME_Line, Line);
	}

	/** Get Line No.
		@return Unique line for this document
	  */
	public BigDecimal getLine () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Line);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	
	
	
}