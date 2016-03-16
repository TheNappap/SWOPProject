package model;

import model.bugreports.BugReportDAO;
import model.bugreports.BugReportManager;
import model.projects.ProjectManager;
import model.users.UserManager;

public class BugTrap {

	private final UserManager userManager;
	private final ProjectManager projectManager;
	final BugReportManager bugReportDAO;
	
	
	public BugTrap() {
		this.userManager = new UserManager();
		this.projectManager = new ProjectManager();
		this.bugReportDAO = new BugReportManager();
	}

	public UserManager getUserManager() {
		return userManager;
	}


	public ProjectManager getProjectManager() {
		return projectManager;
	}


	public BugReportDAO getBugReportDAO() {
		return bugReportDAO;
	}
	
	public void initialize() {
		BugTrapInitializer initializer = new BugTrapInitializer(this);
		initializer.init();
	}
}
