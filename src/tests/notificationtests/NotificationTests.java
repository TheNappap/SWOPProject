package tests.notificationtests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.notifications.Notification;
import tests.BugTrapTest;

public class NotificationTests extends BugTrapTest {
	
	private Notification not;
	
	@Before
	public void setUp() {
		super.setUp();
		not = new Notification("not");
	}
	
	@Test
	public void constructorTest(){
		not = new Notification("not");
		assertEquals("not", not.getText());
		assertFalse(not.isRead());
	}
	
	@Test
	public void markReadTest(){
		assertFalse(not.isRead());
		
		not.markAsRead();

		assertTrue(not.isRead());
	}

}
