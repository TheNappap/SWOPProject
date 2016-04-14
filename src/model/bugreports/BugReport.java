package model.bugreports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.bugreports.bugtag.BugTag;
import model.bugreports.bugtag.BugTagState;
import model.bugreports.comments.Comment;
import model.notifications.Observable;
import model.notifications.observers.Observer;
import model.notifications.signalisations.BugReportChangeSignalisation;
import model.notifications.signalisations.CommentCreationSignalisation;
import model.notifications.signalisations.Signalisation;
import model.projects.ISubsystem;
import model.projects.Subsystem;
import model.users.IUser;

/**
 * This class represents a bugreport in BugTrap.
 */
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
	private final TargetMilestone milestone; //target milestone.
	private final String stackTrace;
	private final String errorMessage;
	private final String reproduction;
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
						IUser issuedBy, Date creationDate, List<Observer> observers, BugTagState bugTag, String stackTrace, String errorMessage, String reproduction,
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
		this.errorMessage	= errorMessage;
		this.stackTrace 	= stackTrace;
		this.reproduction	= reproduction;
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

		Signalisation s = new CommentCreationSignalisation(this);
		for (Observer observer : this.observers) {
			observer.signal(s);
		}

		((Subsystem)getSubsystem()).signal(s);
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

		Signalisation s = new BugReportChangeSignalisation(this);
		for (Observer observer : this.observers) {
			observer.signal(s);
		}

		((Subsystem)getSubsystem()).signal(s);
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

	@Override
	public TargetMilestone getTargetMilestone() {
		return milestone;
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

	@Override
	public String getStackTrace() {
		return stackTrace;
	}

	@Override
	public String getReproduction() {
		return reproduction;
	}

	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * adds a test to the bug report
	 * @param test given test
	 */
	public void addTest(String test) {
		tests.add(new Test(test));
	}
	
	/**
	 * adds a patch to the bug report
	 * @param patch given patch
	 */
	public void addPatch(String patch) {
		patches.add(new Patch(patch));
	}
	
	/**
	 * accepts a given test if its for this bug report
	 * @param test given test
	 */
	public void acceptTest(Test test) {
		if(!tests.contains(test))
			throw new IllegalArgumentException("given test is not a test for this bugreport");
		
		test.accept();
	}
	
	/**
	 * rejects a given test if its for this bug report
	 * @param test given test
	 */
	public void rejectTest(Test test) {
		if(!tests.contains(test))
			throw new IllegalArgumentException("given test is not a test for this bugreport");
		
		tests.remove(test);
	}
	
	/**
	 * accepts a given patch if its for this bug report
	 * @param patch given patch
	 */
	public void acceptPatch(Test patch) {
		if(!patches.contains(patch))
			throw new IllegalArgumentException("given test is not a test for this bugreport");
		
		patch.accept();
	}
	
	/**
	 * rejects a given patch if its for this bug report
	 * @param patch given patch
	 */
	public void rejectPatch(Test patch) {
		if(!patches.contains(patch))
			throw new IllegalArgumentException("given test is not a test for this bugreport");
		
		patches.remove(patch);
	}

	/**
	 * attaches a given observer to this bug report
	 * @param observer
	 */
	@Override
	public void attach(Observer observer) {
		if (!this.observers.contains(observer))
			this.observers.add(observer);
	}

	/**
	 * detaches a given observer from tis bug report
	 * @param observer
	 */
	@Override
	public void detach(Observer observer) {
		if (observers.contains(observer))
			observers.remove(observer);
	}
}