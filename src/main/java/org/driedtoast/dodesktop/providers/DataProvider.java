package org.driedtoast.dodesktop.providers;

public interface DataProvider {
	
	
	void processData(DataVisitor... visitor) throws Exception;
	
}
