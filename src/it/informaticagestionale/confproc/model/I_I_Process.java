/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          *
 * http://www.adempiere.org                                           *
 *                                                                    *
 * Copyright (C) Trifon Trifonov.                                     *
 * Copyright (C) Contributors                                         *
 *                                                                    *
 * This program is free software;
 you can redistribute it and/or      *
 * modify it under the terms of the GNU General Public License        *
 * as published by the Free Software Foundation;
 either version 2     *
 * of the License, or (at your option) any later version.             *
 *                                                                    *
 * This program is distributed in the hope that it will be useful,    *
 * but WITHOUT ANY WARRANTY;
 without even the implied warranty of     *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       *
 * GNU General Public License for more details.                       *
 *                                                                    *
 * You should have received a copy of the GNU General Public License  *
 * along with this program;
 if not, write to the Free Software        *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         *
 * MA 02110-1301, USA.                                                *
 *                                                                    *
 * Contributors:                                                      *
 * - Trifon Trifonov (trifonnt@users.sourceforge.net)                 *
 *                                                                    *
 * Sponsors:                                                          *
 * - Company (http://www.site.com)                                    *
 **********************************************************************/
package it.informaticagestionale.confproc.model;

import java.util.*;
import java.sql.Timestamp;
import java.math.*;
import org.compiere.util.*;
import java.math.BigDecimal;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;
    /** Generated Interface for I_Product
     *  @author Trifon Trifonov (generated) 
     *  @version Release 3.3.0 - 2007-08-24 11:39:48.312
     */
    public interface I_I_Process 
{

    /** TableName=I_Process */
    public static final String Table_Name = "I_PROCESS";

    /** AD_Table_ID=1000028 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 2 - Client 
     */
    BigDecimal accessLevel = new BigDecimal(2);

    /** Load Meta Data */

    /** Column name BPartner_Value */
    public static final String COLUMNNAME_BPartner_Value = "BPartner_Value";

	/** Set Business Partner Key.
	  * The Key of the Business Partner
	  */
	public void setBPartner_Value (String BPartner_Value);

	/** Get Business Partner Key.
	  * The Key of the Business Partner
	  */
	public String getBPartner_Value();

    /**
	 * Column name C_BPartner_ID 
	 */
	public static final String COLUMNNAME_CP_ConfProc_ID = "CP_ConfProc_ID";

	/** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

    /** Set it.informaticagestionale.confproc.process configurator ID.
	  * 
	  */
	public void setCP_ConfProc_ID (int CP_ConfProc_ID);

	/** get it.informaticagestionale.confproc.process configurator ID.
	  * 
	  */
	public int getCP_ConfProc_ID();

	/** Column name TEK_STATO_PROCESSO_ID */
    public static final String COLUMNNAME_CP_ProcessFlowState_ID = "CP_ProcessFlowState_ID";

	/** Set TEK_STATO_PROCESSO_ID.
	  * stato del processo
	  */
	public void setCP_ProcessFlowState_ID (int CP_ProcessFlowState_ID);

	/** Get CP_ProcessFlowState_ID.
	  * stato del processo
	  */
	public int getCP_ProcessFlowState_ID();
	
	   /** Column name CP_ProcessSubFlowState_ID */
    public static final String COLUMNNAME_CP_ProcessSubFlowState_ID = "CP_ProcessSubFlowState_ID";

	/** Set CP_ProcessSubFlowState_ID.
	  * stato delle parti
	  */
	public void setCP_ProcessSubFlowState_ID (int CP_ProcessSubFlowState_ID);

	/** Get CP_ProcessSubFlowState_ID
	  * stato delle parti
	  */
	public int getCP_ProcessSubFlowState_ID();
	
	
	
	

	/** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Description for it.informaticagestionale.confproc.process history
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Descrizione for it.informaticagestionale.confproc.process history
	  */
	public String getDescription();

   /** Column name I_IsImported */
    public static final String COLUMNNAME_I_IsImported = "I_IsImported";

	/** Set Imported.
	  * Has this import been processed
	  */
	public void setI_IsImported (boolean I_IsImported);

	/** Get Imported.
	  * Has this import been processed
	  */
	public boolean isI_IsImported();

    /** Column name I_Process_ID */
    public static final String COLUMNNAME_I_Process_ID = "I_Process_ID";

	/** Set Import Process.
	  * Import it.informaticagestionale.confproc.process
	  */
	public void setI_Process_ID (int I_PROCESS_ID);

	/** Get Import Product.
	  * Import Item or Service
	  */
	public int getI_Process_ID();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	
    /** Column name M_PRODUCTSUB_ID */
    public static final String COLUMNNAME_M_ProductSub_ID = "M_PRODUCTSUB_ID";

	/** Set Product substitute.
	  * Product, Service, Item
	  */
	public void setM_ProductSub_ID (int M_PRODUCTSUB_ID);

	/** Get Product substitute.
	  * Product, Service, Item
	  */
	public int getM_ProductSub_ID();
	

    /** Column name M_UNDERPRODUCT_ID */
    public static final String COLUMNNAME_M_UnderProduct_ID = "M_UNDERPRODUCT_ID";

	/** Set Under it.informaticagestionale.confproc.process Product .
	  * Product, Service, Item
	  */
	public void setM_UnderProduct_ID (int M_UNDERPRODUCT_ID);

	/** Get Under it.informaticagestionale.confproc.process Product .
	  * Product, Service, Item
	  */
	public int getM_UnderProduct_ID();
	
    /** Column name Product_Value */
    public static final String COLUMNNAME_Product_Value = "M_PRODUCT_VALUE";

	/** Set Product  Key	  */
	public void setProduct_Value (String Product_Value);
	/** Get Product  Key	  */
	public String getProduct_Value();
	
	/** Get ProductSub  Key	  */
	public String getProductSub_Value();
	
    /** Column name Product_Value */
    public static final String COLUMNNAME_ProductSub_Value = "M_PRODUCTSUB_VALUE";

	/** Set Product  Key	  */
	public void setProductSub_Value (String ProductSub_Value);


	/** Get ProductSub  Key	  */
	public String getUnderProduct_Value();
	
    /** Column name Product_Value */
    public static final String COLUMNNAME_UnderProduct_Value = "M_UNDERPRODUCT_VALUE";

	/** Set Product  Key	  */
	public void setUnderProduct_Value (String UnderProduct_Value);
	
	
	/** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

 

    /** Column name X12DE355 */
    public static final String COLUMNNAME_X12DE355 = "X12DE355";

	/** Set UOM Code.
	  * UOM EDI X12 Code
	  */
	public void setX12DE355 (String X12DE355);

	/** Get UOM Code.
	  * UOM EDI X12 Code
	  */
	public String getX12DE355();

	/**
	 * Column name I_ErrorMsg 
	 */
	public static final String COLUMNNAME_I_ErrorMsg = "I_ErrorMsg";

	/**
	 * Column name IDPROC 
	 */
	public static final String COLUMNNAME_IDPROC = "IDPROC";

	/**
	 * Column name M_UNDERPRODUCT_ID 
	 */
	public static final String COLUMNNAME_CP_ProcessFlow_ID = "CP_ProcessFlow_ID";

	/**
	 * Column name BPartner_Value 
	 */
	public static final String COLUMNNAME_CustomText2 = "CustomText2";

	/**
	 * Column name DateAcct 
	 */
	public static final String COLUMNNAME_DateAcct = "DateAcct";

	/** Set Import Error Message.
	  * Messages generated from import it.informaticagestionale.confproc.process
	  */
	public void setI_ErrorMsg (String I_ErrorMsg);

	/** Get Import Error Message.
	  * Messages generated from import it.informaticagestionale.confproc.process
	  */
	public String getI_ErrorMsg();

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setIDPROC (String IDPROC);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public String getIDPROC();

	/** Set Under it.informaticagestionale.confproc.process Product .
	  * Product, Service, Item
	  */
	public void setCP_ProcessFlow_ID (int CP_ProcessFlow_ID);

	/** Get Under it.informaticagestionale.confproc.process Product .
	  * Product, Service, Item
	  */
	public int getCP_ProcessFlow_ID();

	/** Set Business Partner Key.
	  * The Key of the Business Partner
	  */
	public void setCustomText2 (String CustomText2);

	/** Get Business Partner Key.
	  * The Key of the Business Partner
	  */
	public String getCustomText2();
	
	/**
	 * Column name BPartner_Value 
	 */
	public static final String COLUMNNAME_CustomText1 = "CustomText1";

	/**
	 * Column name CUSTOMNUM1 
	 */
	public static final String COLUMNNAME_CUSTOMNUM1 = "CUSTOMNUM1";

	/**
	 * Column name CUSTOMNUM2 
	 */
	public static final String COLUMNNAME_CUSTOMNUM2 = "CUSTOMNUM2";

	
	/** Set Business Partner Key.
	  * The Key of the Business Partner
	  */
	public void setCustomText1 (String CustomText2);

	/** Get Business Partner Key.
	  * The Key of the Business Partner
	  */
	public String getCustomText1();

	/** Set CUSTOMNUM1	  */
	public void setCUSTOMNUM1 (BigDecimal CUSTOMNUM1);

	/** Get CUSTOMNUM1	  */
	public BigDecimal getCUSTOMNUM1();

	/** Set CUSTOMNUM2	  */
	public void setCUSTOMNUM2 (BigDecimal CUSTOMNUM2);

	/** Get CUSTOMNUM2	  */
	public BigDecimal getCUSTOMNUM2();
}
