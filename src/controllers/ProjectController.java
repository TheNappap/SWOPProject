package controllers;

import java.util.ArrayList;

import model.BugTrap;
import model.projects.Project;
import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectUpdateForm;

public class ProjectController extends Controller {

	public ProjectController(BugTrap bugTrap) {
		super(bugTrap);
	}

	public ArrayList<Project> getProjectList() {
		// TODO - implement ProjectController.getProjectList
		throw new UnsupportedOperationException();
	}

	public ProjectCreationForm getProjectCreationForm() {
		// TODO - implement ProjectController.getProjectCreationForm
		throw new UnsupportedOperationException();
	}

	public ProjectUpdateForm getProjectUpdateForm() {
		// TODO - implement ProjectController.getProjectUpdateForm
		throw new UnsupportedOperationException();
	}

	public ProjectAssignForm getProjectAssignForm() {
		// TODO - implement ProjectController.getProjectAssignForm
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public void createProject(ProjectCreationForm form) {
		// TODO - implement ProjectController.createProject
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public void updateProject(ProjectUpdateForm form) {
		// TODO - implement ProjectController.updateProject
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public void assignToProject(ProjectAssignForm form) {
		// TODO - implement ProjectController.assignToProject
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param project
	 */
	public void deleteProject(Project project) {
		// TODO - implement ProjectController.deleteProject
		throw new UnsupportedOperationException();
	}

}