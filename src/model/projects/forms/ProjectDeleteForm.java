package model.projects.forms;

import model.Form;
import model.projects.IProject;
import model.projects.Project;

/**
 * Form used to store temporary data to delete a project.
 */
public class ProjectDeleteForm implements Form {
	private IProject project;
	
	public ProjectDeleteForm() {
		project = null;
	}
	
	public IProject getProject() {
		return project;
	}
	
	public void setProject(IProject project) {
		if (project == null) throw new NullPointerException("Given project is null.");
		
		this.project = project;
	}
	
	@Override
	public void allVarsFilledIn() {
		if (project == null) throw new NullPointerException("Given project is null.");
	}
}
