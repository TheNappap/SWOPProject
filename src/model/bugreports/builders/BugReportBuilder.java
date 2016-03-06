package model.bugreports.builders;

import java.util.ArrayList;
import java.util.Date;

import model.bugreports.BugReport;
import model.bugreports.BugTag;
import model.projects.Subsystem;
import model.users.Issuer;

/**
 * Builder pattern.
 * Use setters to set variables for a new BugReport.
 * Use getter to validate variables, build BugReport and return BugReport.
 */
public class BugReportBuilder {

	private String title; //Title of the BugReport. 
	private String description;	//Title of the BugReport. 
	private Subsystem subsystem; //Title of the BugReport. 
	private ArrayList<BugReport> dependsOn; //Other BugReports the BugReports belongs to. 
	private Issuer issuedBy; //Issuer who issued the BugReport. 
	private Date creationDate; //The day this BugReport was created.
	private BugTag tag; //The tag assigned to the BugReport

	/**  
	 * Empty constructor.  
	 */
	public BugReportBuilder() {
		
	}

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
	 * Set the creation date for the BugReport.  
	 * @param creationDate The date on which the BugReport was created  
	 * @return this.  
	 */  
	public BugReportBuilder setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
		return this;
	}
	
	/**  
	 * Set the BugTag for the BugReport.  
	 * @param tag The BugTag for the BugReport  
	 * @return this.  
	 */  
	public BugReportBuilder setBugTag(BugTag tag) {
		this.tag = tag;
		return this;
	}

	/**
	 * Build and return a BugReport with set variables.
	 * @throws NullPointerException if one of variables is null.
	 * @return A BugReport with set variables.
	 */
	public BugReport getBugReport() {
		validate();
		return new BugReport(title, description, subsystem, dependsOn, issuedBy, creationDate, tag);
	}

	//Assure all variables are not null.
	private void validate() {
		if (title == null) 			throw new NullPointerException("Bugreport title is null");
		if (description == null) 	throw new NullPointerException("Bugreport description is null");
		if (subsystem == null) 		throw new NullPointerException("Bugreport subsystem is null");
		if (dependsOn == null) 		throw new NullPointerException("Bugreport dependsOn is null");
		if (issuedBy == null) 		throw new NullPointerException("Bugreport issuedBy is null");
		if (creationDate == null)	throw new NullPointerException("Bugreport creationDate is null");
		if (tag == null) 			throw new NullPointerException("Bugreport tag is null");
	}

}