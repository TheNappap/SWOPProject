package model.notifications.forms;

import model.Form;
import model.notifications.Observable;
import model.notifications.Registration;

public class RegisterNotificationForm implements Form {

	private Observable observable;
	private Registration registration;
	
	public Observable getObservable() {
		return observable;
	}
	
	public void setObservable(Observable observable) {
		if (observable == null) throw new NullPointerException("Observable may not be null.");

		this.observable = observable;
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
		if (observable == null) throw new NullPointerException("Observable may not be null.");
		if (registration == null) throw new NullPointerException("Registration may not be null.");
	}

}
