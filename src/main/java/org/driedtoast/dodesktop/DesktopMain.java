package org.driedtoast.dodesktop;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.FileBrowser;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Window;
import org.driedtoast.dodesktop.actions.ImportExportFileAction;
import org.driedtoast.dodesktop.db.DatabaseService;
import org.driedtoast.dodesktop.views.ViewManager;

public class DesktopMain implements Application {

	private Window window = null;
	private DatabaseService dbservice = null;

	@BXML
	private PushButton importButton;
	@BXML
	private FileBrowser importFile;

	/**
	 * TODO create import splash TODO task list screen with tags / project names
	 * TODO sorting table based on date TODO nice design
	 */

	@Override
	public void startup(final Display display,
			org.apache.pivot.collections.Map<String, String> properties)
			throws Exception {

		dbservice = new DatabaseService();
		dbservice.startup();

		// String language = properties.get("locale");
		// Locale locale = (language == null) ? Locale.getDefault() : new
		// Locale(language);
		// Resources resources = new Resources(this.getClass().getName(),
		// locale);
		//

		BXMLSerializer serializer = new BXMLSerializer();
		window = (Window) serializer.readObject(ViewManager
				.getView("main-window.xml"));
		serializer.bind(this);
		// importButton =
		// (PushButton)serializer.getNamespace().get("importButton");
		ImportExportFileAction action = new ImportExportFileAction(dbservice,
				importFile);
		importButton.setAction(action);
		// button1.getButtonPressListeners().add(new ButtonPressListener() {
		//
		// @Override
		// public void buttonPressed(Button button) {
		// /*
		// * TO display new window.
		// BXMLSerializer serializer = new BXMLSerializer();
		// try {
		// Window testWindow = (Window)
		// serializer.readObject(getClass().getResource("detail-test.xml"));
		// testWindow.open(display, owner);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// */
		// owner.remove(text1);
		// owner.repaint();
		// Alert.alert(MessageType.INFO, "Hello " + text1.getText(),
		// window);
		// }
		// });
		window.open(display);

	}

	@Override
	public boolean shutdown(boolean optional) {
		if (dbservice != null) {
			try {
				dbservice.shutdown();
			} catch (Exception e) {
				// TODO log this? / alert
				e.printStackTrace();
			}
		}
		if (window != null) {
			window.close();
		}
		return false;
	}

	@Override
	public void suspend() {
	}

	@Override
	public void resume() {
	}

	public static void main(String[] args) {
		System.out.println("Starting the app");
		DesktopApplicationContext.applyStylesheet("/main-style.json");
		DesktopApplicationContext.main(DesktopMain.class, args);
	}

}
