package model.projects.forms;

import model.Form;
import model.projects.Project;
import model.projects.Role;
import model.users.Developer;

/**
 * Form used to store temporary data to assign a developer to a
 * project.
 */
public class ProjectAssignForm implements Form {

	private Project project;
	private Developer developer;
	private Role role;

	public ProjectAssignForm() {
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
		if (getProject() == null) 	throw new NullPointerException("Project is null");
		if (getDeveloper() == null) throw new NullPointerException("Developer is null");
		if (getRole() == null)		throw new NullPointerException("Role is null");
	}
}