package model.projects.builders;

import model.projects.Project;
import model.projects.Subsystem;
import model.projects.System;
import model.projects.Version;

public class SubsystemBuilder {
	
	private String name;
	private String description;
	private System parent;
	private Project project;
	private Version version;
	
	/**
	 * Constructor for a SubsystemBuilder. 
	 * This class is able to construct Subsystem objects.
	 */
	public SubsystemBuilder() {
		
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
	 * Method to set the version of the Subsystem object being built.
	 * @param version The version to set for the subsystem.
	 * @return this, with set version.
	 */
	public SubsystemBuilder setVersion(Version version) {
		this.version = version;
		return this;
	}
	
	/**
	 * Method to create the Subsystem object with the set properties.
	 * @return A Subsystem object with the given properties.
	 */
	public Subsystem getSubsystem() {
		validate();
		return new Subsystem(name, description, parent, version, project);
	}
	
	private void validate() {
		if (name == null) throw new NullPointerException("Name is null");
		if (description == null) throw new NullPointerException("Description is null");
		if (parent == null) throw new NullPointerException("Parent is null");
		if (project == null) throw new NullPointerException("Project is null");
		if (version == null) throw new NullPointerException("Version is null");
	}
}
