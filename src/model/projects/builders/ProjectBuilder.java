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
	
	public ProjectBuilder() {
		
	}
	
	public ProjectBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public ProjectBuilder setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public ProjectBuilder setVersion(Version version) {
		this.version = version;
		return this;
	}
	
	public ProjectBuilder setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
		return this;
	}
	
	public ProjectBuilder setStartDate(Date startDate) {
		this.startDate = startDate;
		return this;
	}

	public ProjectBuilder setBudgetEstimate(double budgetEstimate) {
		this.budgetEstimate = budgetEstimate;
		return this;
	}
	
	public ProjectBuilder setTeam(ProjectTeam team) {
		this.team = team;
		return this;
	}	
	
	public Project getProject() {
		validate();
		return new Project(name, description, version, creationDate, startDate, budgetEstimate, team);
	}
	
	private void validate() {
		assert (name != null) : "Project name is null";
		assert (description != null) : "Project description is null";
		assert (version != null) : "Project version is null";
		assert (creationDate != null) : "Project creation date is null";
		assert (startDate != null) : "Project start date is null";
		assert (budgetEstimate > 0) : "Project budget estimate should be strictly positive";
		assert (team != null) : "Project team is null";
	}
}
