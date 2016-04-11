package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.notifications.Mailbox;
import model.notifications.RegistrationType;
import model.notifications.forms.RegisterNotificationForm;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;

public class RegisterForNotificationsUseCaseTest {

	private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		//Make System.
		bugTrap = new BugTrap();
		
		//Make Users.
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createIssuer("", "", "", "ISSUER");
		
		//Log in as Administrator, create Project/Subsystem and log off.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		bugTrap.getProjectManager().createSubsystem("name", "description", bugTrap.getProjectManager().getProjects().get(0), bugTrap.getProjectManager().getProjects().get(0));
		bugTrap.getUserManager().logOff();
		
		//Log in as Developer, add BugReport and log off.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), bugTrap.getProjectManager().getProjects().get(0).getSubsystems().get(0), bugTrap.getUserManager().getUser("DEV"), new ArrayList<>(), new ArrayList<>(), BugTag.NEW);
		bugTrap.getUserManager().logOff();
	}
	
	@Test
	public void registerForNotificationProjectTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		
		//1. The issuer indicates that he wants to register for receiving notifications.
		RegisterNotificationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeRegisterNotificationForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//2. The system asks if he wants to register for a Project, Subsystem or BugReport.
		//3. The issuer indicates he wants to register for a project.
		//4/5. (...)
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		form.setObservable(project);
		
		//6. The system presents a form describing the specific system changes that
		//can be subscribed to for the selected object of interest: (...)
		//7. The issuer selects the system change he wants to be notified of.
		//8. The system registers this issuer to receive notifications about the selected object of interest for the specified changes
		form.setTag(BugTag.NOTABUG);
		form.setRegistrationType(RegistrationType.BUGREPORT_SPECIFIC_TAG);

		Mailbox box = null;
		try {
			bugTrap.getNotificationManager().registerForNotification(form.getRegistrationType(), form.getObservable(), form.getTag());
			box = bugTrap.getNotificationManager().getMailboxForUser(bugTrap.getUserManager().getUser("DEV"));
		} catch (UnauthorizedAccessException e) {
			fail("Not authorized.");
			e.printStackTrace();
		}

		IBugReport bugReport = bugTrap.getBugReportManager().getBugReportList().get(0);
		//Confirm
		//Initially no notifications.
		assertEquals(0, box.getNotifications().size());
		bugTrap.getBugReportManager().updateBugReport(bugReport, BugTag.UNDERREVIEW);
		//Not requested tag, still no notification.
		assertEquals(0, box.getNotifications().size());
		bugTrap.getBugReportManager().updateBugReport(bugReport, BugTag.NOTABUG);
		//Requested tag, so notification.
		assertEquals(1, box.getNotifications().size());
	}
	
	@Test
	public void registerForNotificationSubsystemTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		
		//1. The issuer indicates that he wants to register for receiving notifications.
		RegisterNotificationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeRegisterNotificationForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//2. The system asks if he wants to register for a Project, Subsystem or BugReport.
		//3a. The issuer indicates he wants to register for a bug report.
			//1/2
			IProject project = bugTrap.getProjectManager().getProjects().get(0);
			//3/4
			ISubsystem subsystem = project.getAllDirectOrIndirectSubsystems().get(0);
			form.setObservable(subsystem);
		
		//6. The system presents a form describing the specific system changes that
		//can be subscribed to for the selected object of interest: (...)
		//7. The issuer selects the system change he wants to be notified of.
		//8. The system registers this issuer to receive notifications about the selected object of interest for the specified changes
		form.setTag(BugTag.NOTABUG);
		form.setRegistrationType(RegistrationType.BUGREPORT_SPECIFIC_TAG);

		Mailbox box = null;
		try {
			bugTrap.getNotificationManager().registerForNotification(form.getRegistrationType(), form.getObservable(), form.getTag());
			box = bugTrap.getNotificationManager().getMailboxForUser(bugTrap.getUserManager().getUser("DEV"));
		} catch (UnauthorizedAccessException e) {
			fail("Not authorized.");
			e.printStackTrace();
		}

		IBugReport bugReport = bugTrap.getBugReportManager().getBugReportList().get(0);
		//Confirm
		//Initially no notifications.
		assertEquals(0, box.getNotifications().size());
		bugTrap.getBugReportManager().updateBugReport(bugReport, BugTag.UNDERREVIEW);
		//Not requested tag, still no notification.
		assertEquals(0, box.getNotifications().size());
		bugTrap.getBugReportManager().updateBugReport(bugReport, BugTag.NOTABUG);
		//Requested tag, so notification.
		assertEquals(1, box.getNotifications().size());
	}

	@Test
	public void registerForNotificationBugReportTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		
		//1. The issuer indicates that he wants to register for receiving notifications.
		RegisterNotificationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeRegisterNotificationForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//2. The system asks if he wants to register for a Project, Subsystem or BugReport.
		//3b. The issuer indicates he wants to register for a bug report.
		IBugReport bugReport = bugTrap.getBugReportManager().getBugReportList().get(0);
		form.setObservable(bugReport);
		
		//6. The system presents a form describing the specific system changes that
		//can be subscribed to for the selected object of interest: (...)
		//7. The issuer selects the system change he wants to be notified of.
		//8. The system registers this issuer to receive notifications about the selected object of interest for the specified changes
		form.setTag(BugTag.NOTABUG);
		form.setRegistrationType(RegistrationType.BUGREPORT_SPECIFIC_TAG);

		Mailbox box = null;
		try {
			bugTrap.getNotificationManager().registerForNotification(form.getRegistrationType(), form.getObservable(), form.getTag());
			box = bugTrap.getNotificationManager().getMailboxForUser(bugTrap.getUserManager().getUser("DEV"));
		} catch (UnauthorizedAccessException e) {
			fail("Not authorized.");
			e.printStackTrace();
		}

		//Confirm
		//Initially no notifications.
		assertEquals(0, box.getNotifications().size());
		bugTrap.getBugReportManager().updateBugReport(bugReport, BugTag.UNDERREVIEW);
		//Not requested tag, still no notification.
		assertEquals(0, box.getNotifications().size());
		bugTrap.getBugReportManager().updateBugReport(bugReport, BugTag.NOTABUG);
		//Requested tag, so notification.
		assertEquals(1, box.getNotifications().size());
	}
	
	@Test
	public void authorisationTest() {
		//Can't register when not logged in.
		try {
			bugTrap.getFormFactory().makeRegisterNotificationForm();
			fail("Can't register for notification when not logged in.");
		} catch (UnauthorizedAccessException e) { }
	}
	
	@Test
	public void varsNotFilledTest() {
		//Log in as Administrator.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		
		try {
			bugTrap.getFormFactory().makeRegisterNotificationForm().allVarsFilledIn();
			fail("should throw exception");
		} 
		catch (UnauthorizedAccessException e) 	{ fail("not authorized"); }
		catch (NullPointerException e) 			{ }
	}	

}
