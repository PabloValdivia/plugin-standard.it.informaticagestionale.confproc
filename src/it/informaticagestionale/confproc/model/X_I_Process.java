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

import java.util.*;
import java.sql.*;
import java.math.*;
import java.lang.reflect.Constructor;
import java.util.logging.Level;
import org.compiere.util.*;
import java.sql.ResultSet;
import java.util.Properties;

import it.informaticagestionale.confproc.model.X_CP_ProcessFlowState;
import org.compiere.util.Env;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.*;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;


/** Generated Model for I_Product
 *  @author Adempiere (generated) 
 *  @version Release 3.3.0 - $Id$ */
public class X_I_Process extends PO implements I_I_Process, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    /** Standard Constructor */
    public X_I_Process (Properties ctx, int I_Product_ID, String trxName)
    {
      super (ctx, I_Product_ID, trxName);
      /** if (I_Product_ID == 0)        {			setI_IsImported (false);
			setI_Product_ID (0);
} */
    }

    /** Load Constructor */
    public X_I_Process (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID);
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_I_Product[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Business Partner Key.
		@param BPartner_Value 
		The Key of the Business Partner
	  */
	public void setBPartner_Value (String BPartner_Value)
	{
		if (BPartner_Value != null && BPartner_Value.length() > 40)
		{
			log.warning("Length > 40 - truncated");
			BPartner_Value = BPartner_Value.substring(0, 39);
		}
		set_Value (COLUMNNAME_BPartner_Value, BPartner_Value);
	}

	/** Get Business Partner Key.
		@return The Key of the Business Partner
	  */
	public String getBPartner_Value () 
	{
		return (String)get_Value(COLUMNNAME_BPartner_Value);
	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID <= 0) 		set_Value (COLUMNNAME_C_BPartner_ID, null);
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
		if (Description != null && Description.length() > 255)
		{
			log.warning("Length > 255 - truncated");
			Description = Description.substring(0, 254);
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


	/** Set Import Error Message.
		@param I_ErrorMsg 
		Messages generated from import it.informaticagestionale.confproc.process
	  */
	public void setI_ErrorMsg (String I_ErrorMsg)
	{
		if (I_ErrorMsg != null && I_ErrorMsg.length() > 2000)
		{
			log.warning("Length > 2000 - truncated");
			I_ErrorMsg = I_ErrorMsg.substring(0, 1999);
		}
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	/** Get Import Error Message.
		@return Messages generated from import it.informaticagestionale.confproc.process
	  */
	public String getI_ErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** Set Imported.
		@param I_IsImported 
		Has this import been processed
	  */
	public void setI_IsImported (boolean I_IsImported)
	{
		set_Value (COLUMNNAME_I_IsImported, Boolean.valueOf(I_IsImported));
	}

	/** Get Imported.
		@return Has this import been processed
	  */
	public boolean isI_IsImported () 
	{
		Object oo = get_Value(COLUMNNAME_I_IsImported);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}


	
	
	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID <= 0) 		set_Value (COLUMNNAME_M_Product_ID, null);
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
	
	/** Set Processed.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Processed.
		@return The document has been processed
	  */
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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




	/** Set UOM Code.
		@param X12DE355 
		UOM EDI X12 Code
	  */
	public void setX12DE355 (String X12DE355)
	{
		if (X12DE355 != null && X12DE355.length() > 4)
		{
			log.warning("Length > 4 - truncated");
			X12DE355 = X12DE355.substring(0, 3);
		}
		set_Value (COLUMNNAME_X12DE355, X12DE355);
	}

	/** Get UOM Code.
		@return UOM EDI X12 Code
	  */
	public String getX12DE355 () 
	{
		return (String)get_Value(COLUMNNAME_X12DE355);
	}

	/** Set Product.
			@param M_Product_ID 
			Product, Service, Item
		  */
		public void setM_ProductSub_ID (int M_ProductSub_ID)
		{
			if (M_ProductSub_ID <= 0) 		set_Value (COLUMNNAME_M_ProductSub_ID, null);
	 else 
			set_Value (COLUMNNAME_M_ProductSub_ID, Integer.valueOf(M_ProductSub_ID));
		}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_ProductSub_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_ProductSub_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product.
			@param M_Product_ID 
			Product, Service, Item
		  */
		public void setM_UnderProduct_ID (int M_UnderProduct_ID)
		{
			if (M_UnderProduct_ID <= 0) 		set_Value (COLUMNNAME_M_UnderProduct_ID, null);
	 else 
			set_Value (COLUMNNAME_M_UnderProduct_ID, Integer.valueOf(M_UnderProduct_ID));
		}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_UnderProduct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_UnderProduct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product_Value Key.
		@param Product_Value 
		The Key of the Business Partner
	  */
	public void setProductSub_Value (String ProductSub_Value)
	{
		if (ProductSub_Value != null && ProductSub_Value.length() > 40)
		{
			log.warning("Length > 40 - truncated");
			ProductSub_Value = ProductSub_Value.substring(0, 39);
		}
		set_Value (COLUMNNAME_ProductSub_Value, ProductSub_Value);
	}

	/** Get Product_Value Key.
		@return The Key of the Product_Value
	  */
	public String getProductSub_Value () 
	{
		return (String)get_Value(COLUMNNAME_ProductSub_Value);
	}

	/** Set Product_Value Key.
		@param Product_Value 
		The Key of the Business Partner
	  */
	public void setProduct_Value (String Product_Value)
	{
		if (Product_Value != null && Product_Value.length() > 40)
		{
			log.warning("Length > 40 - truncated");
			Product_Value = Product_Value.substring(0, 39);
		}
		set_Value (COLUMNNAME_Product_Value, Product_Value);
	}

	/** Get Product_Value Key.
		@return The Key of the Product_Value
	  */
	public String getProduct_Value () 
	{
		return (String)get_Value(COLUMNNAME_Product_Value);
	}

	/** Set Product_Value Key.
		@param Product_Value 
		The Key of the Business Partner
	  */
	public void setUnderProduct_Value (String UnderProduct_Value)
	{
		if (UnderProduct_Value != null && UnderProduct_Value.length() > 40)
		{
			log.warning("Length > 40 - truncated");
			UnderProduct_Value = UnderProduct_Value.substring(0, 39);
		}
		set_Value (COLUMNNAME_UnderProduct_Value, UnderProduct_Value);
	}

	/** Get Product_Value Key.
		@return The Key of the Product_Value
	  */
	public String getUnderProduct_Value () 
	{
		return (String)get_Value(COLUMNNAME_UnderProduct_Value);
	}

	/** Set TEK_STATO_PROCESSO_ID .
			@param TEK_STATO_PROCESSO_ID 
			Identifies a Business Partner
		  */
	
public void setCP_ProcessFlowState_ID (int CP_ProcessFlowState_ID)
		{
			if (CP_ProcessFlowState_ID <= 0) set_Value (COLUMNNAME_CP_ProcessFlowState_ID, null);
	 else 
			set_Value (COLUMNNAME_CP_ProcessFlowState_ID, Integer.valueOf(CP_ProcessFlowState_ID));
		}

	/** Get CP_ProcessFlowState_ID .
		@return Identifies a Business Partner
	  */
	public int getCP_ProcessFlowState_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CP_ProcessFlowState_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set AD_ConfProc_ID .
			@param AD_ConfProc_ID 
			Identifies a Business Partner
		  */
		public void setCP_ConfProc_ID (int CP_ConfProc_ID)
		{
			if (CP_ConfProc_ID <= 0) 		set_Value (COLUMNNAME_CP_ConfProc_ID, null);
	 else 
			set_Value (COLUMNNAME_CP_ConfProc_ID, Integer.valueOf(CP_ConfProc_ID));
		}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getCP_ConfProc_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CP_ConfProc_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set CP_ProcessSubFlowState_ID .
			@param CP_ProcessSubFlowState_ID 
			Identifies a Business Partner
		  */
		public void setCP_ProcessSubFlowState_ID (int CP_ProcessSubFlowState_ID)
		{
			if (CP_ProcessSubFlowState_ID <= 0) 		set_Value (COLUMNNAME_CP_ProcessSubFlowState_ID, null);
	 else 
			set_Value (COLUMNNAME_CP_ProcessSubFlowState_ID, Integer.valueOf(CP_ProcessSubFlowState_ID));
		}

	/** Get TEK_STATO_PARTI_ID .
		@return Identifies a Business Partner
	  */
	public int getCP_ProcessSubFlowState_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CP_ProcessSubFlowState_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Product.
			@param M_Product_ID 
			Product, Service, Item
		  */
		public void setI_Process_ID (int I_Process_ID)
		{
			if (I_Process_ID <= 0) 		set_Value (COLUMNNAME_I_Process_ID, null);
	 else 
			set_Value (COLUMNNAME_I_Process_ID, Integer.valueOf(I_Process_ID));
		}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getI_Process_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_I_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Business Partner Key.
		@param BPartner_Value 
		The Key of the Business Partner
	  */
	public void setIDPROC (String IDPROC)
	{
		if (IDPROC != null && IDPROC.length() > 40)
		{
			log.warning("Length > 40 - truncated");
			IDPROC = IDPROC.substring(0, 39);
		}
		set_Value (COLUMNNAME_IDPROC, IDPROC);
	}

	/** Get Business Partner Key.
		@return The Key of the Business Partner
	  */
	public String getIDPROC () 
	{
		return (String)get_Value(COLUMNNAME_IDPROC);
	}

	/** Set Product.
			@param M_Product_ID 
			Product, Service, Item
		  */
		public void setCP_ProcessFlow_ID (int CP_ProcessFlow_ID)
		{
			if (CP_ProcessFlow_ID <= 0) 		set_Value (COLUMNNAME_CP_ProcessFlow_ID, null);
	 else 
			set_Value (COLUMNNAME_CP_ProcessFlow_ID, Integer.valueOf(CP_ProcessFlow_ID));
		}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getCP_ProcessFlow_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CP_ProcessFlow_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Document Date.
	Date of the Document */
	public void setCustomDate1 (Timestamp CustomDate1)
	{
	if (CustomDate1 == null) throw new IllegalArgumentException ("CustomDate1 is mandatory.");
	set_Value ("CustomDate1", CustomDate1);
	}

	/** Get Document Date.
	Date of the Document */
	public Timestamp getCustomDate1() 
	{
	return (Timestamp)get_Value("CustomDate1");
	}

	/** Set SerNo Key.
		@param SerNo 
		The SerNo of the Business Partner
	  */
	public void setCustomText2 (String CustomText2)
	{
		if (CustomText2 != null && CustomText2.length() > 40)
		{
			log.warning("Length > 40 - truncated");
			CustomText2 = CustomText2.substring(0, 39);
		}
		set_Value (COLUMNNAME_CustomText2, CustomText2);
	}

	/** Get SerNo Key.
		@return The SerNo of the Product_Value
	  */
	public String getCustomText2 () 
	{
		return (String)get_Value(COLUMNNAME_CustomText2);
	}
	
	public void setCustomText1 (String CustomText1)
	{
		if (CustomText1 != null && CustomText1.length() > 40)
		{
			log.warning("Length > 40 - truncated");
			CustomText1 = CustomText1.substring(0, 39);
		}
		set_Value (COLUMNNAME_CustomText1, CustomText1);
	}

	/** Get SerNo Key.
		@return The SerNo of the Product_Value
	  */
	public String getCustomText1 () 
	{
		return (String)get_Value(COLUMNNAME_CustomText1);
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
}