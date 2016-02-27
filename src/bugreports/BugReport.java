package bugreports;

import java.util.ArrayList;
import java.util.Date;

import bugreports.forms.BugReportAssignForm;
import bugreports.forms.BugReportUpdateForm;
import bugreports.forms.CommentCreationForm;
import projects.Subsystem;
import users.*;

public class BugReport {

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
	BugReport(String title, String description, Subsystem subsystem, ArrayList<BugReport> dependsOn, Issuer issuedBy) {
		//Variables on instantiation.
		setTitle(title);
		setDescription(description);
		setSubsystem(subsystem);
		setDependsOn(dependsOn);
		setIssuedBy(issuedBy);
		
		//Non-variables on instantiation.
		setAssignees(new ArrayList<Developer>()); //No assignees yet on fresh BugReport.
		setBugTag(BugTag.NEW);	//Fresh BugReport.
		setDuplicate(null);		//No reported Duplicate yet.
		setCreationDate(new Date());	//This BugReport is created NOW.
		setComments(new ArrayList<InitialComment>());	//No comments yet on fresh BugReport.
	}
	
	/**
	 * Create and add an InitialComment to this BugReport.
	 * @param form The CommentCreationForm that contains all necessary details to create a Comment.
	 */
	public void createComment(CommentCreationForm form) {
		switch (form.getInitialOrReply()) {
		case "initial":
			getComments().add(new InitialComment(form.getText(), this));
			break;
		case "reply":
			if (form.getComment() == null) throw new NullPointerException("comment is null.");
			form.getComment().createComment(form);
			break;
		default:
			throw new IllegalArgumentException("initialOrReply isn't \"initial\" or \"reply\". It is: " + form.getInitialOrReply());
		}
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