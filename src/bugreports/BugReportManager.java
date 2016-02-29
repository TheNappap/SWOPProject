package bugreports;

import java.util.ArrayList;
import java.util.Collections;

import bugreports.builders.BugReportBuilder;
import bugreports.filters.BugReportFilter;
import bugreports.filters.FilterType;
import bugreports.forms.BugReportCreationForm;

/**
 * 
 * Class that stores and manages BugReports.
 *
 */
public class BugReportManager {

	private final ArrayList<BugReport> bugReportList; //List that keeps BugReports.

	/**
	 * BugReportManager Constructor.
	 */
	public BugReportManager() {
		this.bugReportList = new ArrayList<BugReport>();
	}
	
	/**
	 * Creates and adds a new BugReport to the list.
	 * @param form The information for the new BugReport.
	 */
	public void createBugReport(BugReportCreationForm form) {
		getBugReportList().add((new BugReportBuilder()).setTitle(form.getTitle())
								.setDescription(form.getDescription())
								.setSubsystem(form.getSubsystem())
								.setIssuer(form.getIssuer())
								.getBugReport());
	}

	/**
	 * Returns a COPY of the BugReport list with given filters applied.
	 * @param types The FilterTypes
	 * @param arguments The arguments for the FilterTypes
	 * @return A filtered, ordered BugReport list.
	 */
	public ArrayList<BugReport> getOrderedList(FilterType[] types, String[] arguments) {
		ArrayList<BugReport> filteredList = cloneList();
		BugReportFilter filter = new BugReportFilter(filteredList);
		
		for (int index = 0; index < types.length; index++)
			filter.filter(types[index], arguments[index]);
		
		Collections.sort(filteredList);
		
		return filteredList;
	}

	public ArrayList<BugReport> getBugReportList() {
		return bugReportList;
	}
	
	private ArrayList<BugReport> cloneList() {
		ArrayList<BugReport> clonedList = new ArrayList<BugReport>();
		
		for (BugReport bugReport : getBugReportList()) clonedList.add(bugReport.clone());
		
		return clonedList;
	}

}