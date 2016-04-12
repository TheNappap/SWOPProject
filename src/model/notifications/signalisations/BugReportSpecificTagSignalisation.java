package model.notifications.signalisations;

import model.bugreports.IBugReport;
import model.notifications.RegistrationType;

public class BugReportSpecificTagSignalisation extends Signalisation{

	public BugReportSpecificTagSignalisation(IBugReport bugReport) {
		super(RegistrationType.BUGREPORT_SPECIFIC_TAG, bugReport);
	}

}
