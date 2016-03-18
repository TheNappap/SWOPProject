package model.projects.forms;

import java.util.Date;

import model.Form;
import model.projects.Project;

/**
 * Form used to store temporary data to update a project.
 */
public class ProjectUpdateForm implements Form {

	private String name;
	private String description;
	private double budgetEstimate;
	private Date startDate;
	
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
	
	public Project getProject() {
		return project;
	}
	
	public void setProject(Project project) {
		if (project == null) throw new NullPointerException("Given project is null.");
		
		this.project = project;
	}

	@Override
	public void allVarsFilledIn() {
		if (getName() == null) throw new NullPointerException("Name is null");
		if (getDescription() == null) throw new NullPointerException("Description is null");
		if (getBudgetEstimate() <= 0) throw new IllegalArgumentException("Budget estimate is null");
		if (getStartDate() == null) throw new NullPointerException("Start Date is null");
		if (getProject() == null) throw new NullPointerException("Project is null");
	}
}