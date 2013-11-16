package org.driedtoast.dodesktop.views;

import org.apache.pivot.beans.BXML;
import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.HashMap;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Container;
import org.apache.pivot.wtk.TableView;
import org.driedtoast.dodesktop.models.ModelListener;
import org.driedtoast.dodesktop.models.Task;

/**
 * View abstraction to setup the task list view 
 * 
 * @author dmarchant
 */
public class TaskListView extends AbstractView<Container> implements ModelListener<Task> {

	@BXML
	private TableView taskList;
	private java.util.List<Task> tasks;

	public TaskListView(Container parent, java.util.List<Task> tasks) {
		super(parent);
		this.tasks = tasks;
	}

	@Override
	public void postRender(Container component) {
		taskList.setTableData(createData());
	}

	protected List<Map<String, Object>> createData() {
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		for(Task task:tasks) {
		  data.add(convertTask(task));	
		}
		return data;
	}
	
	protected Map<String, Object> convertTask(Task task) {
		Map<String, Object> testMap = new HashMap<String, Object>();
		testMap.put("name", task.getName());
		// testMap.put("name", task.getDate());
		testMap.put("completed", task.isCompleted());
		return testMap;
	}
	
	@Override
	public String getViewLocation() {
		return "tasklist.xml";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void trigger( EventType evt, Task model) {
		List<Map<String, Object>> data = (List<Map<String, Object>>)taskList.getTableData();
		if(evt == EventType.ADD) {
			data.add(convertTask(model));
		}
	}

}
