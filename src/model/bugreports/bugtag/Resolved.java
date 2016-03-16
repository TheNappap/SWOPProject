package model.bugreports.bugtag;

public class Resolved extends BugTag {

	public Resolved() {
		super(new BugTagEnum[]{BugTagEnum.CLOSED, BugTagEnum.NOT_A_BUG, BugTagEnum.DUPLICATE});
	}

	@Override
	public BugTagEnum getBugTagEnum() {
		return BugTagEnum.RESOLVED;
	}

}
