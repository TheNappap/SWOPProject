package controllers;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.notifications.forms.RegisterNotificationForm;
import model.notifications.forms.ShowChronologicalNotificationForm;
import model.notifications.forms.UnregisterNotificationForm;

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
	
	public RegisterNotificationForm getRegisterNotificationForm() throws UnauthorizedAccessException{
		return getBugTrap().getFormFactory().makeRegisterNotificationForm();
	}
	
	public UnregisterNotificationForm getUnregisterNotificationForm() throws UnauthorizedAccessException{
		return getBugTrap().getFormFactory().makeUnregisterNotificationForm();
	}

	/**
	 * Shows notifications with the information provided in the form.
	 * @param form ShowChronologicalNotificationForm containing all the details about the request for notifications.
	 * @throws UnauthorizedAccessException 
	 */
	public void showNotifications(ShowChronologicalNotificationForm form) throws UnauthorizedAccessException{
		getBugTrap().getNotificationManager().getNotifications(form.getNbOfNotifications());
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
}
