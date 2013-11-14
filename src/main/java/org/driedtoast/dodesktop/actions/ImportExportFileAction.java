package org.driedtoast.dodesktop.actions;

import java.io.File;

import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.FileBrowser;
import org.driedtoast.dodesktop.db.DatabaseService;
import org.driedtoast.dodesktop.providers.DataProvider;
import org.driedtoast.dodesktop.providers.DataVisitor;
import org.driedtoast.dodesktop.providers.impl.DatabaseDataVisitor;
import org.driedtoast.dodesktop.providers.impl.DoDataProvider;
import org.driedtoast.dodesktop.providers.impl.TaskCollectorDataVisitor;
import org.driedtoast.dodesktop.views.TaskListView;

public class ImportExportFileAction extends AbstractDatabaseAction {

	private FileBrowser fileBrowser = null;

	public ImportExportFileAction(DatabaseService db, FileBrowser fileBrowser) {
		super(db);
		this.fileBrowser = fileBrowser;
	}

	@Override
	public void perform(Component source, DatabaseService service) {
		File jsonFile = fileBrowser.getSelectedFile();
		DataProvider provider = new DoDataProvider(jsonFile);
		DataVisitor visitor = new DatabaseDataVisitor(service);
		TaskCollectorDataVisitor taskListVisitor = new TaskCollectorDataVisitor();
		try {
			provider.processData(visitor);
		} catch (Exception e) {
			// TODO alert ui message
			e.printStackTrace();
		}
		TaskListView view = new TaskListView(source.getParent().getParent(),
				taskListVisitor.getTasks());
		view.render();
	}

	
}
