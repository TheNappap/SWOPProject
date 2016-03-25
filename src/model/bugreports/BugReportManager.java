package model.bugreports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

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

	/**
	 * Constructor.
	 */
	public BugReportManager(BugTrap bugTrap) {
		this.bugReportList = new ArrayList<BugReport>();
	}

	public List<IBugReport> getOrderedList(FilterType[] types, String[] arguments) {
		List<IBugReport> filteredList = getBugReportList();
		
		BugReportFilter filter = new BugReportFilter(filteredList);

		for (int index = 0; index < types.length; index++)
			filter.filter(types[index], arguments[index]);
		
		Collections.sort(filteredList);
		
		return filter.getFilteredList();
	}

	public List<IBugReport> getBugReportList() {
		ArrayList<IBugReport> clonedList = new ArrayList<IBugReport>();
		clonedList.addAll(bugReportList);
		return clonedList;
	}

	public List<IBugReport> getBugReportsForProject(IProject project) {
		List<ISubsystem> projectSubs = project.getAllDirectOrIndirectSubsystems();
		List<IBugReport> projectReports = new ArrayList<IBugReport>();

		for (IBugReport r : bugReportList)
			if (projectSubs.contains(r.getSubsystem()))
				projectReports.add(r);

		return projectReports;
	}

	public IBugReport addBugReport(String title, String description, Date creationDate, ISubsystem subsystem, IUser issuer, List<IBugReport> dependencies, List<IUser> assignees, BugTag tag) {
		BugReport report = (new BugReportBuilder()).setTitle(title)
				.setDescription(description)
				.setSubsystem(subsystem)
				.setIssuer(issuer)
				.setDependsOn(dependencies)
				.setCreationDate(creationDate)
				.setAssignees(assignees)
				.setBugTag(tag)
				.getBugReport();
		bugReportList.add(report);
		return report;
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