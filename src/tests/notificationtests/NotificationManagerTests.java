package tests.notificationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.notifications.INotification;
import model.notifications.Mailbox;
import tests.BugTrapTest;

public class NotificationManagerTests extends BugTrapTest {

	@Before
	public void setUp() throws UnauthorizedAccessException {
		super.setUp();

		// Drop some notifications in the admin's mailbox
		Mailbox box = bugTrap.getNotificationManager().getMailboxForUser(admin);
		box.addNotification("Notification 1");
		box.addNotification("Notification 2");
		box.addNotification("Notification 3");
	}

	@Test
	public void getMailboxForUserTest(){
		//no mailbox made
		Mailbox mb = bugTrap.getNotificationManager().getMailboxForUser(lead);
		assertEquals(lead, mb.getUser());
		//get created mailbox
		mb = bugTrap.getNotificationManager().getMailboxForUser(lead);
		assertEquals(lead, mb.getUser());
	}

	@Test (expected = UnauthorizedAccessException.class)
	public void getRegistrationsLoggedInUserUnauthorizedTest() throws UnauthorizedAccessException{
		bugTrap.getUserManager().logOff();
		bugTrap.getNotificationManager().getRegistrationsLoggedInUser();
	}

	 @Test
	public void getNotifications() throws UnauthorizedAccessException {
		 bugTrap.getUserManager().loginAs(admin);
		 List<INotification> notificationList = bugTrap.getNotificationManager().getNotifications(2);

		 assertEquals("Notification 3", notificationList.get(0).getText());
		 assertEquals("Notification 2", notificationList.get(1).getText());
		 assertFalse(notificationList.get(0).isRead());
		 notificationList.get(0).markAsRead();

		 notificationList = bugTrap.getNotificationManager().getNotifications(1);
		 assertTrue(notificationList.get(0).isRead());
	 }

	@Test (expected = UnauthorizedAccessException.class)
	public void getNotificationsNotLoggedIn() throws UnauthorizedAccessException {
		bugTrap.getNotificationManager().getNotifications(2);
	}
}
