package model.projects;

import java.util.ArrayList;

import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectUpdateForm;
import model.projects.forms.SubsystemCreationForm;
import model.users.Developer;

public interface ProjectDAO {
	public ArrayList<Project> getProjects();
	public ArrayList<Project> getProjectsForDeveloper(Developer dev);
	
	public void createProject(ProjectCreationForm form);
	public void updateProject(ProjectUpdateForm form);
	public void deleteProject(Project project);
	public void assignToProject(ProjectAssignForm form);
	
	public void createSubsystem(SubsystemCreationForm form);
}
