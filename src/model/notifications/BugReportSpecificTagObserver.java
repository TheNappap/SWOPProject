package model.notifications;

import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;

public class BugReportSpecificTagObserver extends BugReportObserver {

	private BugTag bugTag;
	private IBugReport report;
	
	public BugReportSpecificTagObserver(Mailbox mailbox, IBugReport bugReport, BugTag bugTag) {
		super(mailbox, bugReport);
		
		this.bugTag = bugTag;
		this.report = bugReport;
	}
	
	@Override
	public void signal(String notificationText) {
		if (this.report.getBugTag() == this.bugTag)
			this.getMailbox().addNotification(notificationText);
	}
}
