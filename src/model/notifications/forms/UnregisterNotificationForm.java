package model.notifications.forms;

import model.Form;
import model.notifications.Observable;
import model.notifications.Registration;

public class UnregisterNotificationForm implements Form {

	private Observable observable;
	private Registration registration;
	
	public UnregisterNotificationForm() {
		
	}

	@Override
	public void allVarsFilledIn() {
		throw new UnsupportedOperationException();
	}

	public Observable getObservable() {
		throw new UnsupportedOperationException();
	}

	public void setObservable(Observable observable) {
		throw new UnsupportedOperationException();
	}

	public Registration getRegistration() {
		throw new UnsupportedOperationException();
	}

	public void setRegistration(Registration registration) {
		throw new UnsupportedOperationException();
	}

}
