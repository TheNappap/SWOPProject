package model.bugreports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.BugTrap;
import model.bugreports.bugtag.BugTag;
import model.bugreports.builders.BugReportBuilder;
import model.bugreports.comments.Commentable;
import model.bugreports.filters.BugReportFilter;
import model.bugreports.filters.FilterType;
import model.notifications.Observable;
import model.notifications.Observer;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.users.IUser;

/**
 * Class that stores and manages BugReports.
 */
public class BugReportManager implements Observable {

	private final List<BugReport> bugReportList; //List that keeps BugReports.
	private final BugTrap bugTrap;

	private final List<Observer> observers = new ArrayList<Observer>();
	/**
	 * Constructor.
	 */
	public BugReportManager(BugTrap bugTrap) {
		this.bugReportList = new ArrayList<BugReport>();
		this.bugTrap = bugTrap;
	}
	
	public FilterType[] getFilterTypes() {
		return FilterType.values();
	}

	public List<IBugReport> getOrderedList(FilterType[] types, String[] arguments) {
		List<IBugReport> filteredList = getBugReportList();
		
		BugReportFilter filter = new BugReportFilter(filteredList);

		for (int index = 0; index < types.length; index++)
			filter.filter(types[index], arguments[index]);
		
		return filter.getFilteredList();
	}

	public List<IBugReport> getBugReportList(){
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

	public void addBugReport(String title, String description, Date creationDate, ISubsystem subsystem, IUser issuer, List<IBugReport> dependencies, List<IUser> assignees, BugTag tag) {
		bugReportList.add(new BugReportBuilder().setTitle(title)
				.setDescription(description)
				.setSubsystem(subsystem)
				.setIssuer(issuer)
				.setDependsOn(dependencies)
				.setCreationDate(creationDate)
				.setAssignees(assignees)
				.setBugTag(tag)
				.getBugReport());

		for (Observer observer : this.observers) {
			if (observer.isCreateBugReportObserver())
				observer.signal("New bug report: " + title + " by " + issuer.getFirstName() + " " + issuer.getLastName());
		}
	}

	public void assignToBugReport(IBugReport bugReport, IUser dev){
		BugReport report = null;
		for (BugReport b : bugReportList)
			if (b == bugReport)
				report = b;
		
		report.assignDeveloper(dev);
	}
	
	public void updateBugReport(IBugReport bugReport, BugTag tag) {
		if (bugReport == null || tag == null)
			throw new IllegalArgumentException("Arguments should not be null.");

		BugReport report = null;
		for (BugReport b : bugReportList)
			if (b == bugReport)
				report = b;
		
		report.updateBugTag(tag);
	}

	public void addComment(Commentable commentable, String text) {
		if (commentable == null || text == null)
			throw new IllegalArgumentException("Arguments should not be null.");
		
		commentable.addComment(text);
	}

	@Override
	public void attach(Observer observer) {
		if (observer.isCreateBugReportObserver() && !this.observers.contains(observer))
			this.observers.add(observer);
	}

	@Override
	public void detach(Observer observer) {
		if (this.observers.contains(observer))
			this.observers.remove(observer);
	}
}