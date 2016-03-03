package model.projects.forms;

import java.util.Date;

import model.Form;
import model.projects.Project;
import model.projects.Version;
import model.users.Developer;

public class ProjectUpdateForm implements Form {

	private String name;
	private String description;
	private double budgetEstimate;
	private Date startDate;
	private Developer leadDeveloper;
	private Version version;
	
	private Project project;

	public ProjectUpdateForm() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) throw new NullPointerException("Given name is null.");
		
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null) throw new NullPointerException("Given description is null.");
		
		this.description = description;
	}

	public double getBudgetEstimate() {
		return budgetEstimate;
	}

	public void setBudgetEstimate(double budgetEstimate) {
		if (budgetEstimate <= 0) throw new IllegalArgumentException("The budget estimate should be strictly positive.");
		
		this.budgetEstimate = budgetEstimate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		if (startDate == null) throw new NullPointerException("Given starting date is null.");
		
		this.startDate = startDate;
	}

	public Developer getLeadDeveloper() {
		return leadDeveloper;
	}

	public void setLeadDeveloper(Developer leadDeveloper) {
		if (leadDeveloper == null) throw new NullPointerException("Given lead developer is null.");
		
		this.leadDeveloper = leadDeveloper;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		if (version == null) throw new NullPointerException("Given version is null.");
		if (this.version != null && this.version.compareTo(version) == -1) throw new IllegalArgumentException("New version can not be less than previous version.");
		
		this.version = version;
	}
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		if (project == null) throw new NullPointerException("Given project is null.");
		
		this.project = project;
	}

	@Override
	public void allVarsFilledIn() {
		assert(name != null) : "Name is null";
		assert(description != null) : "Description is null";
		assert(budgetEstimate > 0) : "Budget estimate is not strictly positive";
		assert(startDate != null) : "Starting date is null";
		assert(leadDeveloper != null) : "Lead developer is null";
		assert(project != null) : "Project is null";
		assert(version != null) : "Version is null";
	}
}