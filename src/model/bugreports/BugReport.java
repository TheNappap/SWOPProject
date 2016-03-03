package model.bugreports;

import java.util.ArrayList;
import java.util.Date;

import model.bugreports.builders.BugReportBuilder;
import model.bugreports.comments.Commentable;
import model.bugreports.comments.InitialComment;
import model.projects.Subsystem;
import model.users.Developer;
import model.users.Issuer;

public class BugReport implements Comparable<BugReport>, Cloneable, Commentable {

	//Immutable
	private final Date creationDate;	//Creation Date of the BugReport.
	private final ArrayList<Developer> assignees;	//List of Developers assigned to this BugReport.
	private final ArrayList<BugReport> dependsOn;	//List of BugReports on which this BugReport depends.
	private final ArrayList<InitialComment> comments;	//Comments on this BugReport.
	private final Issuer issuedBy;		//The Issuer who issued this BugReport.
	private final Subsystem subsystem;	//Subsystem to which this BugReport is attached.
	
	//Mutable
	private String title;			//Title of the BugReport.
	private String description;		//Description of the BugReport.
	private BugTag bugTag;			//BugTag that is attached to this BugReport.
	private BugReport duplicate;	//The duplicate of this BugReport, if any.

	/**
	 * BugReport Constructor. 
	 * @param title	The title of the BugReport.
	 * @param description Description of the BugReport.
	 * @param subsystem Subsystem this BugReport is attached to.
	 * @param dependsOn	List of BugReports on which this BugReport depends.
	 * @param issuedBy Issued who issued this BugReport.
	 */
	public BugReport(String title, String description, Subsystem subsystem, ArrayList<BugReport> dependsOn, Issuer issuedBy) {
		//Variables on instantiation.
		this.dependsOn 	= dependsOn;
		this.issuedBy 	= issuedBy;
		this.subsystem	= subsystem;
		setTitle(title);
		setDescription(description);
		
		//Non-variables on instantiation.
		this.assignees 		= new ArrayList<Developer>();		//No assignees yet on fresh BugReport.
		this.comments 		= new ArrayList<InitialComment>();	//No comments yet on fresh BugReport.
		this.creationDate 	= new Date();				//This BugReport is created NOW.
		setBugTag(BugTag.NEW);							//Fresh BugReport.
		setDuplicate(null);								//No reported Duplicate yet.
	}
	
	/**
	 * Create and add an InitialComment to this BugReport.
	 * @param form The CommentCreationForm that contains all necessary details to create a Comment.
	 */
	//TO-DO : Factory pattern?
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
	
	@Override
	public BugReport clone() { //All variables are REFERENCES.
		return (new BugReportBuilder())
					.setTitle(getTitle())
					.setSubsystem(getSubsystem())
					.setIssuer(getIssuedBy())
					.setDescription(getDescription())
					.setDependsOn(getDependsOn())
					.getBugReport();
	}
	
	//Getters and Setters
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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

	public ArrayList<Developer> getAssignees() {
		return assignees;
	}

	public ArrayList<BugReport> getDependsOn() {
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

	public ArrayList<InitialComment> getComments() {
		return comments;
	}
	
}