package model.notifications;

import model.bugreports.BugReportManager;

public class CreateBugReportObserver extends Observer {

	public CreateBugReportObserver(Mailbox mailbox, BugReportManager observable) {
		super(mailbox, observable);
	}

	@Override
	public void signal(String notificationText) {
		this.getMailbox().addNotification(notificationText);
	}

}
