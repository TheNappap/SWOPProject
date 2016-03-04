package controllers;

import java.util.ArrayList;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.Project;
import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectDeleteForm;
import model.projects.forms.ProjectUpdateForm;
import model.projects.forms.SubsystemCreationForm;
import model.users.Developer;
import model.users.UserCategory;

public class ProjectController extends Controller {

	public ProjectController(BugTrap bugTrap) {
		super(bugTrap);
	}

	public ArrayList<Project> getProjectList() {
		return getBugTrap().getProjectDAO().getProjects();
	}
	
	public ArrayList<Project> getProjectsForLeadDeveloper(Developer dev) {
		return getBugTrap().getProjectDAO().getProjectsForLeadDeveloper(dev);
	}

	public ProjectCreationForm getProjectCreationForm() throws UnauthorizedAccessException {
		if (getBugTrap().getUserDAO().getLoggedInUser() == null || getBugTrap().getUserDAO().getLoggedInUser().getCategory() != UserCategory.ADMIN)
			throw new UnauthorizedAccessException("You need to be logged in as an administrator to perform this action.");
		
		return new ProjectCreationForm();
	}

	public ProjectUpdateForm getProjectUpdateForm() throws UnauthorizedAccessException {
		if (getBugTrap().getUserDAO().getLoggedInUser() == null || getBugTrap().getUserDAO().getLoggedInUser().getCategory() != UserCategory.ADMIN)
			throw new UnauthorizedAccessException("You need to be logged in as an administrator to perform this action.");
		
		return new ProjectUpdateForm();
	}
	
	public ProjectDeleteForm getProjectDeleteForm() throws UnauthorizedAccessException {
		if (getBugTrap().getUserDAO().getLoggedInUser() == null || getBugTrap().getUserDAO().getLoggedInUser().getCategory() != UserCategory.ADMIN)
			throw new UnauthorizedAccessException("You need to be logged in as an administrator to perform this action.");
		
		return new ProjectDeleteForm();
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
	public void deleteProject(ProjectDeleteForm form) {
		getBugTrap().getProjectDAO().deleteProject(form);
	}

	public SubsystemCreationForm getSubsystemCreationForm() throws UnauthorizedAccessException {
		if (getBugTrap().getUserDAO().getLoggedInUser() == null || getBugTrap().getUserDAO().getLoggedInUser().getCategory() != UserCategory.ADMIN)
			throw new UnauthorizedAccessException("You need to be logged in as an administrator to perform this action.");
		
		return new SubsystemCreationForm();
	}
	
	public void createSubsystem(SubsystemCreationForm form) {
		form.allVarsFilledIn();
		getBugTrap().getProjectDAO().createSubsystem(form);
	}
}