package org.driedtoast.dodesktop;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Border;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.FileBrowser;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.Window;
import org.driedtoast.dodesktop.actions.ImportExportFileAction;
import org.driedtoast.dodesktop.db.DatabaseService;
import org.driedtoast.dodesktop.db.GenericDao;
import org.driedtoast.dodesktop.models.Task;
import org.driedtoast.dodesktop.views.ImportView;
import org.driedtoast.dodesktop.views.SidebarView;
import org.driedtoast.dodesktop.views.TaskListView;
import org.driedtoast.dodesktop.views.ViewManager;

public class DesktopMain implements Application {

	private static final Logger logger = Logger.getLogger(DesktopMain.class
			.getName());

	private Window window = null;
	private DatabaseService dbservice = null;

	@BXML
	private Border sidebar;

	@BXML
	private Border body;

	/**
	 * TODO create import splash TODO task list screen with tags / project names
	 * TODO sorting table based on date TODO nice design
	 */

	@Override
	public void startup(final Display display, Map<String, String> properties)
			throws Exception {

		// TODO setup dock icons and window title to be nicer
		java.awt.Window hostWindow = display.getHostWindow();
		java.awt.Frame hostFrame = (java.awt.Frame) hostWindow;
		hostFrame.setTitle("Do Desktop");
		InputStream is = getClass().getResourceAsStream("/do_logo.png");
		BufferedImage img = ImageIO.read(is);
		hostWindow.getIconImages().add(img);

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
		// Alert.alert(MessageType.INFO, "Hello " + text1.getText(), window);

		window.setIcon(getClass().getResource("/do_logo.png"));
		window.open(display);
		initializeViews();
	}

	protected void initializeViews() {

		SidebarView view = new SidebarView(sidebar, dbservice);
		view.render();

		GenericDao<Task> taskDao = new GenericDao<Task>(Task.class, dbservice);
		if (taskDao.count() > 0) {
			List<Task> tasks = taskDao.list(50);
			TaskListView taskListView = new TaskListView(body, tasks);
			taskListView.render();
		} else {
			ImportView importView = new ImportView(body, dbservice);
			importView.render();
		}
	}

	@Override
	public boolean shutdown(boolean optional) {
		if (dbservice != null) {
			try {
				dbservice.shutdown();
			} catch (Exception e) {
				logger.log(Level.WARNING, "Problem shutting down", e);
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
		logger.info("Starting the app");
		DesktopApplicationContext.applyStylesheet("/main-style.json");
		DesktopApplicationContext.main(DesktopMain.class, args);
	}

}
