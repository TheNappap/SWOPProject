package model.projects.forms;

import java.util.ArrayList;
import java.util.Date;

import model.projects.Project;
import model.users.Developer;

public class ProjectUpdateForm {

	private String name;
	private String description;
	private String budgetEstimate;
	private Date startingDate;
	private Developer leadDeveloper;
	private ArrayList<Developer> possibleLeads;

	/**
	 * 
	 * @param project
	 */
	ProjectUpdateForm(Project project) {
		// TODO - implement ProjectUpdateForm.ProjectUpdateForm
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