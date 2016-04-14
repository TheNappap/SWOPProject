package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the Assigned bug tag.
 * This means that a BugReport has been assigned to a
 * developer to be resolved.
 */
public class Assigned extends InProgress {

    public Assigned(BugReport bugReport) {
		super(bugReport);
		// TODO Auto-generated constructor stub
	}

	@Override
    public BugTag getTag() {
        return BugTag.ASSIGNED;
    }
}