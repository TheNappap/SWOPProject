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
		this.observable = observable;
	}
	
	public Registration getRegistration() {
		return registration;
	}
	
	public void setRegistration(Registration registration) {
		this.registration = registration;
	}
	
	@Override
	public void allVarsFilledIn() {
		throw new UnsupportedOperationException();
	}

}
