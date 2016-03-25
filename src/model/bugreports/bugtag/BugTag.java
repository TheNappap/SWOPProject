package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * Class representing a BugTag.
 * BugTags are assigned to BugReports to indicate their
 * current status.
 */
public interface BugTag {
	
	public abstract BugTag		confirmBugTag(BugTag bugTag);
	public abstract BugReport 	getLinkedBugReport();
	
	public abstract boolean isNew();
	public abstract boolean isInProgress();
	public abstract boolean isClosed();

}