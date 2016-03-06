package model.projects;

import java.util.Date;

public class Project extends System {

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
	
	public Date getCreationDate() {
		return creationDate;
	}

	void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public double getBudgetEstimate() {
		return budgetEstimate;
	}

	void setBudgetEstimate(double budgetEstimate) {
		this.budgetEstimate = budgetEstimate;
	}

	public ProjectTeam getTeam() {
		return team;
	}

	void setTeam(ProjectTeam team) {
		this.team = team;
	}
}