package model.bugreports;

import java.util.ArrayList;

import model.bugreports.filters.FilterType;
import model.bugreports.forms.BugReportCreationForm;
import model.projects.Project;

public interface BugReportDAO {

	public ArrayList<BugReport> getBugReportList();
	public ArrayList<BugReport> getOrderedList(FilterType[] type, String[] arguments);
	public void addBugReport(BugReportCreationForm form);
	
}
