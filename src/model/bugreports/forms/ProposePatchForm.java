package model.bugreports.forms;

import model.Form;
import model.bugreports.IBugReport;

/**
 * Form used to store temporary data to propose a test.
 */
public class ProposePatchForm implements Form {

	private IBugReport bugReport;
	private String patch;

	public ProposePatchForm() {
		//Explicitly setting this to null.
		this.bugReport = null;
		this.patch = null;
	}
	
	@Override
	public void allVarsFilledIn() {
		if (getBugReport() == null) throw new NullPointerException("Bugreport is null");
		if (getPatch() == null) throw new NullPointerException("Patch is null");
	}

	//Getters and Setters
	
	public IBugReport getBugReport() {
		return bugReport;
	}
	
	public void setBugReport(IBugReport bugReport) {
		if (bugReport == null) throw new NullPointerException("Bugreport is null");
		
		this.bugReport = bugReport;
	}

	public String getPatch() {
		return patch;
	}

	public void setPatch(String patch) {
		if (patch == null) throw new NullPointerException("Patch is null");
		
		this.patch = patch;
	}
	
	
	
}