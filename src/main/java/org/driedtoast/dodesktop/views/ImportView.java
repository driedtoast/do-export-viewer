package org.driedtoast.dodesktop.views;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.wtk.Container;
import org.apache.pivot.wtk.FileBrowser;
import org.apache.pivot.wtk.PushButton;
import org.driedtoast.dodesktop.actions.ImportExportFileAction;
import org.driedtoast.dodesktop.db.DatabaseService;

public class ImportView extends AbstractView<Container> {

	@BXML
	private PushButton importButton;

	@BXML
	private FileBrowser importFile;
	
	private DatabaseService service;
	
	public ImportView(Container parent, DatabaseService service) {
		super(parent);
		this.service = service;
	}

	@Override
	public String getViewLocation() {
		return "importview.xml";
	}
	
	@Override
	public void postRender(Container component) {
		super.postRender(component);
		ImportExportFileAction action = new ImportExportFileAction(service, importFile);
		importButton.setAction(action);
	}

}
