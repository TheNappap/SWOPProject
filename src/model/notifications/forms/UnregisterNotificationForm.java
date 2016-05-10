package model.notifications.forms;

import model.Form;
import model.notifications.IRegistration;
import model.notifications.NotificationType;
import model.notifications.Observable;
import model.notifications.observers.ObserverWithMailbox;

public class UnregisterNotificationForm implements Form {

	private IRegistration registration;
	
	public UnregisterNotificationForm() {
		
	}

	public IRegistration getRegistration() {
		return registration;
	}

	public void setRegistration(IRegistration registration) {
		if (registration == null)
			throw new IllegalArgumentException("Registration can not be null.");

		this.registration = registration;
	}

	@Override
	public void allVarsFilledIn() {
		if (registration == null) throw new NullPointerException("Registration can not be null.");
	}
}
