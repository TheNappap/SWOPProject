package controllers;

import java.util.Date;
import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.IProject;
import model.projects.Version;
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
	 * Create a project with the information provided in the form.
	 * @param form ProjectCreationForm containing all the details about the project to be created.
	 * @return The Project that was created.
	 * @throws UnauthorizedAccessException 
	 */
	public IProject createProject(ProjectCreationForm form) throws UnauthorizedAccessException {
		form.allVarsFilledIn();
		return getBugTrap().getProjectManager().createProject(form.getName(), form.getDescription(), new Date(), form.getStartDate(), form.getBudgetEstimate(), form.getLeadDeveloper(), Version.firstVersion());
	}

	/**
	 * Fork a project with the information provided in the form.
	 * @param form ProjectForkForm containing all the details about the fork.
	 * @return The Project that was forked.
	 * @throws UnauthorizedAccessException 
     */
	public IProject forkProject(ProjectForkForm form) throws UnauthorizedAccessException {
		form.allVarsFilledIn();
		return getBugTrap().getProjectManager().createFork(form.getProject(), form.getBudgetEstimate(), form.getVersion(), form.getStartDate());
	}

	/**
	 * Update a project with the information provided in the form.
	 * @param form ProjectUpdateForm containing all the details about the update.
	 * @throws UnauthorizedAccessException 
	 */
	public void updateProject(ProjectUpdateForm form) throws UnauthorizedAccessException {
		form.allVarsFilledIn();
		getBugTrap().getProjectManager().updateProject(form.getProject(), form.getName(), form.getDescription(), form.getBudgetEstimate(), form.getStartDate());
	}

	/**
	 * Assign a developer to the project with the information provided in the form.
	 * @param form ProjectAssignForm containing the details about the assignment.
	 * @throws UnauthorizedAccessException 
	 */
	public void assignToProject(ProjectAssignForm form) throws UnauthorizedAccessException {
		form.allVarsFilledIn();
		getBugTrap().getProjectManager().assignToProject(form.getProject(), form.getDeveloper(), form.getRole());
	}

	/**
	 * Delete a project with the information provided in the form.
	 * @param form ProjectDeleteForm containing the details about the project to be deleted.
	 * @throws UnauthorizedAccessException 
	 */
	public void deleteProject(ProjectDeleteForm form) throws UnauthorizedAccessException {
		getBugTrap().getProjectManager().deleteProject(form.getProject());
	}
	
	/**
	 * Create a subsystem with the information provided in the form.
	 * @param form SubsystemCreationForm containing all the details about the subsystem to be created.
	 * @throws UnauthorizedAccessException 
	 */
	public void createSubsystem(SubsystemCreationForm form) throws UnauthorizedAccessException {
		form.allVarsFilledIn();
		getBugTrap().getProjectManager().createSubsystem(form.getName(), form.getDescription(), form.getProject(), form.getParent(), Version.firstVersion());
	}
}