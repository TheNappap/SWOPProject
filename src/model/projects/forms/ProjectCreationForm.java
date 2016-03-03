package model.projects.forms;

import java.util.Date;

import model.Form;
import model.users.Developer;

public class ProjectCreationForm implements Form {

	private String name;
	private String description;
	private double budgetEstimate;
	private Date startDate;
	private Developer leadDeveloper;

	public ProjectCreationForm() {
		name = null;
		description = null;
		budgetEstimate = 0;
		startDate = null;
		leadDeveloper = null;
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

	@Override
	public void allVarsFilledIn() {
		assert(name != null) : "Name is null";
		assert(description != null) : "Description is null";
		assert(budgetEstimate > 0) : "Budget estimate is not strictly positive";
		assert(startDate != null) : "Starting date is null";
		assert(leadDeveloper != null) : "Lead developer is null";
	}
}