package org.driedtoast.dodesktop.db;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

public class GenericDaoTest extends TestCase {

	@Test
	public void testCreateTableSql() throws Exception {
		GenericDao<TestModel> dao = new GenericDao<TestModel>(TestModel.class, null);
		
		String sql = dao.createTableStatement();
		assertNotNull("Sql is not returned", sql );
		assertTrue("String should be filled out", sql.trim().length() > 0 );
		assertTrue(sql.contains("CREATE TABLE " + dao.tableName()));
	}
	
	@Test
	public void testCreateIndexSql() throws Exception {
		GenericDao<TestModel> dao = new GenericDao<TestModel>(TestModel.class, null);
		
		List<String> sql = dao.createIndexStatements();
		assertNotNull("Sql is not returned", sql );
		assertEquals("Length should be correct", 2, sql.size() );
		
		assertTrue(sql.get(0).startsWith("CREATE INDEX TESTMODEL_ID ON TESTMODEL"));
		assertTrue(sql.get(1).startsWith("CREATE INDEX TESTMODEL_NAME ON TESTMODEL"));
	}
	
	
}
