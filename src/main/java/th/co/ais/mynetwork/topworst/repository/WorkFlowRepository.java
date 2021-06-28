package th.co.ais.mynetwork.topworst.repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
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
	
	public List<TopWorstCell> getTopWorstCell(SearchTopWorstCellRequest request) throws Exception {
		FunctionLog functionLog = new FunctionLog(Thread.currentThread().getStackTrace()[1].getMethodName());
		CallableStatement properCase = null;
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
			if (result.isBeforeFirst()) {
			    while (result.next()) {
			    	TopWorstCell topWorstCell = new TopWorstCell();
			    	topWorstCell.setId(result.getInt("id"));
			    	topWorstCell.setCell_type(result.getString("CELL_TYPE"));
			    	topWorstCell.setSeverity(result.getString("SEVERITY"));
			    	topWorstCell.setCellname(result.getString("cellname"));
			    	dataLists.add(topWorstCell);
			    }
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
		    properCase.close();
		    db.closeConnection();
		}
		
		LOGGER.info(functionLog.end());
		return dataLists;	
	}
		
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
		    properCase.close();
		    db.closeConnection();
		}
		
		LOGGER.info(functionLog.end());
		return resultSave;	
	}	
	
}
