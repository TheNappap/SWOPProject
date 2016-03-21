package model.bugreports;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import model.bugreports.bugtag.BugTag;
import model.bugreports.builders.BugReportBuilder;
import model.bugreports.filters.BugReportFilter;
import model.bugreports.filters.FilterType;
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
	public BugReportManager() {
		this.bugReportList = new ArrayList<BugReport>();
	}

	public ArrayList<IBugReport> getOrderedList(FilterType[] types, String[] arguments) {
		ArrayList<IBugReport> filteredList = cloneList();
		BugReportFilter filter = new BugReportFilter(filteredList);
		
		for (int index = 0; index < types.length; index++)
			filter.filter(types[index], arguments[index]);
		
		Collections.sort(filteredList);
		
		return filteredList;
	}

	public List<IBugReport> getBugReportList() {
		return cloneList();
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