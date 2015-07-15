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
import java.math.BigDecimal;
import org.compiere.util.Env;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for CP_ProcessHistory
 *  @author Adempiere (generated) 
 *  @version Release 3.4.0s - $Id$ */
public class X_CP_ProcessHistory extends PO implements I_CP_ProcessHistory, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * C_DocType_ID AD_Reference_ID=170 
	 */
	public static final int C_DOCTYPE_ID_AD_Reference_ID = 170;

    /** Standard Constructor */
    public X_CP_ProcessHistory (Properties ctx, int CP_ProcessHistory_ID, String trxName)
    {
      super (ctx, CP_ProcessHistory_ID, trxName);
      /** if (CP_ProcessHistory_ID == 0)
        {
			setCP_ProcessFlow_ID (0);
			setCP_ProcessHistory_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CP_ProcessHistory (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CP_ProcessHistory[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_User getAD_User() throws Exception 
    {
        Class<?> clazz = MTable.getClass(I_AD_User.Table_Name);
        I_AD_User result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (I_AD_User)constructor.newInstance(new Object[] {getCtx(), new Integer(getAD_User_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
    }

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0) 
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CP_ProcessFlowState getCP_ProcessFlowState() throws Exception 
    {
        Class<?> clazz = MTable.getClass(I_CP_ProcessFlowState.Table_Name);
        I_CP_ProcessFlowState result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (I_CP_ProcessFlowState)constructor.newInstance(new Object[] {getCtx(), new Integer(getCP_ProcessFlowState_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
    }

	/** Set ProcessFlowState.
		@param CP_ProcessFlowState_ID ProcessFlowState	  */
	public void setCP_ProcessFlowState_ID (int CP_ProcessFlowState_ID)
	{
		if (CP_ProcessFlowState_ID < 1) 
			set_Value (COLUMNNAME_CP_ProcessFlowState_ID, null);
		else 
			set_Value (COLUMNNAME_CP_ProcessFlowState_ID, Integer.valueOf(CP_ProcessFlowState_ID));
	}

	/** Get ProcessFlowState.
		@return ProcessFlowState	  */
	public int getCP_ProcessFlowState_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CP_ProcessFlowState_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CP_ProcessFlow getCP_ProcessFlow() throws Exception 
    {
        Class<?> clazz = MTable.getClass(I_CP_ProcessFlow.Table_Name);
        I_CP_ProcessFlow result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (I_CP_ProcessFlow)constructor.newInstance(new Object[] {getCtx(), new Integer(getCP_ProcessFlow_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
    }

	/** Set ProcessFlow.
		@param CP_ProcessFlow_ID ProcessFlow	  */
	public void setCP_ProcessFlow_ID (int CP_ProcessFlow_ID)
	{
		if (CP_ProcessFlow_ID < 1)
			 throw new IllegalArgumentException ("CP_ProcessFlow_ID is mandatory.");
		set_Value (COLUMNNAME_CP_ProcessFlow_ID, Integer.valueOf(CP_ProcessFlow_ID));
	}

	/** Get ProcessFlow.
		@return ProcessFlow	  */
	public int getCP_ProcessFlow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CP_ProcessFlow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set ProcessHistory.
		@param CP_ProcessHistory_ID ProcessHistory	  */
	public void setCP_ProcessHistory_ID (int CP_ProcessHistory_ID)
	{
		if (CP_ProcessHistory_ID < 1)
			 throw new IllegalArgumentException ("CP_ProcessHistory_ID is mandatory.");
		set_ValueNoCheck (COLUMNNAME_CP_ProcessHistory_ID, Integer.valueOf(CP_ProcessHistory_ID));
	}

	/** Get ProcessHistory.
		@return ProcessHistory	  */
	public int getCP_ProcessHistory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CP_ProcessHistory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CP_ProcessSubFlowState getCP_ProcessSubFlowState() throws Exception 
    {
        Class<?> clazz = MTable.getClass(I_CP_ProcessSubFlowState.Table_Name);
        I_CP_ProcessSubFlowState result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (I_CP_ProcessSubFlowState)constructor.newInstance(new Object[] {getCtx(), new Integer(getCP_ProcessSubFlowState_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
    }

	/** Set ProcessSubFlowState.
		@param CP_ProcessSubFlowState_ID ProcessSubFlowState	  */
	public void setCP_ProcessSubFlowState_ID (int CP_ProcessSubFlowState_ID)
	{
		if (CP_ProcessSubFlowState_ID < 1) 
			set_Value (COLUMNNAME_CP_ProcessSubFlowState_ID, null);
		else 
			set_Value (COLUMNNAME_CP_ProcessSubFlowState_ID, Integer.valueOf(CP_ProcessSubFlowState_ID));
	}

	/** Get ProcessSubFlowState.
		@return ProcessSubFlowState	  */
	public int getCP_ProcessSubFlowState_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CP_ProcessSubFlowState_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CUSTOMDATE1.
		@param CUSTOMDATE1 CUSTOMDATE1	  */
	public void setCUSTOMDATE1 (Timestamp CUSTOMDATE1)
	{
		set_Value (COLUMNNAME_CUSTOMDATE1, CUSTOMDATE1);
	}

	/** Get CUSTOMDATE1.
		@return CUSTOMDATE1	  */
	public Timestamp getCUSTOMDATE1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CUSTOMDATE1);
	}

	/** Set CUSTOMDATE2.
		@param CUSTOMDATE2 CUSTOMDATE2	  */
	public void setCUSTOMDATE2 (Timestamp CUSTOMDATE2)
	{
		set_Value (COLUMNNAME_CUSTOMDATE2, CUSTOMDATE2);
	}

	/** Get CUSTOMDATE2.
		@return CUSTOMDATE2	  */
	public Timestamp getCUSTOMDATE2 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_CUSTOMDATE2);
	}

	/** Set CUSTOMNUM1.
		@param CUSTOMNUM1 CUSTOMNUM1	  */
	public void setCUSTOMNUM1 (BigDecimal CUSTOMNUM1)
	{
		set_Value (COLUMNNAME_CUSTOMNUM1, CUSTOMNUM1);
	}

	/** Get CUSTOMNUM1.
		@return CUSTOMNUM1	  */
	public BigDecimal getCUSTOMNUM1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CUSTOMNUM1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CUSTOMNUM2.
		@param CUSTOMNUM2 CUSTOMNUM2	  */
	public void setCUSTOMNUM2 (BigDecimal CUSTOMNUM2)
	{
		set_Value (COLUMNNAME_CUSTOMNUM2, CUSTOMNUM2);
	}

	/** Get CUSTOMNUM2.
		@return CUSTOMNUM2	  */
	public BigDecimal getCUSTOMNUM2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CUSTOMNUM2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set CUSTOMTEXT1.
		@param CUSTOMTEXT1 CUSTOMTEXT1	  */
	public void setCUSTOMTEXT1 (String CUSTOMTEXT1)
	{

		if (CUSTOMTEXT1 != null && CUSTOMTEXT1.length() > 100)
		{
			log.warning("Length > 100 - truncated");
			CUSTOMTEXT1 = CUSTOMTEXT1.substring(0, 100);
		}
		set_Value (COLUMNNAME_CUSTOMTEXT1, CUSTOMTEXT1);
	}

	/** Get CUSTOMTEXT1.
		@return CUSTOMTEXT1	  */
	public String getCUSTOMTEXT1 () 
	{
		return (String)get_Value(COLUMNNAME_CUSTOMTEXT1);
	}

	/** Set CUSTOMTEXT2.
		@param CUSTOMTEXT2 CUSTOMTEXT2	  */
	public void setCUSTOMTEXT2 (String CUSTOMTEXT2)
	{

		if (CUSTOMTEXT2 != null && CUSTOMTEXT2.length() > 100)
		{
			log.warning("Length > 100 - truncated");
			CUSTOMTEXT2 = CUSTOMTEXT2.substring(0, 100);
		}
		set_Value (COLUMNNAME_CUSTOMTEXT2, CUSTOMTEXT2);
	}

	/** Get CUSTOMTEXT2.
		@return CUSTOMTEXT2	  */
	public String getCUSTOMTEXT2 () 
	{
		return (String)get_Value(COLUMNNAME_CUSTOMTEXT2);
	}

	public I_C_BPartner getC_BPartner() throws Exception 
    {
        Class<?> clazz = MTable.getClass(I_C_BPartner.Table_Name);
        I_C_BPartner result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (I_C_BPartner)constructor.newInstance(new Object[] {getCtx(), new Integer(getC_BPartner_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
    }

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
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
	
		if (Description != null && Description.length() > 100)
		{
			log.warning("Length > 100 - truncated");
			Description = Description.substring(0, 100);
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
	
		if (Help != null && Help.length() > 100)
		{
			log.warning("Length > 100 - truncated");
			Help = Help.substring(0, 100);
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
	
		if (Name != null && Name.length() > 120)
		{
			log.warning("Length > 120 - truncated");
			Name = Name.substring(0, 120);
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

	/** Set Document number
	@param DocumentNo 
	  */
	public void setDocumentNo (String DocumentNo)
	{
		if (DocumentNo != null && DocumentNo.length() > 255)
		{
			log.warning("Length > 255 - truncated");
			DocumentNo = DocumentNo.substring(0, 254);
		}
		set_Value ("DocumentNo", DocumentNo);
	}

	/** Get Document Number
		@return DocumentNo
	  */
	public String getDocumentNo () 
	{
		return (String)get_Value("DocumentNo");
		
	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_DocType getC_DocType() throws Exception 
	{
	    Class<?> clazz = MTable.getClass(I_C_DocType.Table_Name);
	    I_C_DocType result = null;
	    try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
		    result = (I_C_DocType)constructor.newInstance(new Object[] {getCtx(), new Integer(getC_DocType_ID()), get_TrxName()});
	    } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
	       throw e;
	    }
	    return result;
	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0)
			 throw new IllegalArgumentException ("C_DocType_ID is mandatory.");
		set_ValueNoCheck (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Document Type.
		@return Document type or rules
	  */
	public int getC_DocType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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