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

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for CP_ProcessFlow
 *  @author Trifon Trifonov (generated) 
 *  @version Release 3.4.0s
 */
public interface I_CP_ProcessFlow 
{

    /** TableName=CP_ProcessFlow */
    public static final String Table_Name = "CP_ProcessFlow";

    /** AD_Table_ID=1000007 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

	/** Column name C_DocType_ID */
	public static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

    /** Load Meta Data */

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public I_AD_User getAD_User() throws Exception;

    /** Column name CP_ProcessFlowState_ID */
    public static final String COLUMNNAME_CP_ProcessFlowState_ID = "CP_ProcessFlowState_ID";

	/** Set ProcessFlowState	  */
	public void setCP_ProcessFlowState_ID (int CP_ProcessFlowState_ID);

	/** Get ProcessFlowState	  */
	public int getCP_ProcessFlowState_ID();

	public I_CP_ProcessFlowState getCP_ProcessFlowState() throws Exception;


    
    /** Column name CP_CONFPROC_ID */
    public static final String COLUMNNAME_CP_ConfProc_ID = "CP_ConfProc_ID";
	/** Set ProcessFlow	  */
	public void setCP_ConfProc_ID (int CP_ConfProc_ID);

	/** Get ProcessFlow	  */
	public int getCP_ConfProc_ID();
	
    /** Column name CP_ProcessFlow_ID */
    public static final String COLUMNNAME_CP_ProcessFlow_ID = "CP_ProcessFlow_ID";	
    
	/** Set ProcessFlow	  */
	public void setCP_ProcessFlow_ID (int CP_ProcessFlow_ID);

	/** Get ProcessFlow	  */
	public int getCP_ProcessFlow_ID();

    /** Column name idDoc */
    public static final String COLUMNNAME_idDoc = "idDoc";	
    
	/** Set idDoc	  */
	public void setIdDoc (int idDoc);

	/** Get idDoc	  */
	public int getIdDoc();	

    /** Column name idDocLine */
    public static final String COLUMNNAME_idDocLine = "idDocLine";	
    
	/** Set idDocLine	  */
	public void setIdDocLine (int idDocLine);

	/** Get idDocLine	  */
	public int getIdDocLine();	
	
	
	
    /** Column name CUSTOMDATE1 */
    public static final String COLUMNNAME_CUSTOMDATE1 = "CUSTOMDATE1";

	/** Set CUSTOMDATE1	  */
	public void setCUSTOMDATE1 (Timestamp CUSTOMDATE1);

	/** Get CUSTOMDATE1	  */
	public Timestamp getCUSTOMDATE1();

    /** Column name CUSTOMDATE2 */
    public static final String COLUMNNAME_CUSTOMDATE2 = "CUSTOMDATE2";

	/** Set CUSTOMDATE2	  */
	public void setCUSTOMDATE2 (Timestamp CUSTOMDATE2);

	/** Get CUSTOMDATE2	  */
	public Timestamp getCUSTOMDATE2();

    /** Column name CUSTOMNUM1 */
    public static final String COLUMNNAME_CUSTOMNUM1 = "CUSTOMNUM1";

	/** Set CUSTOMNUM1	  */
	public void setCUSTOMNUM1 (BigDecimal CUSTOMNUM1);

	/** Get CUSTOMNUM1	  */
	public BigDecimal getCUSTOMNUM1();

    /** Column name CUSTOMNUM2 */
    public static final String COLUMNNAME_CUSTOMNUM2 = "CUSTOMNUM2";

	/** Set CUSTOMNUM2	  */
	public void setCUSTOMNUM2 (BigDecimal CUSTOMNUM2);

	/** Get CUSTOMNUM2	  */
	public BigDecimal getCUSTOMNUM2();

    /** Column name CUSTOMTEXT1 */
    public static final String COLUMNNAME_CUSTOMTEXT1 = "CUSTOMTEXT1";

	/** Set CUSTOMTEXT1	  */
	public void setCUSTOMTEXT1 (String CUSTOMTEXT1);

	/** Get CUSTOMTEXT1	  */
	public String getCUSTOMTEXT1();

    /** Column name CUSTOMTEXT2 */
    public static final String COLUMNNAME_CUSTOMTEXT2 = "CUSTOMTEXT2";

	/** Set CUSTOMTEXT2	  */
	public void setCUSTOMTEXT2 (String CUSTOMTEXT2);

	/** Get CUSTOMTEXT2	  */
	public String getCUSTOMTEXT2();

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

	public I_C_BPartner getC_BPartner() throws Exception;

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

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

	public I_M_Product getM_Product() throws Exception;

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name Qty */
    public static final String COLUMNNAME_Qty = "Qty";

	/** Set Quantity.
	  * Quantity
	  */
	public void setQty (BigDecimal Qty);

	/** Get Quantity.
	  * Quantity
	  */
	public BigDecimal getQty();

    /** Column name TotalLines */
    public static final String COLUMNNAME_TotalLines = "TotalLines";

	/** Set Total Lines.
	  * Total of all document lines
	  */
	public void setTotalLines (BigDecimal TotalLines);

	/** Get Total Lines.
	  * Total of all document lines
	  */
	public BigDecimal getTotalLines();

	/** Set Document Type.
	  * Document type or rules
	  */
	public void setC_DocType_ID (int C_DocType_ID);

	/** Get Document Type.
	  * Document type or rules
	  */
	public int getC_DocType_ID();

	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException;
}
