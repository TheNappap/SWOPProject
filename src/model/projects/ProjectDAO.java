package model.projects;

import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectUpdateForm;

public interface ProjectDAO {
	public void createProject(ProjectCreationForm form);
	public void updateProject(ProjectUpdateForm form);
	public void deleteProject(Project project);
	public void assignToProject(ProjectAssignForm form);
}
