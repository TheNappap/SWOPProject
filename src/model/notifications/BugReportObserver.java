package model.notifications;

import model.bugreports.IBugReport;

public class BugReportObserver extends Observer {

	public BugReportObserver(Mailbox mailbox, IBugReport bugReport) {
		super(mailbox, bugReport);
	}

	@Override
	public void signal() {
		throw new UnsupportedOperationException();
	}
}
