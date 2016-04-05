package model.bugreports.bugtag;

/**
 * This class represents the New bug tag.
 * This means that a BugReport has recently been
 * created and is not yet assigned a tag.
 */
public class New extends BugTagState {

	@Override
	public BugTagState confirmBugTag(BugTagState bugTag) {
		return bugTag;
	}

	@Override
	public boolean isNew() {
		return true;
	}

	@Override
	public BugTag getTag() {
		return BugTag.NEW;
	}
}