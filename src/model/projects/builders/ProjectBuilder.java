package model.projects.builders;

import java.util.Date;

import model.projects.Project;
import model.projects.ProjectTeam;
import model.projects.Version;

public class ProjectBuilder {
	
	private String name;
	private String description;
	private Version version;
	private Date creationDate;
	private Date startDate;
	private double budgetEstimate;
	private ProjectTeam team;
	
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
	 * Method to set the creation date of the Project object being built.
	 * @param creationDate The creation date to set for the project
	 * @return this, with set creation date.
	 */
	public ProjectBuilder setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	 * Method to create the Project object with the set properties.
	 * @return A Project object with the given properties.
	 */
	public Project getProject() {
		validate();
		return new Project(name, description, version, creationDate, startDate, budgetEstimate, team);
	}
	
	private void validate() {
		if (name == null) throw new NullPointerException("Name is null.");
		if (description == null) throw new NullPointerException("Description is null.");
		if (version == null) throw new NullPointerException("Version is null.");
		if (creationDate == null) throw new NullPointerException("CreationDate is null");
		if (startDate == null) throw new NullPointerException("StartDate is null");
		if (budgetEstimate <= 0) throw new IllegalArgumentException("Budget estimate must be strictly positive.");
	}
}
