package model.bugreports.bugtag;

import model.bugreports.BugReport;

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
