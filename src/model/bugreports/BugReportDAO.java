package model.bugreports;

import model.bugreports.filters.FilterType;
import model.bugreports.forms.BugReportCreationForm;

import java.util.ArrayList;

public interface BugReportDAO {

	/**  
	 * Simply get the list of BugReports.  
	 * @return The BugReport list.  
	 */
	public ArrayList<BugReport> getBugReportList();
	
	/**  
	 * Returns a COPY of the BugReport list with given filters applied.  
	 * @param types The FilterTypes  
	 * @param arguments The arguments for the FilterTypes  
	 * @return A filtered, ordered BugReport list.  
	 */
	public ArrayList<BugReport> getOrderedList(FilterType[] type, String[] arguments);
	
	/**  
	 * Add a BugReport to the list according to given BugReportCreationForm.   
	 * @param form The BugReportCreationForm containing the information for the new BugReport.  
	 */
	public void addBugReport(BugReportCreationForm form);
	
}
