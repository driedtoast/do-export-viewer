package org.driedtoast.dodesktop.providers.impl;

import java.util.ArrayList;
import java.util.List;

import org.driedtoast.dodesktop.models.Group;
import org.driedtoast.dodesktop.models.Project;
import org.driedtoast.dodesktop.models.Section;
import org.driedtoast.dodesktop.models.Task;
import org.driedtoast.dodesktop.providers.DataVisitor;

// TODO fill out?
public class TaskCollectorDataVisitor implements DataVisitor {

	private List<Task> tasks = new ArrayList<Task>();
	
	@Override
	public void visitTask(Task task) {
		tasks.add(task);
	}

	@Override
	public void visitSection(Section task) {
	}

	@Override
	public void visitGroup(Group group) {
	}

	@Override
	public void visitProject(Project project) {
	}

	public List<Task> getTasks() {
		return tasks;
	}
	
}
