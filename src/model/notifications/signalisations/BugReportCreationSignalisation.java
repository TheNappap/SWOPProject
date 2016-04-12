package model.notifications.signalisations;

import model.bugreports.IBugReport;
import model.notifications.RegistrationType;

public class BugReportCreationSignalisation extends Signalisation{

	public BugReportCreationSignalisation(IBugReport bugReport) {
		super(RegistrationType.CREATE_BUGREPORT, bugReport);
	}

}
