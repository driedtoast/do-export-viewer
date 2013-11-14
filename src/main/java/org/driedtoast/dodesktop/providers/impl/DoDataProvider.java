package org.driedtoast.dodesktop.providers.impl;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.driedtoast.dodesktop.db.GenericDao;
import org.driedtoast.dodesktop.models.Group;
import org.driedtoast.dodesktop.models.Project;
import org.driedtoast.dodesktop.models.Section;
import org.driedtoast.dodesktop.models.Task;
import org.driedtoast.dodesktop.providers.DataProvider;
import org.driedtoast.dodesktop.providers.DataVisitor;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class DoDataProvider implements DataProvider {

	private File jsonFile;

	public DoDataProvider(File jsonFile) {
		this.jsonFile = jsonFile;
	}

	@Override
	public void processData(DataVisitor... visitors) throws Exception {

		JsonParser parser = new JsonParser();
		JsonElement jsonElement = null;
		jsonElement = parser.parse(new FileReader(jsonFile));

		if (jsonElement == null) {
			return;
		}
		JsonObject jsonObject = jsonElement.getAsJsonObject();
		JsonArray workspaces = jsonObject.getAsJsonArray("workspaces");
		Iterator<JsonElement> elements = workspaces.iterator();
		while (elements.hasNext()) {
			JsonObject workspace = elements.next().getAsJsonObject();
			Group group = Group.fromJson(workspace);
			visitGroup(group, visitors);

			JsonArray sections = workspace.getAsJsonObject().getAsJsonArray(
					"sections");
			processSections(sections, visitors);
			JsonArray projects = workspace.getAsJsonObject().getAsJsonArray(
					"projects");
			processProjects(projects, visitors);
		}

	}

	// / Visiting methods
	private void visitTask(Task task, DataVisitor... visitors) {
		// setup task

		for (DataVisitor visitor : visitors) {
			visitor.visitTask(task);
		}
	}

	private void visitSection(Section section, DataVisitor... visitors) {
		for (DataVisitor visitor : visitors) {
			visitor.visitSection(section);
		}
	}

	private void visitProject(Project project, DataVisitor... visitors) {
		for (DataVisitor visitor : visitors) {
			visitor.visitProject(project);
		}
	}

	private void visitGroup(Group group, DataVisitor... visitors) {
		for (DataVisitor visitor : visitors) {
			visitor.visitGroup(group);
		}
	}

	// / End visiting methods

	/**
	 * Process the projects based on the json array
	 * 
	 */
	protected List<Project> processProjects(JsonArray jsonProjects,
			DataVisitor... visitors) {
		List<Project> projects = new ArrayList<Project>();
		Iterator<JsonElement> projectElements = jsonProjects.iterator();
		while (projectElements.hasNext()) {
			JsonObject jsonProject = projectElements.next().getAsJsonObject();
			Project project = Project.fromJson(jsonProject);
			visitProject(project, visitors);
			JsonArray sections = jsonProject.getAsJsonArray("sections");
			processSections(sections, visitors);
			projects.add(project);
		}
		return projects;
	}

	protected List<Section> processSections(JsonArray jsonSections,
			DataVisitor... visitors) {
		List<Section> sections = new ArrayList<Section>();
		Iterator<JsonElement> sectionElements = jsonSections.iterator();
		while (sectionElements.hasNext()) {
			JsonObject jsonSection = sectionElements.next().getAsJsonObject();
			Section section = Section.fromJson(jsonSection);
			visitSection(section, visitors);
			section.setTasks(processTasks(jsonSection, visitors));
			sections.add(section);
		}
		return sections;
	}

	protected List<Task> processTasks(JsonObject jsonObject,
			DataVisitor... visitors) {
		JsonArray jsonTasks = jsonObject.getAsJsonArray("tasks");
		List<Task> tasks = new ArrayList<Task>();
		Iterator<JsonElement> elements = jsonTasks.iterator();
		while (elements.hasNext()) {
			JsonElement element = elements.next();
			Task task = Task.fromJson(element.getAsJsonObject());
			visitTask(task, visitors);
			tasks.add(task);
		}
		return tasks;
	}

}
