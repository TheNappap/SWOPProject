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
	
	/**
	 * Create and add a new project to the list.
	 * @param form The filled in form with the details about the project to be created.
	 */
	public IProject createProject(ProjectCreationForm form) {
		if (form == null) throw new IllegalArgumentException("ProjectCreationForm can not be null!");

		ProjectTeam team = new ProjectTeam();
		team.addMember(form.getLeadDeveloper(), Role.LEAD);
		return createProject(form.getName(), form.getDescription(), new Date(), form.getStartDate(), form.getBudgetEstimate(), team, new Version(1, 0, 0));
	}

	public IProject createProject(String name, String description, Date creationDate, Date startDate, double budgetEstimate, ProjectTeam team, Version version) {
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
	 * Fork an existing project and add it to the projects list.
	 * @param form The ProjectForkForm containing all the details about the project to be forked.
     */
	public IProject createFork(ProjectForkForm form) {
		if (form == null) throw new IllegalArgumentException("ProjectForkForm can not be null!");
		form.allVarsFilledIn();

		return createFork(form.getProject(), form.getBudgetEstimate(), form.getVersion(), form.getStartDate());
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

	/**
	 * Method to update a project.
	 * @param form The ProjectUpdateForm containing all the details about the project to update.
	 */
	public IProject updateProject(ProjectUpdateForm form) {
		if (form == null) throw new IllegalArgumentException("ProjectUpdateForm can not be null!");
		form.allVarsFilledIn();

		return updateProject(form.getProject(), form.getName(), form.getDescription(), form.getBudgetEstimate(), form.getStartDate());
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

	/**
	 * Method to delete a project.
	 * @param form The ProjectDeleteForm containing all the details about the project to delete.
	 */
	public void deleteProject(ProjectDeleteForm form) {
		if (form == null) throw new IllegalArgumentException("ProjectDeleteForm can not be null!");
		form.allVarsFilledIn();

		deleteProject(form.getProject());
	}

	public void deleteProject(IProject project) {
		for (int i = 0; i < projectList.size(); i++) {
			if (projectList.get(i) == project)
				projectList.remove(i);
		}
	}

	/**
	 * Method to assign a developer to a project.
	 * @param form The ProjectAssignForm containing all the details about the assignment.
	 */
	public void assignToProject(ProjectAssignForm form) {
		if (form == null) throw new IllegalArgumentException("ProjectAssignForm can not be null!");
		form.allVarsFilledIn();

		assignToProject(form.getProject(), form.getDeveloper(), form.getRole());
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

	/**
	 * Method to create a subsystem.
	 * @param form The SubsystemCreationForm containing all the data needed to create the subsystem.
	 * @return 
     */
	public ISubsystem createSubsystem(SubsystemCreationForm form) {
		if (form == null) throw new IllegalArgumentException("SubsystemCreationForm can not be null!");

		Project project = null;
		for (Project p : projectList)
			if (p == form.getProject())
				project = p;
		System system = null;
		if (form.getParent() == project)
			system = project;
		for (System s : project.getAllSubsystems())
			if (s == form.getParent())
				system = s;

		return createSubsystem(form.getName(), form.getDescription(), project, system, new Version(1, 0, 0));
	}
	
	public ISubsystem createSubsystem(String name, String description, Project project, System parent, Version version) {
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