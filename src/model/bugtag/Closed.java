package model.bugtag;

public class Closed extends BugTag {

	public Closed() {
		super(new BugTagEnum[]{});
	}

	@Override
	public BugTagEnum getBugTagEnum() {
		return BugTagEnum.CLOSED;
	}

}
