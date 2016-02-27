package bugreports.forms;

import bugreports.BugReport;
import bugreports.BugTag;

public class BugReportUpdateForm {

	private BugReport bugReport;
	private BugTag bugTag;

	
	BugReportUpdateForm() {
		this.bugReport 	= null;
		this.bugTag		= null;
	}
	
	//Getters and Setters
	
	public void setBugReport(BugReport bugReport) {
		if (bugReport == null) throw new NullPointerException("Given BugReport is null.");
		
		this.bugReport = bugReport;
	}
	
	public BugReport getBugReport() {
		return bugReport;
	}
	
	public void setBugTag(BugTag bugTag) {
		if (bugTag == null) throw new NullPointerException("Given BugTag is null");
		
		this.bugTag = bugTag;
	}
	
	public BugTag getBugTag() {
		return bugTag;
	}
}