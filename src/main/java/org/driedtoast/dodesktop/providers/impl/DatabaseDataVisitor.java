package org.driedtoast.dodesktop.providers.impl;

import org.driedtoast.dodesktop.db.DatabaseService;
import org.driedtoast.dodesktop.db.GenericDao;
import org.driedtoast.dodesktop.models.Group;
import org.driedtoast.dodesktop.models.Project;
import org.driedtoast.dodesktop.models.Section;
import org.driedtoast.dodesktop.models.Task;
import org.driedtoast.dodesktop.providers.DataVisitor;

public class DatabaseDataVisitor implements DataVisitor {

	private DatabaseService service;
	private GenericDao<Section> sectionDao;
	private GenericDao<Task> taskDao;
	private GenericDao<Group> groupDao;
	private GenericDao<Project> projectDao;
	
	public DatabaseDataVisitor(DatabaseService service) {
		this.service = service;
		initialize();
	}
	
	private void initialize() {
		sectionDao = new GenericDao<Section>(Section.class, service);
		taskDao = new GenericDao<Task>(Task.class, service);
		groupDao = new GenericDao<Group>(Group.class, service);
		projectDao = new GenericDao<Project>(Project.class, service);
	}
	
	@Override
	public void visitTask(Task task) {
		// TODO throw an exception if doesn't work?
		// Stop the visiting?
		taskDao.insert(task);
	}

	@Override
	public void visitSection(Section section) {
		sectionDao.insert(section);
	}

	@Override
	public void visitGroup(Group group) {
		groupDao.insert(group);
	}

	@Override
	public void visitProject(Project project) {
		projectDao.insert(project);
	}

}
