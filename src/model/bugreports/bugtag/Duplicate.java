package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the Duplicate bug tag.
 * This means that a BugReport is a duplicate from
 * another BugReport.
 */
public class Duplicate extends Closed {


	public Duplicate(BugReport bugReport) {
		super(bugReport);
	}

	@Override
	public BugTag getTag() {
		return BugTag.DUPLICATE;
	}
}