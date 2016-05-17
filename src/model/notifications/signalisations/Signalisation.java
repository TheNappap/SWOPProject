package model.notifications.signalisations;

import model.bugreports.IBugReport;
import model.notifications.NotificationType;
import model.projects.ISystem;

/**
 * This class represents a signal that is signaled when something
 * happens in an observable and it notifies it observers.
 */
public class Signalisation {
	
	public Signalisation(NotificationType type, IBugReport bugReport){
		this.type = type;
		this.bugReport = bugReport;
		this.system = null;
	}

	public Signalisation(NotificationType type, ISystem system) {
		this.type = type;
		this.system = system;
		this.bugReport = null;
	}
	
	private final NotificationType type;
	private final IBugReport bugReport;
	private final ISystem system;

	public IBugReport getBugReport() {
		return bugReport;
	}

	public ISystem getSystem() { return system; }

	public NotificationType getType() {
		return type;
	}

}
