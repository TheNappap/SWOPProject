package model.bugreports.bugtag;

/**
 * This class represents the Closed bug tag.
 * This means that the BugReport has been closed
 * and is no longer considered.
 */
public class Closed extends BugTag {

	public Closed() {
		super(new BugTagEnum[]{});
	}

	@Override
	public BugTagEnum getBugTagEnum() {
		return BugTagEnum.CLOSED;
	}

}
