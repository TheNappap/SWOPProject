package model.projects;

import model.users.IUser;

import java.util.Date;
import java.util.List;

public class Project extends System implements IProject {

	/**
	 * Constructor for a project
	 * @param name The name for the project
	 * @param description The description for the project
	 * @param version The version of the project
	 * @param creationDate The date the project was created
	 * @param startDate The date the project starts
	 * @param budgetEstimate The budget estimate for the project
     * @param team The team assigned to the project
     */
	public Project(String name, String description, Version version, Date creationDate, Date startDate, double budgetEstimate, ProjectTeam team) {
		super(name, description, null, version);
		setCreationDate(creationDate);
		setStartDate(startDate);
		setBudgetEstimate(budgetEstimate);
		setTeam(team);
	}

	/**
	 * Copy constructor.
	 * @param project The Project object to copy.
     */
	Project(Project project) {
		super(project.getName(), project.getDescription(), null, project.getVersion().copy());
		setCreationDate(new Date(project.getCreationDate().getTime()));
		setStartDate(new Date(project.getStartDate().getTime()));
		setBudgetEstimate(project.getBudgetEstimate());
		setTeam(new ProjectTeam(project.getTeam()));
	}

	public Project copy() {
		return new Project(this);
	}

	private Date creationDate;
	private Date startDate;
	private double budgetEstimate;
	private ProjectTeam team;

	/**
	 * Method to get the creation date for the project
	 * @return The creation date of the project
     */
	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Method to get the start date for the project
	 * @return The start date of the project
	 */
	@Override
	public Date getStartDate() {
		return startDate;
	}

	void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * Method to get the budget estimate for the project
	 * @return The budget estimate of the project
	 */
	@Override
	public double getBudgetEstimate() {
		return budgetEstimate;
	}

	void setBudgetEstimate(double budgetEstimate) {
		this.budgetEstimate = budgetEstimate;
	}

	/**
	 * Method to get the team assigned to the project
	 * @return The team assigned the project
	 */
	ProjectTeam getTeam() {
		return team;
	}

	void setTeam(ProjectTeam team) {
		this.team = team;
	}

	@Override
	public IUser getLeadDeveloper() {
		return getTeam().getLeadDeveloper();
	}

	@Override
	public void setLeadDeveloper(IUser user) {
		if (!user.isDeveloper())
			throw new IllegalArgumentException("Lead developer should be a developer.");
		getTeam().setLeadDeveloper(user);
	}

	@Override
	public List<IUser> getProgrammers() {
		return getTeam().getProgrammers();
	}

	@Override
	public List<IUser> getTesters() {
		return getTeam().getTesters();
	}

	@Override
	public List<IUser> getAllDevelopers() {
		return getTeam().getAllDevelopers();
	}

	@Override
	public void addProgrammer(IUser programmer) {
		if (!programmer.isDeveloper())
			throw new IllegalArgumentException("Programmer should be a developer!");
		getTeam().addMember(programmer, Role.PROGRAMMER);
	}

	@Override
	public void addTester(IUser tester) {
		if (!tester.isDeveloper())
			throw new IllegalArgumentException("Tester should be a developer!");
		getTeam().addMember(tester, Role.TESTER);
	}
	

	/**
	 * Returns the roles that are not assigned to a given developer in a given project
	 * @param dev the given developer
	 * @return a list of roles not assigned to a developer
	 */
	@Override
	public List<Role> getRolesNotAssignedTo(IUser dev){
		return getTeam().getRolesNotAssignedTo(dev);
	}
	
	/**
	 * Returns if a given dev is lead
	 * @param dev the given developer
	 * @return if the given dev is lead
	 */
	@Override
	public boolean isLead(IUser dev){
		return getTeam().isLead(dev);
	}
	
	/**
	 * Returns if a given dev is a tester
	 * @param dev the given developer
	 * @return if the given dev is tester
	 */
	@Override
	public boolean isTester(IUser dev){
		return getTeam().isTester(dev);
	}
	
	/**
	 * Returns if a given dev is a programmer
	 * @param dev the given developer
	 * @return if the given dev is a programmer
	 */
	@Override
	public boolean isProgrammer(IUser dev){
		return getTeam().isProgrammer(dev);
	}
}