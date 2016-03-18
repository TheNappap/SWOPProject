package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the Duplicate bug tag.
 * This means that a BugReport is a duplicate from
 * another BugReport.
 */
public class Duplicate extends BugTag {

	private BugReport duplicate;
	
	public Duplicate() {
		super(new BugTagEnum[]{});
	}

	@Override
	public BugTagEnum getBugTagEnum() {
		return BugTagEnum.DUPLICATE;
	}

	@Override
	public BugReport getDuplicate() {
		return duplicate;
	}

}
