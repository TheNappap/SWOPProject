package model.bugreports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.bugreports.bugtag.BugTag;
import model.bugreports.bugtag.BugTagState;
import model.bugreports.comments.Comment;
import model.notifications.Observable;
import model.notifications.Observer;
import model.projects.ISubsystem;
import model.projects.Subsystem;
import model.users.IUser;

public class BugReport implements IBugReport, Observable { //A Comment can be commented on.

	//Immutable
	private final Date creationDate;	//Creation Date of the BugReport.
	private final IUser issuedBy;		//The Issuer who issued this BugReport.
	private final ISubsystem subsystem;	//Subsystem to which this BugReport is attached.
	private final String title;			//Title of the BugReport.
	private final String description;	//Description of the BugReport.
	private final List<Comment> comments;		//Comments on this BugReport.
	private final List<IUser> assignees;	//List of Developers assigned to this BugReport.
	private final List<IBugReport> dependsOn;	//List of BugReports on which this BugReport depends.
	private final List<String> optionals; 	//List of optional addons.
	private final TargetMilestone milestone; //target milestone.
	private final List<Observer> observers; //list of observers.
	private final List<Test> tests; //list of tests.
	private final List<Patch> patches; //list of patches.
	
	//Mutable
	private BugTagState bugTag;			//BugTag that is attached to this BugReport.

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
	public BugReport(String title, String description, ISubsystem subsystem, List<IBugReport> dependsOn, List<IUser> assignees, List<Comment> comments, 
						IUser issuedBy, Date creationDate, List<Observer> observers, BugTagState bugTag, List<String> optionals, 
						TargetMilestone milestone, List<Test> tests, List<Patch> patches) {
		this.dependsOn 		= dependsOn;
		this.issuedBy 		= issuedBy;
		this.subsystem		= subsystem;
		this.title			= title;
		this.description 	= description;
		this.assignees 		= assignees;
		this.comments 		= comments;	
		this.creationDate 	= creationDate;
		this.observers		= observers;
		this.bugTag 		= bugTag;
		this.optionals		= optionals;
		this.milestone		= milestone;
		this.tests 			= tests;
		this.patches		= patches;
	}
	
	/**
	 * Create and add an InitialComment to this BugReport.
	 * @param commentText The text of the comment.
	 */
	public void addComment(String commentText) {
		comments.add(new Comment(commentText));

		for (Observer observer : this.observers) {
			if (observer.isCreateCommentObserver()) {
				observer.signal("New comment on bug report " + getTitle() + ": '" + commentText + "'");
			}
		}
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
		this.bugTag = this.bugTag.confirmBugTag(bugTag.createState());

		for (Observer observer : this.observers) {
			if (observer.isBugReportObserver()) {
				observer.signal("Bugreport " + getTitle() + " has received the tag " + getBugTag());
			}
		}
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
		return bugTag.getTag();
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
	
	public TargetMilestone getTargetMilestone() {
		return milestone;
	}
	
	public List<String> getOptionals() {
		List<String> returnList = new ArrayList<String>(); 
		
		returnList.addAll(optionals);
		
		return returnList;
	}

	@Override
	public List<Test> getTests() {
		List<Test> returnList = new ArrayList<Test>(); 
		
		returnList.addAll(tests);
		
		return returnList;
	}

	@Override
	public List<Patch> getPatches() {
		List<Patch> returnList = new ArrayList<Patch>(); 
		
		returnList.addAll(patches);
		
		return returnList;
	}
	
	public void addTest(String test) {
		tests.add(new Test(test));
	}
	
	public void addPatch(String patch) {
		patches.add(new Patch(patch));
	}
	
	public void acceptTest(Test test) {
		if(!tests.contains(test))
			throw new IllegalArgumentException("given test is not a test for this bugreport");
		
		test.accept();
	}
	
	public void rejectTest(Test test) {
		if(!tests.contains(test))
			throw new IllegalArgumentException("given test is not a test for this bugreport");
		
		tests.remove(test);
	}
	
	public void acceptPatch(Test patch) {
		if(!patches.contains(patch))
			throw new IllegalArgumentException("given test is not a test for this bugreport");
		
		patch.accept();
	}
	
	public void rejectPatch(Test patch) {
		if(!patches.contains(patch))
			throw new IllegalArgumentException("given test is not a test for this bugreport");
		
		patches.remove(patch);
	}

	@Override
	public void attach(Observer observer) {
		if (!this.observers.contains(observer))
			this.observers.add(observer);
	}

	@Override
	public void detach(Observer observer) {
		if (observers.contains(observer))
			observers.remove(observer);
	}

	void signalNewComment(String bugReportName) {
		((Subsystem)this.subsystem).signalNewComment(bugReportName);

		for (Observer observer : this.observers) {
			if (observer.isCreateCommentObserver()) {
				observer.signal("New comment or reply to comment created on bug report '" + bugReportName + "'");
			}
		}
	}
}