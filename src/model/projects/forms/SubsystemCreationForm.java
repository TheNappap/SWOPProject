package model.projects.forms;

import model.Form;
import model.projects.System;
import model.projects.Project;

public class SubsystemCreationForm implements Form {

	private String name;
	private String description;
	private System parent;
	private Project project;
		
	public SubsystemCreationForm() {
		
	}
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) throw new NullPointerException("Given name is null.");
		
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null) throw new NullPointerException("Given description is null.");
		
		this.description = description;
	}

	public System getParent() {
		return parent;
	}

	public void setParent(System parent) {
		if (parent == null) throw new NullPointerException("Given parent is null.");
		
		// The project is the root
		System p = parent;
		while (p.getParent() != null)
			p = p.getParent();
		
		this.project = (Project)p;
		
		this.parent = parent;
	}

	public Project getProject() {
		return project;
	}

	@Override
	public void allVarsFilledIn() {
		if (getName() == null) throw new NullPointerException("Name is null");
		if (getDescription() == null) throw new NullPointerException("Description is null");
		if (getParent() == null) throw new NullPointerException("Parent is null");
		if (getProject() == null) throw new NullPointerException("Project is null");
	}

}
