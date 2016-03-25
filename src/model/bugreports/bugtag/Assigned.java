package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the Assigned bug tag.
 * This means that a BugReport has been assigned to a
 * developer to be resolved.
 */
public class Assigned extends InProgress {

    static final String TAGSTRING = "ASSIGNED";

    @Override
    public boolean isAssigned() {
        return true;
    }

    @Override
    protected String getTagString() {
        return TAGSTRING;
    }

    static BugTag fromStringChain(String tag, BugReport report) {
        if (tag.equals(TAGSTRING))
            return new Assigned();

        return Closed.fromStringChain(tag, report);
    }
}
