package model.notifications;

import model.bugreports.IBugReport;

public class BugReportObserver extends Observer {

	public BugReportObserver(Mailbox mailbox) {
		super(mailbox);
	}

	@Override
	public void signal(IBugReport report) {
		
	}
}
