package th.co.ais.mynetwork.topworst.repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import oracle.jdbc.OracleTypes;
import th.co.ais.mynetwork.module.database.Database;
import th.co.ais.mynetwork.module.enumeration.ExceptionType;
import th.co.ais.mynetwork.module.log.ErrorLog;
import th.co.ais.mynetwork.module.log.FunctionLog;
import th.co.ais.mynetwork.module.util.ExceptionHandle;
import th.co.ais.mynetwork.topworst.dbconection.DBConnection;
import th.co.ais.mynetwork.topworst.model.SaveTopWorstCellRequest;
import th.co.ais.mynetwork.topworst.model.SearchTopWorstCellRequest;
import th.co.ais.mynetwork.topworst.model.TopWorstCell;

public class WorkFlowRepository {
	private static final Logger LOGGER = Logger.getLogger(WorkFlowRepository.class);
	
/*
CREATE OR REPLACE FUNCTION mynetwork.batch_get_top_worst_cell(in_param1 character varying)
 RETURNS TABLE(id integer, cell_type character varying, severity character varying, rncname character varying, nodebname character varying, cellname character varying, site_code character varying)
 LANGUAGE plpgsql
AS $function$

BEGIN
	
RETURN QUERY 

        		SELECT 
				tw.id,
				tw.cell_type,
				tw.severity,
				tw.rncname,
				tw.nodebname,
				tw.cellname,
				tw.site_code
				FROM mynetwork.top_worst tw 
				;

END; 

$function$
;
	 
*/
	
	
	public List<TopWorstCell> getTopWorstCell(SearchTopWorstCellRequest request) throws Exception {
		FunctionLog functionLog = new FunctionLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		CallableStatement  properCase = null;
		List<TopWorstCell> dataLists = new ArrayList<TopWorstCell>();

		Database db = new Database(DBConnection.TWC);
		DBConnection.check(db, "getTopWorstCell");
		
		try {
			
			String sql = "{CALL BATCH_GET_TOP_WORST_CELL(?)}";
		    LOGGER.debug("getTopWorstCell :SQL:" + sql);
		    
		    int i = 1;
		    properCase = db.getConn().prepareCall(sql);
		    properCase.setString(i++, "");
		    ResultSet result = properCase.executeQuery();
		    if (result.isBeforeFirst()) {
			    while (result.next()) {
			    	TopWorstCell topWorstCell = new TopWorstCell();
			    	topWorstCell.setId(result.getInt("id"));
			    	topWorstCell.setCell_type(result.getString("CELL_TYPE"));
			    	topWorstCell.setSeverity(result.getString("SEVERITY"));
			    	topWorstCell.setCellname(result.getString("cellname"));
			    	dataLists.add(topWorstCell);
			    }
		    } else {
		    	LOGGER.info("getTopWorstCell: " + sql + " :DataNotFound");
		    	throw new ExceptionHandle(ExceptionType.DataNotFound, "getTopWorstCell : Data Not Found");
		    }
		    
		} catch (SQLException e) {
			LOGGER.error(ErrorLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(), request, e.getMessage()));
			throw e;
		} catch (Exception e) {
			LOGGER.error(ErrorLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(), request, e.getMessage()));
			throw e;
		} finally {
		    try {
				db.closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		LOGGER.info(functionLog.end());
		return dataLists;	
	}
			

/*
CREATE OR REPLACE FUNCTION mynetwork.ftest3(id1 integer, name1 character varying)
 RETURNS void
 LANGUAGE plpgsql
AS $function$
    DECLARE
      --ref refcursor;
    BEGIN
      	insert into  mynetwork.top_worst(id,cellname) values(id1, name1); 
    END;
    $function$
;	 	 
*/
	
	public boolean saveTopWorstCell(SaveTopWorstCellRequest request) throws Exception {
		FunctionLog functionLog = new FunctionLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		CallableStatement properCase = null;
		boolean resultSave = false;

		Database db = new Database(DBConnection.TWC);
		DBConnection.check(db, "saveTopWorstCell");
		db.getConn().setAutoCommit(false);
		
		try {

			String sql = "{CALL ftest3(?,?)}";
		    LOGGER.debug("saveTopWorstCell :SQL:" + sql);

		    int i = 1;
		    properCase = db.getConn().prepareCall( sql );
		    properCase.setInt(i++, 200);
		    properCase.setString(i++, "TEST 200");
		    int intResult = properCase.executeUpdate();//success = 0
		    if (intResult == 0) {
		    	resultSave = true;
		    	db.getConn().commit();
		    }
			
		} catch (SQLException e) {
			db.getConn().rollback();
			LOGGER.error(ErrorLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(), request, e.getMessage()));
			throw e;
		} catch (Exception e) {
			db.getConn().rollback();
			LOGGER.error(ErrorLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(), request, e.getMessage()));
			throw e;
		} finally {
		    try {
				db.closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		LOGGER.info(functionLog.end());
		return resultSave;	
	}	
	
	
/*
CREATE OR REPLACE function PMR.ftest(f_name in varchar2) return SYS_REFCURSOR is
 out_cursor SYS_REFCURSOR;
 begin
  
    OPEN out_cursor FOR SELECT name||f_name AS name FROM pmr.temp1;
    RETURN out_cursor;
 end;
*/
	
	public List<TopWorstCell> getTopWorstCellTemp(SearchTopWorstCellRequest request) throws Exception {
		FunctionLog functionLog = new FunctionLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		List<TopWorstCell> dataLists = new ArrayList<TopWorstCell>();

		Database db = new Database(DBConnection.TWC);
		DBConnection.check(db, "getTopWorstCell");
		
		try {
			
			String sql = "{? = call pmr.ftest(?)}";
			LOGGER.debug("searchLocation :SQL:" + sql);
			int i = 1;
			db.getParameterMapOutParameter().put(i++, OracleTypes.CURSOR);
			db.getParameterMap().put(i++, "bbbbbbbbbb");
			//db.getParameterMapNull().put(i++, java.sql.Types.VARCHAR);//insert null
			
			ResultSet result = db.callStoreOutParameter(sql);
			
				while (result.next()) {
					TopWorstCell topWorstCell = new TopWorstCell();
					topWorstCell.setCellname(result.getString("name"));
					dataLists.add(topWorstCell);
				}
				
				if (dataLists.size() == 0) {
					LOGGER.info("getTopWorstCellTemp: " + sql + " :DataNotFound");
					throw new ExceptionHandle(ExceptionType.DataNotFound, "getTopWorstCellTemp : DataNotFound");
				}
					
			
		} catch (SQLException e) {
			LOGGER.error(ErrorLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(), request, e.getMessage()));
			throw e;
		} catch (Exception e) {
			LOGGER.error(ErrorLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(), request, e.getMessage()));
			throw e;
		} finally {
		    try {
				db.closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		LOGGER.info(functionLog.end());
		return dataLists;	
	}	
		

	
/*
CREATE OR REPLACE procedure PMR.ptest1(name varchar2,refcur OUT SYS_REFCURSOR) is
  i number;
 begin
     INSERT INTO pmr.TEMP1 VALUES('TEST5',name);
     --COMMIT;
	 OPEN refcur FOR SELECT name FROM pmr.TEMP1;
 end;
*/
	
	public boolean saveTopWorstCellTemp(SaveTopWorstCellRequest request) throws Exception {
		FunctionLog functionLog = new FunctionLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		CallableStatement properCase = null;
		boolean resultSave = false;

		Database db = new Database(DBConnection.TWC);
		DBConnection.check(db, "saveTopWorstCell");
		db.getConn().setAutoCommit(false);
		
		try {

			
			db.getParameterMap().clear();
			db.getParameterMapNull().clear();
			db.getParameterMapOutParameter().clear();

			int i = 1;
			//db.getParameterMap().put(i++, "xxxx");
			//db.getParameterMapNull().put(i++, java.sql.Types.VARCHAR);
			//db.getParameterMap().put(i++, null);
			db.getParameterMap().put(i++, "xxxx");

			db.getParameterMapOutParameter().put(i++, OracleTypes.CURSOR);

			String sql = "{call ptest1(?,?)}";
			ResultSet result = db.callStoreOutParameter(sql);
			System.out.println("===============>"+result.isBeforeFirst());

			while (result.next()) {
				System.out.println("=======>"+result.getString("name") );
				resultSave = true;
			}
			
			if (resultSave == false) {
				LOGGER.info("getTopWorstCellTemp: " + sql + " :DataNotFound");
				throw new ExceptionHandle(ExceptionType.DataNotFound, "getTopWorstCellTemp : DataNotFound");
			}
					
			db.getConn().commit();			
			
		} catch (SQLException e) {
			db.getConn().rollback();
			LOGGER.error(ErrorLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(), request, e.getMessage()));
			throw e;
		} catch (Exception e) {
			db.getConn().rollback();
			LOGGER.error(ErrorLog.log(Thread.currentThread().getStackTrace()[1].getMethodName(), request, e.getMessage()));
			throw e;
		} finally {
		    try {
				db.closeConnection();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		LOGGER.info(functionLog.end());
		return resultSave;	
	}	


}
