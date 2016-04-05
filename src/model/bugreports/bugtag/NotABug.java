package model.bugreports.bugtag;


/**
 * This class represents the NotABug bug tag.
 */
public class NotABug extends Closed {

    @Override
    public BugTag getTag() {
        return BugTag.NOTABUG;
    }
}
