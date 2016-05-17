package model.projects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.notifications.NotificationType;
import model.notifications.signalisations.Signalisation;
import model.projects.builders.ProjectBuilder;
import model.users.IUser;

/**
 * Class that stores and manages projects.
 */
public class ProjectManager {

	private final ArrayList<Project> projectList;
	private final BugTrap bugTrap;

	/**
	 * Constructor.
	 */
	public ProjectManager(BugTrap bugTrap) {
		projectList = new ArrayList<Project>();
		this.bugTrap = bugTrap;
	}
	
	/**
	 * Creates a project with a given name, description, creation date, start date, budget estimate, lead and version
	 * @param name 			the name of the project
	 * @param description	the description of the project
	 * @param creationDate	the creation date of the project
	 * @param startDate		the start date of the project
	 * @param budgetEstimate the budget estimate of the project
	 * @param lead			the lead developer of the project
	 * @param version		the version of the project
	 */
	public void createProject(String name, String description, Date creationDate, Date startDate, double budgetEstimate, IUser lead, Version version) {
		if (name == null || description == null || creationDate == null || startDate ==  null)
			throw new IllegalArgumentException("Arguments should not be null.");

		if (version == null)
			version = new Version(1, 0, 0);

		ProjectTeam team = new ProjectTeam();
		if (lead != null)
			team.addMember(lead, Role.LEAD);

		projectList.add(new ProjectBuilder(bugTrap)
				.setName(name)
				.setCreationDate(creationDate)
				.setStartDate(startDate)
				.setDescription(description)
				.setTeam(team)
				.setVersion(version)
				.setBudgetEstimate(budgetEstimate)
				.getProject());
	}

	/**
	 * Creates a fork of an existing project with a given budget estimate, version and start date
	 * @param project			the project to fork
	 * @param budgetEstimate	the budget estimate of the fork
	 * @param version			the version of the fork
	 * @param startDate			the start date of the fork
	 */
	public void createFork(IProject project, double budgetEstimate, Version version, Date startDate) {
		if (project == null || version == null || startDate == null)
			throw new IllegalArgumentException("Arguments should not be null.");
		if (project.getVersion().compareTo(version) != -1)
			throw new IllegalArgumentException("Fork version should be higher than current project version.");

		Project toFork = null;
		for (Project p : projectList)
			if (p == project)
				toFork = p;
		
		Project fork = toFork.copy();
		toFork.signal(new Signalisation(NotificationType.PROJECT_FORK, toFork));

		fork.setVersion(version);
		fork.setBudgetEstimate(budgetEstimate);
		fork.setStartDate(startDate);
		
		projectList.add(fork);
	}

	/**
	 * Deletes a project, its subsystems, the bugreports for the subsystems and the registrations
	 * for the project, subsystems and bugreports
	 * @param project the project to delete
	 * @throws UnauthorizedAccessException 
	 */
	public void deleteProject(IProject project) throws UnauthorizedAccessException {
		if (project == null)
			throw new IllegalArgumentException("Project to delete should not be null.");

		for (int i = 0; i < projectList.size(); i++) {
			if (projectList.get(i) == project) {
				((Project) project).terminate();
				projectList.remove(i);
			}
		}
	}

	/**
	 * Method to get all the projects in the system.
	 * @return List containing all the projects in the system.
     */
	public List<IProject> getProjects() {
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
	private List<IProject> getProjectsForLeadDeveloper(IUser dev) {
		if (dev == null) throw new IllegalArgumentException("Developer can not be null!");
		if (!dev.isDeveloper()) throw new IllegalArgumentException("Developer should be a developer.");

		ArrayList<IProject> projs = new ArrayList<IProject>();
		for (Project p : projectList) {
			if (p.getLeadDeveloper() == dev)
				projs.add(p);
		}

		if (projs.size() == 0) throw new IllegalArgumentException("The given developer is not leading any projects.");

		return projs;
	}

	/**
	 * Returns the projects where the logged in user is lead developer.
	 * @return a list of project where the logged in user is lead
	 */
	public List<IProject> getProjectsForSignedInLeadDeveloper() {
		List<IProject> projects = getProjectsForLeadDeveloper(bugTrap.getUserManager().getLoggedInUser());
		
		return projects;
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