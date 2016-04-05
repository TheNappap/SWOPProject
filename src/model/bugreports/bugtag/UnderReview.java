package model.bugreports.bugtag;

/**
 * This class represents the UnderReview bug tag.
 * This means that a BugReport is currently under review
 * and will be given feedback soon.
 */
public class UnderReview extends InProgress {

    @Override
    public boolean isUnderReview() {
        return true;
    }

    @Override
    public BugTag getTag() {
        return BugTag.UNDERREVIEW;
    }
}