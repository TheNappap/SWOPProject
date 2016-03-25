package model.bugreports.bugtag;

import model.bugreports.BugReport;

/**
 * This class represents the UnderReview bug tag.
 * This means that a BugReport is currently under review
 * and will be given feedback soon.
 */
public class UnderReview extends InProgress {

    static final String TAGSTRING = "UNDERREVIEW";

    @Override
    public boolean isUnderReview() {
        return true;
    }

    @Override
    protected String getTagString() {
        return TAGSTRING;
    }

    static BugTag fromStringChain(String tag, BugReport report) {
        if (tag.equals(TAGSTRING))
            return new UnderReview();

        throw new IllegalArgumentException("Tag can not be parsed to a BugTag.");
    }
}