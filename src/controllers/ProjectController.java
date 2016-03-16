package controllers;

import java.util.ArrayList;
import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.Project;
import model.projects.forms.*;
import model.users.Developer;
import model.users.UserCategory;

public class ProjectController extends Controller {

	public ProjectController(BugTrap bugTrap) {
		super(bugTrap);
	}

	public List<Project> getProjectList() throws UnauthorizedAccessException{
		if (getBugTrap().getUserManager().getLoggedInUser() == null)
			throw new UnauthorizedAccessException("You need to be logged in to perform this action.");
		
		return getBugTrap().getProjectDAO().getProjects();
	}
	
	public List<Project> getProjectsForLeadDeveloper(Developer dev) {
		return getBugTrap().getProjectDAO().getProjectsForLeadDeveloper(dev);
	}

	public ProjectCreationForm getProjectCreationForm() throws UnauthorizedAccessException {
		if (getBugTrap().getUserManager().getLoggedInUser() == null || getBugTrap().getUserManager().getLoggedInUser().getCategory() != UserCategory.ADMIN)
			throw new UnauthorizedAccessException("You need to be logged in as an administrator to perform this action.");
		
		return new ProjectCreationForm();
	}

	public ProjectForkForm getProjectForkForm() throws UnauthorizedAccessException {
		if (getBugTrap().getUserManager().getLoggedInUser() == null || getBugTrap().getUserManager().getLoggedInUser().getCategory() != UserCategory.ADMIN)
			throw new UnauthorizedAccessException("You need to be logged in as an administrator to perform this action.");

		return new ProjectForkForm();
	}

	public ProjectUpdateForm getProjectUpdateForm() throws UnauthorizedAccessException {
		if (getBugTrap().getUserManager().getLoggedInUser() == null || getBugTrap().getUserManager().getLoggedInUser().getCategory() != UserCategory.ADMIN)
			throw new UnauthorizedAccessException("You need to be logged in as an administrator to perform this action.");
		
		return new ProjectUpdateForm();
	}
	
	public ProjectDeleteForm getProjectDeleteForm() throws UnauthorizedAccessException {
		if (getBugTrap().getUserManager().getLoggedInUser() == null || getBugTrap().getUserManager().getLoggedInUser().getCategory() != UserCategory.ADMIN)
			throw new UnauthorizedAccessException("You need to be logged in as an administrator to perform this action.");
		
		return new ProjectDeleteForm();
	}

	public ProjectAssignForm getProjectAssignForm() throws UnauthorizedAccessException {
		if (getBugTrap().getUserManager().getLoggedInUser() == null || getBugTrap().getUserManager().getLoggedInUser().getCategory() != UserCategory.DEVELOPER)
			throw new UnauthorizedAccessException("You need to be logged in as an developer to perform this action.");
		
		return new ProjectAssignForm();
	}

	/**
	 * Create a project with the information provided in the form.
	 * @param form ProjectCreationForm containing all the details about the project to be created.
	 */
	public void createProject(ProjectCreationForm form) {
		form.allVarsFilledIn();
		getBugTrap().getProjectDAO().createProject(form);
	}

	/**
	 * Fork a project with the information provided in the form.
	 * @param form ProjectForkForm containing all the details about the fork.
     */
	public void forkProject(ProjectForkForm form) {
		form.allVarsFilledIn();
		getBugTrap().getProjectDAO().createFork(form);
	}

	/**
	 * Update a project with the information provided in the form.
	 * @param form ProjectUpdateForm containing all the details about the update.
	 */
	public void updateProject(ProjectUpdateForm form) {
		form.allVarsFilledIn();
		getBugTrap().getProjectDAO().updateProject(form);
	}

	/**
	 * Assign a developer to the project with the information provided in the form.
	 * @param form ProjectAssignForm containing the details about the assignment.
	 */
	public void assignToProject(ProjectAssignForm form) {
		form.allVarsFilledIn();
		getBugTrap().getProjectDAO().assignToProject(form);
	}

	/**
	 * Delete a project with the information provided in the form.
	 * @param form ProjectDeleteForm containing the details about the project to be deleted.
	 */
	public void deleteProject(ProjectDeleteForm form) {
		getBugTrap().getProjectDAO().deleteProject(form);
	}

	public SubsystemCreationForm getSubsystemCreationForm() throws UnauthorizedAccessException{

		if (getBugTrap().getUserManager().getLoggedInUser() == null || getBugTrap().getUserManager().getLoggedInUser().getCategory() != UserCategory.ADMIN)
			throw new UnauthorizedAccessException("You need to be logged in as an administrator to perform this action.");
		
		return new SubsystemCreationForm();
	}
	
	public void createSubsystem(SubsystemCreationForm form) {
		form.allVarsFilledIn();
		getBugTrap().getProjectDAO().createSubsystem(form);
	}
}