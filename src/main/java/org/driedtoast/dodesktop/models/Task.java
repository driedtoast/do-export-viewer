package org.driedtoast.dodesktop.models;

import java.util.Date;

import com.google.gson.JsonObject;

public class Task {

	private String externalId;
	private String name;
	private String id;
	private Date date;
	private boolean completed;
	private String groupName;
	
	
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	
	
	public static Task fromJson(JsonObject object) {
		Task task = new Task();
		task.externalId = object.get("id").getAsString();
		task.name = object.get("name").getAsString();
		// task.date = object.get("due")
		return task;
	}
	
}
