package model.notifications.signalisations;

import model.bugreports.IBugReport;
import model.notifications.RegistrationType;

/**
 * This class represents a signal that is signaled when something
 * happens in an observable and it notifies it observers.
 */
public abstract class Signalisation {
	
	public Signalisation(RegistrationType type, IBugReport bugReport){
		this.type = type;
		this.bugReport = bugReport;
	}
	
	private final RegistrationType type;
	private final IBugReport bugReport;

	public IBugReport getBugReport() {
		return bugReport;
	}

	public RegistrationType getType() {
		return type;
	}

}
