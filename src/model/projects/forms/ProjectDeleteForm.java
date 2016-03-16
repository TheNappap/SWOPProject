package model.projects.forms;

import model.Form;
import model.projects.Project;

/**
 * Form used to store temporary data to delete a project.
 */
public class ProjectDeleteForm implements Form {
	private Project project;
	
	public ProjectDeleteForm() {
		project = null;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		if (project == null) throw new NullPointerException("Given project is null.");
		
		this.project = project;
	}
	
	@Override
	public void allVarsFilledIn() {
		if (project == null) throw new NullPointerException("Given project is null.");
	}
}
