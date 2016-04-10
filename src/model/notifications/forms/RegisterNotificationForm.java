package model.notifications.forms;

import model.Form;
import model.bugreports.bugtag.BugTag;
import model.notifications.Observable;
import model.notifications.RegistrationType;

public class RegisterNotificationForm implements Form {

	private Observable observable;
	private RegistrationType registrationType;
	private BugTag tag;
	
	public Observable getObservable() {
		return observable;
	}
	
	public void setObservable(Observable observable) {
		if (observable == null) throw new NullPointerException("Observable may not be null.");

		this.observable = observable;
	}
	
	public RegistrationType getRegistrationType() {
		return registrationType;
	}
	
	public void setRegistrationType(RegistrationType registrationType) {
		if (registrationType == null) throw new NullPointerException("RegistrationType may not be null.");

		this.registrationType = registrationType;
	}

	public BugTag getTag() {
		return tag;
	}

	public void setTag(BugTag tag) {
		if (registrationType == RegistrationType.BUGREPORT_SPECIFIC_TAG && tag == null) throw new NullPointerException("BugTag may not be null.");

		this.tag = tag;
	}

	@Override
	public void allVarsFilledIn() {
		if (observable == null) throw new NullPointerException("Observable may not be null.");
		if (registrationType == null) throw new NullPointerException("RegistrationType may not be null.");
		if (registrationType == RegistrationType.BUGREPORT_SPECIFIC_TAG && tag == null) throw new NullPointerException("BugTag may not be null.");
	}
}
