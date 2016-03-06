package model;

import model.bugreports.BugReportDAO;
import model.bugreports.BugReportManager;
import model.projects.ProjectDAO;
import model.projects.ProjectManager;
import model.users.UserDAO;
import model.users.UserManager;

public class BugTrap {

	final UserManager userDAO;
	final ProjectManager projectDAO;
	final BugReportManager bugReportDAO;
	
	
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
	
	public void initialize() {
		BugTrapInitializer initializer = new BugTrapInitializer(this);
		initializer.init();
	}
}
