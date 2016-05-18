package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.FormFactory;
import model.bugreports.BugReport;
import model.bugreports.bugtag.BugTag;
import model.notifications.INotification;
import model.notifications.NotificationType;
import model.notifications.forms.RegisterNotificationForm;
import model.notifications.forms.ShowChronologicalNotificationForm;

public class ShowNotificationsUseCaseTest extends BugTrapTest{

	@Before
	public void setUp() {
		//Setup BugTrap
		super.setUp();

		//Log in as an Issuer, register for notification and log off.
		userController.loginAs(issuer);
		try {
			RegisterNotificationForm form = notificationController.getRegisterNotificationForm();
			form.setObservable(office);
			form.setNotificationType(NotificationType.BUGREPORT_CHANGE);
			notificationController.registerNotification(form);
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}

		//Initially, no notifications.
		// Using BugTrap to shortcut, as it's just the set up.
		assertEquals(0, bugTrap.getNotificationManager().getMailboxForUser(issuer).getNotifications().size());

		BugReport report = (BugReport) bugReportController.getBugReportList().get(0);

		//Update project with 2 new values.
		try {
			report.updateBugTag(BugTag.ASSIGNED);
			report.updateBugTag(BugTag.UNDERREVIEW);
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}

		//2 new tags, 2 new notifications.
		assertEquals(2, bugTrap.getNotificationManager().getMailboxForUser(issuer).getNotifications().size());

		//Update Bug Report with new tag..
		//Log in as Lead to update bug report.
		userController.loginAs(lead);
		try {
			report.updateBugTag(BugTag.RESOLVED);
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}
		//Log back in as issuer.
		userController.loginAs(issuer);
		//After this, 2+1=3 notifications.
		assertEquals(3, bugTrap.getNotificationManager().getMailboxForUser(issuer).getNotifications().size());

		userController.logOff();
	}

	@Test
	public void showNotificationsTest() throws UnauthorizedAccessException {
		userController.loginAs(issuer);

		//1. The issuer indicates he wants to view his notifications
		ShowChronologicalNotificationForm form = null;
		try {
			form = notificationController.getShowChronologicalNotificationForm();
		} catch (UnauthorizedAccessException e) { fail("Must be logged in."); } 
		
		//2. The system asks how many notifications the issuer wants to see.
		//3. The issuer specifies the number of notifications.
		form.setNbOfNotifications(2);
	
		//4. The system shows the requested number of received notifications in chronological order with the most recent notification first.
		List<INotification> reqNotifications = null;
		try {
			reqNotifications = notificationController.showNotifications(form);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		//Confirm.
		//-2 Notifications requested.
		assertEquals(2,		reqNotifications.size());
		//-Not marked as read yet.
		assertFalse(reqNotifications.get(0).isRead());
		assertFalse(reqNotifications.get(1).isRead());
		//-Chronological. Most recent first.
		assertTrue(reqNotifications.get(0).getText().contains("RESOLVED"));
		assertTrue(reqNotifications.get(1).getText().contains("UNDERREVIEW"));
	}

	@Test
	public void showTooMuchNotificationsTest() throws UnauthorizedAccessException {
		userController.loginAs(issuer);

		//1. The issuer indicates he wants to view his notifications
		ShowChronologicalNotificationForm form = null;
		try {
			form = notificationController.getShowChronologicalNotificationForm();
		} catch (UnauthorizedAccessException e) { fail("Must be logged in."); }

		//2. The system asks how many notifications the issuer wants to see.
		//3. The issuer specifies the number of notifications.
		form.setNbOfNotifications(20);

		//4. The system shows the requested number of received notifications in chronological order with the most recent notification first.
		List<INotification> reqNotifications = null;
		try {
			reqNotifications = notificationController.showNotifications(form);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}

		//Confirm.
		//-20 Notifications requested, only 3 available.
		assertEquals(3,		reqNotifications.size());
		//-Not marked as read yet.
		assertFalse(reqNotifications.get(0).isRead());
		assertFalse(reqNotifications.get(1).isRead());
		assertFalse(reqNotifications.get(2).isRead());
		//-Chronological. Most recent first.
		assertTrue(reqNotifications.get(0).getText().contains("RESOLVED"));
		assertTrue(reqNotifications.get(1).getText().contains("UNDERREVIEW"));
		assertTrue(reqNotifications.get(2).getText().contains("ASSIGNED"));
	}
}
