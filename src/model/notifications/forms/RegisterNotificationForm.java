package model.notifications.forms;

import model.Form;
import model.bugreports.bugtag.BugTag;
import model.notifications.NotificationType;
import model.notifications.Observable;

public class RegisterNotificationForm implements Form {

	private Observable observable;
	private NotificationType notificationType;
	private BugTag tag;
	
	public Observable getObservable() {
		return observable;
	}
	
	public void setObservable(Observable observable) {
		if (observable == null) throw new IllegalArgumentException("Observable may not be null.");

		this.observable = observable;
	}
	
	public NotificationType getRegistrationType() {
		return notificationType;
	}
	
	public void setNotificationType(NotificationType notificationType) {
		if (notificationType == null) throw new IllegalArgumentException("RegistrationType may not be null.");

		this.notificationType = notificationType;
	}

	public BugTag getTag() {
		return tag;
	}

	public void setTag(BugTag tag) {
		if (notificationType == NotificationType.BUGREPORT_SPECIFIC_TAG && tag == null) throw new NullPointerException("BugTag may not be null.");

		this.tag = tag;
	}

	@Override
	public void allVarsFilledIn() {
		if (observable == null) throw new NullPointerException("Observable may not be null.");
		if (notificationType == null) throw new NullPointerException("RegistrationType may not be null.");
		if (notificationType == NotificationType.BUGREPORT_SPECIFIC_TAG && tag == null) throw new NullPointerException("BugTag may not be null.");
	}
}
