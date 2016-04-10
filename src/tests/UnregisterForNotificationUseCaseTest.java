package tests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import model.notifications.CreateBugReportObserver;
import model.notifications.Registration;
import model.projects.Project;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.notifications.forms.UnregisterNotificationForm;
import model.projects.Version;

public class UnregisterForNotificationUseCaseTest {

	private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		//Make System.
		bugTrap = new BugTrap();
		
		//Make Users.
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createIssuer("", "", "", "ISSUER");
		
		//Log in as Administrator, create Project/Subsystem, register for Notification and log off.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		bugTrap.getProjectManager().createSubsystem("name", "description", bugTrap.getProjectManager().getProjects().get(0), bugTrap.getProjectManager().getProjects().get(0));
		((Project)bugTrap.getProjectManager().getProjects().get(0)).attach(new CreateBugReportObserver(bugTrap.getNotificationManager().getMailboxForUser(bugTrap.getUserManager().getUser("ADMIN")), ((Project)bugTrap.getProjectManager().getProjects().get(0))));
		bugTrap.getUserManager().logOff();
	}

	@Test
	public void unregisterForNotificationTest() {
		//Log in as Administrator.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		
		//1. The issuer indicates that he wants to unregister from receiving specific notifications.
		UnregisterNotificationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeUnregisterNotificationForm();
		} catch (UnauthorizedAccessException e) { fail("Not logged in."); }
		
		//2. The system shows all active registrationTypes for notifications.
		List<Registration> registrations = null;
		try {
			registrations = bugTrap.getNotificationManager().getRegistrationsLoggedInUser();
		} catch (UnauthorizedAccessException e) {
			fail("Not authorized.");
			e.printStackTrace();
		}

		//3. The issuer selects a specific registration.
		form.setRegistration(registrations.get(0));
		
		//User is registered for one thing.
		try {
			assertEquals(1, bugTrap.getNotificationManager().getRegistrationsLoggedInUser().size());
		} catch (UnauthorizedAccessException e) {
			fail("Not authorized.");
			e.printStackTrace();
		}

		//4. The system deactivates the specified registration for notifications.
		try {
			bugTrap.getNotificationManager().unregisterForNotification(form.getRegistration());
		} catch (UnauthorizedAccessException e) {
			fail("Not authorized.");
			e.printStackTrace();
		}

		//User is registered for nothing.
		try {
			assertEquals(0, bugTrap.getNotificationManager().getRegistrationsLoggedInUser().size());
		} catch (UnauthorizedAccessException e) {
			e.printStackTrace();
			fail("Not authorized.");
		}
	}

}
