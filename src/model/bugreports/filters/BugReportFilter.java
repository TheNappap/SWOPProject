package model.bugreports.filters;

import java.util.ArrayList;
import java.util.Iterator;

import model.bugreports.BugReport;
import model.users.Developer;

public class BugReportFilter {

	private final ArrayList<BugReport> filteredList;
	
	public BugReportFilter(ArrayList<BugReport> bugReportList) {
		this.filteredList = bugReportList;
	}
	
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