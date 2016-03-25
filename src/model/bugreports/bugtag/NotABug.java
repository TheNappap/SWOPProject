package model.bugreports.bugtag;


import model.bugreports.BugReport;


/**
 * This class represents the NotABug bug tag.
 */
public class NotABug extends Closed {

    static final String TAGSTRING = "NOTABUG";

    @Override
    public boolean isNotABug() {
        return true;
    }

    @Override
    protected String getTagString() {
        return TAGSTRING;
    }

    static BugTag fromStringChain(String tag, BugReport report) {
        if (tag.equals(TAGSTRING))
            return new NotABug();

        return Resolved.fromStringChain(tag, report);
    }
}
