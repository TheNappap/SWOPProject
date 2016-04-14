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
import model.notifications.Observable;
import model.notifications.Registration;
import model.notifications.RegistrationType;
import model.projects.Project;
import model.projects.Version;
import model.users.IUser;

public class NotificationManagerTests {
	
	private BugTrap bugTrap;
	private IUser admin;
	private IUser dev;
	private Observable system;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		admin = bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		dev = bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().loginAs(admin);
		bugTrap.getProjectManager().createProject("project", "descr", new Date(3), new Date(5), 55, dev, Version.firstVersion());
		
		system = (Project) bugTrap.getProjectManager().getProjects().get(0);
		
		bugTrap.getNotificationManager().registerForNotification(RegistrationType.BUGREPORT_CHANGE, system, null);
	}
	
	@Test
	public void getMailboxForUserTest(){
		//no mailbox made
		Mailbox mb = bugTrap.getNotificationManager().getMailboxForUser(admin);
		
		assertEquals(admin,mb.getUser());
		
		//already mailbox made
		mb = bugTrap.getNotificationManager().getMailboxForUser(admin);
		
		assertEquals(admin,mb.getUser());
	}
	
	@Test
	public void registerForNotificationTest(){
		try {
			List<Registration> regs = bugTrap.getNotificationManager().getRegistrationsLoggedInUser();
			assertEquals(1, regs.size());
			
			bugTrap.getNotificationManager().registerForNotification(RegistrationType.CREATE_COMMENT, system, null);
			
			regs = bugTrap.getNotificationManager().getRegistrationsLoggedInUser();
			assertEquals(2, regs.size());
			assertEquals(RegistrationType.CREATE_COMMENT, regs.get(1).getRegistrationType());
			
		} catch (UnauthorizedAccessException e) {
			fail("unauthorized");
			e.printStackTrace();
		}
		
	}
	
	@Test (expected = UnauthorizedAccessException.class)
	public void registerUnauthorizedTest() throws UnauthorizedAccessException{
		bugTrap.getUserManager().logOff();

		bugTrap.getNotificationManager().registerForNotification(RegistrationType.BUGREPORT_CHANGE, system, null);
	}
	
	@Test
	public void unregisterForNotificationTest(){
		try {
			List<Registration> regs = bugTrap.getNotificationManager().getRegistrationsLoggedInUser();
			assertEquals(1, regs.size());
			
			bugTrap.getNotificationManager().unregisterForNotification(regs.get(0));
			
			regs = bugTrap.getNotificationManager().getRegistrationsLoggedInUser();
			assertEquals(0, regs.size());
			
		} catch (UnauthorizedAccessException e) {
			fail("unauthorized");
			e.printStackTrace();
		}
	}
	
	@Test (expected = UnauthorizedAccessException.class)
	public void unregisterUnauthorizedTest() throws UnauthorizedAccessException{
		bugTrap.getUserManager().logOff();

		bugTrap.getNotificationManager().unregisterForNotification(null);
	}
	
	@Test (expected = UnauthorizedAccessException.class)
	public void getRegistrationsLoggedInUserUnauthorizedTest() throws UnauthorizedAccessException{
		bugTrap.getUserManager().logOff();

		bugTrap.getNotificationManager().getRegistrationsLoggedInUser();
	}

}
