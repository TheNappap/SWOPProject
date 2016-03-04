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
	
	public SubsystemBuilder() {
		
	}

	public SubsystemBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public SubsystemBuilder setDescription(String description) {
		this.description = description;
		return this;
	}

	public SubsystemBuilder setParent(System parent) {
		this.parent = parent;
		return this;
	}

	public SubsystemBuilder setProject(Project project) {
		this.project = project;
		return this;
	}

	public SubsystemBuilder setVersion(Version version) {
		this.version = version;
		return this;
	}
	
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
