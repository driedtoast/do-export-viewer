package org.driedtoast.dodesktop.providers.impl;

import java.io.File;
import java.net.URL;
import java.util.List;

import junit.framework.TestCase;

import org.driedtoast.dodesktop.db.DatabaseService;
import org.driedtoast.dodesktop.db.TableMigrator;
import org.driedtoast.dodesktop.db.TestDbHelper;
import org.driedtoast.dodesktop.models.Task;
import org.driedtoast.dodesktop.providers.DataVisitor;
import org.junit.Test;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class DoDataProviderTest extends TestCase {

	@Test
	public void testTaskProcessing() throws Exception {
		URL url = this.getClass().getResource("/do-export.json");
		File file = new File(url.toURI());
		DoDataProvider provider = new DoDataProvider(file);
		TaskCollectorDataVisitor visitor = new TaskCollectorDataVisitor();
		provider.processData(visitor);
		List<Task> tasks = visitor.getTasks(); 
		assertNotNull(tasks);
		assertTrue(tasks.size() > 0);
		for(Task task: tasks) {
			assertNotNull( task.getExternalId());
		}
	}
	
	@Test
	public void testTaskDoubleDbProcessing() throws Exception {
		URL url = this.getClass().getResource("/do-export.json");
		File file = new File(url.toURI());
		DoDataProvider provider = new DoDataProvider(file);
		DatabaseService service = TestDbHelper.createDatabaseService();
		TableMigrator migrator = new TableMigrator(service);
		migrator.generateTables();
		DatabaseDataVisitor visitor = new DatabaseDataVisitor(service);
		provider.processData(visitor);
		
		SqlJetDb db = service.getDb();
		db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
		ISqlJetTable table = db.getTable("TASK");
		ISqlJetCursor cursor = table.open();
		long count = cursor.getRowCount();
		cursor.close();
		db.commit();
		
		// process again
		provider.processData(visitor);
		db.beginTransaction(SqlJetTransactionMode.READ_ONLY);
		table = db.getTable("TASK");
		cursor = table.open();
		long doubleCount = cursor.getRowCount();
		cursor.close();
		db.commit();
		
		assertSame(count, doubleCount);
	}
}
