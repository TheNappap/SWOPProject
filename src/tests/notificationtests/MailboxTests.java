package tests.notificationtests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.notifications.INotification;
import model.notifications.Mailbox;
import model.users.Administrator;

public class MailboxTests {
	
	private Mailbox mailbox;
	
	@Before
	public void setUp() throws Exception {
		mailbox = new Mailbox(new Administrator("","","","ADMIN"));
		
		mailbox.addNotification("not1");
		mailbox.addNotification("not2");
	}
	
	@Test
	public void addNotificationTest(){
		int nbnot = mailbox.getNotifications().size();
		assertEquals(2,nbnot);
		
		mailbox.addNotification("not3");
		
		nbnot = mailbox.getNotifications().size();
		assertEquals(3,nbnot);
		assertEquals("not3",mailbox.getNotifications().get(0).getText());
	}
	
	@Test
	public void getNotificationTest(){
		int nbnot = mailbox.getNotifications().size();
		assertEquals(2,nbnot);
		
		mailbox.addNotification("not3");
		
		List<INotification> list = mailbox.getNotifications(2);
		assertEquals(2,list.size());
		assertEquals("not3",list.get(0).getText());
		assertEquals("not2",list.get(1).getText());
	}

}
