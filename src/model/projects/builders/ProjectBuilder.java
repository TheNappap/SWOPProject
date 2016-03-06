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
		if (name == null) throw new NullPointerException("Name is null.");
		if (description == null) throw new NullPointerException("Description is null.");
		if (version == null) throw new NullPointerException("Version is null.");
		if (creationDate == null) throw new NullPointerException("CreationDate is null");
		if (startDate == null) throw new NullPointerException("StartDate is null");
		if (budgetEstimate <= 0) throw new IllegalArgumentException("Budget estimate must be strictly positive.");
	}
}
