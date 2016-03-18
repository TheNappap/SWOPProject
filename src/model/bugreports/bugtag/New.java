package model.bugreports.bugtag;

import model.bugreports.BugReport;

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
