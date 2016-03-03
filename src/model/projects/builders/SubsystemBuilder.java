package model.projects.builders;

import java.util.ArrayList;

import model.projects.Project;
import model.projects.Subsystem;
import model.projects.System;
import model.projects.Version;

public class SubsystemBuilder {
	
	String name;
	String description;
	System parent;
	Project project;
	Version version;
	
	private ArrayList<Subsystem> subsystems;
	
	public SubsystemBuilder() {
		subsystems = new ArrayList<Subsystem>();
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
		return new Subsystem(name, description, parent, subsystems, version, project);
	}
}
