package model.bugreports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.bugtag.BugTag;
import model.bugreports.builders.BugReportBuilder;
import model.bugreports.comments.Commentable;
import model.bugreports.filters.BugReportFilter;
import model.bugreports.filters.FilterType;
import model.notifications.signalisations.BugReportCreationSignalisation;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.ISystem;
import model.projects.Subsystem;
import model.users.IUser;

/**
 * Class that stores and manages BugReports.
 */
public class BugReportManager {

	private final List<BugReport> bugReportList; //List that keeps BugReports.
	private final BugTrap bugTrap;
	/**
	 * Constructor.
	 */
	public BugReportManager(BugTrap bugTrap) {
		this.bugReportList = new ArrayList<BugReport>();
		this.bugTrap = bugTrap;
	}
	
	/**
	 * returns an array of all the different filter types
	 * @return an array of filter types
	 */
	public FilterType[] getFilterTypes() {
		return FilterType.values();
	}

	/**
	 * returns an ordered list of bug reports
	 * @param types filter types
	 * @param arguments
	 * @return an ordered list of bug reports
	 */
	public List<IBugReport> getOrderedList(FilterType[] types, String[] arguments) {
		List<IBugReport> filteredList = getBugReportList();
		
		BugReportFilter filter = new BugReportFilter(filteredList);

		for (int index = 0; index < types.length; index++)
			filter.filter(types[index], arguments[index]);
		
		return filter.getFilteredList();
	}

	/**
	 * returns a copy of the bug report list
	 * @return list of bug reports
	 */
	public List<IBugReport> getBugReportList(){
		ArrayList<IBugReport> clonedList = new ArrayList<IBugReport>();
		clonedList.addAll(bugReportList);
		return clonedList;
	}

	/**
	 * returns all the bug reports for a given project
	 * @param project
	 * @return list of bug reports
	 */
	public List<IBugReport> getBugReportsForProject(IProject project) {
		return getBugReportsForSystem(project);
	}
	
	public List<IBugReport> getBugReportsForSystem(ISystem system) {
		List<ISubsystem> subs = system.getAllDirectOrIndirectSubsystems();
		List<IBugReport> reports = new ArrayList<IBugReport>();

		for (IBugReport r : bugReportList)
			for (ISubsystem s : subs)
				if (r.getSubsystem() == s)
					reports.add(r);

		return reports;
	}

	/**
	 * adds a bug report
	 * @param title
	 * @param description
	 * @param creationDate
	 * @param subsystem
	 * @param issuer
	 * @param dependencies
	 * @param assignees
	 * @param tag
	 */
	public void addBugReport(String title, String description, Date creationDate, ISubsystem subsystem, IUser issuer, List<IBugReport> dependencies, List<IUser> assignees, BugTag tag) {
		BugReport report = new BugReportBuilder().setTitle(title)
				.setDescription(description)
				.setSubsystem(subsystem)
				.setIssuer(issuer)
				.setDependsOn(dependencies)
				.setCreationDate(creationDate)
				.setAssignees(assignees)
				.setBugTag(tag)
				.getBugReport();
		bugReportList.add(report);
		((Subsystem)subsystem).signal(new BugReportCreationSignalisation(report));
	}
	
	public void addBugReportWithTargetMilestone(String title, String description, Date creationDate, ISubsystem subsystem, IUser issuer, List<IBugReport> dependencies, List<IUser> assignees, BugTag tag, List<Integer> milestone) {
		TargetMilestone target = new TargetMilestone(milestone);
		if(target.compareTo(subsystem.getAchievedMilestone()) <= 0) throw new IllegalArgumentException("The target milestone should be strict higher than the achieved milestone of the subsystem");
		
		BugReport report = new BugReportBuilder().setTitle(title)
				.setDescription(description)
				.setSubsystem(subsystem)
				.setIssuer(issuer)
				.setDependsOn(dependencies)
				.setCreationDate(creationDate)
				.setAssignees(assignees)
				.setBugTag(tag)
				.setMilestone(milestone)
				.getBugReport();
		bugReportList.add(report);
		((Subsystem)subsystem).signal(new BugReportCreationSignalisation(report));
	}

	/**
	 * assigns a given developer to a bugreport
	 * @param bugReport
	 * @param dev given developer
	 * @throws UnauthorizedAccessException 
	 */
	public void assignToBugReport(IBugReport bugReport, IUser dev) throws UnauthorizedAccessException{
		IProject project = bugReport.getSubsystem().getProject();
		IUser user = bugTrap.getUserManager().getLoggedInUser();
		if(!project.isLead(user) && !project.isTester(user)) throw new UnauthorizedAccessException("A lead or tester should be logged in to assign bug report");
		
		BugReport report = null;
		for (BugReport b : bugReportList)
			if (b == bugReport)
				report = b;
		
		report.assignDeveloper(dev);
	}
	
	/**
	 * update a bug report with a tag
	 * @param bugReport
	 * @param tag
	 */
	public void updateBugReport(IBugReport bugReport, BugTag tag) {
		if (bugReport == null || tag == null)
			throw new IllegalArgumentException("Arguments should not be null.");
		
		BugReport report = null;
		for (BugReport b : bugReportList)
			if (b == bugReport)
				report = b;
		
		report.updateBugTag(tag);
	}

	/**
	 * adds a comment to a commentable object
	 * @param commentable
	 * @param text
	 */
	public void addComment(Commentable commentable, String text) {
		if (commentable == null || text == null)
			throw new IllegalArgumentException("Arguments should not be null.");
		
		commentable.addComment(text);
	}
	
	/**
	 * proposes a test to a given bug report
	 * @param report given bug report
	 * @param test
	 * @throws UnauthorizedAccessException if the logged in user is not a tester for this bugreport
	 */
	public void proposeTest(IBugReport report, String test) throws UnauthorizedAccessException {
		if (report == null || test == null)
			throw new IllegalArgumentException("Arguments should not be null.");
		IUser user = bugTrap.getUserManager().getLoggedInUser();
		BugReport bugReport = (BugReport) report;
		if(!bugReport.getSubsystem().getProject().isTester(user))
			throw new UnauthorizedAccessException("The logged in user needs to be a tester to propose a test");
		
		bugReport.addTest(test);
	}
	
	/**
	 * proposes a patch to a given bug report
	 * @param report given bug report
	 * @param patch
	 * @throws UnauthorizedAccessException if the logged in user is not a programmer for this bugreport
	 */
	public void proposePatch(IBugReport report, String patch) throws UnauthorizedAccessException {
		if (report == null || patch == null)
			throw new IllegalArgumentException("Arguments should not be null.");
		IUser user = bugTrap.getUserManager().getLoggedInUser();
		BugReport bugReport = (BugReport) report;
		if(!bugReport.getSubsystem().getProject().isProgrammer(user))
			throw new UnauthorizedAccessException("The logged in user needs to be a programmer to propose a patch");
		
		bugReport.addPatch(patch);
	}
}