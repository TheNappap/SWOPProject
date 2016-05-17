package model.notifications.signalisations;

import model.bugreports.IBugReport;
import model.notifications.NotificationType;

/**
 * This class represents a signal that is signaled when something
 * happens in an observable and it notifies it observers.
 */
public class Signalisation {
	
	public Signalisation(NotificationType type, IBugReport bugReport){
		this.type = type;
		this.bugReport = bugReport;
	}
	
	private final NotificationType type;
	private final IBugReport bugReport;

	public IBugReport getBugReport() {
		return bugReport;
	}

	public NotificationType getType() {
		return type;
	}

}
