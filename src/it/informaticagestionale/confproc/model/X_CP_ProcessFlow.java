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
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for CP_ProcessFlow
 *  @author Adempiere (generated) 
 *  @version Release 3.4.0s - $Id$ */
public class X_CP_ProcessFlow extends PO implements I_CP_ProcessFlow, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    /** Standard Constructor */
    public X_CP_ProcessFlow (Properties ctx, int CP_ProcessFlow_ID, String trxName)
    {
      super (ctx, CP_ProcessFlow_ID, trxName);
      /** if (CP_ProcessFlow_ID == 0)
        {
			setCP_ProcessFlow_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CP_ProcessFlow (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CP_ProcessFlow[")
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

	/** Set ProcessFlow.
		@param CP_ProcessFlow_ID ProcessFlow	  */
	public void setCP_ProcessFlow_ID (int CP_ProcessFlow_ID)
	{
		if (CP_ProcessFlow_ID < 1)
			 throw new IllegalArgumentException ("CP_ProcessFlow_ID is mandatory.");
		set_ValueNoCheck (COLUMNNAME_CP_ProcessFlow_ID, Integer.valueOf(CP_ProcessFlow_ID));
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
	
	
		/** Set CP_CONFPROC_ID.
		@param CP_CONFPROC_ID CP_CONFPROC_ID	  */
		public void setCP_ConfProc_ID (int CP_ConfProc_ID)
		{
			if (CP_ConfProc_ID < 1)
				 throw new IllegalArgumentException ("CP_ConfProc_ID is mandatory.");
			set_ValueNoCheck (COLUMNNAME_CP_ConfProc_ID, Integer.valueOf(CP_ConfProc_ID));
		}
	
	/** Get ProcessFlow.
		@return ProcessFlow	  */
	public int getCP_ConfProc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CP_ConfProc_ID);
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

	public I_M_Product getM_Product() throws Exception 
    {
        Class<?> clazz = MTable.getClass(I_M_Product.Table_Name);
        I_M_Product result = null;
        try	{
	        Constructor<?> constructor = null;
	    	constructor = clazz.getDeclaredConstructor(new Class[]{Properties.class, int.class, String.class});
    	    result = (I_M_Product)constructor.newInstance(new Object[] {getCtx(), new Integer(getM_Product_ID()), get_TrxName()});
        } catch (Exception e) {
	        log.log(Level.SEVERE, "(id) - Table=" + Table_Name + ",Class=" + clazz, e);
	        log.saveError("Error", "Table=" + Table_Name + ",Class=" + clazz);
           throw e;
        }
        return result;
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ONE;
		return bd;
	}

	/** Set Total Lines.
		@param TotalLines 
		Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines)
	{
		set_Value (COLUMNNAME_TotalLines, TotalLines);
	}

	/** Get Total Lines.
		@return Total of all document lines
	  */
	public BigDecimal getTotalLines () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_TotalLines);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public void setIdDoc(int idDoc) {
		if (idDoc < 1) 
			set_Value (COLUMNNAME_idDoc, null);
		else 
			set_Value (COLUMNNAME_idDoc, Integer.valueOf(idDoc));		
	}

	@Override
	public int getIdDoc() {
		Integer ii = (Integer)get_Value(COLUMNNAME_idDoc);
		if (ii == null)
			 return 0;
		return ii.intValue();

	}

	@Override
	public void setIdDocLine(int idDocLine) {
		if (idDocLine < 1) 
			set_Value (COLUMNNAME_idDocLine, null);
		else 
			set_Value (COLUMNNAME_idDocLine, Integer.valueOf(idDocLine));	
		
	}

	@Override
	public int getIdDocLine() {
		Integer ii = (Integer)get_Value(COLUMNNAME_idDocLine);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document Type.
		@param C_DocType_ID 
		Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_C_DocType_ID, null);
		else 
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

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return (org.compiere.model.I_C_DocType)MTable.get(getCtx(), org.compiere.model.I_C_DocType.Table_Name)
			.getPO(getC_DocType_ID(), get_TrxName());	}

}