package bugreports.builders;

import java.util.ArrayList;

import bugreports.BugReport;
import projects.*;
import users.*;

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
		assert (title != null) 			: "Bugreport title is null";
		assert (description != null) 	: "Bugreport description is null";
		assert (subsystem != null) 		: "Bugreport subsystem is null";
		assert (dependsOn != null) 		: "Bugreport dependencies is null";
		assert (issuedBy != null) 		: "Bugreport issuer is null";
	}

}