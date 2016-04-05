package model.notifications;

import model.bugreports.IBugReport;

public class CreateBugReportObserver extends Observer {

	public CreateBugReportObserver(Mailbox mailbox) {
		super(mailbox);
	}

	@Override
	public void signalBugReportCreation(IBugReport report) {

	}

}
