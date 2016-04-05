package model.bugreports.bugtag;


/**
 * This class represents the NotABug bug tag.
 */
public class NotABug extends Closed {

    @Override
    public boolean isNotABug() {
        return true;
    }

    @Override
    public BugTag getTag() {
        return BugTag.NOTABUG;
    }
}
