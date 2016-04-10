package model.notifications.forms;

import model.Form;
import model.notifications.Observable;
import model.notifications.Registration;
import model.notifications.RegistrationType;

public class UnregisterNotificationForm implements Form {

	private Registration registration;
	
	public UnregisterNotificationForm() {
		
	}

	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		if (registration == null) throw new NullPointerException("Registration may not be null.");

		this.registration = registration;
	}

	@Override
	public void allVarsFilledIn() {
		if (registration == null) throw new NullPointerException("Registration may not be null.");
	}
}
