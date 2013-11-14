package org.driedtoast.dodesktop.models;

import java.util.List;

import com.google.gson.JsonObject;

public class Section {

	private String externalId;
	private String name;
	private List<Task> tasks;

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

	public static Section fromJson(JsonObject object) {
		Section section = new Section();
		section.externalId = object.get("id").getAsString();
		section.name = object.get("name").getAsString();
		return section;
	}
	
}
