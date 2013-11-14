package org.driedtoast.dodesktop.models;

import java.util.List;

import org.driedtoast.dodesktop.db.Indexed;
import org.driedtoast.dodesktop.db.Primary;

import com.google.gson.JsonObject;

public class Group {

	@Primary
	private String id;

	@Indexed(name = "externalId", fieldNames = { "externalId" })
	private String externalId;
	private String name;

	private List<Section> sections;
	private List<Task> tasks;
	private List<Project> projects;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Group fromJson(JsonObject object) {
		Group group = new Group();
		group.externalId = object.get("id").getAsString();
		group.name = object.get("name").getAsString();
		return group;
	}

}
