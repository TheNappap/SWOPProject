package model.bugreports;

import java.util.Date;
import java.util.List;

import model.bugreports.comments.Commentable;
import model.bugreports.comments.InitialComment;
import model.bugreports.bugtag.BugTag;
import model.projects.Subsystem;
import model.users.Developer;
import model.users.Issuer;

public class BugReport implements Comparable<BugReport>, Commentable { //A Comment can be commented on.

	//Immutable
	private final Date creationDate;	//Creation Date of the BugReport.
	private final Issuer issuedBy;		//The Issuer who issued this BugReport.
	private final Subsystem subsystem;	//Subsystem to which this BugReport is attached.
	private final String title;			//Title of the BugReport.
	private final String description;	//Description of the BugReport.
	private final List<Developer> assignees;	//List of Developers assigned to this BugReport.
	private final List<BugReport> dependsOn;	//List of BugReports on which this BugReport depends.
	private final List<InitialComment> comments;	//Comments on this BugReport.
	
	//Mutable
	private BugTag bugTag;			//BugTag that is attached to this BugReport.
	private BugReport duplicate;	//The duplicate of this BugReport, if any.

	/**
	 * BugReport Constructor. 
	 * Preferably not to be used for direct creation of BugReport. Use BugReportBuilder!
	 * @param title	The title of the BugReport.
	 * @param description Description of the BugReport.
	 * @param subsystem Subsystem this BugReport is attached to.
	 * @param assignees The Developers assigned to this BugReport.
	 * @param comments The InitialComments on this BugReport.
	 * @param dependsOn	List of BugReports on which this BugReport depends.
	 * @param issuedBy Issuer who issued this BugReport.
	 * @param creationDate The date the BugReport was created.
	 * @param bugTag The BugTag to assign to the BugReport
	 * @param duplicate The duplicate BugReport of this BugReport, if any.
	 */
	public BugReport(String title, String description, Subsystem subsystem, List<BugReport> dependsOn, List<Developer> assignees, List<InitialComment> comments, Issuer issuedBy, Date creationDate, BugTag bugTag, BugReport duplicate) {
		this.dependsOn 		= dependsOn;
		this.issuedBy 		= issuedBy;
		this.subsystem		= subsystem;
		this.title			= title;
		this.description 	= description;
		this.assignees 		= assignees;		
		this.comments 		= comments;	
		this.creationDate 	= creationDate;						
		this.bugTag			= bugTag;						
		this.duplicate		= duplicate;								
	}
	
	/**
	 * Create and add an InitialComment to this BugReport.
	 * @param form The CommentCreationForm that contains all necessary details to create a Comment.
	 */
	public void addComment(String commentText) {
		getComments().add(new InitialComment(commentText, this));
	}
	
	/**
	 * Adds the given Developer to the assignees list.
	 * @param developer The Developer to add.
	 * @post The Developer is part of the assignees list.
	 * 		| getAssignees().contains(developer);
	 */
	public void assignDeveloper(Developer developer) {
		getAssignees().add(developer);
	}

	/**
	 * Changes the current BugTag to the given BugTag.
	 * @param bugTag The new BugTag
	 * @post The given BugTag will be the new BugTag.
	 * 		| getBugTag() == bugTag
	 */
	public void updateBugTag(BugTag bugTag) {
		setBugTag(bugTag);
	}
	
	@Override
	public int compareTo(BugReport otherBugReport) {
		return getTitle().compareTo(otherBugReport.getTitle());
	}
	
	//Getters and Setters
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public Subsystem getSubsystem() {
		return subsystem;
	}

	public BugTag getBugTag() {
		return bugTag;
	}

	public void setBugTag(BugTag bugTag) {
		this.bugTag = bugTag;
	}

	public List<Developer> getAssignees() {
		return assignees;
	}

	public List<BugReport> getDependsOn() {
		return dependsOn;
	}

	public BugReport getDuplicate() {
		return duplicate;
	}

	public void setDuplicate(BugReport duplicate) {
		this.duplicate = duplicate;
	}

	public Issuer getIssuedBy() {
		return issuedBy;
	}

	public List<InitialComment> getComments() {
		return comments;
	}
	
}