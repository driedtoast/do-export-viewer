package org.driedtoast.dodesktop.db;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.driedtoast.util.CollectionUtil;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

/**
 * Simple Data access object that takes a model and figures out what to do with
 * the rest.
 * 
 * @author dmarchant
 */
public class GenericDao<T> {

	private Class<?> modelClass;
	private DatabaseService service;
	private Map<Class<?>, String> types = new HashMap<Class<?>,String>();

	public GenericDao(Class<?> clazz, DatabaseService db) {
		modelClass = clazz;
		this.service = db;
		initialize();
	}

	public void initialize() {
		types.put(String.class, "TEXT");
		types.put(Date.class, "TEXT");
		types.put(Number.class, "INTEGER");
		types.put(Integer.class, "INTEGER");
		types.put(Double.class, "REAL");
	}
	
	public String tableName() {
		return modelClass.getSimpleName().toUpperCase();
	}

	/**
	 * Returns a Create table string.
	 * Example:
	 *   CREATE TABLE test (id TEXT NOT NULL PRIMARY KEY, name TEXT)
	 * @return
	 */
	protected String createTableStatement() {
		StringBuilder createSql = new StringBuilder();
		Field[] fields = modelClass.getDeclaredFields();
		createSql.append("CREATE TABLE ");
		createSql.append(tableName());
		createSql.append("(");
		boolean start = true;
		for (Field field : fields) {
			if (field.getModifiers() != Modifier.PRIVATE) continue;
			
			if (start) { start = false; } else { createSql.append(","); }
			createSql.append(field.getName().toUpperCase());
			createSql.append(" ");
			createSql.append( types.get(field.getType()) );
			if (field.getAnnotation(Primary.class) != null) {
				createSql.append("NOT NULL PRIMARY KEY");				
			}
		}
		createSql.append(");");
		return createSql.toString();
	}

	/**
	 * Creates a list of index create statements based on the Indexed annotation
	 * 
	 */
	protected List<String> createIndexStatements() {
		List<String> indexes = new ArrayList<String>();

		Field[] fields = modelClass.getDeclaredFields();
		for (Field field : fields) {
			if (field.getModifiers() != Modifier.PRIVATE) {
				continue;
			}
			Annotation[] annotations = field.getAnnotations();
			for(Annotation annotation: annotations) {
				if (!annotation.annotationType().equals(Indexed.class)) continue;				
				Indexed indexed = (Indexed)annotation;
				StringBuilder indexSql = new StringBuilder();
				indexSql.append("CREATE INDEX ");
				indexSql.append(tableName());
				indexSql.append("_");
				indexSql.append(indexed.name().toUpperCase());
				indexSql.append(" ON ");
				indexSql.append(tableName());
				indexSql.append("(");
				indexSql.append(CollectionUtil.join(indexed.fieldNames(),",",true));
				indexSql.append(")");
				indexes.add(indexSql.toString());
			}
		}
		return indexes;
	}

	/**
	 * Inserts the model into the db
	 * 
	 * @param model
	 * @return
	 */
	public boolean insert(T model) {
		SqlJetDb db = service.getDb();
		// TODO create insert statement on the model
		return false;
	}

	/**
	 * Updates the model
	 * 
	 * @param model
	 * @return
	 */
	public boolean update(T model) {
		return false;
	}

	/**
	 * Finds a specific model based on id
	 * 
	 * @param id
	 * @return
	 */
	public T find(Integer id) {
		return null;
	}

}
