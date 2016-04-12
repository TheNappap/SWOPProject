package model.projects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.BugTrap;
import model.Milestone;
import model.bugreports.BugReport;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.projects.builders.ProjectBuilder;
import model.projects.builders.SubsystemBuilder;
import model.users.IUser;

/**
 * Class that stores and manages projects.
 */
public class ProjectManager {

	private final ArrayList<Project> projectList;
	private final BugTrap bugTrap;

	public ProjectManager(BugTrap bugTrap) {
		projectList = new ArrayList<Project>();
		this.bugTrap = bugTrap;
	}
	
	public void createProject(String name, String description, Date creationDate, Date startDate, double budgetEstimate, IUser lead, Version version) {
		if (name == null || description == null || creationDate == null || startDate ==  null)
			throw new IllegalArgumentException("Arguments should not be null.");

		if (version == null)
			version = new Version(1, 0, 0);

		ProjectTeam team = new ProjectTeam();
		if (lead != null)
			team.addMember(lead, Role.LEAD);

		projectList.add(new ProjectBuilder()
				.setName(name)
				.setCreationDate(creationDate)
				.setStartDate(startDate)
				.setDescription(description)
				.setTeam(team)
				.setVersion(version)
				.setBudgetEstimate(budgetEstimate)
				.getProject());
	}

	public void createFork(IProject project, double budgetEstimate, Version version, Date startDate) {
		if (project == null || version == null || startDate == null)
			throw new IllegalArgumentException("Arguments should not be null.");

		Project toFork = null;
		for (Project p : projectList)
			if (p == project)
				toFork = p;
		
		Project fork = toFork.copy();
		
		fork.setVersion(version);
		fork.setBudgetEstimate(budgetEstimate);
		fork.setStartDate(startDate);
		
		projectList.add(fork);
		
		
	}

	public void updateProject(IProject project, String name, String description, double budgetEstimate, Date startDate) {
		if (project == null || name == null || description == null || startDate == null)
			throw new IllegalArgumentException("Arguments should not be null.");

		for (Project p : projectList) {
			if (p == project) {
				p.setBudgetEstimate(budgetEstimate);
				p.setDescription(description);
				p.setName(name);
				p.setStartDate(startDate);
			}
		}
	}

	public void deleteProject(IProject project) {
		if (project == null)
			throw new IllegalArgumentException("Project to delete should not be null.");

		for (int i = 0; i < projectList.size(); i++) {
			if (projectList.get(i) == project)
				projectList.remove(i);//TODO Recursively delete subsystems and bugReports
		}
	}


	public void assignToProject(IProject project, IUser dev, Role role) {
		if (project == null || dev == null || role == null)
			throw new IllegalArgumentException("Arguments should not be null.");

		for (Project p : projectList) {
			if (p == project)
				p.getTeam().addMember(dev, role);
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
			if (p.getTeam().getLeadDeveloper() == dev) 
				projs.add(p);
		}
		return projs;
	}

	public List<IProject> getProjectsForSignedInLeadDeveloper() {
		List<IProject> projects = getProjectsForLeadDeveloper(bugTrap.getUserManager().getLoggedInUser());
		
		if (projects.size() == 0) throw new IllegalArgumentException();
		
		return projects;
	}


	public void createSubsystem(String name, String description, IProject iproject, ISystem iparent) {
		if (name == null || description == null || iproject == null || iparent == null)
			throw  new IllegalArgumentException("Arguments should not be null.");

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

		(new SubsystemBuilder())
				.setDescription(description)
				.setName(name)
				.setProject(project)
				.setParent(system)
				.getSubsystem();
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

	/**
	 * Declares achieved milestone to a system
	 * @param system
	 * @param numbers
	 */
	public void declareAchievedMilestone(ISystem system, List<Integer> numbers) {
		if (numbers == null || numbers.isEmpty()) throw new IllegalArgumentException("Numbers can not be null or empty!");
		if (system == null) throw new IllegalArgumentException("System can not be null!");
		
		List<IBugReport> bugreports = bugTrap.getBugReportManager().getBugReportsForSystem(system);
		AchievedMilestone achieved = new AchievedMilestone(numbers);
		for (IBugReport bugreport : bugreports) {
			BugTag tag = bugreport.getBugTag();
			if(tag == BugTag.CLOSED || tag == BugTag.NOTABUG || tag == BugTag.DUPLICATE){
				continue;
			}
			
			Milestone target = ((BugReport) bugreport).getTargetMilestone();
			if(target == null || achieved.compareTo(target) >= 0){
				throw new IllegalArgumentException("The new declared achieved milestone should be less than a target milestone of a bugreport in progress");
			}
		}
		
		((System) system).declareAchievedMilestone(numbers);
	}
	
}