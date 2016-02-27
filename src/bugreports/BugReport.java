package bugreports;

import java.util.ArrayList;
import java.util.Date;

import bugreports.forms.BugReportAssignForm;
import bugreports.forms.BugReportUpdateForm;
import bugreports.forms.CommentCreationForm;
import projects.Subsystem;
import users.*;

public class BugReport {

	private int id;
	private String title;
	private String description;
	private Date creationDate;
	private Subsystem subsystem;
	private BugTag bugTag;
	private ArrayList<Developer> assignees;
	private ArrayList<BugReport> dependsOn;
	private BugReport duplicate;
	private Issuer issuedBy;
	private ArrayList<InitialComment> comments;

	BugReport(int id, String title, String description, Date creationDate, Subsystem subsystem, BugTag bugTag, ArrayList<Developer> assignees, ArrayList<BugReport> dependsOn, BugReport duplicate, Issuer issuedBy, ArrayList<InitialComment> comments) {
		setId(id);
		setTitle(title);
		setDescription(description);
		setCreationDate(creationDate);
		setSubsystem(subsystem);
		setBugTag(bugTag);
		setAssignees(assignees);
		setDependsOn(dependsOn);
		setDuplicate(duplicate);
		setIssuedBy(issuedBy);
		setComments(comments);
	}
	
	/**
	 * 
	 * @param form
	 */
	public void createComment(CommentCreationForm form) {
		// TODO - implement BugReport.createComment
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public void assignDeveloper(BugReportAssignForm form) {
		// TODO - implement BugReport.assignDeveloper
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public void update(BugReportUpdateForm form) {
		// TODO - implement BugReport.update
		throw new UnsupportedOperationException();
	}
	
	//Getters and Setters
	
	public int getId() {
		return id;
	}

	void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}
	
	void setDescription(String description) {
		this.description = description;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Subsystem getSubsystem() {
		return subsystem;
	}

	void setSubsystem(Subsystem subsystem) {
		this.subsystem = subsystem;
	}

	public BugTag getBugTag() {
		return bugTag;
	}

	void setBugTag(BugTag bugTag) {
		this.bugTag = bugTag;
	}

	public ArrayList<Developer> getAssignees() {
		return assignees;
	}

	void setAssignees(ArrayList<Developer> assignees) {
		this.assignees = assignees;
	}

	public ArrayList<BugReport> getDependsOn() {
		return dependsOn;
	}

	void setDependsOn(ArrayList<BugReport> dependsOn) {
		this.dependsOn = dependsOn;
	}

	public BugReport getDuplicate() {
		return duplicate;
	}

	void setDuplicate(BugReport duplicate) {
		this.duplicate = duplicate;
	}

	public Issuer getIssuedBy() {
		return issuedBy;
	}

	void setIssuedBy(Issuer issuedBy) {
		this.issuedBy = issuedBy;
	}

	public ArrayList<InitialComment> getComments() {
		return comments;
	}

	void setComments(ArrayList<InitialComment> comments) {
		this.comments = comments;
	}
	
	
}