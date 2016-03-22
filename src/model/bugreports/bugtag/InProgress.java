package model.bugreports.bugtag;

import model.bugreports.BugReport;

public abstract class InProgress implements BugTag {

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
	public boolean isNew() {
		return false;
	}
	
	@Override
	public boolean isInProgress() {
		return true;
	}
	
	@Override
	public boolean isClosed() {
		return false;
	}
}
