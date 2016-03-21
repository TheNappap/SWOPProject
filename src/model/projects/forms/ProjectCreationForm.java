package model.projects.forms;

import java.util.Date;

import model.Form;
import model.users.Developer;
import model.users.IUser;

/**
 * Form used to store temporary data to create a project.
 */
public class ProjectCreationForm implements Form {

	private String name;
	private String description;
	private double budgetEstimate;
	private Date startDate;
	private IUser leadDeveloper;

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

	public IUser getLeadDeveloper() {
		return leadDeveloper;
	}

	public void setLeadDeveloper(IUser leadDeveloper) {
		if (leadDeveloper == null) throw new NullPointerException("Given lead developer is null.");
		if (!leadDeveloper.isDeveloper()) throw new IllegalArgumentException("Lead developer should be a developer.");

		this.leadDeveloper = leadDeveloper;
	}

	@Override
	public void allVarsFilledIn() {
		if (getName() == null) throw new NullPointerException("Name is null");
		if (getDescription() == null) throw new NullPointerException("Description is null");
		if (getBudgetEstimate() <= 0) throw new IllegalArgumentException("Budget estimate is null");
		if (getStartDate() == null) throw new NullPointerException("Start date is null");
		if (getLeadDeveloper() == null) throw new NullPointerException("Lead is null");
	}
}