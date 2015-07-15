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
import org.compiere.model.*;
import org.compiere.util.KeyNamePair;

/** Generated Interface for CP_ConfProcLine
 *  @author Trifon Trifonov (generated) 
 *  @version Release 3.4.0s
 */
public interface I_CP_ConfProcLine 
{

    /** TableName=CP_ConfProcLine */
    public static final String Table_Name = "CP_ConfProcLine";

    /** AD_Table_ID=1000005 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set Table.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get Table.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public I_AD_Table getAD_Table() throws Exception;

    /** Column name CP_ConProcException_ID */
    public static final String COLUMNNAME_CP_ConProcException_ID = "CP_ConProcException_ID";

	/** Set ConProcException	  */
	public void setCP_ConProcException_ID (int CP_ConProcException_ID);

	/** Get ConProcException	  */
	public int getCP_ConProcException_ID();

	public I_CP_ConProcException getCP_ConProcException() throws Exception;

    /** Column name CP_ConfProcLine_ID */
    public static final String COLUMNNAME_CP_ConfProcLine_ID = "CP_ConfProcLine_ID";

	/** Set ConfProcLine	  */
	public void setCP_ConfProcLine_ID (int CP_ConfProcLine_ID);

	/** Get ConfProcLine	  */
	public int getCP_ConfProcLine_ID();

    /** Set Line No.
	  * Unique line for this document
	  */
	public void setLine (BigDecimal Line);

	/** Get Line No.
	  * Unique line for this document
	  */
	public BigDecimal getLine();

	/** Column name CP_processFlowStateTO_ID */
    public static final String COLUMNNAME_CP_processFlowStateTO_ID = "CP_processFlowStateTO_ID";

    /** Column name IsSOTrx */
    public static final String COLUMNNAME_IsSOTrx = "IsSOTrx";
    
    /** Column name IsCodeException */
    public static final String COLUMNNAME_IsCodeException = "IsCodeException";

	/**
	 * Column name Line 
	 */
	public static final String COLUMNNAME_Line = "Line";
    
}
