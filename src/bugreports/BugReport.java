package bugreports;

import java.util.ArrayList;
import java.util.Date;

import bugreports.comments.Commentable;
import bugreports.comments.InitialComment;
import projects.Subsystem;
import users.*;

public class BugReport implements Comparable<BugReport>, Commentable {

	private String title;		//Title of the BugReport.
	private String description;	//Description of the BugReport.
	private Date creationDate;	//Creation Date of the BugReport.
	private Subsystem subsystem;	//Subsystem to which this BugReport is attached.
	private BugTag bugTag;			//BugTag that is attached to this BugReport.
	private ArrayList<Developer> assignees;	//List of Developers assigned to this BugReport.
	private ArrayList<BugReport> dependsOn;	//List of BugReports on which this BugReport depends.
	private BugReport duplicate;	//The duplicate of this BugReport, if any.
	private Issuer issuedBy;		//The Issuer who issued this BugReport.
	private ArrayList<InitialComment> comments;	//Comments on this BugReport.

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
		setTitle(title);
		setDescription(description);
		setSubsystem(subsystem);
		setDependsOn(dependsOn);
		setIssuedBy(issuedBy);
		
		//Non-variables on instantiation.
		setAssignees(new ArrayList<Developer>());		//No assignees yet on fresh BugReport.
		setComments(new ArrayList<InitialComment>());	//No comments yet on fresh BugReport.
		setCreationDate(new Date());					//This BugReport is created NOW.
		setBugTag(BugTag.NEW);							//Fresh BugReport.
		setDuplicate(null);								//No reported Duplicate yet.

	}
	
	/**
	 * Create and add an InitialComment to this BugReport.
	 * @param form The CommentCreationForm that contains all necessary details to create a Comment.
	 */
	public //TO-DO : Factory pattern?
	void addComment(String commentText) {
		getComments().add(new InitialComment(commentText, this));
	}
	
	public void assignDeveloper(Developer developer) {
		getAssignees().add(developer);
	}

	public void updateBugTag(BugTag bugTag) {
		setBugTag(bugTag);
	}
	
	//Getters and Setters
	
	@Override
	public int compareTo(BugReport otherBugReport) {
		return getTitle().compareTo(otherBugReport.getTitle());
	}

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
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Subsystem getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(Subsystem subsystem) {
		this.subsystem = subsystem;
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

	public void setAssignees(ArrayList<Developer> assignees) {
		this.assignees = assignees;
	}

	public ArrayList<BugReport> getDependsOn() {
		return dependsOn;
	}

	public void setDependsOn(ArrayList<BugReport> dependsOn) {
		this.dependsOn = dependsOn;
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

	public void setIssuedBy(Issuer issuedBy) {
		this.issuedBy = issuedBy;
	}

	public ArrayList<InitialComment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<InitialComment> comments) {
		this.comments = comments;
	}
	
}