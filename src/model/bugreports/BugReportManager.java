package model.bugreports;

import java.util.ArrayList;
import java.util.Collections;

import model.bugreports.builders.BugReportBuilder;
import model.bugreports.filters.BugReportFilter;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.BugReportCreationForm;

/**
 * 
 * Class that stores and manages BugReports.
 *
 */
public class BugReportManager implements BugReportDAO {

	private final ArrayList<BugReport> bugReportList; //List that keeps BugReports.

	/**
	 * Constructor.
	 */
	public BugReportManager() {
		this.bugReportList = new ArrayList<BugReport>();
	}

	@Override
	public ArrayList<BugReport> getOrderedList(FilterType[] types, String[] arguments) {
		ArrayList<BugReport> filteredList = cloneList();
		BugReportFilter filter = new BugReportFilter(filteredList);
		
		for (int index = 0; index < types.length; index++)
			filter.filter(types[index], arguments[index]);
		
		Collections.sort(filteredList);
		
		return filteredList;
	}

	@Override
	public ArrayList<BugReport> getBugReportList() {
		return bugReportList;
	}
	
	/**
	 * Creates and adds a new BugReport to the list.
	 * @param form The information for the new BugReport.
	 */
	@Override
	public void addBugReport(BugReportCreationForm form) {
		getBugReportList().add((new BugReportBuilder()).setTitle(form.getTitle())
				.setDescription(form.getDescription())
				.setSubsystem(form.getSubsystem())
				.setIssuer(form.getIssuer())
				.setDependsOn(form.getDependsOn())
				.getBugReport());
	}


	private ArrayList<BugReport> cloneList() {
		ArrayList<BugReport> clonedList = new ArrayList<BugReport>();
		
		for (BugReport bugReport : getBugReportList()) clonedList.add(bugReport);
		
		return clonedList;
	}

}