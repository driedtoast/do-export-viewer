package org.driedtoast.dodesktop.models;

import java.util.List;

import com.google.gson.JsonObject;


// TODO from/to db,
// TODO create table
public class Project {
	
	private String externalId;
	private String name;
	private List<Task> tasks;
	private Group group;

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
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

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	public static Project fromJson(JsonObject object) {
		Project project = new Project();
		project.externalId = object.get("id").getAsString();
		project.name = object.get("name").getAsString();
		return project;
	}

}
