package org.driedtoast.dodesktop.db;

import junit.framework.TestCase;

import org.junit.Test;

public class TableMigratorTest extends TestCase {
	
	@Test
	public void testTableCreation() throws Exception {
		DatabaseService service = createDatabaseService();
		TableMigrator migrator = new TableMigrator(service);
		migrator.createTable(TestModel.class);
		assertNotNull(service.getDb().getTable("TESTMODEL"));
	}
	
	@Test
	public void testModelTableCreation() throws Exception {
		DatabaseService service = createDatabaseService();
		TableMigrator migrator = new TableMigrator(service);
		migrator.generateTables();
		for(Class<?> modelClass: TableMigrator.MODELS) {
			GenericDao<?> dao = new GenericDao(modelClass, service);
			assertNotNull(service.getDb().getTable(dao.tableName()));
		}
	
	}

	protected DatabaseService createDatabaseService() throws Exception {
		TestDatabaseService dbservice = new TestDatabaseService();
		dbservice.startup();
		return dbservice;
	}
}
