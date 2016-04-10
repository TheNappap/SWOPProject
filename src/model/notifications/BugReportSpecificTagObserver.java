package model.notifications;

import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;

public class BugReportSpecificTagObserver extends BugReportObserver {

	private BugTag bugTag;
	
	public BugReportSpecificTagObserver(Mailbox mailbox, Observable bugReport, BugTag bugTag) {
		super(mailbox, bugReport);
		
		this.bugTag = bugTag;
	}
	
	@Override
	public void signal(String notificationText) {
		this.getMailbox().addNotification(notificationText);
	}
}
