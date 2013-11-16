package org.driedtoast.dodesktop.views;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.Container;
import org.apache.pivot.wtk.ListView;
import org.apache.pivot.wtk.content.ListItem;
import org.driedtoast.dodesktop.db.DatabaseService;
import org.driedtoast.dodesktop.db.GenericDao;
import org.driedtoast.dodesktop.models.ModelListener;
import org.driedtoast.dodesktop.models.ModelManager;
import org.driedtoast.dodesktop.models.Project;

public class SidebarView extends AbstractView<ListView> implements ModelListener<Project> {

	@BXML
	private ListView listView;
	
	private DatabaseService service;
	
	public SidebarView(Container parent, DatabaseService service) {
		super(parent);
		this.service = service;
	}
	
	@Override
	public void postRender(ListView component) {
		super.postRender(component);
		ModelManager.getManager().addListener(Project.class, this);
		
		GenericDao<Project> projectDao = new GenericDao<Project>(Project.class, service);
		java.util.List<Project> projects = projectDao.list(100);
		for(Project project: projects) {
			trigger(EventType.ADD, project);
		}		
		// ListDataBindMapping mapping = listView.getListDataBindMapping();
	}

	@Override
	public String getViewLocation() {
		return "sidebar.xml";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void trigger( EventType evt, Project model) {
		switch(evt) {
		  case ADD:
			  ListItem item = new ListItem(model.getName());
			  List<ListItem> items = (List<ListItem>)listView.getListData();
			  items.add(item);
			  break;			  
		  default:
			  // Do nothing
			  break;
		}
	}

}
