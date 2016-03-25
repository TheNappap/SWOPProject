package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the Duplicate bug tag.
 * This means that a BugReport is a duplicate from
 * another BugReport.
 */
public class Duplicate extends Closed {

	static final String TAGSTRING = "DUPLICATE";

	private BugReport duplicate;
	
	public Duplicate(BugReport duplicate) {
		this.duplicate = duplicate;
	}

	@Override
	public BugReport getLinkedBugReport() {
		return duplicate;
	}

	@Override
	public boolean isDuplicate() {
		return true;
	}

	@Override
	protected String getTagString() {
		return TAGSTRING;
	}

	static BugTag fromStringChain(String tag, BugReport report) {
		if (tag.equals(TAGSTRING))
			return new Duplicate(report);

		return New.fromStringChain(tag, report);
	}
}
