package model.bugreports.bugtag;

import model.bugreports.BugReport;

public abstract class InProgress extends BugTagState {

	public InProgress(BugReport bugReport) {
		super(bugReport);
	}

	@Override
	public BugTagState confirmBugTag(BugTagState bugTag) {
		if (bugTag.isClosed() || bugTag.isInProgress()) return bugTag;
		
		throw new IllegalStateException();
	}

	@Override
	public boolean isInProgress() {
		return true;
	}
}