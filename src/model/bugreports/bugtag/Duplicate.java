package model.bugreports.bugtag;

import model.bugreports.BugReport;

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
