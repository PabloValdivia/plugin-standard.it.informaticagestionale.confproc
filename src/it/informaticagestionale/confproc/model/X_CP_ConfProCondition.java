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

import java.sql.ResultSet;import java.math.BigDecimal;
import org.compiere.util.Env;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for CP_ConfProCondition
 *  @author Adempiere (generated) 
 *  @version Release 3.4.0s - $Id$ */
public class X_CP_ConfProCondition extends PO implements I_CP_ConfProCondition, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    /** Standard Constructor */
    public X_CP_ConfProCondition (Properties ctx, int CP_ConfProCondition_ID, String trxName)
    {
      super (ctx, CP_ConfProCondition_ID, trxName);
      /** if (CP_ConfProCondition_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_CP_ConfProCondition (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CP_ConfProCondition[")
        .append(get_ID()).append("]");
      return sb.toString();
    }
    
    /** Set Conf Proc Line Id Type.
	@param CP_ConfProcLine_ID 
  */
	public void setCP_ConfProcLine_ID (int CP_ConfProcLine_ID)
	{
		if (CP_ConfProcLine_ID < 0)
			 throw new IllegalArgumentException ("CP_ConfProcLine_ID is mandatory.");
		set_Value ("CP_ConfProcLine_ID", Integer.valueOf(CP_ConfProcLine_ID));
	}
	
	/** Get Conf Proc Line Id Type.
		@return Conf Proc line id
	  */
	public int getCP_ConfProcLine_ID () 
	{
		Integer ii = (Integer)get_Value("CP_ConfProcLine_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	 /** Set Conf Proc operation Id Type.
	@param CP_ConfProcOperation_ID 
  */
	public void setCP_ConfProcOperation_ID (int CP_ConfProcOperation_ID)
	{
		if (CP_ConfProcOperation_ID < 0)
			 throw new IllegalArgumentException ("CP_ConfProcOperation_ID is mandatory.");
		set_Value ("CP_ConfProcOperation_ID", Integer.valueOf(CP_ConfProcOperation_ID));
	}
	
	/** Get Conf Proc operation Id Type.
		@return Conf Proc operation id
	  */
	public int getCP_ConfProcOperation_ID () 
	{
		Integer ii = (Integer)get_Value("CP_ConfProcOperation_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	 /** Set id della colonna della join.
	@param AD_ColumnJoin_ID 
	  */
	public void setAD_ColumnJoin_ID (int AD_ColumnJoin_ID)
	{
		if (AD_ColumnJoin_ID < 0)
			 throw new IllegalArgumentException ("AD_ColumnJoin_ID is mandatory.");
		set_Value ("AD_ColumnJoin_ID", Integer.valueOf(AD_ColumnJoin_ID));
	}
	
	/** Get Colonna della join
		@return AD_ColumnJoin_ID
	  */
	public int getAD_ColumnJoin_ID () 
	{
		Integer ii = (Integer)get_Value("AD_ColumnJoin_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	 /** Set id della tabella della join.
	@param AD_TableJoin_ID 
	  */
	public void setAD_TableJoin_ID (int AD_TableJoin_ID)
	{
		if (AD_TableJoin_ID < 0)
			 throw new IllegalArgumentException ("AD_TableJoin_ID is mandatory.");
		set_Value ("AD_TableJoin_ID", Integer.valueOf(AD_TableJoin_ID));
	}
	
	/** Get tabella della join
		@return AD_TableJoin_ID
	  */
	public int getAD_TableJoin_ID () 
	{
		Integer ii = (Integer)get_Value("AD_TableJoin_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/** Set id della tabella condizione.
	@param AD_Table_ID 
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 0)
			 throw new IllegalArgumentException ("AD_Table_ID is mandatory.");
		set_Value ("AD_Table_ID", Integer.valueOf(AD_Table_ID));
	}
	
	/** Get tabella della condizione
		@return AD_Table_ID
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value("AD_Table_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	
	/** Set id della colonna condizione.
	@param AD_Column_ID 
	  */
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 0)
			 throw new IllegalArgumentException ("AD_Column_ID is mandatory.");
		set_Value ("AD_Column_ID", Integer.valueOf(AD_Column_ID));
	}
	
	/** Get colonna della condizione
		@return AD_Column_ID
	  */
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value("AD_Column_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	
	 /** Set Valore Stringa della condizione
	@param ValString 
	  */
	public void setValString (String ValString)
	{
		if (ValString != null && ValString.length() > 255)
		{
			log.warning("Length > 255 - truncated");
			ValString = ValString.substring(0, 254);
		}
		set_Value ("ValString", ValString);
	}
	
	/** Get Valore Stringa della condizione
		@return ValString
	  */
	public String getValString () 
	{
		return (String)get_Value("ValString");
		
	}
	
	
	
	/** Set valore numerico della condizione 
	@param ValNum 
	  */
	public void setValNum (int ValNum)
	{
		if (ValNum < 0)
			 throw new IllegalArgumentException ("ValNum is mandatory.");
		set_Value ("ValNum", Integer.valueOf(ValNum));
	}
	
	/** Get valore numerico della condizione 
		@return ValNum
	  */
	public int getValNum () 
	{
		Integer ii = (Integer)get_Value("ValNum");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/** Set logica dell'operazione
	@param logic 
	  */
	public void setLogic (String logic)
	{
		if (logic != null && logic.length() > 1)
		{
			log.warning("Length > 1 - truncated");
			logic = logic.substring(0, 1);
		}
		set_Value ("logic", logic);
	}
	
	/** Get logica dell'operazione 
		@return logic
	  */
	public String getLogic () 
	{
		return (String)get_Value("logic");
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
	
	
	
	
	
	
	
}