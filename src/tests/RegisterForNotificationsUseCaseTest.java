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
import model.notifications.BugReportSpecificTagObserver;
import model.notifications.Mailbox;
import model.notifications.SystemObserver;
import model.notifications.forms.RegisterNotificationForm;
import model.projects.IProject;
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

	@SuppressWarnings("deprecation")
	@Test
	public void registerForNotificationProjectTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ISSUER"));
		
		//1. The issuer indicates that he wants to register for receiving notifications.
		RegisterNotificationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeRegisterNotificationForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//2. The system asks if he wants to register for a Project, Subsystem or BugReport.
		//3. The issuer indicates he wants to register for a Project.
		//4. The system shows a list of projects.
		//5. The issuer selects a project.
		IProject project = null;
		try {
			project = bugTrap.getProjectManager().getProjects().get(0);
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		form.setObservable(project);
		
		//6. The system presents a form describing the specific system changes that
		//can be subscribed to for the selected object of interest: (...)
		//7. The issuer selects the system change he wants to be notified of.
		//8. The system registers this issuer to receive notifications about the selected object of interest for the specified changes
		Mailbox box = bugTrap.getNotificationManager().getMailboxForUser(bugTrap.getUserManager().getUser("ISSUER"));
		project.attach(new SystemObserver(box, project));
		
		//Confirm
		try {
			bugTrap.getProjectManager().updateProject(project, "newName", "newDescription", 314e10, new Date(2016,4,8));
		} catch (UnauthorizedAccessException e) { fail("Not authorised"); }
		
		//Name, Description, Budget and startDate (4 things) changed.
		assertEquals(4, box.getNotifications().size());
	}
	
	@Test
	public void registerForNotificationSpecificTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		
		//1. The issuer indicates that he wants to register for receiving notifications.
		RegisterNotificationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeRegisterNotificationForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		//2. The system asks if he wants to register for a Project, Subsystem or BugReport.
		//3. The issuer indicates he wants to register for a bug report.
		//4/5. (...)
		IBugReport bugReport = null;
		try {
			bugReport = bugTrap.getBugReportManager().getBugReportList().get(0);
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }
		
		form.setObservable(bugReport);
		
		//6. The system presents a form describing the specific system changes that
		//can be subscribed to for the selected object of interest: (...)
		//7. The issuer selects the system change he wants to be notified of.
		//8. The system registers this issuer to receive notifications about the selected object of interest for the specified changes
		Mailbox box = bugTrap.getNotificationManager().getMailboxForUser(bugTrap.getUserManager().getUser("ISSUER"));
		bugReport.attach(new BugReportSpecificTagObserver(box, bugReport, BugTag.NOTABUG));
		
		//Confirm
		//Initially no notifications.
		assertEquals(0, box.getNotifications().size());
		try {
			bugTrap.getBugReportManager().updateBugReport(bugReport, BugTag.UNDERREVIEW);
		} catch (UnauthorizedAccessException e) { System.out.println("Not authorised."); }
		//Not requested tag, still no notification.
		assertEquals(0, box.getNotifications().size());
		try {
			bugTrap.getBugReportManager().updateBugReport(bugReport, BugTag.NOTABUG);
		} catch (UnauthorizedAccessException e) { System.out.println("Not authorised."); }
		//Requested tag, so notification.
		assertEquals(1, box.getNotifications().size());
	}
	
	@Test
	public void authorisationTest() {
		//Can't create BugReport when not logged in.
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
