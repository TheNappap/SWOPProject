package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the Closed bug tag.
 * This means that the BugReport has been closed
 * and is no longer considered.
 */
public class Closed extends BugTagState {

	public Closed(BugReport bugReport) {
		super(bugReport);
	}

	@Override
	public boolean isClosed() {
		return true;
	}

	@Override
	public BugTag getTag() {
		return BugTag.CLOSED;
	}

	@Override
	public double getMultiplier() {
		return 0;
	}
}