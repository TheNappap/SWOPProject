package controllers;

import java.util.ArrayList;

import model.BugTrap;
import model.projects.Project;
import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectUpdateForm;
import model.users.Developer;

public class ProjectController extends Controller {

	public ProjectController(BugTrap bugTrap) {
		super(bugTrap);
	}

	public ArrayList<Project> getProjectList() {
		return getBugTrap().getProjectDAO().getProjects();
	}
	
	public ArrayList<Project> getProjectsForDeveloper(Developer dev) {
		return getBugTrap().getProjectDAO().getProjectsForDeveloper(dev);
	}

	public ProjectCreationForm getProjectCreationForm() {
		return new ProjectCreationForm();
	}

	public ProjectUpdateForm getProjectUpdateForm() {
		return new ProjectUpdateForm();
	}

	public ProjectAssignForm getProjectAssignForm() {
		return new ProjectAssignForm();
	}

	/**
	 * 
	 * @param form
	 */
	public void createProject(ProjectCreationForm form) {
		form.allVarsFilledIn();
		getBugTrap().getProjectDAO().createProject(form);
	}

	/**
	 * 
	 * @param form
	 */
	public void updateProject(ProjectUpdateForm form) {
		form.allVarsFilledIn();
		getBugTrap().getProjectDAO().updateProject(form);
	}

	/**
	 * 
	 * @param form
	 */
	public void assignToProject(ProjectAssignForm form) {
		form.allVarsFilledIn();
		getBugTrap().getProjectDAO().assignToProject(form);
	}

	/**
	 * 
	 * @param project
	 */
	public void deleteProject(Project project) {
		getBugTrap().getProjectDAO().deleteProject(project);
	}

}