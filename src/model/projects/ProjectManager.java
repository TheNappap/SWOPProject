package model.projects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.builders.ProjectBuilder;
import model.projects.builders.SubsystemBuilder;
import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectDeleteForm;
import model.projects.forms.ProjectForkForm;
import model.projects.forms.ProjectUpdateForm;
import model.projects.forms.SubsystemCreationForm;
import model.users.IUser;

public class ProjectManager {

	private final ArrayList<Project> projectList;
	private final BugTrap bugTrap;

	public ProjectManager(BugTrap bugTrap) {
		projectList = new ArrayList<Project>();
		this.bugTrap = bugTrap;
	}
	
	public IProject createProject(String name, String description, Date creationDate, Date startDate, double budgetEstimate, IUser lead, Version version) {
		if (version == null)
			version = new Version(1, 0, 0);

		ProjectTeam team = new ProjectTeam();
		if (lead != null)
			team.addMember(lead, Role.LEAD);

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

	public IProject createFork(IProject project, double budgetEstimate, Version version, Date startDate) {
		Project toFork = null;
		for (Project p : projectList)
			if (p == project)
				toFork = p;

		Project fork = toFork.copy();
		fork.setBudgetEstimate(budgetEstimate);
		fork.setVersion(version);
		fork.setStartDate(startDate);
		projectList.add(fork);
		return fork;
	}

	public IProject updateProject(IProject project, String name, String description, double budgetEstimate, Date startDate) {
		for (Project p : projectList) {
			if (p == project) {
				p.setBudgetEstimate(budgetEstimate);
				p.setDescription(description);
				p.setName(name);
				p.setStartDate(startDate);
			}
		}
		return project;
	}

	public void deleteProject(IProject project) {
		for (int i = 0; i < projectList.size(); i++) {
			if (projectList.get(i) == project)
				projectList.remove(i);
		}
	}

	public void assignToProject(IProject project, IUser dev, Role role) {
		for (Project p : projectList) {
			if (p == project)
				p.getTeam().addMember(dev, role);
		}
	}

	/**
	 * Method to get all the projects in the system.
	 * @return List containing all the projects in the system.
     */
	public List<IProject> getProjects() throws UnauthorizedAccessException {
		if (!bugTrap.isLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in to perform this action.");

		ArrayList<IProject> projects = new ArrayList<IProject>();
		for (Project p : projectList)
			projects.add(p);
		return projects;
	}

	/**
	 * Method to get all the projects for which a given developer is lead.
	 * @param dev The developer for who to find the projects he/she leads.
	 * @return List containing all the projects for which the given developer is lead.
     */
	public List<IProject> getProjectsForLeadDeveloper(IUser dev) {
		if (dev == null) throw new IllegalArgumentException("Developer can not be null!");
		if (!dev.isDeveloper()) throw new IllegalArgumentException("Developer should be a developer.");

		ArrayList<IProject> projs = new ArrayList<IProject>();
		for (Project p : projectList) {
			if (p.getTeam().getLeadDeveloper() == dev) 
				projs.add(p);
		}
		return projs;
	}

	public ISubsystem createSubsystem(String name, String description, IProject iproject, ISystem iparent, Version version) {
		if (version == null)
			version = new Version(1, 0, 0);

		Project project = null;
		for (Project p : projectList)
			if (p == iproject)
				project = p;
		System system = null;
		if (iparent == project)
			system = project;
		for (System s : project.getAllSubsystems())
			if (s == iparent)
				system = s;

		Subsystem sub = (new SubsystemBuilder())
				.setDescription(description)
				.setName(name)
				.setProject(project)
				.setVersion(version)
				.setParent(system)
				.getSubsystem();
		system.addSubsystem(sub);
		return sub;
	}

	/**
	 * Method to get the subsystem in BugTrap with the given name.
	 * @param name The name for which to search.
	 * @return Subsystem with the given name.
     */
	public ISubsystem getSubsystemWithName(String name) {
		if (name == null) throw new IllegalArgumentException("Subsystem name can not be null!");

		for (Project p : projectList) {
			for (ISubsystem s : p.getAllDirectOrIndirectSubsystems()) {
				if (s.getName().equals(name))
					return s;
			}
		}
		return null;
	}
}