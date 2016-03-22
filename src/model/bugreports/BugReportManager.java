package model.bugreports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.bugtag.BugTag;
import model.bugreports.builders.BugReportBuilder;
import model.bugreports.filters.BugReportFilter;
import model.bugreports.filters.FilterType;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.users.IUser;

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

	public ArrayList<IBugReport> getOrderedList(FilterType[] types, String[] arguments) throws UnauthorizedAccessException {
		if (!bugTrap.isLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in to perform this action.");

		ArrayList<IBugReport> filteredList = cloneList();
		BugReportFilter filter = new BugReportFilter(filteredList);
		
		for (int index = 0; index < types.length; index++)
			filter.filter(types[index], arguments[index]);
		
		Collections.sort(filteredList);
		
		return filteredList;
	}

	public List<IBugReport> getBugReportList() throws UnauthorizedAccessException {
		if (!bugTrap.isLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in to perform this action.");
		return cloneList();
	}

	public List<IBugReport> getBugReportsForProject(IProject project) throws UnauthorizedAccessException {
		if (!bugTrap.isLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in to perform this action.");

		List<ISubsystem> projectSubs = project.getAllDirectOrIndirectSubsystems();
		List<IBugReport> projectReports = new ArrayList<IBugReport>();

		for (IBugReport r : bugReportList)
			if (projectSubs.contains(r.getSubsystem()))
				projectReports.add(r);

		return projectReports;
	}

	public void addBugReport(String title, String description, Date creationDate, ISubsystem subsystem, IUser issuer, List<IBugReport> dependencies, List<IUser> assignees, BugTag tag) {
		bugReportList.add((new BugReportBuilder()).setTitle(title)
				.setDescription(description)
				.setSubsystem(subsystem)
				.setIssuer(issuer)
				.setDependsOn(dependencies)
				.setCreationDate(creationDate)
				.setAssignees(assignees)
				.setBugTag(tag)
				.getBugReport());
	}

	private ArrayList<IBugReport> cloneList() {
		ArrayList<IBugReport> clonedList = new ArrayList<IBugReport>();
		
		for (IBugReport bugReport : bugReportList) 
			clonedList.add(bugReport);
		
		return clonedList;
	}

	public void assignToBugReport(IBugReport bugReport, IUser dev) {
		if (!dev.isDeveloper()) throw new IllegalArgumentException("The given user should be a developer.");
		
		BugReport report = null;
		for (BugReport b : bugReportList)
			if (b == bugReport)
				report = b;
		
		report.assignDeveloper(dev);
	}
	
	public void updateBugReport(IBugReport bugReport, BugTag tag) {
		BugReport report = null;
		for (BugReport b : bugReportList)
			if (b == bugReport)
				report = b;
		
		report.updateBugTag(tag);
	}
}