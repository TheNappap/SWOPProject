package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.notifications.IRegistration;
import model.notifications.NotificationType;
import model.notifications.forms.RegisterNotificationForm;
import model.notifications.forms.UnregisterNotificationForm;

public class UnregisterForNotificationUseCaseTest extends BugTrapTest {

	@Before
	public void setUp()  {
		super.setUp();
		//Log in and register for notifications to be able to unregister
		userController.loginAs(issuer);

		RegisterNotificationForm form = null;
		try {
			form = notificationController.getRegisterNotificationForm();
		} catch (UnauthorizedAccessException e) { fail("Not authorised."); }

		form.setObservable(office);

		//7. The issuer selects the system change he wants to be notified of.
		form.setNotificationType(NotificationType.CREATE_BUGREPORT);

		try {
			notificationController.registerNotification(form);

		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
			e.printStackTrace();
		}

		userController.logOff();
	}

	@Test
	public void unregisterForNotificationTest() {
		//Log in as Administrator.
		userController.loginAs(issuer);
		
		//1. The issuer indicates that he wants to unregister from receiving specific notifications.
		UnregisterNotificationForm form = null;
		try {
			form = notificationController.getUnregisterNotificationForm();
		} catch (UnauthorizedAccessException e) { fail("Not logged in."); }
		
		//2. The system shows all active registrationTypes for notifications.
		List<IRegistration> registrations = null;
		try {
			registrations = notificationController.getRegistrations();
		} catch (UnauthorizedAccessException e) {
			fail("Not authorized.");
			e.printStackTrace();
		}

		//3. The issuer selects a specific registration.
		form.setRegistration(registrations.get(0));
		
		//User is registered for one thing.
		try {
			assertEquals(1, notificationController.getRegistrations().size());
		} catch (UnauthorizedAccessException e) {
			fail("Not authorized.");
			e.printStackTrace();
		}

		//4. The system deactivates the specified registration for notifications.
		try {
			notificationController.unregisterNotification(form);
		} catch (UnauthorizedAccessException e) {
			fail("Not authorized.");
			e.printStackTrace();
		}

		//User is registered for nothing.
		try {
			assertEquals(0, notificationController.getRegistrations().size());
		} catch (UnauthorizedAccessException e) {
			e.printStackTrace();
			fail("Not authorized.");
		}
	}
	
	@Test
	public void authorisationTest() {
		//Can't unregister when not logged in.
		try {
			notificationController.getRegisterNotificationForm();
			fail("Can't unregister for notification when not logged in.");
		} catch (UnauthorizedAccessException e) { }
	}
	
	@Test
	public void varsNotFilledTest() {
		//Log in as Administrator.
		userController.loginAs(admin);
		
		try {
			notificationController.getUnregisterNotificationForm().allVarsFilledIn();
			fail("should throw exception");
		} 
		catch (UnauthorizedAccessException e) 	{ fail("not authorized"); }
		catch (NullPointerException e) 			{ }
	}	

}
