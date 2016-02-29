package bugreports;

import java.util.ArrayList;
import java.util.Collections;

import bugreports.builders.BugReportBuilder;
import bugreports.filters.BugReportFilter;
import bugreports.filters.FilterType;
import bugreports.forms.BugReportCreationForm;

public class BugReportManager {

	private static ArrayList<BugReport> bugReportList;

	public static void createBugReport(BugReportCreationForm form) {
		getBugReportList().add((new BugReportBuilder()).setTitle(form.getTitle())
								.setDescription(form.getDescription())
								.setSubsystem(form.getSubsystem())
								.setIssuer(form.getIssuer())
								.getBugReport());
	}

	public static ArrayList<BugReport> getBugReportList() {
		return BugReportManager.bugReportList;
	}

	public static ArrayList<BugReport> getOrderedList(FilterType[] types, String[] arguments) {
		ArrayList<BugReport> filteredList = getBugReportList();
		BugReportFilter filter = new BugReportFilter(filteredList);
		
		for (int index = 0; index < types.length; index++)
			filter.filter(types[index], arguments[index]);
		
		Collections.sort(filteredList);
		
		return filteredList;
	}

}