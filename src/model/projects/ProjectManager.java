package model.projects;

import java.util.ArrayList;
import java.util.Date;

import model.projects.builders.ProjectBuilder;
import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectUpdateForm;

public class ProjectManager implements ProjectDAO {

	private ArrayList<Project> projectList;

	public ArrayList<Project> getProjectList() {
		return this.projectList;
	}

	/**
	 * Create and add a new project to the list.
	 * @param form The filled in form with the details about the project to be created.
	 */
	public void createProject(ProjectCreationForm form) {
		ProjectTeam team = new ProjectTeam();
		team.addMember(form.getLeadDeveloper(), Role.LEAD);
		Project p = (new ProjectBuilder())
						.setName(form.getName())
						.setCreationDate(new Date())
						.setStartDate(form.getStartingDate())
						.setDescription(form.getDescription())
						.setTeam(team)
						.setVersion(new Version(1, 0, 0))
						.setBudgetEstimate(form.getBudgetEstimate())
						.getProject();
		projectList.add(p);
	}

	/**
	 * 
	 * @param form
	 */
	@Override
	public void updateProject(ProjectUpdateForm form) {
		// TODO - implement ProjectManager.updateProject
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param project
	 */
	@Override
	public void deleteProject(Project project) {
		// TODO - implement ProjectManager.deleteProject
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	@Override
	public void assignToProject(ProjectAssignForm form) {
		// TODO - implement ProjectManager.assignToProject
		throw new UnsupportedOperationException();
	}

}