package model.projects.builders;

import java.util.ArrayList;
import java.util.List;

import model.BugTrap;
import model.projects.AchievedMilestone;
import model.projects.Project;
import model.projects.Subsystem;
import model.projects.System;

public class SubsystemBuilder {

	// Immutable
	private final BugTrap bugTrap;

	//System variables.
	//-Required
	private String name;
	private String description;
	private System parent;
	//-Optional
	private List<Subsystem> subsystems = new ArrayList<Subsystem>();
	private AchievedMilestone milestone = new AchievedMilestone();
	
	//Subsystem variables.
	//-Required.
	private Project project;
	
	/**
	 * Constructor for a SubsystemBuilder. 
	 * This class is able to construct Subsystem objects.
	 */
	public SubsystemBuilder(BugTrap bugTrap) {
		this.bugTrap = bugTrap;
	}

	/**
	 * Method to set the name of the Subsystem object being built.
	 * @param name The name to set for the subsystem.
	 * @return this, with set name.
	 */
	public SubsystemBuilder setName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Method to set the description of the Subsystem object being built.
	 * @param description The description to set for the subsystem.
	 * @return this, with set description.
	 */
	public SubsystemBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	/**
	 * Method to set the parent system of the Subsystem object being built.
	 * @param parent The parent system to set for the subsystem.
	 * @return this, with set parent system.
	 */
	public SubsystemBuilder setParent(System parent) {
		this.parent = parent;
		return this;
	}
	
	public SubsystemBuilder setSubsystems(List<Subsystem> subsystems) {
		this.subsystems = subsystems;
		return this;
	}
	
	public SubsystemBuilder setMilestone(AchievedMilestone milestone) {
		this.milestone = milestone;
		return this;
	}

	/**
	 * Method to set the project of the Subsystem object being built.
	 * @param project The project to set for the subsystem.
	 * @return this, with set project.
	 */
	public SubsystemBuilder setProject(Project project) {
		this.project = project;
		return this;
	}
	
	/**
	 * Method to create the Subsystem object with the set properties.
	 * @return A Subsystem object with the given properties.
	 */
	public Subsystem getSubsystem() {
		validate();
		return new Subsystem(bugTrap, name, description, parent, subsystems, project, milestone);
	}
	
	private void validate() {
		if (name == null) 			throw new NullPointerException("Name is null");
		if (description == null) 	throw new NullPointerException("Description is null");
		if (parent == null) 		throw new NullPointerException("Parent is null");
		if (subsystems == null) 	throw new NullPointerException("Subsystems is null");
		if (milestone == null)		throw new NullPointerException("Milestone is null");
		if (project == null) 		throw new NullPointerException("Project is null");
	}
}
