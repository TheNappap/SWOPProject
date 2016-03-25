package model.bugreports.bugtag;


import model.bugreports.BugReport;

/**
 * This class represents the Resolved bug tag.
 * This means that a BugReport has been fixed.
 */

public class Resolved extends Closed {

    static final String TAGSTRING = "RESOLVED";

    @Override
    public boolean isResolved() {
        return true;
    }

    @Override
    protected String getTagString() {
        return TAGSTRING;
    }

    static BugTag fromStringChain(String tag, BugReport report) {
        if (tag.equals(TAGSTRING))
            return new Resolved();

        return UnderReview.fromStringChain(tag, report);
    }
}
