package projects.forms;

import java.util.Date;
import java.util.ArrayList;

import users.*;

public class ProjectCreationForm {

	private String name;
	private String description;
	private double budgetEstimate;
	private Date startingDate;
	private Developer leadDeveloper;

	ProjectCreationForm() {
		name = null;
		description = null;
		budgetEstimate = 0;
		startingDate = null;
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

	public Date getStartingDate() {
		return startingDate;
	}

	public void setStartingDate(Date startingDate) {
		if (startingDate == null) throw new NullPointerException("Given starting date is null.");
		
		this.startingDate = startingDate;
	}

	public Developer getLeadDeveloper() {
		return leadDeveloper;
	}

	public void setLeadDeveloper(Developer leadDeveloper) {
		if (leadDeveloper == null) throw new NullPointerException("Given lead developer is null.");
		
		this.leadDeveloper = leadDeveloper;
	}
}