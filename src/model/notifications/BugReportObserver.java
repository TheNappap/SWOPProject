package model.notifications;

import model.bugreports.IBugReport;

public class BugReportObserver extends Observer {

	protected IBugReport report;

	public BugReportObserver(Mailbox mailbox, IBugReport bugReport) {
		super(mailbox, bugReport);
		this.report = bugReport;
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
