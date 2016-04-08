package model.notifications;

import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;

public class BugReportSpecificTagObserver extends BugReportObserver {

	private BugTag bugTag;
	
	public BugReportSpecificTagObserver(Mailbox mailbox, IBugReport bugReport, BugTag bugTag) {
		super(mailbox, bugReport);
		
		this.bugTag = bugTag;
	}
	
	@Override
	public void signal() {
		throw new UnsupportedOperationException();
	}
}
