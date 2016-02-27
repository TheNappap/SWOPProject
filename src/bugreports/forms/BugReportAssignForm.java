package bugreports.forms;

import java.util.ArrayList;

import bugreports.BugReport;
import users.*;

public class BugReportAssignForm implements Form {

	private BugReport bugReport;
	private ArrayList<Developer> developers;

	public BugReportAssignForm() {
		//Explicitly setting this to null.
		this.bugReport = null;
		this.developers = null;
	}
	
	@Override
	public boolean allVarsFilledIn() {
		return getBugReport() != null &&
				getDevelopers() != null;
	}

	//Getters and Setters
	
	public BugReport getBugReport() {
		return bugReport;
	}
	
	public void setBugReport(BugReport bugReport) {
		if (bugReport == null) throw new NullPointerException("Given BugReport is null.");
		
		this.bugReport = bugReport;
	}
	
	public ArrayList<Developer> getDevelopers() {
		return developers;
	}
	
	public void setDevelopers(ArrayList<Developer> developers) {
		if (developers == null) throw new NullPointerException("Given Developer List is null.");
		
		this.developers = developers;
	}
	
}