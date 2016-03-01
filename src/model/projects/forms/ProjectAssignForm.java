package model.projects.forms;

import java.util.ArrayList;

import model.projects.Project;
import model.projects.Role;
import model.users.Developer;

public class ProjectAssignForm {

	private ArrayList<Project> projects;
	private ArrayList<Developer> developers;
	private Project selectedProject;
	private Developer selectedDeveloper;
	private Role selectedRole;

	/**
	 * 
	 * @param project
	 * @param dev
	 */
	public ArrayList<Role> getRolesForDeveloperInProject(Project project, Developer dev) {
		// TODO - implement ProjectAssignForm.getRolesForDeveloperInProject
		throw new UnsupportedOperationException();
	}

}