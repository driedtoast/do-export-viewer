package org.driedtoast.dodesktop.models;

import java.util.List;

import org.driedtoast.dodesktop.db.Indexed;
import org.driedtoast.dodesktop.db.Primary;

import com.google.gson.JsonObject;

// TODO from/to db,
// TODO create table
public class Project {

	@Primary
	private String id;

	@Indexed(name = "externalId", fieldNames = { "externalId" })
	private String externalId;
	private String name;
	private List<Task> tasks;
	private List<Section> sections;
	
	@Indexed(name = "groupId", fieldNames = { "groupId" })
	private String groupId;

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

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
