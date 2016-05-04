
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
	
	protected BugReport bugReport;	//BugReport this Tag belongs to.
	
	/**
	 * BugTagState Constructor
	 * @param bugReport BugReport this Tag belongs to.
	 */
	public BugTagState(BugReport bugReport) {
		this.bugReport = bugReport;
	}
	
	/**
	 * Confirm that this Tag may change in the given Tag
	 * @param bugTag Tag to conform
	 * @throws IllegalStateException if the Tag change is not allowed
	 * @return The given Tag.
	 */
	public BugTagState confirmBugTag(BugTagState bugTag) {
		throw new IllegalStateException();
	}
	
	/**
	 * Returns the multiplier of the bug tag state
	 * @return the multiplier of the bug tag state
	 */
	public abstract double getMultiplier();

	/**
	 * Get Enum representation of the Tag.
	 * @return Enum representation of the Tag.
	 */
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