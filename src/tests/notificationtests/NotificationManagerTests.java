package tests.notificationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.notifications.Mailbox;
import model.projects.Project;
import model.projects.Version;
import model.users.IUser;

public class NotificationManagerTests {
	
	private BugTrap bugTrap;
	private IUser admin;
	private IUser dev;

	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		admin = bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		dev = bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().loginAs(admin);
		bugTrap.getProjectManager().createProject("project", "descr", new Date(3), new Date(5), 55, dev, Version.firstVersion());
	}
	
	@Test
	public void getMailboxForUserTest(){
		//no mailbox made
		Mailbox mb = bugTrap.getNotificationManager().getMailboxForUser(admin);
		
		assertEquals(admin, mb.getUser());

		mb = bugTrap.getNotificationManager().getMailboxForUser(admin);
		
		assertEquals(admin, mb.getUser());
	}
	

	@Test (expected = UnauthorizedAccessException.class)
	public void getRegistrationsLoggedInUserUnauthorizedTest() throws UnauthorizedAccessException{
		bugTrap.getUserManager().logOff();

		bugTrap.getNotificationManager().getRegistrationsLoggedInUser();
	}
}
