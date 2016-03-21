package model.projects.forms;

import model.Form;
import model.projects.IProject;
import model.projects.Project;
import model.projects.Role;
import model.users.Developer;
import model.users.IUser;

/**
 * Form used to store temporary data to assign a developer to a
 * project.
 */
public class ProjectAssignForm implements Form {

	private IProject project;
	private IUser developer;
	private Role role;

	public ProjectAssignForm() {
		project = null;
		developer = null;
		role = null;
	}

	public IProject getProject() {
		return project;
	}

	public void setProject(IProject project) {
		if (project == null) throw new NullPointerException("Given project is null.");
		
		this.project = project;
	}

	public IUser getDeveloper() {
		return developer;
	}

	public void setDeveloper(IUser developer) {
		if (developer == null) throw new NullPointerException("Given developer is null.");
		if (!developer.isDeveloper()) throw new IllegalArgumentException("Developer should be a developer.");

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