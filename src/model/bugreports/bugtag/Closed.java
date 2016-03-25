package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the Closed bug tag.
 * This means that the BugReport has been closed
 * and is no longer considered.
 */
public class Closed implements BugTag {

	@Override
	public BugReport getLinkedBugReport() {
		throw new IllegalStateException();
	}

	@Override
	public BugTag confirmBugTag(BugTag bugTag) {
		throw new IllegalStateException();
	}

	@Override
	public boolean isNew() {
		return false;
	}

	@Override
	public boolean isInProgress() {
		return false;
	}

	@Override
	public boolean isClosed() {
		return true;
	}

}
