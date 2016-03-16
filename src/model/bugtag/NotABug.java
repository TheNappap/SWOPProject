package model.bugtag;

public class NotABug extends BugTag {

	public NotABug() {
		super(new BugTagEnum[]{});
	}

	@Override
	public BugTagEnum getBugTagEnum() {
		return BugTagEnum.NOT_A_BUG;
	}

}
