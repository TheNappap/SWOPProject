package model.bugreports.forms;

import java.util.ArrayList;

import model.Form;
import model.bugreports.BugReport;
import model.projects.Subsystem;
import model.users.Issuer;

public class BugReportCreationForm implements Form {

	//Information needed to create a BugReport.
	private Issuer issuer;	//Issuer who issues a BugReport.
	private String title;	//A Title for the BugReport.
	private String description;	//A description for the BugReport.
	private Subsystem subsystem;	//The Subsystem the BugReport is about.
	private ArrayList<BugReport> dependsOn;	//List of BugReports the BugReport depends on.
	
	public BugReportCreationForm() {
		//Explicitly settings this to null.
		this.issuer			= null;
		this.title			= null;
		this.description 	= null;
		this.subsystem		= null;
		this.dependsOn		= null;
	}

	@Override
	public void allVarsFilledIn() {
		assert (getIssuer() != null) : "Issuer is null.";
		assert (getTitle() != null)  : "Title is null.";
		assert (getDescription() != null) 	: "Description is null.";
		assert (getSubsystem() != null)		: "Subsystem is null.";
		assert (getDependsOn() != null)		: "DependsOn is null.";
	}

	//Getters and Setters

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		assert (title != null) : "Title is null.";
		
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		assert (description != null) : "Description is null.";
		
		this.description = description;
	}

	public Subsystem getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(Subsystem subsystem) {
		assert (subsystem != null) : "Subsystem is null.";
		
		this.subsystem = subsystem;
	}

	public ArrayList<BugReport> getDependsOn() {
		return dependsOn;
	}

	public void setDependsOn(ArrayList<BugReport> dependsOn) {
		assert (dependsOn != null) : "DependsOn is null.";
		
		this.dependsOn = dependsOn;
	}

	public Issuer getIssuer() {
		return issuer;
	}

	public void setIssuer(Issuer issuer) {
		assert (issuer != null) : "Issuer is null.";
		
		this.issuer = issuer;
	}
}