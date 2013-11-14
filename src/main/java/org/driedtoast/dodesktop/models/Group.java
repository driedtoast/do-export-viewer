package org.driedtoast.dodesktop.models;

import java.util.List;

import com.google.gson.JsonObject;

public class Group {
	
	
	private String externalId;
	private String name;

	private List<Section> sections;
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

	
	public static Group fromJson(JsonObject object) {
		Group group = new Group();
		group.externalId = object.get("id").getAsString();
		group.name = object.get("name").getAsString();
		return group;
	}
	
}
