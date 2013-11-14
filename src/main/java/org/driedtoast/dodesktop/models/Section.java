package org.driedtoast.dodesktop.models;

import java.util.List;

import org.driedtoast.dodesktop.db.Indexed;
import org.driedtoast.dodesktop.db.Primary;

import com.google.gson.JsonObject;

public class Section {

	@Primary
	private String id;

	@Indexed(name = "externalId", fieldNames = { "externalId" })
	private String externalId;

	// Could be null if its on the global level
	@Indexed(name = "projectId", fieldNames = { "projectId" })
	private String projectId;

	// Could be nullable
	@Indexed(name = "groupId", fieldNames = { "groupId" })
	private String groupId;

	private String name;
	private List<Task> tasks;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public static Section fromJson(JsonObject object) {
		Section section = new Section();
		section.externalId = object.get("id").getAsString();
		section.name = object.get("name").getAsString();
		return section;
	}

}
