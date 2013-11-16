package org.driedtoast.dodesktop.models;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.driedtoast.dodesktop.db.TestModel;
import org.driedtoast.dodesktop.models.ModelListener.EventType;
import org.junit.Test;

public class ModelManagerTest extends TestCase {

	public class TestModelListener implements ModelListener<TestModel> {
		protected Map<EventType, TestModel> events = new HashMap<EventType, TestModel>();
		
		@Override
		public void trigger(EventType evt, TestModel model) {
			events.put(evt, model);
		}
	}
	
	
	@Test
	public void testListenerEvents() throws Exception {
		ModelManager manager = ModelManager.getManager();
		
		TestModelListener listener = new TestModelListener();
		manager.addListener(TestModel.class, listener);
		
		TestModel model = new TestModel();
		model.setId("1");
		model.setName("Untitled");
		
		manager.<TestModel>triggerEvent(EventType.ADD, model);
		
		assertEquals(1,listener.events.size());
		assertTrue(listener.events.containsKey(EventType.ADD));
		listener.events.clear();		
	}
}
