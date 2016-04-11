package tests.notificationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.notifications.Notification;

public class NotificationTests {
	
	private Notification not;
	
	@Before
	public void setUp() throws Exception {
		not = new Notification("not");
	}
	
	@Test
	public void constructorTest(){
		assertEquals("not",not.getText());
		assertFalse(not.isRead());
	}
	
	@Test
	public void markReadTest(){
		assertFalse(not.isRead());
		
		not.markAsRead();

		assertTrue(not.isRead());
	}

}
