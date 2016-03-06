package model.bugreports.builders;

import java.util.ArrayList;

import model.bugreports.BugReport;
import model.projects.Subsystem;
import model.users.Issuer;

/**
 * Builder pattern.
 * Use setters to set variables for a new BugReport.
 * Use getter to validate variables, build BugReport and return BugReport.
 */
public class BugReportBuilder {

	private String title;	//Title of the BugReport.
	private String description;	//Description of the BugReport.
	private Subsystem subsystem;	//Subsystem where the BugReport belongs.
	private ArrayList<BugReport> dependsOn;	//Other BugReports the BugReports belongs to.
	private Issuer issuedBy;	//Issuer who issued the BugReport.

	/**
	 * Empty constructor.
	 */
	public BugReportBuilder() { }

	/**
	 * Set the title for the BugReport.
	 * @param title The title for the BugReport.
	 * @return this.
	 */
	public BugReportBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	/**
	 * Set the description for the BugReport.
	 * @param description The description for the BugReport.
	 * @return this.
	 */
	public BugReportBuilder setDescription(String description) {
		this.description = description;
		return this;
	}
	
	/**
	 * Set the dependencies for the BugReport.
	 * @param dependsOn The BugReports the BugReport depends on.
	 * @return this.
	 */
	public BugReportBuilder setDependsOn(ArrayList<BugReport> dependsOn) {
		this.dependsOn = dependsOn;
		return this;
	}

	/**
	 * Set the Subsystem the BugReport belongs to.
	 * @param subsystem The Subsystem the BugReport belongs to.
	 * @return this.
	 */
	public BugReportBuilder setSubsystem(Subsystem subsystem) {
		this.subsystem = subsystem;
		return this;
	}

	/**
	 * Set the Issuer who creates the BugReport.
	 * @param issuedBy The Issuer who creates the BugReport
	 * @return this.
	 */
	public BugReportBuilder setIssuer(Issuer issuedBy) {
		this.issuedBy = issuedBy;
		return this;
	}

	/**
	 * Build and return a BugReport with set variables.
	 * @throws NullPointerException if one of variables is null.
	 * @return A BugReport with set variables.
	 */
	public BugReport getBugReport() {
		validate();
		return new BugReport(title, description, subsystem, dependsOn, issuedBy);
	}

	//Assure all variables are not null.
	private void validate() {
		if (title == null) 			throw new NullPointerException("Bugreport title is null");
		if (description == null) 	throw new NullPointerException("Bugreport description is null");
		if (subsystem == null) 		throw new NullPointerException("Bugreport subsystem is null");
		if (dependsOn == null) 		throw new NullPointerException("Bugreport dependsOn is null");
		if (issuedBy == null) 		throw new NullPointerException("Bugreport issuedBy is null");
	}

}