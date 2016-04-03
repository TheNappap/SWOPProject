package model.notifications;

import model.bugreports.bugtag.BugTag;

public class BugReportSpecificTagObserver extends BugReportObserver {

	private BugTag bugTag;
	
	public BugReportSpecificTagObserver(Mailbox mailbox, BugTag bugTag) {
		super(mailbox);
		
		this.bugTag = bugTag;
	}

	@Override
	public void signal() {
			
	}
}
