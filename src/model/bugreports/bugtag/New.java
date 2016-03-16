package model.bugreports.bugtag;

public class New extends BugTag {

	public New() {
		super(new BugTagEnum[]{BugTagEnum.ASSIGNED, BugTagEnum.DUPLICATE, BugTagEnum.NOT_A_BUG});
	}

	@Override
	public BugTagEnum getBugTagEnum() {
		return BugTagEnum.NEW;
	}

}
