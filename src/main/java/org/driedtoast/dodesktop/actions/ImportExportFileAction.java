package org.driedtoast.dodesktop.actions;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.FileBrowser;
import org.driedtoast.dodesktop.db.DatabaseService;
import org.driedtoast.dodesktop.models.Section;
import org.driedtoast.dodesktop.models.Task;
import org.driedtoast.dodesktop.views.TaskListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ImportExportFileAction extends AbstractDatabaseAction {

	private FileBrowser fileBrowser = null;

	public ImportExportFileAction(DatabaseService db, FileBrowser fileBrowser) {
		super(db);
		this.fileBrowser = fileBrowser;
	}

	@Override
	public void perform(Component source, DatabaseService service) {
		File jsonFile = fileBrowser.getSelectedFile();

		JsonParser parser = new JsonParser();
		JsonElement jsonElement = null;
		try {
			jsonElement = parser.parse(new FileReader(jsonFile));
		} catch (Exception e) {
			// TODO alert ui message
			e.printStackTrace();
		}
		if (jsonElement == null) {
			return;
		}
		// TODO get named component off parent
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		System.out.println("loading file " + jsonFile);
		System.out.println(jsonObject);

			
		List<Task> tasks = new ArrayList<Task>();
		
		JsonArray workspaces = jsonObject.getAsJsonArray("workspaces");
		Iterator<JsonElement> elements = workspaces.iterator();
		while (elements.hasNext()) {
			JsonElement workspace = elements.next();
			debug(workspace.getAsJsonObject());
			JsonArray sections = workspace.getAsJsonObject().getAsJsonArray("sections");
			List<Section> sectionObjects = processSections(sections);
			for(Section sectionObj: sectionObjects) {
				tasks.addAll(sectionObj.getTasks());
			}
			
			// Entries sections
			// TODO process projects and store in db / update sidebar 
			// TODO process sections and store in db / update sidebar with an all
			// TODO process sections in a project store in db
			// Entries projects
			
		}

		// List<Task> tasks = getTasks(jsonObject);
		TaskListView view = new TaskListView(source.getParent().getParent(),
				tasks);
		view.render();
	}

	private void debug(JsonObject jsonObject) {
		Iterator<Entry<String, JsonElement>> iterator = jsonObject.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, JsonElement> entry = iterator.next();
			System.out.println("Entries " + entry.getKey());
		}
	}
	
	protected List<Section> processSections(JsonArray jsonSections) {
		List<Section> sections = new ArrayList<Section>();
		Iterator<JsonElement> sectionElements = jsonSections.iterator();
		while (sectionElements.hasNext()) {
		  JsonObject jsonSection = sectionElements.next().getAsJsonObject();
		  Section section = Section.fromJson(jsonSection);
		  section.setTasks(getTasks(jsonSection));
		  sections.add(section);
		} 
		return sections;
	}

	protected List<Task> getTasks(JsonObject jsonObject) {

		JsonArray jsonTasks = jsonObject.getAsJsonArray("tasks");
		List<Task> tasks = new ArrayList<Task>();
		Iterator<JsonElement> elements = jsonTasks.iterator();
		while (elements.hasNext()) {
			JsonElement element = elements.next();
			tasks.add(Task.fromJson(element.getAsJsonObject()));
		}
		return tasks;
	}

}
