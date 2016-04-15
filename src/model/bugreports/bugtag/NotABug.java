package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the NotABug bug tag.
 */
public class NotABug extends Closed {

    public NotABug(BugReport bugReport) {
		super(bugReport);
	}

	@Override
    public BugTag getTag() {
        return BugTag.NOTABUG;
    }
}
