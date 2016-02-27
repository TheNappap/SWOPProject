package bugreports.forms;

import java.util.ArrayList;

import bugreports.BugReport;
import users.*;

public class BugReportAssignForm {

	private BugReport bugReport;
	private ArrayList<Developer> developers;

	BugReportAssignForm() {
		//Explicitly setting this to null.
		this.bugReport = null;
		this.developers = null;
	}
	
	public BugReport getBugReport() {
		return bugReport;
	}
	
	/**
	 * Sets the given BugReport to this Form's BugReport.
	 * @param bugReport The BugReport for this Form.
	 * @throws NullPointerException If the given BugReport is null.
	 */
	public void setBugReport(BugReport bugReport) {
		if (bugReport == null) throw new NullPointerException("Given BugReport is null.");
		
		this.bugReport = bugReport;
	}
	
	public ArrayList<Developer> getDevelopers() {
		return developers;
	}
	
	/**
	 * Sets the given ArrayList of Developers to this Form's ArrayList of Developers.
	 * @param developers The ArrayList of Developers for this Form.
	 * @throws NullPointerException if the given ArrayList is null.
	 */
	public void setDevelopers(ArrayList<Developer> developers) {
		if (developers == null) throw new NullPointerException("Given Developer List is null.");
		
		this.developers = developers;
	}
	
}