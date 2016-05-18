package model;

import model.bugreports.BugReportManager;
import model.notifications.NotificationManager;
import model.projects.ProjectManager;
import model.users.IUser;
import model.users.UserManager;

/**
 * This class represents the complete BugTrap system.
 * It contains several managers and a form factory.
 */
public class BugTrap {

	private final UserManager userManager;
	private final ProjectManager projectManager;
	private final BugReportManager bugReportManager;
	private final NotificationManager notificationManager;
	private final FormFactory formFactory;
	
	/**
	 * Constructor. Initializes managers.
	 */
	public BugTrap() {
		this.userManager = new UserManager();
		this.projectManager = new ProjectManager(this);
		this.bugReportManager = new BugReportManager(this);
		this.notificationManager = new NotificationManager(this);
		this.formFactory = new FormFactory(this);
	}

	/**
	 * 
	 * @return The User Manager
	 */
	public UserManager getUserManager() {
		return userManager;
	}

	/**
	 * 
	 * @return The Project Manager
	 */
	public ProjectManager getProjectManager() {
		return projectManager;
	}

	/**
	 * 
	 * @return The BugReport Manager
	 */
	public BugReportManager getBugReportManager() {
		return bugReportManager;
	}

	/**
	 * 
	 * @return The Notification Manager
	 */
	public NotificationManager getNotificationManager() {
		return notificationManager;
	}
	
	/**
	 * 
	 * @return The Form Factory.
	 */
	public FormFactory getFormFactory() {
		return formFactory;
	}
	
	/**
	 * Determines if there's a logged in User.
	 * @return <tt>true</tt> if there's a logged in User.
	 */
	public boolean isLoggedIn(){
		if(getUserManager().getLoggedInUser() == null)
			return false;
		else
			return true;
	}
	
	public IUser getLoggedInUser() {
		return getUserManager().getLoggedInUser();
	}
	
	/**
	 * Determines if the logged in User is an Administrator.
	 * @return <tt>true</tt> if the logged in User is an Administrator.
	 */
	public boolean isAdminLoggedIn(){
		if(!isLoggedIn())
			return false;
		return getUserManager().getLoggedInUser().isAdmin();
	}
	
	/**
	 * Determines if the logged in User is an Issuer.
	 * @return <tt>true</tt> if the logged in User is an Issuer
	 */
	public boolean isIssuerLoggedIn(){
		if(!isLoggedIn())
			return false;
		return getUserManager().getLoggedInUser().isIssuer();
	}
	
	/**
	 * Determines if the logged in User is a Developer.
	 * @return <tt>true</tt> if the logged in User is a Developer.
	 */
	public boolean isDeveloperLoggedIn(){
		if(!isLoggedIn())
			return false;
		return getUserManager().getLoggedInUser().isDeveloper();
	}
	
	
	/**
	 * Initialize the BugTrap system with some data.
	 */
	public void initialize() {
		BugTrapInitializer initializer = new BugTrapInitializer(this);
		initializer.init();
	}
}