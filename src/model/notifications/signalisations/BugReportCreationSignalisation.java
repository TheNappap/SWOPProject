package model.notifications.signalisations;

import model.bugreports.IBugReport;
import model.notifications.NotificationType;

public class BugReportCreationSignalisation extends Signalisation{

	public BugReportCreationSignalisation(IBugReport bugReport) {
		super(NotificationType.CREATE_BUGREPORT, bugReport);
	}

}
