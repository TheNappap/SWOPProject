package model.bugreports.forms;

import model.Form;
import model.bugreports.IBugReport;

/**
 * Form used to store temporary data to propose a test.
 */
public class ProposeTestForm implements Form {

	private IBugReport bugReport;
	private String test;

	public ProposeTestForm() {
		//Explicitly setting this to null.
		this.bugReport = null;
		this.test = null;
	}
	
	@Override
	public void allVarsFilledIn() {
		if (getBugReport() == null) throw new NullPointerException("Bugreport is null");
		if (getTest() == null) throw new NullPointerException("Test is null");
	}

	//Getters and Setters
	
	public IBugReport getBugReport() {
		return bugReport;
	}
	
	public void setBugReport(IBugReport bugReport) {
		if (bugReport == null) throw new NullPointerException("Bugreport is null");
		
		this.bugReport = bugReport;
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		if (test == null) throw new NullPointerException("Test is null");
		
		this.test = test;
	}
	
	
	
}