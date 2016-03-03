package model.projects.forms;

import java.util.ArrayList;

import model.Form;
import model.projects.Project;
import model.projects.Role;
import model.users.Developer;

public class ProjectAssignForm implements Form {

	private Project project;
	private Developer developer;
	private Role role;

	ProjectAssignForm() {
		project = null;
		developer = null;
		role = null;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		if (project == null) throw new NullPointerException("Given project is null.");
		
		this.project = project;
	}

	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer) {
		if (developer == null) throw new NullPointerException("Given developer is null.");
		
		this.developer = developer;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		if (role == null) throw new NullPointerException("Given role is null.");
		
		this.role = role;
	}

	@Override
	public void allVarsFilledIn() {
		// TODO Auto-generated method stub
		
	}
}