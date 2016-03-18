package model.bugreports.bugtag;

import model.bugreports.BugReport;

public class Closed extends BugTag {

	public Closed() {
		super(new BugTagEnum[]{});
	}

	@Override
	public BugTagEnum getBugTagEnum() {
		return BugTagEnum.CLOSED;
	}

	@Override
	public BugReport getDuplicate() {
		return null;
	}

}
