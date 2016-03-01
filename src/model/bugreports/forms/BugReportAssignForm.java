package model.bugreports.forms;

import model.bugreports.BugReport;
import model.users.Developer;

public class BugReportAssignForm implements Form {

	private BugReport bugReport;
	private Developer developer;

	public BugReportAssignForm() {
		//Explicitly setting this to null.
		this.bugReport = null;
		this.developer = null;
	}
	
	@Override
	public void allVarsFilledIn() {
		assert (getBugReport() != null) : "BugReport is null.";
		assert (getDeveloper() != null) : "Developer is null.";
	}

	//Getters and Setters
	
	public BugReport getBugReport() {
		return bugReport;
	}
	
	public void setBugReport(BugReport bugReport) {
		assert (bugReport != null) : "BugReport is null.";
		
		this.bugReport = bugReport;
	}
	
	public Developer getDeveloper() {
		return developer;
	}
	
	public void setDeveloper(Developer developer) {
		assert (developer != null) : "Developer is null.";
		
		this.developer = developer;
	}
	
}