package th.co.ais.mynetwork.topworst.dbconection;

import th.co.ais.mynetwork.module.database.Database;
import th.co.ais.mynetwork.module.enumeration.ExceptionType;
import th.co.ais.mynetwork.module.util.ExceptionHandle;

public class DBConnection {
	public static final String TWC = "mynetwork.global.datasource.name2"; //mynetwork.global.datasource.name2 --> oracle
	
	public static void check(Database db, String methodName) throws Exception {
		if (db.getConn() == null) {
			throw new ExceptionHandle(ExceptionType.SystemError, methodName + " : " + "Invalid Database Connection!");
		}
	}
	
}
