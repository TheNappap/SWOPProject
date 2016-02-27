package bugreports.forms;

import java.util.ArrayList;

import bugreports.BugReport;
import projects.Subsystem;
import users.Issuer;

public class BugReportCreationForm implements Form {

	//Information needed to create a BugReport.
	private Issuer issuer;	//Issuer who issues a BugReport.
	private String title;	//A Title for the BugReport.
	private String description;	//A description for the BugReport.
	private Subsystem subsystem;	//The Subsystem the BugReport is about.
	private ArrayList<BugReport> dependsOn;	//List of BugReports the BugReport depends on.
	
	public BugReportCreationForm() {
		this.issuer			= null;
		this.title			= null;
		this.description 	= null;
		this.subsystem		= null;
		this.dependsOn		= null;
	}

	@Override
	public boolean allVarsFilledIn() {
		return getIssuer() != null &&
				getTitle() != null &&
				getDescription() != null &&
				getSubsystem() != null &&
				getDependsOn() != null;
	}

	//Getters and Setters

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (title == null) throw new NullPointerException("Given title is null.");
		
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null) throw new NullPointerException("Given Description is null.");
		
		this.description = description;
	}

	public Subsystem getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(Subsystem subsystem) {
		if (subsystem == null) throw new NullPointerException("Given Subsystem is null");
		
		this.subsystem = subsystem;
	}

	public ArrayList<BugReport> getDependsOn() {
		return dependsOn;
	}

	public void setDependsOn(ArrayList<BugReport> dependsOn) {
		if (dependsOn == null) throw new NullPointerException("Given BugReport ArrayList is null."); 
		
		this.dependsOn = dependsOn;
	}

	public Issuer getIssuer() {
		return issuer;
	}

	public void setIssuer(Issuer issuer) {
		if (issuer == null) throw new NullPointerException("Given Issuer is null.");
		
		this.issuer = issuer;
	}
}