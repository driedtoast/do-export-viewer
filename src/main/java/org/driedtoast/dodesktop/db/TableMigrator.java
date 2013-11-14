package org.driedtoast.dodesktop.db;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.driedtoast.dodesktop.models.Group;
import org.driedtoast.dodesktop.models.Project;
import org.driedtoast.dodesktop.models.Section;
import org.driedtoast.dodesktop.models.Task;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

public class TableMigrator {
	
	public static final Class<?>[] MODELS = new Class[] { Group.class, Project.class, Task.class, Section.class };
	private static final Logger logger = Logger.getLogger(TableMigrator.class.getName());
	
	
	private DatabaseService service;
	
	public TableMigrator(DatabaseService service) {
		this.service = service;
	}
	
	public void generateTables() {
		// create tables in the db
		for(Class<?> clazz: MODELS) {
			try {
				createTable(clazz);
			} catch (SqlJetException e) {
				// TODO should throw alert or something
				logger.log(Level.SEVERE, "Failed to create table " + clazz.getSimpleName().toUpperCase(), e);
			}
		}
	}
	
	/**
	 * Alters table or creates table based on the database definitions
	 * 
	 * @param clazz
	 * @param db
	 * @throws SqlJetException
	 */
	
	@SuppressWarnings("rawtypes")
	protected void createTable(Class<?> clazz) throws SqlJetException {
	  SqlJetDb db = service.getDb();
	  GenericDao<?> dao = new GenericDao(clazz, service);
	  // check the existence of tables and than create a table
	  ISqlJetTable table = null;
	  try {
		  table = db.getTable(dao.tableName());
	  } catch(SqlJetException sqle) {
		  // Assume table is not there
		  logger.log(Level.INFO, "Couldn't find table " + dao.tableName() + " going to create it.", sqle);
	  }
	  if(table != null) {
		  // TODO check diff in columns
		  // table.getDefinition().getColumn(name)
		  return;
	  }
	  db.beginTransaction(SqlJetTransactionMode.WRITE);
	  try {            
	    db.createTable( dao.createTableStatement());
	    List<String> indexStatements = dao.createIndexStatements();
	    for(String indexStatement: indexStatements) {
	    	db.createIndex(indexStatement);
	    }
	  } finally {
	    db.commit();
	  }		
	}

}
