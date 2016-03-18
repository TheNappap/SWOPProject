package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the NotABug bug tag.
 * This means that a BugReport is not considered
 * a bug and thus will not be fixed.
 */
public class NotABug extends BugTag {

	public NotABug() {
		super(new BugTagEnum[]{});
	}

	@Override
	public BugTagEnum getBugTagEnum() {
		return BugTagEnum.NOT_A_BUG;
	}

	@Override
	public BugReport getDuplicate() {
		return null;
	}

}
