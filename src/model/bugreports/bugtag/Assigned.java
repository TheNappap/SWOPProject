package model.bugreports.bugtag;

/**
 * This class represents the Assigned bug tag.
 * This means that a BugReport has been assigned to a
 * developer to be resolved.
 */
public class Assigned extends InProgress {

    @Override
    public BugTag getTag() {
        return BugTag.ASSIGNED;
    }
}