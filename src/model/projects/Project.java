package model.projects;

import java.util.Date;

public class Project extends System {

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

	private Date creationDate;
	private Date startDate;
	private double budgetEstimate;
	private ProjectTeam team;

	/**
	 * Method to get the creation date for the project
	 * @return The creation date of the project
     */
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
	public ProjectTeam getTeam() {
		return team;
	}

	void setTeam(ProjectTeam team) {
		this.team = team;
	}
}