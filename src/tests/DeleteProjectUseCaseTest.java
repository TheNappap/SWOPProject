package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.notifications.NotificationType;
import model.notifications.forms.RegisterNotificationForm;
import model.projects.IProject;
import model.projects.forms.ProjectDeleteForm;

/**
 * TO DO 
 * @author Niels
 *
 */
public class DeleteProjectUseCaseTest extends BugTrapTest {
	
	@Before
	public void setUp() throws UnauthorizedAccessException {
		//Setup BugTrap
		super.setUp();

		//Log in as Administrator and register for notifications.
		userController.loginAs(admin);
		// Register for notifications to make sure registrations are cleared as well.
		try {
			RegisterNotificationForm form = notificationController.getRegisterNotificationForm();
			form.setObservable(office); // Register for the entire project
			form.setNotificationType(NotificationType.CREATE_BUGREPORT);
			notificationController.registerNotification(form);

			form = notificationController.getRegisterNotificationForm();
			form.setObservable(word); // Register for the subsystem
			form.setNotificationType(NotificationType.BUGREPORT_CHANGE);
			notificationController.registerNotification(form);

			form = notificationController.getRegisterNotificationForm();
			form.setObservable(clippyBug); // Register for the bugreport
			form.setNotificationType(NotificationType.CREATE_COMMENT);
			notificationController.registerNotification(form);
			assertEquals(3, notificationController.getRegistrations().size());
			userController.logOff();
		} catch (UnauthorizedAccessException e) {
			fail("Unauthorized.");
			e.printStackTrace();
		}
	}

	@Test
	public void DeleteProjectTest() {
		//Log in as Administrator.
		userController.loginAs(admin);
		
		try { 
			//Make sure there's a project.
			assertFalse(projectController.getProjectList().isEmpty());
					
			//1. The administrator indicates he wants to delete a project.
			ProjectDeleteForm form = projectController.getProjectDeleteForm();
			
			//2. The system shows a list of all projects.
			List<IProject> list = projectController.getProjectList();
			
			//3. The administrator selects a project.
			form.setProject(list.get(0));
			
			//4. The system deletes a project and recursively all subsystems that are
			//part of the project. All bug reports fore those subsystem are also
			//removed from BugTrap.
			projectController.deleteProject(form);
			
			//confirm
			assertTrue(projectController.getProjectList().isEmpty());
			assertTrue(bugReportController.getBugReportList().isEmpty());
			assertTrue(notificationController.getRegistrations().isEmpty());
		} catch (UnauthorizedAccessException e) { fail("not authorized"); }
	}
	
	@Test
	public void notAuthorizedTest() {
		//Should be logged in to delete Projects.
		try {
			projectController.getProjectDeleteForm();
			fail("Can't be not logged in to delete Projects.");
		} catch (UnauthorizedAccessException e) { }
		
		//Developer can't delete Projects.
		userController.loginAs(prog);
		try {
			projectController.getProjectDeleteForm();
			fail("Developer can't delete projects.");
		} catch (UnauthorizedAccessException e) { }
		
		//Issuer can't delete Projects.
		userController.loginAs(issuer);
		try {
			projectController.getProjectDeleteForm();
			fail("Issuer can't delete projects.");
		} catch (UnauthorizedAccessException e) { }
	}
	
	@Test
	public void varsNotFilledTest() {
		//Log in as Administrator.
		userController.loginAs(admin);
		
		try {
			projectController.deleteProject(projectController.getProjectDeleteForm());
			fail("Can't pass nulls.");
		} 
		catch (UnauthorizedAccessException e) { fail("not authorized"); }
		catch (IllegalArgumentException e) { }
	}
	
	@Test
	public void nullFormTest() {
		//Log in as Administrator.
		userController.loginAs(admin);
		
		try {
			projectController.deleteProject(null);
			fail("Can't pass nulls.");
		}
		catch (IllegalArgumentException e) { } catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
	}
}
