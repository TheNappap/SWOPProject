package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the Resolved bug tag.
 * This means that a BugReport has been fixed.
 */

public class Resolved extends Closed {

    public Resolved(BugReport bugReport) {
		super(bugReport);
	}

	@Override
    public BugTag getTag() {
        return BugTag.RESOLVED;
    }
}
