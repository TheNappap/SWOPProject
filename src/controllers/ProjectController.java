package controllers;

import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.IProject;
import model.projects.commands.AssignProjectCommand;
import model.projects.commands.CreateProjectCommand;
import model.projects.commands.CreateSubsystemCommand;
import model.projects.commands.DeleteProjectCommand;
import model.projects.commands.ForkProjectCommand;
import model.projects.commands.UpdateProjectCommand;
import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectDeleteForm;
import model.projects.forms.ProjectForkForm;
import model.projects.forms.ProjectUpdateForm;
import model.projects.forms.SubsystemCreationForm;

/**
 * Controller for all Project related things.
 * Controllers are the interface that is available to developers
 * creating e.g. a BugTrap UI.
 */
public class ProjectController extends Controller {
	public ProjectController(BugTrap bugTrap) {
		super(bugTrap);
	}

	public List<IProject> getProjectList() throws UnauthorizedAccessException{
		return getBugTrap().getProjectManager().getProjects();
	}

	public List<IProject> getProjectsForSignedInLeadDeveloper() throws UnauthorizedAccessException {
		return getBugTrap().getProjectManager().getProjectsForSignedInLeadDeveloper();
	}

	public ProjectCreationForm getProjectCreationForm() throws UnauthorizedAccessException {
		return getBugTrap().getFormFactory().makeProjectCreationForm();
	}

	public ProjectForkForm getProjectForkForm() throws UnauthorizedAccessException {
		return getBugTrap().getFormFactory().makeProjectForkForm();
	}

	public ProjectUpdateForm getProjectUpdateForm() throws UnauthorizedAccessException {
		return getBugTrap().getFormFactory().makeProjectUpdateForm();
	}
	
	public ProjectDeleteForm getProjectDeleteForm() throws UnauthorizedAccessException {
		return getBugTrap().getFormFactory().makeProjectDeleteForm();
	}

	public ProjectAssignForm getProjectAssignForm() throws UnauthorizedAccessException {
		return getBugTrap().getFormFactory().makeProjectAssignForm();
	}

	public SubsystemCreationForm getSubsystemCreationForm() throws UnauthorizedAccessException{
		return getBugTrap().getFormFactory().makeSubsystemCreationForm();
	}

	/**
	 * Creates a project with the information provided in the form.
	 * @param form ProjectCreationForm containing all the details about the creation.
	 * @throws UnauthorizedAccessException 
	 */
	public void createProject(ProjectCreationForm form) throws UnauthorizedAccessException {
		new CreateProjectCommand(getBugTrap(), form).execute();
	}

	/**
	 * Forks a project with the information provided in the form.
	 * @param form ProjectForkForm containing all the details about the fork.
	 * @throws UnauthorizedAccessException 
	 */
	public void forkProject(ProjectForkForm form) throws UnauthorizedAccessException {
		new ForkProjectCommand(getBugTrap(), form).execute();
	}

	/**
	 * Update a project with the information provided in the form.
	 * @param form ProjectUpdateForm containing all the details about the update.
	 * @throws UnauthorizedAccessException 
	 */
	public void updateProject(ProjectUpdateForm form) throws UnauthorizedAccessException {
		new UpdateProjectCommand(getBugTrap(), form).execute();
	}

	/**
	 * Assign a developer to the project with the information provided in the form.
	 * @param form ProjectAssignForm containing the details about the assignment.
	 * @throws UnauthorizedAccessException 
	 */
	public void assignToProject(ProjectAssignForm form) throws UnauthorizedAccessException {
		new AssignProjectCommand(getBugTrap(), form).execute();
	}

	/**
	 * Delete a project with the information provided in the form.
	 * @param form ProjectDeleteForm containing the details about the project to be deleted.
	 * @throws UnauthorizedAccessException 
	 */
	public void deleteProject(ProjectDeleteForm form) throws UnauthorizedAccessException {
		new DeleteProjectCommand(getBugTrap(), form).execute();
	}
	
	/**
	 * Create a subsystem with the information provided in the form.
	 * @param form SubsystemCreationForm containing all the details about the subsystem to be created.
	 * @throws UnauthorizedAccessException 
	 */
	public void createSubsystem(SubsystemCreationForm form) throws UnauthorizedAccessException {
		new CreateSubsystemCommand(getBugTrap(), form).execute();
	}
}