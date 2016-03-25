package model.bugreports.bugtag;

import model.bugreports.BugReport;

public abstract class InProgress extends BugTag {

	@Override
	public BugReport getLinkedBugReport() {
		throw new IllegalStateException();
	}
	
	@Override
	public BugTag confirmBugTag(BugTag bugTag) {
		if (bugTag.isClosed() || bugTag.isInProgress()) return bugTag;
		
		throw new IllegalStateException();
	}

	@Override
	public boolean isInProgress() {
		return true;
	}
}