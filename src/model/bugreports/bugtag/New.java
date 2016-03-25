package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the New bug tag.
 * This means that a BugReport has recently been
 * created and is not yet assigned a tag.
 */
public class New extends BugTag {

	static final String TAGSTRING = "NEW";

	@Override
	public BugTag confirmBugTag(BugTag bugTag) {
		return bugTag;
	}

	@Override
	public boolean isNew() {
		return true;
	}

	@Override
	protected String getTagString() {
		return TAGSTRING;
	}

	static BugTag fromStringChain(String tag, BugReport report) {
		if (tag.equals(TAGSTRING))
			return new New();

		return NotABug.fromStringChain(tag, report);
	}
}
