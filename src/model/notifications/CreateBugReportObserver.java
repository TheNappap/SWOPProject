package model.notifications;

import model.bugreports.BugReportManager;
import model.projects.ISystem;

public class CreateBugReportObserver extends Observer {

	public CreateBugReportObserver(Mailbox mailbox, Observable observable) {
		super(mailbox, observable);
	}

	@Override
	public void signal(String notificationText) {
		this.getMailbox().addNotification(notificationText);
	}

}
