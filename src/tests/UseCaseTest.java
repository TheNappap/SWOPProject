package tests;

import org.junit.Before;

import controllers.BugReportController;
import controllers.NotificationController;
import controllers.ProjectController;
import controllers.UserController;
import model.BugTrap;

public abstract class UseCaseTest {

	protected BugReportController bugReportController;
	protected NotificationController notificationController;
	protected ProjectController projectController;
	protected UserController userController;
	
	protected BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		this.bugTrap = new BugTrap();
		
		this.bugReportController	= new BugReportController(bugTrap);
		this.notificationController	= new NotificationController(bugTrap);
		this.projectController	= new ProjectController(bugTrap);
		this.userController		= new UserController(bugTrap);
	}
}
