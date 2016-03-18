package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the New bug tag.
 * This means that a BugReport has recently been
 * created and is not yet assigned a tag.
 */
public class New extends BugTag {

	public New() {
		super(new BugTagEnum[]{BugTagEnum.ASSIGNED, BugTagEnum.DUPLICATE, BugTagEnum.NOT_A_BUG});
	}

	@Override
	public BugTagEnum getBugTagEnum() {
		return BugTagEnum.NEW;
	}

	@Override
	public BugReport getDuplicate() {
		return null;
	}

}
