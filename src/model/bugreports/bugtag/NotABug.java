package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the NotABug bug tag.
 */
public class NotABug extends Closed {

    public NotABug(BugReport bugReport) {
		super(bugReport);
		// TODO Auto-generated constructor stub
	}

	@Override
    public BugTag getTag() {
        return BugTag.NOTABUG;
    }
}
