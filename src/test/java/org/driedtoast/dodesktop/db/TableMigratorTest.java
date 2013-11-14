package org.driedtoast.dodesktop.db;

import junit.framework.TestCase;

import org.junit.Test;

public class TableMigratorTest extends TestCase {
	
	@Test
	public void testTableCreation() throws Exception {
		DatabaseService service = TestDbHelper.createDatabaseService();
		TableMigrator migrator = new TableMigrator(service);
		migrator.createTable(TestModel.class);
		assertNotNull(service.getDb().getTable("TESTMODEL"));
	}
	
	
	@Test
	@SuppressWarnings({"unchecked","rawtypes"})
	public void testModelTableCreation() throws Exception {
		DatabaseService service = TestDbHelper.createDatabaseService();
		TableMigrator migrator = new TableMigrator(service);
		migrator.generateTables();
		for(Class<?> modelClass: TableMigrator.MODELS) {
			GenericDao<?> dao = new GenericDao(modelClass, service);
			assertNotNull(service.getDb().getTable(dao.tableName()));
		}
	
	}
}
