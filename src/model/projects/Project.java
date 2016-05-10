package model.projects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.BugTrap;
import model.bugreports.IBugReport;
import model.users.IUser;

/**
 * This class represents a project in BugTrap.
 */
public class Project extends System implements IProject {

	private Version version;
	private final Date creationDate;
	private ProjectTeam projectTeam;
	
	private Date startDate;
	private double budgetEstimate;
	
	
	/**
	 * Constructor for a project
	 * @param bugTrap The BugTrap in which this project lives
	 * @param name The name for the project
	 * @param description The description for the project
	 * @param version The version of the project
	 * @param creationDate The date the project was created
	 * @param startDate The date the project starts
	 * @param budgetEstimate The budget estimate for the project
     * @param projectTeam The team assigned to the project
     */
	public Project(BugTrap bugTrap, String name, String description, List<Subsystem> subsystems, Version version, Date creationDate, Date startDate, double budgetEstimate, ProjectTeam projectTeam, AchievedMilestone milestone) {
		super(bugTrap, name, description, null, subsystems, milestone);
		
		this.version		= version;
		this.creationDate	= creationDate;
		this.startDate		= startDate;
		this.budgetEstimate	= budgetEstimate;

		if (projectTeam == null)
			this.projectTeam = new ProjectTeam();
		else
			this.projectTeam	= projectTeam;
	}

	//Copy constructor.
	protected Project(Project other) {
		super(other.bugTrap, other.name, other.description, null, other.subsystems, other.milestone);
		
		this.version		= other.version;
		this.creationDate 	= new Date();
		this.startDate	  	= new Date(other.getStartDate().getTime());
		this.projectTeam 	= new ProjectTeam(other.projectTeam);
		this.budgetEstimate = other.getBudgetEstimate();
		this.milestone 		= new AchievedMilestone();
	}

	/**
	 * 
	 * @return A clone of this Project
	 */
	public Project copy() {
		return new Project(this);
	}
	
	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}

	@Override
	public double getBudgetEstimate() {
		return budgetEstimate;
	}

	@Override
	public IUser getLeadDeveloper() {
		return projectTeam.getLeadDeveloper();
	}

	ProjectTeam getTeam() {
		return projectTeam;
	}

	@Override
	public void setLeadDeveloper(IUser user) {
		if (!user.isDeveloper())
			throw new IllegalArgumentException("Lead developer should be a developer.");
		projectTeam.setLeadDeveloper(user);
	}

	@Override
	public List<IUser> getProgrammers() {
		return projectTeam.getProgrammers();
	}

	@Override
	public List<IUser> getTesters() {
		return projectTeam.getTesters();
	}

	@Override
	public List<IUser> getAllDevelopers() {
		return projectTeam.getAllDevelopers();
	}

	@Override
	public void addProgrammer(IUser programmer) {
		if (!programmer.isDeveloper())
			throw new IllegalArgumentException("Programmer should be a developer!");
		projectTeam.addMember(programmer, Role.PROGRAMMER);
	}

	@Override
	public void addTester(IUser tester) {
		if (!tester.isDeveloper())
			throw new IllegalArgumentException("Tester should be a developer!");
		projectTeam.addMember(tester, Role.TESTER);
	}
	
	@Override
	public List<Role> getRolesNotAssignedTo(IUser dev){
		return projectTeam.getRolesNotAssignedTo(dev);
	}
	
	@Override
	public boolean isLead(IUser dev){
		return projectTeam.isLead(dev);
	}

	@Override
	public boolean isTester(IUser dev){
		return projectTeam.isTester(dev);
	}

	@Override
	public boolean isProgrammer(IUser dev){
		return projectTeam.isProgrammer(dev);
	}

	@Override
	public Version getVersion() {
		return version;
	}
	
	/**
	 * Set the version of the Project.
	 * @param version The new version of the Project.
	 */
	public void setVersion(Version version) {
		this.version = version;
	}

	/**
	 * Set the budget estimate of the Project.
	 * @param budgetEstimate The new budget estimate of the Project.
	 */
	public void setBudgetEstimate(double budgetEstimate) {
		this.budgetEstimate = budgetEstimate;
	}

	/**
	 * Set the start Date of the Project.
	 * @param startDate The new start Date of the Project.
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o))
			return false;

		// Budget estimate, team, version etc are allowed to change
		// when forking, so those are not compared here.

		Project project = (Project)o;
		if (this.getLeadDeveloper() == null ^ project.getLeadDeveloper() == null)
			return false;

		if (this.getLeadDeveloper() != null && !this.getLeadDeveloper().equals(project.getLeadDeveloper()))
			return false;

		return true;
	}
	
	@Override
	public void terminate() {
		for (ISubsystem sys : this.getAllDirectOrIndirectSubsystems()){
			((Subsystem) sys).terminate();
		}

		bugTrap.getNotificationManager().deleteRegistrationsForObservable(this);
		super.terminate();
		projectTeam.terminate();
		projectTeam = null;
	}

	@Override
	public List<IBugReport> getAllBugReports() {
		List<IBugReport> reports = new ArrayList<>();
		for (ISubsystem s : subsystems)
			reports.addAll(s.getAllBugReports());
		return reports;
	}

	@Override
	public List<IBugReport> getBugReports() {
		return new ArrayList<>();
	}

	@Override
	public double getBugImpact() {
		return 0;
	}
}