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

/** Generated Model for CP_ConfProc
 *  @author Adempiere (generated) 
 *  @version Release 3.4.0s - $Id$ */
public class X_CP_ConfProc extends PO implements I_CP_ConfProc, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    /** Standard Constructor */
    public X_CP_ConfProc (Properties ctx, int CP_ConfProc_ID, String trxName)
    {
      super (ctx, CP_ConfProc_ID, trxName);
      /** if (CP_ConfProc_ID == 0)
        {
			setCP_ConfProc_ID (0);
        } */
    }

    /** Load Constructor */
    public X_CP_ConfProc (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CP_ConfProc[")
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

	/** Set ConfProc.
		@param CP_ConfProc_ID ConfProc	  */
	public void setCP_ConfProc_ID (int CP_ConfProc_ID)
	{
		if (CP_ConfProc_ID < 1)
			 throw new IllegalArgumentException ("CP_ConfProc_ID is mandatory.");
		set_ValueNoCheck (COLUMNNAME_CP_ConfProc_ID, Integer.valueOf(CP_ConfProc_ID));
	}

	/** Get ConfProc.
		@return ConfProc	  */
	public int getCP_ConfProc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CP_ConfProc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set customDate1.
		@param customDate1 customDate1	  */
	public void setCustomDate1 (Timestamp customDate1)
	{
		set_Value (COLUMNNAME_customDate1, customDate1);
	}

	/** Get customDate1.
		@return customDate1	  */
	public Timestamp getCustomDate1 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_customDate1);
	}

	/** Set customDate2.
		@param customDate2 customDate2	  */
	public void setCustomDate2 (Timestamp customDate2)
	{
		set_Value (COLUMNNAME_customDate2, customDate2);
	}

	/** Get customDate2.
		@return customDate2	  */
	public Timestamp getCustomDate2 () 
	{
		return (Timestamp)get_Value(COLUMNNAME_customDate2);
	}

	/** Set customNum1.
		@param customNum1 customNum1	  */
	public void setCustomNum1 (BigDecimal customNum1)
	{
		set_Value (COLUMNNAME_customNum1, customNum1);
	}

	/** Get CustomNum1.
		@return CustomNum1	  */
	public BigDecimal getCustomNum1 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_customNum1);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set customNum2.
		@param customNum2 customNum2	  */
	public void setCustomNum2 (BigDecimal customNum2)
	{
		set_Value (COLUMNNAME_customNum2, customNum2);
	}

	/** Get customNum2.
		@return customNum2	  */
	public BigDecimal getCustomNum2 () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_customNum2);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set customText1.
		@param customText1 customText1	  */
	public void setCustomText1 (String customText1)
	{

		if (customText1 != null && customText1.length() > 100)
		{
			log.warning("Length > 100 - truncated");
			customText1 = customText1.substring(0, 100);
		}
		set_Value (COLUMNNAME_customText1, customText1);
	}

	/** Get customText1.
		@return customText1	  */
	public String getCustomText1 () 
	{
		return (String)get_Value(COLUMNNAME_customText1);
	}

	/** Set customText2.
		@param customText2 customText2	  */
	public void setCustomText2 (String customText2)
	{

		if (customText2 != null && customText2.length() > 100)
		{
			log.warning("Length > 100 - truncated");
			customText2 = customText2.substring(0, 100);
		}
		set_Value (COLUMNNAME_customText2, customText2);
	}

	/** Get customText2.
		@return customText2	  */
	public String getCustomText2 () 
	{
		return (String)get_Value(COLUMNNAME_customText2);
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
}