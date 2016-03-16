package model.projects;

import java.util.ArrayList;
import java.util.List;

import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectDeleteForm;
import model.projects.forms.ProjectUpdateForm;
import model.projects.forms.SubsystemCreationForm;
import model.users.Developer;

public interface ProjectDAO {
	/**
	 * Method to get the list of all projects.
	 * @return An ArrayList containing all projects
     */
	public List<Project> getProjects();

	/**
	 * Method to get all projects assigned to a certain lead developer.
	 * @param dev The developer for who to find the projects he/she leads.
	 * @return An ArrayList containing all projects lead by the given developer.
     */
	public List<Project> getProjectsForLeadDeveloper(Developer dev);
	
	public void createProject(ProjectCreationForm form);
	public void updateProject(ProjectUpdateForm form);
	public void deleteProject(ProjectDeleteForm form);
	public void assignToProject(ProjectAssignForm form);
	
	public void createSubsystem(SubsystemCreationForm form);
}
