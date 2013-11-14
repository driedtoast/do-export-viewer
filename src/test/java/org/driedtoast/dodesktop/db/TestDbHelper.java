package org.driedtoast.dodesktop.db;

public class TestDbHelper {
	
	public static DatabaseService createDatabaseService() throws Exception {
		TestDatabaseService dbservice = new TestDatabaseService();
		dbservice.startup();
		return dbservice;
	}

}
