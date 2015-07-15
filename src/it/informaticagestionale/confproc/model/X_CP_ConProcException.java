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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;

/** Generated Model for CP_ConProcException
 *  @author Adempiere (generated) 
 *  @version Release 3.4.0s - $Id$ */
public class X_CP_ConProcException extends PO implements I_CP_ConProcException, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    /** Standard Constructor */
    public X_CP_ConProcException (Properties ctx, int CP_ConProcException_ID, String trxName)
    {
      super (ctx, CP_ConProcException_ID, trxName);
      /** if (CP_ConProcException_ID == 0)
        {
			setCP_ConProcException_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CP_ConProcException (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CP_ConProcException[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set ConProcException.
		@param CP_ConProcException_ID ConProcException	  */
	public void setCP_ConProcException_ID (int CP_ConProcException_ID)
	{
		if (CP_ConProcException_ID < 1)
			 throw new IllegalArgumentException ("CP_ConProcException_ID is mandatory.");
		set_ValueNoCheck (COLUMNNAME_CP_ConProcException_ID, Integer.valueOf(CP_ConProcException_ID));
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
	
	/** Set CP_ProcessFlowState_ID.
	@param CP_ProcessFlowState_ID CP_ProcessFlowState	  */
	public void setCP_ProcessFlowState_ID (int CP_ProcessFlowState_ID)
	{
		if (CP_ProcessFlowState_ID < 1)
			 throw new IllegalArgumentException ("CP_ProcessFlowState_ID is mandatory.");
		set_ValueNoCheck ("CP_ProcessFlowState_ID", Integer.valueOf(CP_ProcessFlowState_ID));
	}
	
	/** Get CP_ProcessFlowState_ID.
		@return CP_ProcessFlowState_ID	  */
	public int getCP_ProcessFlowState_ID() 
	{
		Integer ii = (Integer)get_Value("CP_ProcessFlowState_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/** Set CP_ProcessFlowStateTO_ID.
	@param CP_ProcessFlowStateTO_ID CP_ProcessFlowState	  */
	public void setCP_ProcessFlowStateTO_ID (int CP_ProcessFlowStateTO_ID)
	{
		if (CP_ProcessFlowStateTO_ID < 1)
			 throw new IllegalArgumentException ("CP_ProcessFlowStateTO_ID is mandatory.");
		set_ValueNoCheck ("CP_ProcessFlowStateTO_ID", Integer.valueOf(CP_ProcessFlowStateTO_ID));
	}
	
	/** Get CP_ProcessFlowStateTO_ID.
		@return CP_ProcessFlowStateTO_ID	  */
	public int getCP_ProcessFlowStateTO_ID() 
	{
		Integer ii = (Integer)get_Value("CP_ProcessFlowStateTO_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	
	/** Set CP_ProcessSubFlowState_ID .
	@param CP_ProcessSubFlowState_ID  CP_ProcessSubFlowState	  */
	public void setCP_ProcessSubFlowState_ID (int CP_ProcessSubFlowState_ID)
	{
		if (CP_ProcessSubFlowState_ID < 1)
			 throw new IllegalArgumentException ("CP_ProcessSubFlowState_ID is mandatory.");
		set_ValueNoCheck ("CP_ProcessSubFlowState_ID", Integer.valueOf(CP_ProcessSubFlowState_ID));
	}
	
	/** Get getCP_ProcessSubFlowState_ID.
		@return getCP_ProcessSubFlowState_ID	  */
	public int getCP_ProcessSubFlowState_ID() 
	{
		Integer ii = (Integer)get_Value("CP_ProcessSubFlowState_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/** Set CP_ProcessSubFlowStateTO_ID.
	@param CP_ProcessSubFlowStateTO_ID CP_ProcessSubFlowStateTO_ID	  */
	public void setCP_ProcessSubFlowStateTO_ID (int CP_ProcessSubFlowStateTO_ID)
	{
		if (CP_ProcessSubFlowStateTO_ID < 1)
			 throw new IllegalArgumentException ("CP_ProcessSubFlowStateTO_ID is mandatory.");
		set_ValueNoCheck ("CP_ProcessSubFlowStateTO_ID", Integer.valueOf(CP_ProcessSubFlowStateTO_ID));
	}
	
	/** Get CP_ProcessSubFlowStateTO_ID.
		@return CP_ProcessSubFlowStateTO_ID	  */
	public int getCP_ProcessSubFlowStateTO_ID() 
	{
		Integer ii = (Integer)get_Value("CP_ProcessSubFlowStateTO_ID");
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
	
		if (Description != null && Description.length() > 255)
		{
			log.warning("Length > 255 - truncated");
			Description = Description.substring(0, 255);
		}
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
	
		if (Help != null && Help.length() > 2000)
		{
			log.warning("Length > 2000 - truncated");
			Help = Help.substring(0, 2000);
		}
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		if (Name == null)
			throw new IllegalArgumentException ("Name is mandatory.");
	
		if (Name.length() > 60)
		{
			log.warning("Length > 60 - truncated");
			Name = Name.substring(0, 60);
		}
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}
	
	
	
}