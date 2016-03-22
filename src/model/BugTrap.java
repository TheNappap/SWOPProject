package model;

import model.bugreports.BugReportManager;
import model.projects.ProjectManager;
import model.users.UserManager;

public class BugTrap {

	private final UserManager userManager;
	private final ProjectManager projectManager;
	private final BugReportManager bugReportManager;
	private final FormFactory formFactory;
	
	
	public BugTrap() {
		this.userManager = new UserManager();
		this.projectManager = new ProjectManager(this);
		this.bugReportManager = new BugReportManager(this);
		this.formFactory = new FormFactory(this);
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
	
	public FormFactory getFormFactory() {
		return formFactory;
	}
	
	public boolean isLoggedIn(){
		if(getUserManager().getLoggedInUser() == null)
			return false;
		else
			return true;
	}
	
	public boolean isAdminLoggedIn(){
		if(!isLoggedIn())
			return false;
		return getUserManager().getLoggedInUser().isAdmin();
	}
	
	public boolean isIssuerLoggedIn(){
		if(!isLoggedIn())
			return false;
		return getUserManager().getLoggedInUser().isIssuer();
	}
	
	public boolean isDeveloperLoggedIn(){
		if(!isLoggedIn())
			return false;
		return getUserManager().getLoggedInUser().isDeveloper();
	}
	
	public void initialize() {
		BugTrapInitializer initializer = new BugTrapInitializer(this);
		initializer.init();
	}
}