package model.bugreports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.bugreports.bugtag.BugTag;
import model.bugreports.comments.Comment;
import model.projects.ISubsystem;
import model.users.IUser;

public class BugReport implements IBugReport { //A Comment can be commented on.

	//Immutable
	private final Date creationDate;	//Creation Date of the BugReport.
	private final IUser issuedBy;		//The Issuer who issued this BugReport.
	private final ISubsystem subsystem;	//Subsystem to which this BugReport is attached.
	private final String title;			//Title of the BugReport.
	private final String description;	//Description of the BugReport.
	private final List<Comment> comments;		//Comments on this BugReport.
	private final List<IUser> assignees;	//List of Developers assigned to this BugReport.
	private final List<IBugReport> dependsOn;	//List of BugReports on which this BugReport depends.
	
	//Mutable
	private BugTag bugTag;			//BugTag that is attached to this BugReport.

	/**
	 * BugReport Constructor. 
	 * Preferably not to be used for direct creation of BugReport. Use BugReportBuilder!
	 * @param title	The title of the BugReport.
	 * @param description Description of the BugReport.
	 * @param subsystem Subsystem this BugReport is attached to.
	 * @param assignees The Developers assigned to this BugReport.
	 * @param comments The Comments on this BugReport.
	 * @param dependsOn	List of BugReports on which this BugReport depends.
	 * @param issuedBy Issuer who issued this BugReport.
	 * @param creationDate The date the BugReport was created.
	 * @param bugTag The BugTag to assign to the BugReport
	 */
	public BugReport(String title, String description, ISubsystem subsystem, List<IBugReport> dependsOn, List<IUser> assignees, List<Comment> comments, IUser issuedBy, Date creationDate, BugTag bugTag) {
		this.dependsOn 		= dependsOn;
		this.issuedBy 		= issuedBy;
		this.subsystem		= subsystem;
		this.title			= title;
		this.description 	= description;
		this.assignees 		= assignees;		
		this.comments 		= comments;	
		this.creationDate 	= creationDate;						
		this.bugTag			= bugTag;														
	}
	
	/**
	 * Create and add an InitialComment to this BugReport.
	 * @param commentText The text of the comment.
	 */
	public void addComment(String commentText) {
		comments.add(new Comment(commentText));
	}
	
	/**
	 * Adds the given Developer to the assignees list.
	 * @param developer The developer to add.
	 * @pre The developer is a developer.
	 * 		| developer.isDeveloper() == true;
	 * @post The Developer is part of the assignees list.
	 * 		| getAssignees().contains(developer);
	 */
	public void assignDeveloper(IUser developer) {
		if (!developer.isDeveloper()) throw new IllegalArgumentException();
		
		assignees.add(developer);
	}

	/**
	 * Changes the current BugTag to the given BugTag.
	 * @param bugTag The new BugTag
	 * @post The given BugTag will be the new BugTag.
	 * 		| getBugTag() == bugTag
	 */
	public void updateBugTag(BugTag bugTag) {
		this.bugTag = getBugTag().confirmBugTag(bugTag);
	}
	
	@Override
	public int compareTo(IBugReport otherBugReport) {
		return getTitle().compareTo(otherBugReport.getTitle());
	}
	
	//Getters and Setters

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public Date getCreationDate() {
		return creationDate;
	}

	@Override
	public ISubsystem getSubsystem() {
		return subsystem;
	}

	@Override
	public BugTag getBugTag() {
		return bugTag;
	}

	@Override
	public List<IUser> getAssignees() {
		List<IUser> returnList = new ArrayList<>();
		
		returnList.addAll(assignees);
		
		return returnList;
	}

	@Override
	public List<IBugReport> getDependsOn() {
		List<IBugReport> returnList = new ArrayList<>();
		
		returnList.addAll(dependsOn);
		
		return returnList;
	}

	@Override
	public IUser getIssuedBy() {
		return issuedBy;
	}

	@Override
	public List<Comment> getComments() {
		List<Comment> returnList = new ArrayList<>(); 
		
		returnList.addAll(comments);
		
		return returnList;
	}
	
}