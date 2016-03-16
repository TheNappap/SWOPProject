package model.bugreports.bugtag;

public class Assigned extends BugTag {
	
	public Assigned() {
		super(new BugTagEnum[]{BugTagEnum.UNDER_REVIEW, BugTagEnum.NOT_A_BUG, BugTagEnum.DUPLICATE});
	}

	@Override
	public BugTagEnum getBugTagEnum() {
		return BugTagEnum.ASSIGNED;
	}

}
