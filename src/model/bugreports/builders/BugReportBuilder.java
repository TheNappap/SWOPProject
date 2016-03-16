package model.bugreports.builders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.bugreports.BugReport;
import model.bugreports.BugTag;
import model.bugreports.comments.InitialComment;
import model.projects.Subsystem;
import model.users.Developer;
import model.users.Issuer;

/**
 * Builder pattern.
 * Use setters to set variables for a new BugReport.
 * Use getter to validate variables, build BugReport and return BugReport.
 */
public class BugReportBuilder {

	//Required parameters
	private String title; 			//Title of the BugReport. 
	private String description;		//Description of the BugReport. 
	private Subsystem subsystem; 	//Subsystem the BugReport belongs to.
	private Issuer issuedBy; 		//Issuer who issued the BugReport. 
	private ArrayList<BugReport> dependsOn; //Other BugReports the BugReports depends on.
	
	//Optional Parameters
	private Date creationDate 	= new Date();	//The day this BugReport was created.
	private BugTag bugTag		= BugTag.NEW; 	//The tag assigned to the BugReport.
	private BugReport duplicate;				//Duplicate of the BugReport, if any.
	private List<Developer> assignees 		= new ArrayList<Developer>();		//Developers assigned to the BugReport.
	private List<InitialComment> comments 	= new ArrayList<InitialComment>();	//Comments on the BugReport.
	
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
	 * Set the dependencies for the BugReport.  
	 * @param dependsOn The BugReports the BugReport depends on.  
	 * @return this.  
	 */
	public BugReportBuilder setDependsOn(ArrayList<BugReport> dependsOn) {
		this.dependsOn = dependsOn;
		return this;
	}
	
	/**
	 * Set the duplicate of the BugReport.
	 * @param duplicate The duplicate of the BugReport.
	 * @return this.
	 */
	public BugReportBuilder setDuplicate(BugReport duplicate) {
		this.duplicate = duplicate;
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
		this.bugTag = tag;
		return this;
	}
	
	/**
	 * Set the assignees for the BugReport.
	 * @param assignees The Developers assigned to the BugReport.
	 * @return this.
	 */
	public BugReportBuilder setAssignees(List<Developer> assignees) {
		this.assignees = assignees;
		return this;
	}
	
	/**
	 * Set the comments on the BugReport.
	 * @param comments The InitialComments on the BugReport.
	 * @return this.
	 */
	public BugReportBuilder setComments(List<InitialComment> comments) {
		this.comments = comments;
		return this;
	}

	/**
	 * Build and return a BugReport with set variables.
	 * @throws NullPointerException if one of variables is null.
	 * @return A BugReport with set variables.
	 */
	public BugReport getBugReport() {
		validate();
		return new BugReport(title, description, subsystem, dependsOn, assignees, comments, issuedBy, creationDate, bugTag, duplicate);
	}

	//Assure all variables are not null.
	private void validate() {
		if (title == null) 			throw new IllegalStateException("Title is null");
		if (description == null) 	throw new IllegalStateException("Description is null");
		if (subsystem == null) 		throw new IllegalStateException("Subsystem is null");
		if (issuedBy == null) 		throw new IllegalStateException("IssuedBy is null");
		if (dependsOn == null) 		throw new IllegalStateException("DependsOn is null");
		if (creationDate == null)	throw new IllegalStateException("CreationDate is null");
		if (bugTag == null) 		throw new IllegalStateException("BugTag is null");
		if (assignees == null)		throw new IllegalStateException("Assignees is null");
		if (comments == null)		throw new IllegalStateException("Comments is null");
		
		if (bugTag == BugTag.DUPLICATE && duplicate == null) throw new IllegalStateException("BugTag is DUPLICATE but duplicate is null.");
		if (bugTag != BugTag.DUPLICATE && duplicate != null) throw new IllegalStateException("BugTag isn't DUPLICATE but yet duplicate isn't null.");
	}

}