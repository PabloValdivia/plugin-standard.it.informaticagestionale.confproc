package it.informaticagestionale.confproc.model;
import java.io.*;
import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.logging.*;

import org.compiere.model.MOrder;
import org.compiere.process.*;
import org.compiere.util.*;

/**
 *	Process Sub Flow Model
 *	
 *  @author Giorgio Cafasso & Corrado Chiodi
 *  @version $Id: CP_ProcessFlow.java,v 1.2 2008/04/21 00:51:05 gcafasso cchiodi Exp $
 */
public class CP_ConfProcLine extends X_CP_ConfProcLine {



	/**
	 * 	Standard Constructor
	 *	@param ctx context
	 *	@param CP_ProcessFlow_ID id
	 */
	public CP_ConfProcLine (Properties ctx, int CP_ConfProcLine_ID, String trxName)
	{
		super (ctx, CP_ConfProcLine_ID, trxName);
		if (CP_ConfProcLine_ID == 0)
		{

		

		}
	}	//	CP_ConfProcLine

	
	
	/**
	 * 	Load Constructor
	 *	@param ctx context
	 *	@param rs result set
	 */
	public CP_ConfProcLine (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	CP_ConfProcLine
	
	
	/**
	 *  Parent Constructor.
	 *  @param  order parent order
	 */
	public CP_ConfProcLine (CP_ConfProc confProc)
	{
		this (confProc.getCtx(), 0, confProc.get_TrxName());
		if (confProc.get_ID() == 0)
			throw new IllegalArgumentException("Header not saved");
		setCP_ConfProc_ID (confProc.getCP_ConfProc_ID());	//	parent
		setCP_ConfProc(confProc);
	}	//	AdConfProcLine


	/**
	 * 	Set Defaults from ConfProc.
	 * 	Does not set Parent !!
	 * 	@param confProc confProc
	 */
	public void setCP_ConfProc (CP_ConfProc confProc)
	{
		setClientOrg(confProc);
		//
	}	//	setOrder
	
	/** Parent							*/
	private CP_ConfProc m_parent = null;
	
	/** Lines						*/
	private CP_ConfProCondition[]		m_lines = null;
	
	/**
	 * Termini della query
	 */
	private String from=null;
	private String join=null;
	private String where=null;
	
	/**
	 * get Parent
	 * @return Parent Line
	 */
	public CP_ConfProc getParent() 
	{
		if (m_parent == null)
			m_parent = new CP_ConfProc (getCtx(), getCP_ConfProc_ID(), get_TrxName());
		return m_parent;
	}	//	getParent
	
	
	public CP_ConfProCondition[] getLines (){
		return getLines (true);
	}
	
	/**
	 * get Lines 
	 * @return CP_ConfProCondition
	 */
	public CP_ConfProCondition[] getLines (boolean requery)
	{
	if (m_lines != null && !requery)
		return m_lines;
	//
	ArrayList<CP_ConfProCondition> list = new ArrayList<CP_ConfProCondition>();
	String sql = "SELECT * FROM CP_ConfProCondition WHERE CP_ConfProcLine_ID=? ORDER BY Line";
	PreparedStatement pstmt = null;
	try
	{
		pstmt = DB.prepareStatement (sql, get_TrxName());
		pstmt.setInt (1, getCP_ConfProcLine_ID());
		ResultSet rs = pstmt.executeQuery ();
		while (rs.next ())
		{
			list.add (new CP_ConfProCondition (getCtx(), rs, get_TrxName()));
		}
		rs.close ();
		pstmt.close ();
		pstmt = null;
	} catch (Exception e)
	{
		log.log(Level.SEVERE, "getLines", e);
	}
	try
	{
		if (pstmt != null)
			pstmt.close ();
		pstmt = null;
	} catch (Exception e)
	{
		pstmt = null;
	}
	
	m_lines = new CP_ConfProCondition[list.size ()];
	list.toArray (m_lines);
	return m_lines;
	}//getLines
	
	private boolean createFJW(){
		clean();
		/**store condition*/
		String condition="";
		CP_ConfProCondition[] linee=this.getLines();
		if(linee!=null)
			for(CP_ConfProCondition linea:linee){
				String nometabella=linea.getTableName();
				String nomecolonna=linea.getColumnName();
				String nometabellajoin=linea.getTableJoinName();
				String nomeoperazione=linea.getOperationName();
				String nomecolonnajoin =linea.getColumnJoinName();
				
				/*FROM*/
				if(from==null)
					if(nometabella.compareTo(nometabellajoin)!=0)
						from=nometabella+" , "+nometabellajoin;
					else
						from=nometabella;
				else{
					if(from.indexOf(nometabella)==-1)
						from+=" , "+nometabella;
					if(from.indexOf(nometabellajoin)==-1)
						from+=" , "+nometabellajoin;
				}
				log.log(Level.INFO,"From" + from);
				
				/**JOIN*/
				
				String nextJoin="";
				if (nometabellajoin!=null & nomecolonnajoin!=null){
					/**condition to add in query*/
					if (join==null){
						/**if values condition exist*/
						//if(nometabellajoin.compareTo(nometabella)!=0)
							nextJoin=nometabellajoin+"."+nomecolonnajoin+"="+nometabella+"."+nomecolonnajoin+" ";
						join=nextJoin;
					}
					else{
						if(nometabellajoin.compareTo(nometabella)!=0){
							nextJoin=nometabellajoin+"."+nomecolonnajoin+"="+nometabella+"."+nomecolonnajoin+"";
							/**insert in addjoin  if is not present yet*/
							if(join.indexOf(nextJoin)==-1)
								join+=" AND "+nextJoin;
						}
					}
				}
				log.log(Level.INFO,"Join" + join);
				
				//inizializzo condition
				int valnum=linea.getValNum();
				String valstring=linea.getValString();
				//take context variable
				if(valstring!=null && valstring.startsWith("#")){
					valnum = Env.getContextAsInt(getCtx(), valstring);
					valstring= null;
				}			
				if ((condition==null) || (condition.length()==0)){
					condition=nometabella+"."+nomecolonna+nomeoperazione;
					/**if the value is a string*/
					if (valstring!=null)
						condition+=" "+valstring;
					/**if the value is a number*/
					else
						condition+=" "+valnum;
				}
				else{
					String nextCondition="";
					/**if the value is a string*/
					if (valstring!=null)
						nextCondition=nometabella+"."+nomecolonna+nomeoperazione+" "+valstring;
					/**if the value is a number*/
					else
						nextCondition=nometabella+"."+nomecolonna+nomeoperazione+" "+valnum;
					/**check logic condition*/
					String Logica=linea.getLogic();
					if(Logica == null){
						log.log(Level.WARNING,"logic operator null... write to AND by DEFAULT: "+Logica);
						Logica="A";
					}
					log.log(Level.INFO,"Logic");
					log.log(Level.INFO,"logic operator: "+Logica);
					
					if(condition.compareTo(nextCondition)!=0)
						if(Logica.compareTo("E")==0 || Logica.compareTo("A")==0)
							condition+=" AND "+nextCondition;
						else if(Logica.compareTo("O")==0)
							condition+=" ) OR ( "+nextCondition;
				}
				
			
				
				log.log(Level.INFO,"val NUM - STRING in condition:" +valnum+ " - " +valstring);																						
				
				/**WHERE*/
				/**if where not have condition yet*/
				if(where==null)
					where="";
				if(join!=null && join.length()!=0){
					if(where.indexOf(condition)==-1) 
						where=" AND (("+ condition+" ))";
				}
				else{
					if(where.indexOf(condition)==-1) 
						where=" (("+condition+" )) ";
				}
					
				log.log(Level.INFO,"Where :"+where);
				
			}
	log.log(Level.INFO,"out of cycle");
	return true;
	}
	
	
	/**
	 * getFromCondition
	 * @return from String of condition rows
	 */
	public String getFromCondition(){
		if (from==null)
			if(createFJW())
				return from;
		return from;
		
	}//getFromCondition
	
	/**
	 * getJoinCondition
	 * @return Join String of condition rows
	 */
	
	public String getJoinCondition(){
		if (join==null)
			if(createFJW())
				return join;
		return join;
	
	}	//	getJoinCondition
	
	/**
	 * getWhereCondition
	 * @return where string of condiiton rows
	 */
	public String getWhereCondition(){
		if (where==null)
			if(createFJW())
				return where;
		return where;
	}//getWhereCondition
	
	public void clean(){
		where=null;
		join=null;
		from=null;
	}
	/**
	 * 
	 * @param contesto (AND condizionale che stabilisce il contesto nel quale 
	 * 	exec  query es: " AND CP_ProcessFlow.CP_ProcessFlow_ID=1000010"
	 *  o if there's a subprocess es: " AND CP_ProcessFlow.CP_ProcessFlow_ID=1000010 AND CP_SubProcessFlow.CP_SubProcessFlow_ID=1000010"
	 * @return true if Evaluate ok
	 * 			false if Evaluate ko
	 */
	public boolean evaluateCondition(String contesto){
		String sql="select * from ";
		if(contesto==null)
			contesto="";
		if(createFJW()){
			log.log(Level.INFO,"after Create");
			if (contesto.length()>0)
				sql+=from+" WHERE "+join+" "+where+" "+contesto;
			else
				sql+=from+" WHERE "+join+" "+where;
			if ((from==null) && (join==null) && (where==null))
				return true;
			try{ 
				PreparedStatement pstmt = DB.prepareStatement (sql, get_TrxName());
				log.log(Level.INFO,"final Query : "+sql);							
				ResultSet rs = pstmt.executeQuery ();
				rs.next ();
				if (rs.getRow()==0){	
					log.log(Level.SEVERE,"conditions not met");
				}
				else{
					log.log(Level.SEVERE,"conditions met");
					log.log(Level.INFO,"good out cycle");
					return true;
				}
			}catch (Exception e){
				log.log(Level.SEVERE,"exception generate from condition test in method evaluateCondition");
				log.log(Level.SEVERE, sql, e);
				log.log(Level.INFO,"bad out cycle");
				return false;
			}
		}
		log.log(Level.INFO,"bad out evaluate");
		return false;	
	}
	
	/**
	 *  evaluateCondition
	 *  without context
	 * @return true se condizioni verificate altrimenti false
	 */
	public boolean evaluateCondition(){
		if(evaluateCondition(null))
			return true;
		else 
			return false;
	}



	/**************************************************************************
	 * 	Get Lines of conf Proc
	 * 	@param whereClause where clause or null (starting with AND)
	 * 	@return lines
	 */
	public CP_ConfProCondition[] getLines (String whereClause, String orderClause)
	{
		ArrayList<CP_ConfProCondition> list = new ArrayList<CP_ConfProCondition> ();
		StringBuffer sql = new StringBuffer("SELECT * FROM CP_ConfProCondition WHERE CP_ConfProc_ID=? AND CP_ConfProcLine_ID=? AND IsActive='Y' ");
		if (whereClause != null)
			sql.append(whereClause);
		if (orderClause != null)
			sql.append(" ").append(orderClause);
		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			pstmt.setInt(1, getCP_ConfProc_ID());
			pstmt.setInt(2, this.getCP_ConfProcLine_ID());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				CP_ConfProCondition ol = new CP_ConfProCondition(getCtx(), rs, get_TrxName());
			//	ol.setHeaderInfo (this);
				list.add(ol);
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		finally
		{
			try
			{
				if (pstmt != null)
					pstmt.close ();
			}
			catch (Exception e)
			{}
			pstmt = null;
		}
		//
		CP_ConfProCondition[] lines = new CP_ConfProCondition[list.size ()];
		list.toArray (lines);
		return lines;
	}	//	getLines



	public CP_ConfProCondition[] getLines (boolean requery, String orderBy)
	{
		if (m_lines != null && !requery)
			return m_lines;
		//
		String orderClause = "ORDER BY ";
		if (orderBy != null && orderBy.length() > 0)
			orderClause += orderBy;
		else
			orderClause += "Line";
		m_lines = getLines(null, orderClause);
		return m_lines;
	}	//	getLines
	
	
	public String getCP_ProcessFlowStateName(int CP_ConfProcLine_ID){
		
		
		StringBuffer sql = new StringBuffer("SELECT CP_ProcessFlowState.Name FROM CP_ProcessFlowState, CP_ConfProcLine WHERE CP_ConfProcLine_ID=? AND CP_ProcessFlowState.CP_ProcessFlowState_ID=CP_ConfProcLine.CP_ProcessFlowState_ID AND CP_ConfProcLine.IsActive='Y' ");

		PreparedStatement pstmt = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), get_TrxName());
			pstmt.setInt(1, CP_ConfProcLine_ID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next())
			{
				return rs.getString(1);
			}
			rs.close();
			pstmt.close();
			pstmt = null;
		}
		catch (Exception e)
		{
			log.log(Level.SEVERE, sql.toString(), e);
		}
		
		return null;
	}
	
}
