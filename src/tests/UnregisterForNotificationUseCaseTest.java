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
	public void setUp() throws UnauthorizedAccessException {
		super.setUp();
		//Log in and register for notifications to be able to unregister
		userController.loginAs(issuer);

		RegisterNotificationForm form = notificationController.getRegisterNotificationForm();
		form.setObservable(office);
		form.setNotificationType(NotificationType.CREATE_BUGREPORT);
		notificationController.registerNotification(form);
		userController.logOff();
	}

	@Test
	public void unregisterForNotificationTest() throws UnauthorizedAccessException {
		//Log in as Administrator.
		userController.loginAs(issuer);
		
		//1. The issuer indicates that he wants to unregister from receiving specific notifications.
		UnregisterNotificationForm form = notificationController.getUnregisterNotificationForm();

		//2. The system shows all active registrationTypes for notifications.
		List<IRegistration> registrations = null;
		registrations = notificationController.getRegistrations();

		//3. The issuer selects a specific registration.
		form.setRegistration(registrations.get(0));
		
		//User is registered for one thing.
		assertEquals(1, notificationController.getRegistrations().size());

		//4. The system deactivates the specified registration for notifications.
		notificationController.unregisterNotification(form);

		//User is registered for nothing.
		assertEquals(0, notificationController.getRegistrations().size());
	}
	
	@Test (expected = UnauthorizedAccessException.class)
	public void authorisationTest() throws UnauthorizedAccessException {
		//Can't unregister when not logged in.
		notificationController.getRegisterNotificationForm();
	}
	
	@Test (expected = NullPointerException.class)
	public void varsNotFilledTest() throws UnauthorizedAccessException {
		//Log in as Administrator.
		userController.loginAs(admin);
		
		notificationController.getUnregisterNotificationForm().allVarsFilledIn();
	}
}
