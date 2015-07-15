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

/** Generated Interface for CP_ConfProc
 *  @author Trifon Trifonov (generated) 
 *  @version Release 3.4.0s
 */
public interface I_CP_ConfProc 
{

    /** TableName=CP_ConfProc */
    public static final String Table_Name = "CP_ConfProc";

    /** AD_Table_ID=1000004 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

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

    /** Column name CP_ConfProc_ID */
    public static final String COLUMNNAME_CP_ConfProc_ID = "CP_ConfProc_ID";

	/** Set ConfProc	  */
	public void setCP_ConfProc_ID (int CP_ConfProc_ID);

	/** Get ConfProc	  */
	public int getCP_ConfProc_ID();

    /** Column name customDate1 */
    public static final String COLUMNNAME_customDate1 = "customDate1";

	/** Set customDate1	  */
	public void setCustomDate1 (Timestamp customDate1);

	/** Get customDate1	  */
	public Timestamp getCustomDate1();

    /** Column name customDate2 */
    public static final String COLUMNNAME_customDate2 = "customDate2";

	/** Set customDate2	  */
	public void setCustomDate2 (Timestamp customDate2);

	/** Get customDate2	  */
	public Timestamp getCustomDate2();

    /** Column name customNum1 */
    public static final String COLUMNNAME_customNum1 = "customNum1";

	/** Set customNum1	  */
	public void setCustomNum1 (BigDecimal customNum1);

	/** Get customNum1	  */
	public BigDecimal getCustomNum1();

    /** Column name customNum2 */
    public static final String COLUMNNAME_customNum2 = "customNum2";

	/** Set customNum2	  */
	public void setCustomNum2 (BigDecimal customNum2);

	/** Get customNum2	  */
	public BigDecimal getCustomNum2();

    /** Column name customText1 */
    public static final String COLUMNNAME_customText1 = "customText1";

	/** Set customText1	  */
	public void setCustomText1 (String customText1);

	/** Get customText1	  */
	public String getCustomText1();

    /** Column name customText2 */
    public static final String COLUMNNAME_customText2 = "customText2";

	/** Set customText2	  */
	public void setCustomText2 (String customText2);

	/** Get customText2	  */
	public String getCustomText2();

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
}
