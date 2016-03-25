package tests.bugreporttests;

import model.bugreports.BugReport;
import model.bugreports.bugtag.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BugTagTests {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void newTest() {
        BugTag tag = new New();
        assertTrue(tag.isNew());
        assertFalse(tag.isAssigned());
        assertFalse(tag.isClosed());
        assertFalse(tag.isDuplicate());
        assertFalse(tag.isInProgress());
        assertFalse(tag.isNotABug());
        assertFalse(tag.isResolved());
        assertFalse(tag.isUnderReview());
        assertTrue(tag.isSameTag(new New()));
    }

    @Test
    public void assignedTest() {
        BugTag tag = new Assigned();
        assertTrue(tag.isAssigned());
        assertFalse(tag.isNew());
        assertFalse(tag.isClosed());
        assertFalse(tag.isDuplicate());
        assertFalse(tag.isInProgress());
        assertFalse(tag.isNotABug());
        assertFalse(tag.isResolved());
        assertFalse(tag.isUnderReview());
        assertTrue(tag.isSameTag(new Assigned()));
    }

    @Test
    public void closedTest() {
        BugTag tag = new Closed();
        assertTrue(tag.isClosed());
        assertFalse(tag.isAssigned());
        assertFalse(tag.isNew());
        assertFalse(tag.isDuplicate());
        assertFalse(tag.isInProgress());
        assertFalse(tag.isNotABug());
        assertFalse(tag.isResolved());
        assertFalse(tag.isUnderReview());
        assertTrue(tag.isSameTag(new Closed()));
    }

    @Test
    public void duplicateTest() {
        BugReport report1 = new BugReport(null, null, null, null, null, null, null, null, null);
        BugReport report2 = new BugReport(null, null, null, null, null, null, null, null, null);

        BugTag tag = new Duplicate(report1);
        assertTrue(tag.isDuplicate());
        assertFalse(tag.isAssigned());
        assertFalse(tag.isClosed());
        assertFalse(tag.isNew());
        assertFalse(tag.isInProgress());
        assertFalse(tag.isNotABug());
        assertFalse(tag.isResolved());
        assertFalse(tag.isUnderReview());
        assertTrue(tag.isSameTag(new Duplicate(report2)));
    }

    @Test
    public void notABugTest() {
        BugTag tag = new NotABug();
        assertTrue(tag.isNotABug());
        assertFalse(tag.isAssigned());
        assertFalse(tag.isNew());
        assertFalse(tag.isDuplicate());
        assertFalse(tag.isInProgress());
        assertFalse(tag.isClosed());
        assertFalse(tag.isResolved());
        assertFalse(tag.isUnderReview());
        assertTrue(tag.isSameTag(new NotABug()));
    }

    @Test
    public void resolvedTest() {
        BugTag tag = new Resolved();
        assertTrue(tag.isResolved());
        assertFalse(tag.isAssigned());
        assertFalse(tag.isNew());
        assertFalse(tag.isDuplicate());
        assertFalse(tag.isInProgress());
        assertFalse(tag.isClosed());
        assertFalse(tag.isNotABug());
        assertFalse(tag.isUnderReview());
        assertTrue(tag.isSameTag(new Resolved()));
    }

    @Test
    public void underReviewTest() {
        BugTag tag = new UnderReview();
        assertTrue(tag.isUnderReview());
        assertFalse(tag.isAssigned());
        assertFalse(tag.isNew());
        assertFalse(tag.isDuplicate());
        assertFalse(tag.isInProgress());
        assertFalse(tag.isClosed());
        assertFalse(tag.isResolved());
        assertFalse(tag.isNotABug());
        assertTrue(tag.isSameTag(new UnderReview()));
    }
}
