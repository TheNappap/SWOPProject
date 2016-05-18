package model.bugreports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.bugtag.BugTag;
import model.bugreports.bugtag.BugTagState;
import model.bugreports.comments.Comment;
import model.bugreports.comments.Commentable;
import model.notifications.NotificationType;
import model.notifications.observers.Observer;
import model.notifications.signalisations.Signalisation;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Subsystem;
import model.users.IUser;

/**
 * This class represents a BugReport. in BugTrap.
 */
public class BugReport implements IBugReport, Commentable { 

	//Immutable
	private final BugTrap bugTrap;

	private final Date creationDate;	//Creation Date of the BugReport.
	private IUser issuedBy;		//The Issuer who issued this BugReport.
	private ISubsystem subsystem;	//Subsystem to which this BugReport is attached.
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
	
	private final TestSection testSection;
	private final PatchSection patchSection; //list of patches.
	
	//Mutable
	private BugTagState bugTag;			//BugTag that is attached to this BugReport.
	
	private double impactFactor; 		//Impact Factor of the bug report
	
	/**
	 * BugReport Constructor.
	 * @param title Title of the BugReport
	 * @param description Description of the BugReport
	 * @param subsystem Subsystem of the BugReport
	 * @param dependsOn Dependencies of the BugReport
	 * @param assignees Assignees of the BugReport
	 * @param comments Comments on the BugReport
	 * @param issuedBy Issuer of the BugReport
	 * @param creationDate Creation Date of the BugReport
	 * @param observers Observers of the BugReport
	 * @param bugTag Tag of the BugReport
	 * @param stackTrace StackTrace of the Bug
	 * @param errorMessage Error Message of the Bug
	 * @param reproduction How to reproduce the Bug 
	 * @param milestone Target Milestone of the BugReport
	 * @param tests Tests for the Bug
	 * @param patches Patches for the Bug
	 */
	public BugReport(BugTrap bugTrap, String title, String description, ISubsystem subsystem, List<IBugReport> dependsOn, List<IUser> assignees, List<Comment> comments,
						IUser issuedBy, Date creationDate, List<Observer> observers, BugTag bugTag, String stackTrace, String errorMessage, String reproduction,
						TargetMilestone milestone, int impactFactor, TestSection testSection, PatchSection patchSection) {
		this.bugTrap 		= bugTrap;
		this.dependsOn 		= dependsOn;
		this.issuedBy 		= issuedBy;
		this.subsystem		= subsystem;
		this.title			= title;
		this.description 	= description;
		this.assignees 		= assignees;
		this.comments 		= comments;	
		this.creationDate 	= creationDate;
		this.observers		= observers;
		this.bugTag 		= bugTag.createState(this);
		this.errorMessage	= errorMessage;
		this.stackTrace 	= stackTrace;
		this.reproduction	= reproduction;
		this.milestone		= milestone;
		this.testSection 	= testSection;
		this.patchSection	= patchSection;
		
		setImpactFactor(impactFactor);

		((Subsystem)subsystem).addBugReport(this);
	}
	
	/**********************************************
	 * GETTERS
	 **********************************************/
	
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

	/**
	 * Class of the BugTag.
	 * @return Class of BugTag
	 */
	public BugTagState getBugTagState() {
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

	@Override
	public TargetMilestone getTargetMilestone() {
		return milestone;
	}

	@Override
	public List<ITest> getTests() {
		return testSection.getTestsAsList();
	}

	@Override
	public List<IPatch> getPatches() {
		return patchSection.getPatchesAsList();
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

	private void setImpactFactor(int impactFactor) {
		if (impactFactor <= 0 && impactFactor > 10) throw new IllegalArgumentException("Impact factor out of bounds (1-10)");

		this.impactFactor = impactFactor;
	}
	
	/**
	 * Returns the impact factor of the bug report
	 * @return the impact factor of the bug report
	 */
	public double getImpactFactor() {
		return impactFactor;
	}

	/**
	 * Returns the impact product of the bug report.
	 * The impact product is the product of the impact factor and the multiplier.
	 * @return the impact product of the bug report.
	 */
	public double getImpactProduct() {
		return bugTag.getMultiplier()*getImpactFactor();
	}

	@Override
	public IProject getProject() {
		return subsystem.getProject();
	}

	
	
	/**********************************************
	 * TESTS
	 **********************************************/

	/**
	 * adds a test to the bug report
	 * @param test given test
	 * @throws UnauthorizedAccessException 
	 */
	public  void addTest(String test) throws UnauthorizedAccessException {
		if (test == null)
			throw new IllegalArgumentException("Test should not be null.");
		
		IUser user = bugTrap.getLoggedInUser();
		if(!this.getSubsystem().getProject().isTester(user))
			throw new UnauthorizedAccessException("The logged in user needs to be a tester to propose a test");
		
		if (!bugTag.canAddTests())
			throw new IllegalArgumentException("Can only add Tests when ASSIGNED");
		
		testSection.addTest(test);
	}

	/**
	 * accepts a given test if its for this bug report
	 * @param test given test
	 * @throws UnauthorizedAccessException 
	 */
	public void acceptTest(String test) throws UnauthorizedAccessException {
		if (test == null)
			throw new IllegalArgumentException("Test should not be null.");
			
		IUser user = bugTrap.getUserManager().getLoggedInUser();
		if (!this.getSubsystem().getProject().isLead(user))
			throw new UnauthorizedAccessException("Only lead Developer can accept Tests.");
		
		testSection.acceptTest(test);
	}

	/**
	 * rejects a given test if its for this bug report
	 * @param test given test
	 * @throws UnauthorizedAccessException 
	 */
	public void rejectTest(String test) throws UnauthorizedAccessException {
		if (test == null)
			throw new IllegalArgumentException("Test should not be null");
		
		IUser user = bugTrap.getUserManager().getLoggedInUser();
		if (!this.getSubsystem().getProject().isLead(user))
			throw new UnauthorizedAccessException("Only lead Developer can accept Tests.");
		
		testSection.removeTest(test);
	}
	
	/**********************************************
	 * PATCHES
	 **********************************************/

	/**
	 * Proposes a patch to the BugReport.
	 * @param patch The Patch to propose.
	 * @throws UnauthorizedAccessException if the logged in user is not a programmer for this BugReport.
	 */
	public void proposePatch(String patch) throws UnauthorizedAccessException {
		if (patch == null)
			throw new IllegalArgumentException("Patch should not be null.");

		if (testSection.noTestsSubmitted())
			throw new IllegalArgumentException("Can't propose patches when there are no tests.");
		
		IUser user = bugTrap.getUserManager().getLoggedInUser();
		if(!this.getSubsystem().getProject().isProgrammer(user))
			throw new UnauthorizedAccessException("The logged in user needs to be a programmer to propose a patch");
		
		if (!bugTag.canAddPatches())
			throw new IllegalArgumentException("Can only propose patches when ASSIGNED or UNDERREVIEW");
		
		
		patchSection.addPatch(patch);
		
		updateBugTag(BugTag.UNDERREVIEW);
	}
	
	public void revertTestsAndPatches() throws UnauthorizedAccessException {
		IUser user = bugTrap.getUserManager().getLoggedInUser();
		if(!this.getSubsystem().getProject().isLead(user))
			throw new UnauthorizedAccessException("Only Lead can revert tests and patches");
		
		if (!bugTag.canRevert())
			throw new IllegalArgumentException("Must be ASSIGNED or UNDERREVIEW to revert.");
		
		testSection.clear();
		patchSection.clear();
		
		updateBugTag(BugTag.ASSIGNED);
	}
	
	@Override
	public IPatch getAcceptedPatch() {
		return patchSection.getAcceptedPatch();
	}
	
	/**
	 * accepts a given patch if its for this bug report
	 * @param patch given patch
	 * @throws UnauthorizedAccessException 
	 */
	public void acceptPatch(String patch) throws UnauthorizedAccessException {
		if (patch == null)
			throw new IllegalArgumentException("Patch should not be null");
		
		IUser user = bugTrap.getUserManager().getLoggedInUser();
		if(!this.getSubsystem().getProject().isLead(user))
			throw new UnauthorizedAccessException("Only Lead can accept Patches.");
		
		patchSection.acceptPatch(patch);
		updateBugTag(BugTag.RESOLVED);
	}
	
	/**
	 * rejects a given patch if its for this bug report
	 * @param patch given patch
	 * @throws UnauthorizedAccessException 
	 */
	public void rejectPatch(String patch) throws UnauthorizedAccessException {
		if (patch == null)
			throw new IllegalArgumentException("Patch should not be null");
		
		IUser user = bugTrap.getLoggedInUser();
		if(!this.getSubsystem().getProject().isLead(user))
			throw new UnauthorizedAccessException("Only Lead can reject Patches.");
		
		patchSection.removePatch(patch);	
	}
	
	public void close(int satisfaction) throws UnauthorizedAccessException {
		IUser user = bugTrap.getLoggedInUser();
		if(user != issuedBy)
			throw new UnauthorizedAccessException("Only the Issuer who created this BugReport can close it.");
		
		patchSection.updateSatisfaction(satisfaction);
		updateBugTag(BugTag.CLOSED);
	}
	
	/**********************************************
	 * OBSERVERS
	 **********************************************/

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

	@Override
	public void notifyObservers(Signalisation s) {
		for (Observer observer : this.observers)
			observer.signal(s);
		
		 ((Subsystem)getSubsystem()).signal(s);
	}
	
	/**********************************************
	 * OTHER
	 **********************************************/
	
	/**
	 * Sets the subsystem of this bug report.
	 * The subsystem of the bug report must contain this bug report.
	 * @param subsystem
	 */
	public void setSubsystem(Subsystem subsystem) {
		subsystem.getBugReports().contains(this);
		
		this.subsystem = subsystem;
	}

	@Override
	public void addComment(String commentText) {
		if (commentText == null)
			throw new IllegalArgumentException("Comment should not be null.");
		
		comments.add(new Comment(this, commentText));
	
		notifyObservers(new Signalisation(NotificationType.CREATE_COMMENT, this));
	}

	

	/**
	 * Adds the given Developer to the assignees list.
	 * @param developer The developer to add.
	 * @pre The developer is a developer.
	 * 		| developer.isDeveloper() == true;
	 * @post The Developer is part of the assignees list.
	 * 		| getAssignees().contains(developer);
	 */
	public void assignDeveloper(IUser developer) throws UnauthorizedAccessException {
		IProject project = subsystem.getProject();
		
		IUser user = bugTrap.getLoggedInUser();
		if (!project.isLead(user) && !project.isTester(user))
			throw new UnauthorizedAccessException("A lead or tester should be logged in to assign bug report");
	
		if (!developer.isDeveloper()) throw new IllegalArgumentException();
		
		assignees.add(developer);
		updateBugTag(BugTag.ASSIGNED);
	}

	/**
	 * Changes the current BugTag to the given BugTag.
	 * @param bugTag The new BugTag
	 * @post The given BugTag will be the new BugTag.
	 * 		| getBugTag() == bugTag
	 */
	public void updateBugTag(BugTag bugTag) throws UnauthorizedAccessException {
		if (bugTag.hasToBeLeadToSet() && !(getProject().getLeadDeveloper() == bugTrap.getUserManager().getLoggedInUser()))
			throw new UnauthorizedAccessException();
	
		this.bugTag = this.bugTag.confirmBugTag(bugTag.createState(this));
		
		notifyObservers(new Signalisation(NotificationType.BUGREPORT_CHANGE, this));
	}

	/**
	 * Terminates this bug report
	 */
	public void terminate() {
		((Subsystem)subsystem).removeBugReport(this);
		bugTrap.getNotificationManager().removeObservable(this);
	
		issuedBy = null;
		subsystem = null;
		assignees.clear();
		dependsOn.clear();
		observers.clear();
		testSection.clear();
		patchSection.clear();
		for (Comment comment : comments) {
			comment.terminate();
		}
		comments.clear();
	}

	@Override
	public int compareTo(IBugReport otherBugReport) {
		int c = getTitle().compareTo(otherBugReport.getTitle());
		if (c < 0)
			return -1;
		else if (c > 0)
			return 1;
		else
			return 0;
	}
}