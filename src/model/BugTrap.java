package model;

import model.bugreports.BugReportDAO;
import model.bugreports.BugReportManager;
import model.projects.ProjectManager;
import model.users.UserDAO;
import model.users.UserManager;

public class BugTrap {

	private final UserDAO userDAO;
	private final ProjectManager projectManager;
	private final BugReportDAO bugReportDAO;
	
	
	public BugTrap() {
		this.userDAO = new UserManager();
		this.projectManager = new ProjectManager();
		this.bugReportDAO = new BugReportManager();
	}


	public UserDAO getUserDAO() {
		return userDAO;
	}


	public ProjectManager getProjectManager() {
		return projectManager;
	}


	public BugReportDAO getBugReportDAO() {
		return bugReportDAO;
	}
}
