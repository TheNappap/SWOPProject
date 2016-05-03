package controllers;

import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.notifications.INotification;
import model.notifications.forms.ShowChronologicalNotificationForm;

/**
 * Controller for all Notification related things.
 * Controllers are the interface that is available to developers
 * creating e.g. a BugTrap UI.
 */
public class NotificationController extends Controller{

	public NotificationController(BugTrap bugTrap) {
		super(bugTrap);
	}
	
	public ShowChronologicalNotificationForm getShowChronologicalNotificationForm() throws UnauthorizedAccessException{
		return getBugTrap().getFormFactory().makeShowChronologicalNotificationForm();
	}
	
//	public RegisterNotificationForm getRegisterNotificationForm() throws UnauthorizedAccessException{
//		return getBugTrap().getFormFactory().makeRegisterNotificationForm();
//	}
//	
//	public UnregisterNotificationForm getUnregisterNotificationForm() throws UnauthorizedAccessException{
//		return getBugTrap().getFormFactory().makeUnregisterNotificationForm();
//	}

	/**
	 * Shows notifications with the information provided in the form.
	 * @param form ShowChronologicalNotificationForm containing all the details about the request for notifications.
	 * @throws UnauthorizedAccessException 
	 */
	public List<INotification> showNotifications(ShowChronologicalNotificationForm form) throws UnauthorizedAccessException{
		return getBugTrap().getNotificationManager().getNotifications(form.getNbOfNotifications());
	}
	
	/**
	 * Register for notifications with the information provided in the form.
	 * @param form RegisterNotificationForm containing all the details about the request for registration.
	 * @throws UnauthorizedAccessException 
	 */
	public void registerForNotification(RegisterNotificationForm form) throws UnauthorizedAccessException {
		getBugTrap().getNotificationManager().registerForNotification(form.getRegistrationType(), form.getObservable(), form.getTag());
	}

	/**
	 * Unregister for notifications with the information provided in the form.
	 * @param form UnregisterNotificationForm containing all the details about the request for unregistration.
	 * @throws UnauthorizedAccessException 
	 */
	public void unregisterForNotification(UnregisterNotificationForm form) throws UnauthorizedAccessException {
		getBugTrap().getNotificationManager().unregisterForNotification(form.getRegistration());
	}

	/**
	 * Get a list of all active registrations for the currently logged in user.
	 * @return A list of current registrations for the user.
	 * @throws UnauthorizedAccessException
     */
	public List<Registration> getRegistrations() throws UnauthorizedAccessException {
		return getBugTrap().getNotificationManager().getRegistrationsLoggedInUser();
	}
}
