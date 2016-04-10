package model.notifications;

import model.bugreports.BugReport;
import model.bugreports.IBugReport;

public class BugReportObserver extends Observer {

	public BugReportObserver(Mailbox mailbox, Observable bugReport) {
		super(mailbox, bugReport);
	}

	@Override
	public void signal(String notificationText) {
		this.getMailbox().addNotification(notificationText);
	}

	@Override
	public boolean isBugReportObserver() {
		return true;
	}
}
