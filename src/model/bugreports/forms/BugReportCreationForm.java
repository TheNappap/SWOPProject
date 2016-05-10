package model.bugreports.forms;

import java.util.List;

import model.Form;
import model.bugreports.IBugReport;
import model.bugreports.TargetMilestone;
import model.projects.ISubsystem;
import model.users.IUser;

public class BugReportCreationForm implements Form {

	//Information needed to create a BugReport.
	private IUser issuer;	//Issuer who issues a BugReport.
	private String title;	//A Title for the BugReport.
	private String description;	//A description for the BugReport.
	private ISubsystem subsystem;	//The Subsystem the BugReport is about.
	private List<IBugReport> dependsOn;	//List of BugReports the BugReport depends on.
	private int impactFactor; //Impact factor of the bug report

	// Optional
	private String errorMessage;
	private String stackTrace;
	private String reproduction;
	private TargetMilestone targetMilestone;
	
	public BugReportCreationForm() {
		//Explicitly settings this to null.
		this.issuer			= null;
		this.title			= null;
		this.description 	= null;
		this.subsystem		= null;
		this.dependsOn		= null;
		this.errorMessage	= null;
		this.stackTrace 	= null;
		this.reproduction	= null;
		this.targetMilestone = null;
		this.impactFactor = 1;
	}

	@Override
	public void allVarsFilledIn() {
		if (getIssuer() == null) throw new NullPointerException("Issuer is null");
		if (getTitle() == null) throw new NullPointerException("Title is null");
		if (getDescription() == null) throw new NullPointerException("Description is null");
		if (getSubsystem() == null) throw new NullPointerException("Subsystem is null");
		if (getDependsOn() == null) throw new NullPointerException("DependsOn is null");
		if(!impactFactorIsValid(getImpactFactor())) throw new IllegalArgumentException("The impact factor should >0 and <=10");
	}

	//Getters and Setters

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (title == null) throw new NullPointerException("Title is null");
		
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null) throw new NullPointerException("Description is null");
		
		this.description = description;
	}

	public ISubsystem getSubsystem() {
		return subsystem;
	}

	public void setSubsystem(ISubsystem subsystem) {
		if (subsystem == null) throw new NullPointerException("Subsystem is null");
		
		this.subsystem = subsystem;
	}

	public List<IBugReport> getDependsOn() {
		return dependsOn;
	}

	public void setDependsOn(List<IBugReport> dependsOn) {
		if (dependsOn == null) throw new NullPointerException("DependsOn is null");
		
		this.dependsOn = dependsOn;
	}

	public IUser getIssuer() {
		return issuer;
	}

	public void setIssuer(IUser issuer) {
		if (issuer == null) throw new NullPointerException("Issuer is null");
		if (!issuer.isIssuer()) throw new IllegalArgumentException("Issuer should be an issuer.");

		this.issuer = issuer;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getReproduction() {
		return reproduction;
	}

	public void setReproduction(String reproduction) {
		this.reproduction = reproduction;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public TargetMilestone getTargetMilestone() {
		return targetMilestone;
	}

	public void setTargetMilestone(TargetMilestone targetMilestone) {
		this.targetMilestone = targetMilestone;
	}

	public int getImpactFactor() {
		return impactFactor;
	}

	public void setImpactFactor(int impactFactor) {
		if(!impactFactorIsValid(impactFactor)) throw new IllegalArgumentException("The impact factor should >0 and <=10");
		
		this.impactFactor = impactFactor;
	}

	private boolean impactFactorIsValid(int impactFactor) {
		if(impactFactor <= 0){
			return false;
		}
		if(impactFactor > 10){
			return false;
		}
		return true;
	}
	
}