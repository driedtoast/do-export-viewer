package org.driedtoast.dodesktop.providers.impl;

import java.util.List;

import org.driedtoast.dodesktop.db.DatabaseService;
import org.driedtoast.dodesktop.db.GenericDao;
import org.driedtoast.dodesktop.models.Group;
import org.driedtoast.dodesktop.models.ModelManager;
import org.driedtoast.dodesktop.models.Project;
import org.driedtoast.dodesktop.models.Section;
import org.driedtoast.dodesktop.models.Task;
import org.driedtoast.dodesktop.models.ModelListener.EventType;
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
	
	// TODO process projects and store in db / update sidebar
	// TODO process sections and store in db / update sidebar with an
	// all
	// TODO process sections in a project store in db
	// Entries projects

	// TODO insert / find by external id
	
	@Override
	public void visitTask(Task task) {
		// TODO throw an exception if doesn't work?
		// Stop the visiting?
		List<Task> returned = taskDao.findByIndex("externalId", task.getExternalId());
		if (returned.size() == 0) { 
			taskDao.insert(task);
			ModelManager.getManager().<Task>triggerEvent(EventType.ADD, task);
		} else {
			task.setId(returned.get(0).getId());
			taskDao.update(task);
		}
		
	}

	@Override
	public void visitSection(Section section) {
		List<Section> returned = sectionDao.findByIndex("externalId", section.getExternalId());
		if (returned.size() == 0) { 
			sectionDao.insert(section);
			ModelManager.getManager().<Section>triggerEvent(EventType.ADD, section);
		} else {
			section.setId(returned.get(0).getId());
			sectionDao.update(section);
		}
	}

	@Override
	public void visitGroup(Group group)
	{
		List<Group> returned = groupDao.findByIndex("externalId", group.getExternalId());
		if (returned.size() == 0) {
			groupDao.insert(group);
			ModelManager.getManager().<Group>triggerEvent(EventType.ADD, group);
		} else {
			group.setId(returned.get(0).getId());
			groupDao.update(group);
		}
	}

	@Override
	public void visitProject(Project project) {
		List<Project> returned = projectDao.findByIndex("externalId", project.getExternalId());
		if (returned.size() == 0) {
		   projectDao.insert(project);
		   ModelManager.getManager().<Project>triggerEvent(EventType.ADD, project);
		} else {
		   project.setId(returned.get(0).getId());
		   projectDao.update(project);
		}
	}

}
