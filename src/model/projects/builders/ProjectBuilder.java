package model.projects.builders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.projects.AchievedMilestone;
import model.projects.Project;
import model.projects.ProjectTeam;
import model.projects.Subsystem;
import model.projects.Version;

public class ProjectBuilder {

	//System variables.
	//-Required.
	private String name;
	private String description;
	//-Optional.
	private List<Subsystem> subsystems = new ArrayList<Subsystem>();
	//-Restricted
	private AchievedMilestone milestone = new AchievedMilestone();
	
	//Project variables.
	//-Required
	private Date creationDate;
	private double budgetEstimate;
	private ProjectTeam team;
	//-Optional
	private Date startDate 	= new Date();
	private Version version = new Version(1,0,0);
	
	/**
	 * Constructor for a ProjectBuilder. 
	 * This class is able to construct Project objects.
	 */
	public ProjectBuilder() {
		
	}
	
	/**
	 * Method to set the name of the Project object being built.
	 * @param name The name to set for the project.
	 * @return this, with set name.
	 */
	public ProjectBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	/**
	 * Method to set the description of the Project object being built.
	 * @param description The description to set for the project
	 * @return this, with set description.
	 */
	public ProjectBuilder setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public ProjectBuilder setSubsystem(List<Subsystem> subsystems) {
		this.subsystems = subsystems;
		return this;
	}
	
	/**
	 * Method to set the creation date of the Project object being built.
	 * @param creationDate The creation date to set for the project
	 * @return this, with set creation date.
	 */
	public ProjectBuilder setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
		return this;
	}

	/**
	 * Method to set the budget estimate of the Project object being built.
	 * @param budgetEstimate The budget estimate to set for the project
	 * @return this, with set budget estimate.
	 */
	public ProjectBuilder setBudgetEstimate(double budgetEstimate) {
		this.budgetEstimate = budgetEstimate;
		return this;
	}

	/**
	 * Method to set the team of the Project object being built.
	 * @param team The team to set for the project
	 * @return this, with set team.
	 */
	public ProjectBuilder setTeam(ProjectTeam team) {
		this.team = team;
		return this;
	}

	/**
	 * Method to set the start date of the Project object being built.
	 * @param startDate The start date to set for the project
	 * @return this, with set start date.
	 */
	public ProjectBuilder setStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}

	/**
	 * Method to set the version of the Project object being built.
	 * @param version The version to set for the project
	 * @return this, with set version.
	 */
	public ProjectBuilder setVersion(Version version) {
		this.version = version;
		return this;
	}
	
	/**
	 * Method to create the Project object with the set properties.
	 * @return A Project object with the given properties.
	 */
	public Project getProject() {
		validate();
		return new Project(name, description, subsystems, version, creationDate, startDate, budgetEstimate, team, milestone);
	}
	
	private void validate() {
		if (name == null) 			throw new NullPointerException("Name is null.");
		if (description == null) 	throw new NullPointerException("Description is null.");
		if (subsystems == null) 	throw new NullPointerException("Subsystems is null");
		if (creationDate == null) 	throw new NullPointerException("CreationDate is null");
		if (team == null) 			throw new NullPointerException("Team is null");
		if (startDate == null) 		throw new NullPointerException("StartDate is null");
		if (version == null)		throw new NullPointerException("Version is null");
		if (budgetEstimate <= 0) 	throw new IllegalArgumentException("Budget estimate must be strictly positive.");
	}
}
