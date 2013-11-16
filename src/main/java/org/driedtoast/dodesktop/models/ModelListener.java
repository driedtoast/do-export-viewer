package org.driedtoast.dodesktop.models;

public interface ModelListener<T> {
	
	public enum EventType {
		ADD,
		REMOVE,
		UPDATE;
	}
	
	void trigger(EventType evt, T model);
}
