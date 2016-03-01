package model;

import model.bugreports.BugReportManager;
import model.projects.ProjectManager;
import model.users.UserManager;

public class BugTrap {

	private final UserManager userManager;
	private final ProjectManager projectManager;
	private final BugReportManager bugReportManager;
	
	
	public BugTrap() {
		this.userManager = new UserManager();
		this.projectManager = new ProjectManager();
		this.bugReportManager = new BugReportManager();
	}


	public UserManager getUserManager() {
		return userManager;
	}


	public ProjectManager getProjectManager() {
		return projectManager;
	}


	public BugReportManager getBugReportManager() {
		return bugReportManager;
	}
}
