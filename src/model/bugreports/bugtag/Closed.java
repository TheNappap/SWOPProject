package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the Closed bug tag.
 * This means that the BugReport has been closed
 * and is no longer considered.
 */
public class Closed extends BugTag {

	static final String TAGSTRING = "CLOSED";

	@Override
	public boolean isClosed() {
		return true;
	}

	@Override
	protected String getTagString() {
		return TAGSTRING;
	}

	static BugTag fromStringChain(String tag, BugReport report) {
		if (tag.equals(TAGSTRING))
			return new Closed();

		return Duplicate.fromStringChain(tag, report);
	}
}