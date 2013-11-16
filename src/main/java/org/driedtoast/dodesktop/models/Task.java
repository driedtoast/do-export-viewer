package org.driedtoast.dodesktop.models;

import java.util.Date;

import org.driedtoast.dodesktop.db.Indexed;
import org.driedtoast.dodesktop.db.Primary;

import com.google.gson.JsonObject;

public class Task {

	@Indexed(name = "externalId", fieldNames = { "externalId" })
	private String externalId;
	private String name;

	@Primary
	private String id;

	@Indexed(name = "date", fieldNames = { "date" })
	private Date date;

	@Indexed(name = "completed", fieldNames = { "completed" })
	private boolean completed;

	@Indexed(name = "groupId", fieldNames = { "groupId" })
	private String groupId;

	// Maps to the section of the project
	@Indexed(name = "sectionId", fieldNames = { "sectionId" })
	private String sectionId;

	// Maps to the section on the highlevel / personal overview
	@Indexed(name = "overviewSectionId", fieldNames = { "overviewSectionId" })
	private String overviewSectionId;

	@Indexed(name = "projectId", fieldNames = { "projectId" })
	private String projectId;

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getOverviewSectionId() {
		return overviewSectionId;
	}

	public void setOverviewSectionId(String overviewSectionId) {
		this.overviewSectionId = overviewSectionId;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isCompleted() {
		return completed;
	}
	
	public boolean getCompleted() {
		return isCompleted();
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public static Task fromJson(JsonObject object) {
		Task task = new Task();
		task.externalId = object.get("id").getAsString();
		if (object.get("name") != null) {
			task.name = object.get("name").getAsString();
		} else {
			task.name = "Untitled";
		}
		// task.date = object.get("due")
		return task;
	}

}
