package tests.bugreporttests;

import model.bugreports.BugReport;
import model.bugreports.bugtag.BugTag;
import model.bugreports.bugtag.BugTagState;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BugTagTests {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void newTest() {
        BugTagState tag = BugTag.NEW.createState();
        assertTrue(tag.isNew());
        assertFalse(tag.isAssigned());
        assertFalse(tag.isClosed());
        assertFalse(tag.isDuplicate());
        assertFalse(tag.isInProgress());
        assertFalse(tag.isNotABug());
        assertFalse(tag.isResolved());
        assertFalse(tag.isUnderReview());
        assertEquals(tag.getTag(), BugTag.NEW);
    }

    @Test
    public void assignedTest() {
        BugTagState tag = BugTag.ASSIGNED.createState();
        assertTrue(tag.isAssigned());
        assertFalse(tag.isNew());
        assertFalse(tag.isClosed());
        assertFalse(tag.isDuplicate());
        assertTrue(tag.isInProgress());
        assertFalse(tag.isNotABug());
        assertFalse(tag.isResolved());
        assertFalse(tag.isUnderReview());
        assertEquals(tag.getTag(), BugTag.ASSIGNED);
    }

    @Test
    public void closedTest() {
        BugTagState tag = BugTag.CLOSED.createState();
        assertTrue(tag.isClosed());
        assertFalse(tag.isAssigned());
        assertFalse(tag.isNew());
        assertFalse(tag.isDuplicate());
        assertFalse(tag.isInProgress());
        assertFalse(tag.isNotABug());
        assertFalse(tag.isResolved());
        assertFalse(tag.isUnderReview());
        assertEquals(tag.getTag(), BugTag.CLOSED);
    }

    @Test
    public void duplicateTest() {
        BugReport report1 = new BugReport(null, null, null, null, null, null, null, null, null, null);
        BugReport report2 = new BugReport(null, null, null, null, null, null, null, null, null, null);

        BugTagState tag = BugTag.DUPLICATE.createState();
        assertTrue(tag.isDuplicate());
        assertFalse(tag.isAssigned());
        assertTrue(tag.isClosed());
        assertFalse(tag.isNew());
        assertFalse(tag.isInProgress());
        assertFalse(tag.isNotABug());
        assertFalse(tag.isResolved());
        assertFalse(tag.isUnderReview());
        assertEquals(tag.getTag(), BugTag.DUPLICATE);
    }

    @Test
    public void notABugTest() {
        BugTagState tag = BugTag.NOTABUG.createState();
        assertTrue(tag.isNotABug());
        assertFalse(tag.isAssigned());
        assertFalse(tag.isNew());
        assertFalse(tag.isDuplicate());
        assertFalse(tag.isInProgress());
        assertTrue(tag.isClosed());
        assertFalse(tag.isResolved());
        assertFalse(tag.isUnderReview());
        assertEquals(tag.getTag(), BugTag.NOTABUG);
    }

    @Test
    public void resolvedTest() {
        BugTagState tag = BugTag.RESOLVED.createState();
        assertTrue(tag.isResolved());
        assertFalse(tag.isAssigned());
        assertFalse(tag.isNew());
        assertFalse(tag.isDuplicate());
        assertFalse(tag.isInProgress());
        assertTrue(tag.isClosed());
        assertFalse(tag.isNotABug());
        assertFalse(tag.isUnderReview());
        assertEquals(tag.getTag(), BugTag.RESOLVED);
    }

    @Test
    public void underReviewTest() {
        BugTagState tag = BugTag.UNDERREVIEW.createState();
        assertTrue(tag.isUnderReview());
        assertFalse(tag.isAssigned());
        assertFalse(tag.isNew());
        assertFalse(tag.isDuplicate());
        assertTrue(tag.isInProgress());
        assertFalse(tag.isClosed());
        assertFalse(tag.isResolved());
        assertFalse(tag.isNotABug());
        assertEquals(tag.getTag(), BugTag.UNDERREVIEW);
    }
}
