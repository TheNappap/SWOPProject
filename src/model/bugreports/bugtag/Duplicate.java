package model.bugreports.bugtag;

/**
 * This class represents the Duplicate bug tag.
 * This means that a BugReport is a duplicate from
 * another BugReport.
 */
public class Duplicate extends Closed {

	public Duplicate() {
	}

	@Override
	public BugTag getTag() {
		return BugTag.DUPLICATE;
	}
}