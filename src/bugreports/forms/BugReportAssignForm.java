package bugreports.forms;

import bugreports.BugReport;
import users.Developer;

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
		if (bugReport == null) throw new NullPointerException("Given BugReport is null.");
		
		this.bugReport = bugReport;
	}
	
	public Developer getDeveloper() {
		return developer;
	}
	
	public void setDeveloper(Developer developer) {
		if (developer == null) throw new NullPointerException("Given Developer is null.");
		
		this.developer = developer;
	}
	
}