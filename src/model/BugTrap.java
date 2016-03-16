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
	
	public boolean isLoggedIn(){
		if(getUserManager().getLoggedInUser() == null)
			return false;
		else
			return true;
	}
	
	public boolean isAdminLoggedIn(){
		if(isLoggedIn())
			return false;
		return getUserManager().getLoggedInUser().isAdmin();
	}
	
	public boolean isIssuerLoggedIn(){
		if(isLoggedIn())
			return false;
		return getUserManager().getLoggedInUser().isIssuer();
	}
	
	public boolean isDeveloperLoggedIn(){
		if(isLoggedIn())
			return false;
		return getUserManager().getLoggedInUser().isDeveloper();
	}
	
	public void initialize() {
		BugTrapInitializer initializer = new BugTrapInitializer(this);
		initializer.init();
	}
}
