package model.bugreports.builders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.bugreports.BugReport;
import model.bugreports.IBugReport;
import model.bugreports.Patch;
import model.bugreports.TargetMilestone;
import model.bugreports.Test;
import model.bugreports.bugtag.BugTag;
import model.bugreports.comments.Comment;
import model.notifications.observers.Observer;
import model.projects.ISubsystem;
import model.users.IUser;

/**
 * Builder pattern.
 * Use setters to set variables for a new BugReport.
 * Use getter to validate variables, build BugReport and return BugReport.
 */
public class BugReportBuilder {

	//Required parameters
	private String title; 				//Title of the BugReport. 
	private String description;			//Description of the BugReport. 
	private ISubsystem subsystem; 		//Subsystem the BugReport belongs to.
	private IUser issuedBy; 			//Issuer who issued the BugReport.
	private List<IBugReport> dependsOn; //Other BugReports the BugReports depends on.
	
	//Optional Parameters
	private Date creationDate 	= new Date();	//The day this BugReport was created.
	private BugTag bugTag		= BugTag.NEW; 	//The tag assigned to the BugReport.
	private List<Comment> comments 	= new ArrayList<Comment>();		//Comments on the BugReport.
	private List<IUser> assignees 	= new ArrayList<IUser>();	//Developers assigned to the BugReport.
	private List<Observer> observers = new ArrayList<Observer>();
	private List<String> optionals = new ArrayList<String>();
	private List<Test> tests = new ArrayList<Test>();
	private List<Patch> patches = new ArrayList<Patch>();
	private TargetMilestone milestone = new TargetMilestone();
	
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
	public BugReportBuilder setSubsystem(ISubsystem subsystem) {
		this.subsystem = subsystem;
		return this;
	}

	/**
	 * Set the Issuer who creates the BugReport.
	 * @param issuedBy The Issuer who creates the BugReport
	 * @return this.
	 */
	public BugReportBuilder setIssuer(IUser issuedBy) {
		if (!issuedBy.isIssuer()) throw new IllegalArgumentException("Issuer should be an issuer.");

		this.issuedBy = issuedBy;
		return this;
	}

	/**  
	 * Set the dependencies for the BugReport.  
	 * @param dependsOn The BugReports the BugReport depends on.  
	 * @return this.  
	 */
	public BugReportBuilder setDependsOn(List<IBugReport> dependsOn) {
		this.dependsOn = dependsOn;
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
	public BugReportBuilder setAssignees(List<IUser> assignees) {
		this.assignees = assignees;
		return this;
	}
	
	/**
	 * Set the comments on the BugReport.
	 * @param comments The InitialComments on the BugReport.
	 * @return this.
	 */
	public BugReportBuilder setComments(List<Comment> comments) {
		this.comments = comments;
		return this;
	}
	
	public BugReportBuilder setObservers(List<Observer> observers) {
		this.observers = observers;
		return this;
	}

	public BugReportBuilder setOptionals(List<String> optionals) {
		this.optionals = optionals;
		return this;
	}
	
	public BugReportBuilder setTests(List<Test> tests) {
		this.tests = tests;
		return this;
	}
	
	public BugReportBuilder setPatches(List<Patch> patches) {
		this.patches = patches;
		return this;
	}
	
	/**
	 * Build and return a BugReport with set variables.
	 * @throws NullPointerException if one of variables is null.
	 * @return A BugReport with set variables.
	 */
	public BugReport getBugReport() {
		validate();
		return new BugReport(title, description, subsystem, dependsOn, assignees, comments, issuedBy, creationDate, observers, bugTag.createState(), optionals, milestone, tests, patches);
	}

	//Assure all variables are not null.
	private void validate() {
		if (title == null) 			throw new NullPointerException("Title is null");
		if (description == null) 	throw new NullPointerException("Description is null");
		if (subsystem == null) 		throw new NullPointerException("Subsystem is null");
		if (issuedBy == null) 		throw new NullPointerException("IssuedBy is null");
		if (dependsOn == null) 		throw new NullPointerException("DependsOn is null");
		if (creationDate == null)   throw new NullPointerException("CreationDate is null");
		if (bugTag == null)			throw new NullPointerException("BugTag is null");
		if (comments == null) 		throw new NullPointerException("Comments is null");
		if (assignees == null)		throw new NullPointerException("Assignees is null");
		if (observers == null)		throw new NullPointerException("Observers is null");
		if (optionals == null)		throw new NullPointerException("optionals is null");
		if (tests == null)		throw new NullPointerException("tests is null");
		if (patches == null)		throw new NullPointerException("patches is null");
	}

}