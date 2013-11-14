package org.driedtoast.dodesktop.providers.impl;

import java.io.File;
import java.net.URL;
import java.util.List;

import junit.framework.TestCase;

import org.driedtoast.dodesktop.models.Task;
import org.junit.Test;

public class DoDataProviderTest extends TestCase {

	@Test
	public void testTaskProcessing() throws Exception {
		URL url = this.getClass().getResource("/do-export.json");
		File file = new File(url.toURI());
		DoDataProvider provider = new DoDataProvider(file);
		TaskCollectorDataVisitor visitor = new TaskCollectorDataVisitor();
		provider.processData(visitor);
		List<Task> tasks = visitor.getTasks(); 
		assertNotNull(tasks);
		assertTrue(tasks.size() > 0);
		for(Task task: tasks) {
			assertNotNull( task.getExternalId());
		}
	}
}
