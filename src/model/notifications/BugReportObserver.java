package model.notifications;

import model.bugreports.BugReport;

public class BugReportObserver extends Observer {

	public BugReportObserver(Mailbox mailbox, BugReport bugReport) {
		super(mailbox, bugReport);
	}

	@Override
	public void signal() {
		throw new UnsupportedOperationException();
	}
}
