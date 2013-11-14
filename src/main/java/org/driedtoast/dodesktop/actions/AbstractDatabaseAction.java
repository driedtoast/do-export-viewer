package org.driedtoast.dodesktop.actions;

import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.Component;
import org.driedtoast.dodesktop.db.DatabaseService;

/**
 * Base action for database actions
 * 
 * @author dmarchant
 *
 */
public abstract class AbstractDatabaseAction extends Action {

	protected DatabaseService dbservice;
	
	public AbstractDatabaseAction(DatabaseService db) {
		super(true);
		this.dbservice = db;
	}
	
	
    @Override
    @SuppressWarnings("unchecked")
    public final void perform(Component source) {
    	this.perform(source, dbservice);
    }
    
    public abstract void perform(Component source, DatabaseService service);
    
	
}
