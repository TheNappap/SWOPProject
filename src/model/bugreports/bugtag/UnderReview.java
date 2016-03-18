package model.bugreports.bugtag;

/**
 * This class represents the UnderReview bug tag.
 * This means that a BugReport is currently under review
 * and will be given feedback soon.
 */
public class UnderReview extends BugTag {

	public UnderReview() {
		super(new BugTagEnum[]{BugTagEnum.ASSIGNED, BugTagEnum.RESOLVED, BugTagEnum.CLOSED, BugTagEnum.DUPLICATE, BugTagEnum.NOT_A_BUG});
	}

	@Override
	public BugTagEnum getBugTagEnum() {
		return BugTagEnum.UNDER_REVIEW;
	}

}
