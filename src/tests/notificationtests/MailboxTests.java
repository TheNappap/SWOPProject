package tests.notificationtests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import org.junit.Before;
import org.junit.Test;

import model.notifications.INotification;
import model.notifications.Mailbox;
import tests.BugTrapTest;

public class MailboxTests extends BugTrapTest {
	
	private Mailbox mailbox;
	
	@Before
	public void setUp() throws UnauthorizedAccessException {
		super.setUp();
		mailbox = new Mailbox(admin);
		
		mailbox.addNotification("not1");
		mailbox.addNotification("not2");
	}
	
	@Test
	public void addNotificationTest(){
		assertEquals(2, mailbox.getNotifications().size());
		
		mailbox.addNotification("not3");

		assertEquals(3, mailbox.getNotifications().size());
		assertEquals("not3", mailbox.getNotifications().get(0).getText());
	}
	
	@Test
	public void getNotificationTest(){
		int nbnot = mailbox.getNotifications().size();
		assertEquals(2,nbnot);
		
		mailbox.addNotification("not3");
		
		List<INotification> list = mailbox.getNotifications(2);
		assertEquals(2, list.size());
		assertEquals("not3",list.get(0).getText());
		assertEquals("not2",list.get(1).getText());
	}
}
