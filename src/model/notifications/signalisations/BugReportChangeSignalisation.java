package model.notifications.signalisations;

import model.bugreports.IBugReport;
import model.notifications.RegistrationType;

public class BugReportChangeSignalisation extends Signalisation{

	public BugReportChangeSignalisation(IBugReport bugReport) {
		super(RegistrationType.BUGREPORT_CHANGE, bugReport);
	}

}
