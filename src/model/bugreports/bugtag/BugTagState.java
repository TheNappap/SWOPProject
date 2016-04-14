
package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * Class representing a BugTagState.
 * BugTags are assigned to BugReports to indicate their
 * current status.
 * THIS CLASS SHOULD NOT BE PUBLIC. This state class is
 * only used internally, however due to testing it had to be public.
 */
public abstract class BugTagState {
	
	protected BugReport bugReport;
	
	public BugTagState(BugReport bugReport) {
		this.bugReport = bugReport;
	}
	
	public BugTagState confirmBugTag(BugTagState bugTag) {
		throw new IllegalStateException();
	}

	public abstract BugTag getTag();

	public boolean isNew() {
		return false;
	}
	public boolean isInProgress() {
		return false;
	}
	public boolean isClosed() {
		return false;
	}
}