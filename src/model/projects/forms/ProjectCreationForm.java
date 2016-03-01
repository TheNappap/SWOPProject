package model.projects.forms;

import java.util.Date;

import model.users.Developer;

import java.util.ArrayList;

public class ProjectCreationForm {

	private String name;
	private String description;
	private double budgetEstimate;
	private Date startingDate;
	private Developer leadDeveloper;
	private ArrayList<Developer> possibleLeads;

	ProjectCreationForm() {
		// TODO - implement ProjectCreationForm.ProjectCreationForm
		throw new UnsupportedOperationException();
	}

	public ArrayList<Developer> getPossibleLeads() {
		return this.possibleLeads;
	}

	/**
	 * 
	 * @param possibleLeads
	 */
	private void setPossibleLeads(ArrayList<Developer> possibleLeads) {
		this.possibleLeads = possibleLeads;
	}

}