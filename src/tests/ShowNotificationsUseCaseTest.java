package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.FormFactory;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.notifications.BugReportObserver;
import model.notifications.INotification;
import model.notifications.SystemObserver;
import model.notifications.forms.ShowChronologicalNotificationForm;
import model.projects.IProject;
import model.projects.Version;

public class ShowNotificationsUseCaseTest {

	private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		//Make System.
		bugTrap = new BugTrap();
		
		//Make Users.
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createIssuer("", "", "", "ISSUER");
		
		//Log in as Administrator and create Project/Subsystem.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		bugTrap.getProjectManager().createSubsystem("name", "description", bugTrap.getProjectManager().getProjects().get(0), bugTrap.getProjectManager().getProjects().get(0));
		
		//Log in as Developer and add BugReport.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), bugTrap.getProjectManager().getProjects().get(0).getSubsystems().get(0), bugTrap.getUserManager().getUser("DEV"), new ArrayList<>(), new ArrayList<>(), BugTag.NEW);
		
		
		//Log in as an Issuer, register for  notification and log off.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ISSUER"));
		bugTrap.getBugReportManager().getBugReportList().get(0).attach(new BugReportObserver(bugTrap.getNotificationManager().getMailboxForUser(bugTrap.getUserManager().getUser("ISSUER")), bugTrap.getBugReportManager().getBugReportList().get(0)));
		bugTrap.getProjectManager().getProjects().get(0).attach(new SystemObserver(bugTrap.getNotificationManager().getMailboxForUser(bugTrap.getUserManager().getUser("ISSUER")), bugTrap.getProjectManager().getProjects().get(0)));
		bugTrap.getUserManager().logOff();
	}

	@Test
	public void showNotificationsTest() {
		//Log in as Issuer.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ISSUER"));
		
		//Initially, no notifications.
		assertEquals(0, bugTrap.getNotificationManager().getMailboxForUser(bugTrap.getUserManager().getUser("ISSUER")).getNotifications().size());
		
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		
		//Update project with 2 new values.
		bugTrap.getProjectManager().updateProject(project, "newName", project.getDescription(), 125478963, project.getStartDate());	
	
		//2 new values, 2 new notifications.
		assertEquals(2, bugTrap.getNotificationManager().getMailboxForUser(bugTrap.getUserManager().getUser("ISSUER")).getNotifications().size());
		
		IBugReport bugReport = bugTrap.getBugReportManager().getBugReportList().get(0);
		
		//Update Bug Report with new tag..
		bugTrap.getBugReportManager().updateBugReport(bugReport, BugTag.UNDERREVIEW);
		//After this, 2+1=3 notifications.
		assertEquals(3, bugTrap.getNotificationManager().getMailboxForUser(bugTrap.getUserManager().getUser("ISSUER")).getNotifications().size());
		
		//1. The issuer indicates he wants to view his notifications
		ShowChronologicalNotificationForm form = null;
		try {
			form = new FormFactory(bugTrap).makeShowChronologicalNotificationForm();
		} catch (UnauthorizedAccessException e) { fail("Must be logged in."); } 
		
		//2. The system asks how many notifications the issuer wants to see.
		//3. The issuer specifies the number of notifications.
		form.setNbOfNotifications(2);
	
		//4. The system shows the requested number of received notifications in chronological order with the most recent notification first.
		List<INotification> reqNotifications = bugTrap.getNotificationManager().getMailboxForUser(bugTrap.getUserManager().getUser("ISSUER")).getNotifications(form.getNbOfNotifications());
		
		//Confirm.
		//-2 Notifications requested.
		assertEquals(2,		reqNotifications.size());
		//-Not marked as read yet.
		assertFalse(reqNotifications.get(0).isRead());
		assertFalse(reqNotifications.get(1).isRead());
		//-Chronological.
		assertTrue(reqNotifications.get(0).getText().contains("BugReport"));
		assertTrue(reqNotifications.get(1).getText().contains("Project"));
	}

}
