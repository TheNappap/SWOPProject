package model;

import model.bugreports.BugReportDAO;
import model.bugreports.BugReportManager;
import model.projects.ProjectDAO;
import model.projects.ProjectManager;
import model.users.UserDAO;
import model.users.UserManager;

public class BugTrap {

	private final UserDAO userDAO;
	private final ProjectDAO projectDAO;
	private final BugReportDAO bugReportDAO;
	
	
	public BugTrap() {
		this.userDAO = new UserManager();
		this.projectDAO = new ProjectManager();
		this.bugReportDAO = new BugReportManager();
	}


	public UserDAO getUserDAO() {
		return userDAO;
	}


	public ProjectDAO getProjectDAO() {
		return projectDAO;
	}


	public BugReportDAO getBugReportDAO() {
		return bugReportDAO;
	}
}
