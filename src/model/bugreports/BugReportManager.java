package model.bugreports;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.bugtag.BugTag;
import model.bugreports.builders.BugReportBuilder;
import model.bugreports.comments.Commentable;
import model.bugreports.filters.BugReportFilter;
import model.bugreports.filters.FilterType;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.users.IUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * Class that stores and manages BugReports.
 *
 */
public class BugReportManager{

	private final ArrayList<BugReport> bugReportList; //List that keeps BugReports.
	private final BugTrap bugTrap;

	/**
	 * Constructor.
	 */
	public BugReportManager(BugTrap bugTrap) {
		this.bugReportList = new ArrayList<BugReport>();
		this.bugTrap = bugTrap;
	}
	
	public FilterType[] getFilterTypes() throws UnauthorizedAccessException{
		if (!bugTrap.isIssuerLoggedIn())
			throw new UnauthorizedAccessException("An issuer needs to be logged in to perform this action.");
		return FilterType.values();
	}

	public List<IBugReport> getOrderedList(FilterType[] types, String[] arguments) throws UnauthorizedAccessException {
		if (!bugTrap.isIssuerLoggedIn())
			throw new UnauthorizedAccessException("An issuer needs to be logged in to perform this action.");
		
		List<IBugReport> filteredList = getBugReportList();
		
		BugReportFilter filter = new BugReportFilter(filteredList);

		for (int index = 0; index < types.length; index++)
			filter.filter(types[index], arguments[index]);
		
		return filter.getFilteredList();
	}

	public List<IBugReport> getBugReportList() throws UnauthorizedAccessException {
		if (!bugTrap.isIssuerLoggedIn())
			throw new UnauthorizedAccessException("An issuer needs to be logged in to perform this action.");
		
		ArrayList<IBugReport> clonedList = new ArrayList<IBugReport>();
		clonedList.addAll(bugReportList);
		return clonedList;
	}

	public List<IBugReport> getBugReportsForProject(IProject project) throws UnauthorizedAccessException {
		if (!bugTrap.isIssuerLoggedIn())
			throw new UnauthorizedAccessException("An issuer needs to be logged in to perform this action.");
		if (project == null) 
			throw new IllegalArgumentException("Arguments should not be null.");

		List<ISubsystem> projectSubs = project.getAllDirectOrIndirectSubsystems();
		List<IBugReport> projectReports = new ArrayList<IBugReport>();

		for (IBugReport r : bugReportList)
			if (projectSubs.contains(r.getSubsystem()))
				projectReports.add(r);

		return projectReports;
	}

	public void addBugReport(String title, String description, Date creationDate, ISubsystem subsystem, IUser issuer, List<IBugReport> dependencies, List<IUser> assignees, BugTag tag) throws UnauthorizedAccessException {
		if (!bugTrap.isIssuerLoggedIn())
			throw new UnauthorizedAccessException("An issuer needs to be logged in to perform this action.");

		bugReportList.add(new BugReportBuilder().setTitle(title)
				.setDescription(description)
				.setSubsystem(subsystem)
				.setIssuer(issuer)
				.setDependsOn(dependencies)
				.setCreationDate(creationDate)
				.setAssignees(assignees)
				.setBugTag(tag)
				.getBugReport());
	}

	public void assignToBugReport(IBugReport bugReport, IUser dev) throws UnauthorizedAccessException {
		if (!bugTrap.isDeveloperLoggedIn())
			throw new UnauthorizedAccessException("An developer needs to be logged in to perform this action.");
		if (bugReport == null || dev == null) 
			throw new IllegalArgumentException("Arguments should not be null.");
		if (!dev.isDeveloper()) throw new IllegalArgumentException("The given user should be a developer.");
		
		IProject project = bugReport.getSubsystem().getProject();
		IUser loggedInUser = bugTrap.getUserManager().getLoggedInUser();
		if(!project.isLead(loggedInUser) && !project.isTester(loggedInUser)){
			throw new UnauthorizedAccessException("The logged in developer should be lead or tester in the project");
		}

		BugReport report = null;
		for (BugReport b : bugReportList)
			if (b == bugReport)
				report = b;
		
		report.assignDeveloper(dev);
	}
	
	public void updateBugReport(IBugReport bugReport, BugTag tag) throws UnauthorizedAccessException {
		if (!bugTrap.isDeveloperLoggedIn())
			throw new UnauthorizedAccessException("An developer needs to be logged in to perform this action.");
		if (bugReport == null || tag == null) 
			throw new IllegalArgumentException("Arguments should not be null.");

		BugReport report = null;
		for (BugReport b : bugReportList)
			if (b == bugReport)
				report = b;
		
		report.updateBugTag(tag);
	}

	public void addComment(Commentable commentable, String text) throws UnauthorizedAccessException {
		if (commentable == null || text == null)
			throw new IllegalArgumentException("Arguments should not be null.");
		
		if (!bugTrap.isIssuerLoggedIn())
			throw new UnauthorizedAccessException("An issuer needs to be logged in to perform this action.");
		
		commentable.addComment(text);
	}
}