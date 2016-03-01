package gui;

import bugreports.BugReportManager;
import projects.ProjectManager;
import users.UserManager;

public abstract class GUI {

	private BugReportManager bugReportManager;
	private ProjectManager projectManager;
 	private UserManager userManager;
 	
	public GUI() {
		initialize();
	}
	
	private void initialize() {
		setBugReportManager(new BugReportManager());
		setProjectManager(new ProjectManager());
		setUserManager(new UserManager());
	}

	public BugReportManager getBugReportManager() {
		return bugReportManager;
	}

	public void setBugReportManager(BugReportManager bugReportManager) {
		this.bugReportManager = bugReportManager;
	}

	public ProjectManager getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(ProjectManager projectManager) {
		this.projectManager = projectManager;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}
}
