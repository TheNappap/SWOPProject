package bugreports.forms;

import java.util.ArrayList;

import bugreports.BugReport;
import bugreports.BugTag;
import projects.Project;
import projects.Subsystem;
import users.Developer;

public class BugReportCreationForm implements Form {

	private Project project;
	
	private String title;
	
	private String description;
	private Subsystem subsystem;
	private BugTag tag;
	private ArrayList<Developer> assignees;
	private ArrayList<BugReport> dependsOn;
	
	public BugReportCreationForm() {
		this.project 		= null;
		this.title			= null;
		this.description 	= null;
		this.subsystem		= null;
		this.tag			= null;
		this.assignees		= null;
		this.dependsOn		= null;
	}

	@Override
	public boolean allVarsFilledIn() {
		return getProject() != null &&
				getTitle() != null &&
				getDescription() != null &&
				getSubsystem() != null &&
				getTag() != null &&
				getAssignees() != null &&
				getDependsOn() != null;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		if (project == null) throw new NullPointerException("Given project is null.");
		
		this.project = project;
	}

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

	public BugTag getTag() {
		return tag;
	}

	public void setTag(BugTag tag) {
		if (tag == null) throw new NullPointerException("Given Tag is null.");
		
		this.tag = tag;
	}

	public ArrayList<Developer> getAssignees() {
		return assignees;
	}

	public void setAssignees(ArrayList<Developer> assignees) {
		if (assignees == null) throw new NullPointerException("Given Developer ArrayList is null.");
		
		this.assignees = assignees;
	}

	public ArrayList<BugReport> getDependsOn() {
		return dependsOn;
	}

	public void setDependsOn(ArrayList<BugReport> dependsOn) {
		if (dependsOn == null) throw new NullPointerException("Given BugReport ArrayList is null."); 
		
		this.dependsOn = dependsOn;
	}
}