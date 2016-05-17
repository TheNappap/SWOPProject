package controllers;

import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.notifications.INotification;
import model.notifications.IRegistration;
import model.notifications.commands.RegisterForNotificationCommand;
import model.notifications.commands.UnregisterForNotificationCommand;
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
		return getBugTrap().getFormFactory().makeRegisterForNotificationForm();
	}

	public UnregisterNotificationForm getUnregisterNotificationForm() throws UnauthorizedAccessException{
		return getBugTrap().getFormFactory().makeUnregisterFromNotificationForm();
	}

	/**
	 * Registers for notifications
	 * @param form
	 * @throws UnauthorizedAccessException
	 */
	public void registerNotification(RegisterNotificationForm form) throws UnauthorizedAccessException {
		new RegisterForNotificationCommand(getBugTrap(), form).execute();
	}

	/**
	 * Unregisters for notifications
	 * @param form
	 * @throws UnauthorizedAccessException
	 */
	public void unregisterNotification(UnregisterNotificationForm form) throws UnauthorizedAccessException {
		new UnregisterForNotificationCommand(getBugTrap(), form).execute();
	}

	/**
	 * Returns a list of registrations
	 * @return the registrations of the logged in user
	 * @throws UnauthorizedAccessException
	 */
	public List<IRegistration> getRegistrations() throws UnauthorizedAccessException {
		return getBugTrap().getNotificationManager().getRegistrationsLoggedInUser();
	}

	/**
	 * Shows notifications with the information provided in the form.
	 * @param form ShowChronologicalNotificationForm containing all the details about the request for notifications.
	 * @throws UnauthorizedAccessException 
	 */
	public List<INotification> showNotifications(ShowChronologicalNotificationForm form) throws UnauthorizedAccessException{
		return getBugTrap().getNotificationManager().getNotifications(form.getNbOfNotifications());
	}
}
