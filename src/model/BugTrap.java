package model;

import model.bugreports.BugReportDAO;
import model.bugreports.BugReportManager;
import model.projects.ProjectDAO;
import model.projects.ProjectManager;
import model.users.UserManager;

public class BugTrap {

	private final UserManager userManager;
	final ProjectManager projectDAO;
	final BugReportManager bugReportDAO;
	
	
	public BugTrap() {
		this.userManager = new UserManager();
		this.projectDAO = new ProjectManager();
		this.bugReportDAO = new BugReportManager();
	}

	public UserManager getUserManager() {
		return userManager;
	}


	public ProjectDAO getProjectDAO() {
		return projectDAO;
	}


	public BugReportDAO getBugReportDAO() {
		return bugReportDAO;
	}
	
	public void initialize() {
		BugTrapInitializer initializer = new BugTrapInitializer(this);
		initializer.init();
	}
}
