package model.bugreports.forms;

import model.Form;
import model.bugreports.IBugReport;
import model.users.IUser;

public class BugReportAssignForm implements Form {

	private IBugReport bugReport;
	private IUser developer;

	public BugReportAssignForm() {
		//Explicitly setting this to null.
		this.bugReport = null;
		this.developer = null;
	}
	
	@Override
	public void allVarsFilledIn() {
		if (getBugReport() == null) throw new NullPointerException("Bugreport is null");
		if (getDeveloper() == null) throw new NullPointerException("Developer is null");
	}
	
	public IBugReport getBugReport() {
		return bugReport;
	}
	
	public void setBugReport(IBugReport bugReport) {
		if (bugReport == null) throw new NullPointerException("Bugreport is null");
		
		this.bugReport = bugReport;
	}
	
	public IUser getDeveloper() {
		return developer;
	}
	
	public void setDeveloper(IUser developer) {
		if (developer == null) throw new NullPointerException("Developer is null");
		if (!developer.isDeveloper()) throw new IllegalArgumentException("Developer should be a developer.");
		
		this.developer = developer;
	}
	
}