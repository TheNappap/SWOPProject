
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
	
	public BugTagState confirmBugTag(BugTagState bugTag) {
		throw new IllegalStateException();
	}

	public BugReport 	getLinkedBugReport() {
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

	public boolean isAssigned() { return false; }
	public boolean isDuplicate() { return false; }
	public boolean isNotABug() { return false; }
	public boolean isResolved() { return false; }
	public boolean isUnderReview() { return false; }
}