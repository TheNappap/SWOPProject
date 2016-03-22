package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the New bug tag.
 * This means that a BugReport has recently been
 * created and is not yet assigned a tag.
 */
public class New implements BugTag {

	@Override
	public BugReport getLinkedBugReport() {
		throw new IllegalStateException();
	}

	@Override
	public BugTag confirmBugTag(BugTag bugTag) {
		return bugTag;
	}

	@Override
	public boolean isNew() {
		return true;
	}

	@Override
	public boolean isInProgress() {
		return false;
	}

	@Override
	public boolean isClosed() {
		return false;
	}
	
	

}
