package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the Assigned bug tag.
 * This means that a BugReport has been assigned to a
 * developer to be resolved.
 */
public class Assigned extends BugTag {
	
	public Assigned() {
		super(new BugTagEnum[]{BugTagEnum.UNDER_REVIEW, BugTagEnum.NOT_A_BUG, BugTagEnum.DUPLICATE});
	}
	
	protected Assigned(Assigned other) {
		super(other);
	}
	
	public Assigned copy() {
		return new Assigned(this);
	}

	@Override
	public BugTagEnum getBugTagEnum() {
		return BugTagEnum.ASSIGNED;
	}

	@Override
	public BugReport getDuplicate() {
		return null;
	}

}
