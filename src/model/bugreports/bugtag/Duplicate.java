package model.bugreports.bugtag;

/**
 * This class represents the Duplicate bug tag.
 * This means that a BugReport is a duplicate from
 * another BugReport.
 */
public class Duplicate extends BugTag {

	public Duplicate() {
		super(new BugTagEnum[]{});
	}

	@Override
	public BugTagEnum getBugTagEnum() {
		return BugTagEnum.DUPLICATE;
	}

}
