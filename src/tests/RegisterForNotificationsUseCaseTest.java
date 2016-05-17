package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.BugReport;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.notifications.Mailbox;
import model.notifications.NotificationType;
import model.notifications.forms.RegisterNotificationForm;
import model.projects.IProject;
import model.projects.ISubsystem;

public class RegisterForNotificationsUseCaseTest extends BugTrapTest {

	@Test
	public void registerForNotificationProjectTest() throws UnauthorizedAccessException {
		//Log in.
		bugTrap.getUserManager().loginAs(lead);
		
		//1. The issuer indicates that he wants to register for receiving notifications.
		RegisterNotificationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeRegisterForNotificationForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//2. The system asks if he wants to register for a Project, Subsystem or BugReport.
		//3. The issuer indicates he wants to register for a project.
		//4/5. (...)
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		form.setObservable(project);
		
		//6. The system presents a form describing the specific system changes that
		NotificationType.values();
		//7. The issuer selects the system change he wants to be notified of.
		form.setNotificationType(NotificationType.BUGREPORT_SPECIFIC_TAG);
		form.setTag(BugTag.NOTABUG);
		//8. The system registers this issuer to receive notifications about the selected object of interest for the specified changes
		try {
			notificationController.registerNotification(form);
			
		} catch (UnauthorizedAccessException e) {
			fail("Not authorized.");
			e.printStackTrace();
		}

		Mailbox box = bugTrap.getNotificationManager().getMailboxForUser(userController.getLoggedInUser());
		BugReport bugReport = (BugReport) clippyBug;
		//Confirm
		//Initially no notifications.
		assertEquals(0, box.getNotifications().size());
		bugReport.updateBugTag(BugTag.UNDERREVIEW);
		//Not requested tag, still no notification.
		assertEquals(0, box.getNotifications().size());
		bugReport.updateBugTag(BugTag.NOTABUG);
		//Requested tag, so notification.
		assertEquals(1, box.getNotifications().size());
	}
	
	@Test
	public void registerForNotificationSubsystemTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(prog);
		
		//1. The issuer indicates that he wants to register for receiving notifications.
		RegisterNotificationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeRegisterForNotificationForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//2. The system asks if he wants to register for a Project, Subsystem or BugReport.
		//3a. The issuer indicates he wants to register for a bug report.
			//1/2
			IProject project = projectController.getProjectList().get(0);
			//3/4
			ISubsystem subsystem = null;
			for (ISubsystem sub : project.getAllDirectOrIndirectSubsystems())
				if (sub.getName().equals("Clippy"))
					subsystem = sub;
			form.setObservable(subsystem);
		
		//6. The system presents a form describing the specific system changes that
		//can be subscribed to for the selected object of interest: (...)
		//7. The issuer selects the system change he wants to be notified of.
		//8. The system registers this issuer to receive notifications about the selected object of interest for the specified changes
		form.setTag(BugTag.NOTABUG);
		form.setNotificationType(NotificationType.BUGREPORT_SPECIFIC_TAG);

		Mailbox box = bugTrap.getNotificationManager().getMailboxForUser(userController.getLoggedInUser());
		try {
			notificationController.registerNotification(form);
		} catch (UnauthorizedAccessException e) {
			fail("Not authorized.");
			e.printStackTrace();
		}

		bugTrap.getUserManager().logOff();
		bugTrap.getUserManager().loginAs(lead); // Lead changes some tags

		BugReport bugReport = (BugReport)clippyBug;
		//Confirm
		//Initially no notifications.
		assertEquals(0, box.getNotifications().size());
		try {
			bugReport.updateBugTag(BugTag.UNDERREVIEW);
		} catch (UnauthorizedAccessException e) { fail(e.getMessage()); }
		//Not requested tag, still no notification.
		assertEquals(0, box.getNotifications().size());
		
		try {
			bugReport.updateBugTag(BugTag.NOTABUG);
		} catch (UnauthorizedAccessException e) { fail(); }
		
		//Requested tag, so notification.
		assertEquals(1, box.getNotifications().size());
	}

	@Test
	public void registerForNotificationBugReportTest() throws UnauthorizedAccessException {
		//Log in.
		bugTrap.getUserManager().loginAs(issuer);
		
		//1. The issuer indicates that he wants to register for receiving notifications.
		RegisterNotificationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeRegisterForNotificationForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//2. The system asks if he wants to register for a Project, Subsystem or BugReport.
		//3b. The issuer indicates he wants to register for a bug report.
		IBugReport bugReport = bugReportController.getBugReportList().get(0);
		form.setObservable(bugReport);
		
		//6. The system presents a form describing the specific system changes that
		//can be subscribed to for the selected object of interest: (...)
		//7. The issuer selects the system change he wants to be notified of.
		//8. The system registers this issuer to receive notifications about the selected object of interest for the specified changes
		form.setTag(BugTag.NOTABUG);
		form.setNotificationType(NotificationType.BUGREPORT_SPECIFIC_TAG);

		Mailbox box = bugTrap.getNotificationManager().getMailboxForUser(userController.getLoggedInUser());
		try {
			notificationController.registerNotification(form);
		} catch (UnauthorizedAccessException e) {
			fail("Not authorized.");
			e.printStackTrace();
		}

		bugTrap.getUserManager().logOff();
		bugTrap.getUserManager().loginAs(lead); // Lead changes some tags

		//Confirm
		//Initially no notifications.
		assertEquals(0, box.getNotifications().size());
		((BugReport)bugReport).updateBugTag(BugTag.UNDERREVIEW);
		//Not requested tag, still no notification.
		assertEquals(0, box.getNotifications().size());
		((BugReport)bugReport).updateBugTag(BugTag.NOTABUG);
		//Requested tag, so notification.
		assertEquals(1, box.getNotifications().size());
	}
	
	@Test
	public void authorisationTest() {
		//Can't register when not logged in.
		try {
			bugTrap.getFormFactory().makeRegisterForNotificationForm();
			fail("Can't register for notification when not logged in.");
		} catch (UnauthorizedAccessException e) { }
	}
	
	@Test
	public void varsNotFilledTest() {
		//Log in as Administrator.
		bugTrap.getUserManager().loginAs(admin);
		
		try {
			bugTrap.getFormFactory().makeRegisterForNotificationForm().allVarsFilledIn();
			fail("should throw exception");
		} 
		catch (UnauthorizedAccessException e) 	{ fail("not authorized"); }
		catch (NullPointerException e) 			{ }
	}	

}
