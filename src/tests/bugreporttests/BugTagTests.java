package tests.bugreporttests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.bugreports.BugReport;
import model.bugreports.bugtag.BugTag;
import model.bugreports.bugtag.BugTagState;

public class BugTagTests {

	private BugReport bugReport;
	
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void newTest() {
    	bugReport = new BugReport(null, null, null, null, null, null, null, null, null, null, BugTag.NEW, null, null, null, null, null, null, 5);
        BugTagState tag = bugReport.getBugTagState();
        assertTrue(tag.isNew());
        assertFalse(tag.isClosed());
        assertFalse(tag.isInProgress());
        assertEquals(tag.getTag(), BugTag.NEW);
    }

    @Test
    public void assignedTest() {
    	bugReport = new BugReport(null, null, null, null, null, null, null, null, null, null, BugTag.ASSIGNED, null, null, null, null, null, null, 4);
    	BugTagState tag = bugReport.getBugTagState();
        assertFalse(tag.isNew());
        assertFalse(tag.isClosed());
        assertTrue(tag.isInProgress());
        assertEquals(tag.getTag(), BugTag.ASSIGNED);
    }

    @Test
    public void closedTest() {
    	bugReport = new BugReport(null, null, null, null, null, null, null, null, null, null, BugTag.CLOSED, null, null, null, null, null, null, 6);
    	BugTagState tag = bugReport.getBugTagState();
        assertTrue(tag.isClosed());
        assertFalse(tag.isNew());
        assertFalse(tag.isInProgress());
        assertEquals(tag.getTag(), BugTag.CLOSED);
    }

    @Test
    public void duplicateTest() {
    	bugReport = new BugReport(null, null, null, null, null, null, null, null, null, null, BugTag.DUPLICATE, null, null, null, null, null, null, 2);
    	BugTagState tag = bugReport.getBugTagState();
        assertTrue(tag.isClosed());
        assertFalse(tag.isNew());
        assertFalse(tag.isInProgress());
        assertEquals(tag.getTag(), BugTag.DUPLICATE);
    }

    @Test
    public void notABugTest() {
    	bugReport = new BugReport(null, null, null, null, null, null, null, null, null, null, BugTag.NOTABUG, null, null, null, null, null, null, 6);
    	BugTagState tag = bugReport.getBugTagState();
        assertFalse(tag.isNew());
        assertFalse(tag.isInProgress());
        assertTrue(tag.isClosed());
        assertEquals(tag.getTag(), BugTag.NOTABUG);
    }

    @Test
    public void resolvedTest() {
    	bugReport = new BugReport(null, null, null, null, null, null, null, null, null, null, BugTag.RESOLVED, null, null, null, null, null, null, 5);
    	BugTagState tag = bugReport.getBugTagState();
        assertFalse(tag.isNew());
        assertFalse(tag.isInProgress());
        assertTrue(tag.isClosed());
        assertEquals(tag.getTag(), BugTag.RESOLVED);
    }

    @Test
    public void underReviewTest() {
    	bugReport = new BugReport(null, null, null, null, null, null, null, null, null, null, BugTag.UNDERREVIEW, null, null, null, null, null, null, 9);
    	BugTagState tag = bugReport.getBugTagState();
        assertFalse(tag.isNew());
        assertTrue(tag.isInProgress());
        assertFalse(tag.isClosed());
        assertEquals(tag.getTag(), BugTag.UNDERREVIEW);
    }
}
