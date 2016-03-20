package model.bugreports.filters;

import java.util.ArrayList;
import java.util.Iterator;

import model.bugreports.BugReport;
import model.users.Developer;

/**  
 * Class dedicated to filtering BugReport lists according to FilterTypes.  
 */  
public class BugReportFilter {

	private final ArrayList<BugReport> filteredList; //List to filter. 
		
	/**  
	 * Constructor.  
	 * @param bugReportList The BugReport list to filter.  
	 */  
	public BugReportFilter(ArrayList<BugReport> bugReportList) {
		this.filteredList = bugReportList;
	}

	/**  
	 * Filter the list according to given FilterType and argument (of type String).  
	 * @param type FilterType to filter by.  
	 * @param string Argument to filter by.  
	 * @return The list with given filter applied.  
	 */  
	public ArrayList<BugReport> filter(FilterType type, String string) {
		switch (type) {
		case CONTAINS_STRING:
			inTitleOrDesc(string);
			break;
		case FILED_BY_USER:
			issuedByUser(string);
			break;
		case ASSIGNED_TO_USER:
			assignedByUser(string);
			break;
		default: throw new IllegalArgumentException();
		}
		
		return getFilteredList();
	}
	
	private void inTitleOrDesc(String string) {
		Iterator<BugReport> iter = getFilteredList().iterator();
		while (iter.hasNext()) {
			BugReport bugReport = iter.next();
			if (!bugReport.getTitle().contains(string) && !bugReport.getDescription().contains(string))
				iter.remove();
		}
	}
	
	private void issuedByUser(String string) {
		Iterator<BugReport> iter = getFilteredList().iterator();
		while (iter.hasNext()) {
			BugReport bugReport = iter.next();
			if (!bugReport.getIssuedBy().getUserName().equals(string))
				iter.remove();
		}
	}
	
	private void assignedByUser(String string) {
		Iterator<BugReport> iter = getFilteredList().iterator();
		while (iter.hasNext()) {
			BugReport bugReport = iter.next();
			
			boolean remove = true;
			for (Developer assignee : bugReport.getAssignees()) {
				if (assignee.getUserName().equals(string))
					remove = false;
			}
			
			if (remove) iter.remove();
		}
	}

	private ArrayList<BugReport> getFilteredList() {
		return filteredList;
	}
}