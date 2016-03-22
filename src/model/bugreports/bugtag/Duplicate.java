package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the Duplicate bug tag.
 * This means that a BugReport is a duplicate from
 * another BugReport.
 */
public class Duplicate extends Closed {

	private BugReport duplicate;
	
	public Duplicate(BugReport duplicate) {
		this.duplicate = duplicate;
	}

	@Override
	public BugReport getLinkedBugReport() {
		return duplicate;
	}

}
