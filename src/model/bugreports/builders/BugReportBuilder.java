package model.bugreports.builders;

import java.util.ArrayList;

import model.bugreports.BugReport;
import model.projects.Subsystem;
import model.users.Issuer;

public class BugReportBuilder {

	private String title;
	private String description;
	private Subsystem subsystem;
	private ArrayList<BugReport> dependsOn;
	private Issuer issuedBy;

	public BugReportBuilder() {
		
	}

	public BugReportBuilder setTitle(String title) {
		this.title = title;
		return this;
	}

	public BugReportBuilder setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public BugReportBuilder setDependsOn(ArrayList<BugReport> dependsOn) {
		this.dependsOn = dependsOn;
		return this;
	}

	public BugReportBuilder setSubsystem(Subsystem subsystem) {
		this.subsystem = subsystem;
		return this;
	}

	public BugReportBuilder setIssuer(Issuer issuedBy) {
		this.issuedBy = issuedBy;
		return this;
	}

	public BugReport getBugReport() {
		validate();
		return new BugReport(title, description, subsystem, dependsOn, issuedBy);
	}

	private void validate() {
		if (title == null) 			throw new NullPointerException("Bugreport title is null");
		if (description == null) 	throw new NullPointerException("Bugreport description is null");
		if (subsystem == null) 		throw new NullPointerException("Bugreport subsystem is null");
		if (dependsOn == null) 		throw new NullPointerException("Bugreport dependsOn is null");
		if (issuedBy == null) 		throw new NullPointerException("Bugreport issuedBy is null");
	}

}