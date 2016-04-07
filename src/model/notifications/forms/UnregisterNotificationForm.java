package model.notifications.forms;

import model.Form;
import model.notifications.Observer;

public class UnregisterNotificationForm implements Form {

	private Observer observer;
	
	public UnregisterNotificationForm() {
		
	}
	
	public Observer getObserver() {
		return observer;
	}

	public void setObserver(Observer observer) {
		this.observer = observer;
	}

	@Override
	public void allVarsFilledIn() {
		throw new UnsupportedOperationException();
	}

}
