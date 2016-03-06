package model.projects;

import java.util.ArrayList;
import java.util.Date;

import model.projects.builders.ProjectBuilder;
import model.projects.builders.SubsystemBuilder;
import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectDeleteForm;
import model.projects.forms.ProjectUpdateForm;
import model.projects.forms.SubsystemCreationForm;
import model.users.Developer;

public class ProjectManager implements ProjectDAO {

	private ArrayList<Project> projectList;

	public ProjectManager() {
		projectList = new ArrayList<Project>();
	}
	
	/**
	 * Create and add a new project to the list.
	 * @param form The filled in form with the details about the project to be created.
	 */
	public void createProject(ProjectCreationForm form) {
		ProjectTeam team = new ProjectTeam();
		team.addMember(form.getLeadDeveloper(), Role.LEAD);
		createProject(form.getName(), form.getDescription(), new Date(), form.getStartDate(), form.getBudgetEstimate(), team, new Version(1, 0, 0));
	}

	public Project createProject(String name, String description, Date creationDate, Date startDate, double budgetEstimate, ProjectTeam team, Version version) {
		if (version == null)
			version = new Version(1, 0, 0);
		if (team == null)
			team = new ProjectTeam();
		
		Project p = (new ProjectBuilder())
				.setName(name)
				.setCreationDate(creationDate)
				.setStartDate(startDate)
				.setDescription(description)
				.setTeam(team)
				.setVersion(version)
				.setBudgetEstimate(budgetEstimate)
				.getProject();
		projectList.add(p);
		
		return p;
	}
	
	/**
	 * 
	 * @param form
	 */
	@Override
	public void updateProject(ProjectUpdateForm form) {
		Project project = form.getProject();
		for (Project p : projectList) {
			if (p == project) {
				p.setBudgetEstimate(form.getBudgetEstimate());
				p.setDescription(form.getDescription());
				p.setName(form.getName());
				p.setStartDate(form.getStartDate());
				p.setVersion(form.getVersion());
				p.getTeam().setLeadDeveloper(form.getLeadDeveloper());
			}
		}
	}

	/**
	 * 
	 * @param project
	 */
	@Override
	public void deleteProject(ProjectDeleteForm form) {
		Project project = form.getProject();
		for (int i = 0; i < projectList.size(); i++) {
			if (projectList.get(i) == project) 
				projectList.remove(i);
		}
	}

	/**
	 * 
	 * @param form
	 */
	@Override
	public void assignToProject(ProjectAssignForm form) {
		Project project = form.getProject();
		for (Project p : projectList) {
			if (p == project) 
				p.getTeam().addMember(form.getDeveloper(), form.getRole());
		}
	}

	@Override
	public ArrayList<Project> getProjects() {
		return projectList;
	}

	@Override
	public ArrayList<Project> getProjectsForLeadDeveloper(Developer dev) {
		ArrayList<Project> projs = new ArrayList<Project>();
		for (Project p : projectList) {
			if (p.getTeam().getLeadDeveloper() == dev) 
				projs.add(p);
		}
		return projs;
	}

	@Override
	public void createSubsystem(SubsystemCreationForm form) {
		createSubsystem(form.getName(), form.getDescription(), form.getProject(), form.getParent(), new Version(1, 0, 0));
	}
	
	public Subsystem createSubsystem(String name, String description, Project project, System parent, Version version) {
		if (version == null)
			version = new Version(1, 0, 0);
		
		Subsystem sub = (new SubsystemBuilder())
				.setDescription(description)
				.setName(name)
				.setProject(project)
				.setVersion(version)
				.setParent(parent)
				.getSubsystem();
		parent.addSubsystem(sub);
		return sub;
	}
	
	public Subsystem getSubsystemWithName(String name) {
		for (Project p : projectList) {
			for (Subsystem s : p.getAllDirectOrIndirectSubsystems()) {
				if (s.getName().equals(name))
					return s;
			}
		}
		return null;
	}
}