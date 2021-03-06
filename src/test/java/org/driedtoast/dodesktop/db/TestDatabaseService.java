package org.driedtoast.dodesktop.db;

import java.io.File;

public class TestDatabaseService extends DatabaseService {
	
	@Override
	public void startup() throws Exception {
		super.startup(true);
	}
	
	@Override
	public String getDatabaseUrl() {
	   return ".." + File.pathSeparator + "do-test-data.db";
	}
	
	@Override
	protected void runMigrations() {
		
	}
	
}
