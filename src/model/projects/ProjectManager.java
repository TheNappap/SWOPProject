package model.projects;

import java.util.ArrayList;

import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectUpdateForm;

public class ProjectManager {

	private ArrayList<Project> projectList;

	public ArrayList<Project> getProjectList() {
		return this.projectList;
	}

	/**
	 * 
	 * @param form
	 */
	public void createProject(ProjectCreationForm form) {
		// TODO - implement ProjectManager.createProject
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public void updateProject(ProjectUpdateForm form) {
		// TODO - implement ProjectManager.updateProject
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param project
	 */
	public void deleteProject(Project project) {
		// TODO - implement ProjectManager.deleteProject
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public void assignToProject(ProjectAssignForm form) {
		// TODO - implement ProjectManager.assignToProject
		throw new UnsupportedOperationException();
	}

}