package org.driedtoast.dodesktop.providers;

import org.driedtoast.dodesktop.models.Group;
import org.driedtoast.dodesktop.models.Project;
import org.driedtoast.dodesktop.models.Section;
import org.driedtoast.dodesktop.models.Task;

public interface DataVisitor {

	void visitTask(Task task);
	void visitSection(Section task);
	void visitGroup(Group group);
	void visitProject(Project project);
	
}
