package model.bugreports.bugtag;

/**
 * This class represents the Closed bug tag.
 * This means that the BugReport has been closed
 * and is no longer considered.
 */
public class Closed extends BugTagState {

	@Override
	public boolean isClosed() {
		return true;
	}

	@Override
	public BugTag getTag() {
		return BugTag.CLOSED;
	}
}