package model.notifications.signalisations;

import model.bugreports.IBugReport;
import model.notifications.NotificationType;

public class BugReportChangeSignalisation extends Signalisation{

	public BugReportChangeSignalisation(IBugReport bugReport) {
		super(NotificationType.BUGREPORT_CHANGE, bugReport);
	}

}
