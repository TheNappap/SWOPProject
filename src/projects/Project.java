package projects;

import java.util.ArrayList;
import java.util.Date;

import projects.forms.ProjectAssignForm;

public class Project extends System {

	Project(String name, String description, System parent, ArrayList<Subsystem> subsystems, Version version, int id, Date startDate, int budgetEstimate, ProjectTeam team) {
		super(name, description, parent, subsystems, version);
		
	}

	private int id;
	private Date creationDate;
	private Date startDate;
	private int budgetEstimate;
	private ProjectTeam team;
	
	public int getId() {
		return id;
	}

	void setId(int id) {
		this.id = id;
	}

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

	public int getBudgetEstimate() {
		return budgetEstimate;
	}

	void setBudgetEstimate(int budgetEstimate) {
		this.budgetEstimate = budgetEstimate;
	}

	public ProjectTeam getTeam() {
		return team;
	}

	void setTeam(ProjectTeam team) {
		this.team = team;
	}

	/**
	 * 
	 * @param form
	 */
	public void assignDeveloper(ProjectAssignForm form) {
		// TODO - implement Project.assignDeveloper
		throw new UnsupportedOperationException();
	}

}