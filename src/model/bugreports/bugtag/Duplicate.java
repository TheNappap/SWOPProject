package model.bugreports.bugtag;

public class Duplicate extends BugTag {

	public Duplicate() {
		super(new BugTagEnum[]{});
	}

	@Override
	public BugTagEnum getBugTagEnum() {
		return BugTagEnum.DUPLICATE;
	}

}
