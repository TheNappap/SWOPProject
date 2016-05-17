package tests.bugreporttests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.BugReport;
import model.bugreports.IBugReport;
import model.bugreports.Patch;
import model.bugreports.TargetMilestone;
import model.bugreports.bugtag.BugTag;
import model.bugreports.bugtag.BugTagState;
import model.bugreports.comments.Comment;
import model.notifications.observers.Observer;
import model.projects.ISubsystem;
import model.users.IUser;
import model.users.Issuer;
import tests.BugTrapTest;

public class BugTagTests extends BugTrapTest {

	private BugReport bugReport;

    @Before
    public void setUp() {
        super.setUp();
        String title = "BugReport";
        String description = "Awesome BugReport";
        ISubsystem subsystem = excel;
        List<IBugReport> dependsOn = new ArrayList<>();
        List<IUser> assignees = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        List<Observer> observers = new ArrayList<>();
        IUser issuedBy = new Issuer(null, null, null, null);
        Date creationDate = new Date();
        BugTag bugTag = BugTag.NEW;
        String stackTrace = "stack";
        String errorMessage = "error";
        String reproduction = "reproduction";
        TargetMilestone targetMilestone = new TargetMilestone();
        List<model.bugreports.Test> tests = new ArrayList<>();
        List<Patch> patches = new ArrayList<Patch>();
        int impactFactor = 3;
        bugReport = new BugReport(bugTrap, title, description, subsystem, dependsOn, assignees, comments, issuedBy, creationDate, observers, bugTag, stackTrace, errorMessage, reproduction, targetMilestone, tests, patches, impactFactor);
    }

    public void setInitialTag(BugTag tag) {
        bugTrap.getUserManager().loginAs(lead);
        try {
            bugReport.updateBugTag(tag);
        } catch (UnauthorizedAccessException e) {
            fail();
        }
    }

    @Test
    public void newTest() {
        setInitialTag(BugTag.NEW);
    	BugTagState tag = bugReport.getBugTagState();
        assertTrue(tag.isNew());
        assertFalse(tag.isClosed());
        assertFalse(tag.isInProgress());
        assertEquals(tag.getTag(), BugTag.NEW);
    }

    @Test
    public void assignedTest() {
        setInitialTag(BugTag.ASSIGNED);
    	BugTagState tag = bugReport.getBugTagState();
        assertFalse(tag.isNew());
        assertFalse(tag.isClosed());
        assertTrue(tag.isInProgress());
        assertEquals(tag.getTag(), BugTag.ASSIGNED);
    }

    @Test
    public void closedTest() {
        setInitialTag(BugTag.CLOSED);
    	BugTagState tag = bugReport.getBugTagState();
        assertTrue(tag.isClosed());
        assertFalse(tag.isNew());
        assertFalse(tag.isInProgress());
        assertEquals(tag.getTag(), BugTag.CLOSED);
    }

    @Test
    public void duplicateTest() {
        setInitialTag(BugTag.DUPLICATE);
    	BugTagState tag = bugReport.getBugTagState();
        assertTrue(tag.isClosed());
        assertFalse(tag.isNew());
        assertFalse(tag.isInProgress());
        assertEquals(tag.getTag(), BugTag.DUPLICATE);
    }

    @Test
    public void notABugTest() {
        setInitialTag(BugTag.NOTABUG);
    	BugTagState tag = bugReport.getBugTagState();
        assertFalse(tag.isNew());
        assertFalse(tag.isInProgress());
        assertTrue(tag.isClosed());
        assertEquals(tag.getTag(), BugTag.NOTABUG);
    }

    @Test
    public void resolvedTest() {
        setInitialTag(BugTag.RESOLVED);
    	BugTagState tag = bugReport.getBugTagState();
        assertFalse(tag.isNew());
        assertFalse(tag.isInProgress());
        assertTrue(tag.isClosed());
        assertEquals(tag.getTag(), BugTag.RESOLVED);
    }

    @Test
    public void underReviewTest() {
        setInitialTag(BugTag.UNDERREVIEW);
    	BugTagState tag = bugReport.getBugTagState();
        assertFalse(tag.isNew());
        assertTrue(tag.isInProgress());
        assertFalse(tag.isClosed());
        assertEquals(tag.getTag(), BugTag.UNDERREVIEW);
    }

    // -- Move from NEW to any other tag to make sure tags work correctly --
    @Test
    public void updateBugTagNewNew() throws UnauthorizedAccessException {
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.NEW);
        assertEquals(bugReport.getBugTag(), BugTag.NEW);
    }

    @Test
    public void updateBugTagNewAssigned() throws UnauthorizedAccessException {
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.ASSIGNED);
        assertEquals(bugReport.getBugTag(), BugTag.ASSIGNED);
    }

    @Test
    public void updateBugTagNewClosed() throws UnauthorizedAccessException {
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.CLOSED);
        assertEquals(bugReport.getBugTag(), BugTag.CLOSED);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagNewClosedNotAllowed() throws UnauthorizedAccessException {
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.CLOSED);
    }

    @Test
    public void updateBugTagNewDuplicate() throws UnauthorizedAccessException {
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.DUPLICATE);
        assertEquals(bugReport.getBugTag(), BugTag.DUPLICATE);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagNewDuplicateNotAllowed() throws UnauthorizedAccessException {
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.DUPLICATE);
    }

    @Test
    public void updateBugTagNewNotABug() throws UnauthorizedAccessException {
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.NOTABUG);
        assertEquals(bugReport.getBugTag(), BugTag.NOTABUG);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagNewNotABugNotAllowed() throws UnauthorizedAccessException {
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.NOTABUG);
    }

    @Test
    public void updateBugTagNewResolved() throws UnauthorizedAccessException {
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.RESOLVED);
        assertEquals(bugReport.getBugTag(), BugTag.RESOLVED);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagNewResolvedNotAllowed() throws UnauthorizedAccessException {
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.RESOLVED);
    }

    @Test
    public void updateBugTagNewUnderReview() throws UnauthorizedAccessException {
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.UNDERREVIEW);
        assertEquals(bugReport.getBugTag(), BugTag.UNDERREVIEW);
    }


    // -- Move from ASSIGNED to any other tag to make sure tags work correctly --
    @Test (expected = IllegalStateException.class)
    public void updateBugTagAssignedNew() throws UnauthorizedAccessException {
        setInitialTag(BugTag.ASSIGNED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.NEW);
    }

    @Test
    public void updateBugTagAssignedAssigned() throws UnauthorizedAccessException {
        setInitialTag(BugTag.ASSIGNED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.ASSIGNED);
        assertEquals(bugReport.getBugTag(), BugTag.ASSIGNED);
    }

    @Test
    public void updateBugTagAssignedClosed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.ASSIGNED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.CLOSED);
        assertEquals(bugReport.getBugTag(), BugTag.CLOSED);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagAssignedClosedNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.ASSIGNED);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.CLOSED);
    }

    @Test
    public void updateBugTagAssignedDuplicate() throws UnauthorizedAccessException {
        setInitialTag(BugTag.ASSIGNED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.DUPLICATE);
        assertEquals(bugReport.getBugTag(), BugTag.DUPLICATE);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagAssignedDuplicateNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.ASSIGNED);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.DUPLICATE);
    }

    @Test
    public void updateBugTagAssignedNotABug() throws UnauthorizedAccessException {
        setInitialTag(BugTag.ASSIGNED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.NOTABUG);
        assertEquals(bugReport.getBugTag(), BugTag.NOTABUG);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagAssignedNotABugNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.ASSIGNED);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.NOTABUG);
    }

    @Test
    public void updateBugTagAssignedResolved() throws UnauthorizedAccessException {
        setInitialTag(BugTag.ASSIGNED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.RESOLVED);
        assertEquals(bugReport.getBugTag(), BugTag.RESOLVED);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagAssignedResolvedNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.ASSIGNED);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.RESOLVED);
    }

    @Test
    public void updateBugTagAssignedUnderReview() throws UnauthorizedAccessException {
        setInitialTag(BugTag.ASSIGNED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.UNDERREVIEW);
        assertEquals(bugReport.getBugTag(), BugTag.UNDERREVIEW);
    }


    // -- Move from CLOSED to any other tag to make sure tags work correctly --
    @Test (expected = IllegalStateException.class)
    public void updateBugTagClosedNew() throws UnauthorizedAccessException {
        setInitialTag(BugTag.CLOSED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.NEW);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagClosedAssigned() throws UnauthorizedAccessException {
        setInitialTag(BugTag.CLOSED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.ASSIGNED);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagClosedClosed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.CLOSED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.CLOSED);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagClosedClosedNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.CLOSED);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.CLOSED);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagClosedDuplicate() throws UnauthorizedAccessException {
        setInitialTag(BugTag.CLOSED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.DUPLICATE);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagClosedDuplicateNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.CLOSED);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.DUPLICATE);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagClosedNotABug() throws UnauthorizedAccessException {
        setInitialTag(BugTag.CLOSED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.NOTABUG);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagClosedNotABugNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.CLOSED);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.NOTABUG);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagClosedResolved() throws UnauthorizedAccessException {
        setInitialTag(BugTag.CLOSED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.RESOLVED);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagClosedResolvedNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.CLOSED);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.RESOLVED);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagClosedUnderReview() throws UnauthorizedAccessException {
        setInitialTag(BugTag.CLOSED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.UNDERREVIEW);
    }


    // -- Move from DUPLICATE to any other tag to make sure tags work correctly --
    @Test (expected = IllegalStateException.class)
    public void updateBugTagDuplicateNew() throws UnauthorizedAccessException {
        setInitialTag(BugTag.DUPLICATE);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.NEW);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagDuplicateAssigned() throws UnauthorizedAccessException {
        setInitialTag(BugTag.DUPLICATE);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.ASSIGNED);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagDuplicateClosed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.DUPLICATE);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.CLOSED);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagDuplicateClosedNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.DUPLICATE);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.CLOSED);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagDuplicateDuplicate() throws UnauthorizedAccessException {
        setInitialTag(BugTag.DUPLICATE);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.DUPLICATE);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagDuplicateDuplicateNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.DUPLICATE);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.DUPLICATE);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagDuplicateNotABug() throws UnauthorizedAccessException {
        setInitialTag(BugTag.DUPLICATE);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.NOTABUG);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagDuplicateNotABugNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.DUPLICATE);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.NOTABUG);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagDuplicateResolved() throws UnauthorizedAccessException {
        setInitialTag(BugTag.DUPLICATE);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.RESOLVED);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagDuplicateResolvedNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.DUPLICATE);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.RESOLVED);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagDuplicateUnderReview() throws UnauthorizedAccessException {
        setInitialTag(BugTag.DUPLICATE);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.UNDERREVIEW);
    }


    // -- Move from NOTABUG to any other tag to make sure tags work correctly --
    @Test (expected = IllegalStateException.class)
    public void updateBugTagNotABugNew() throws UnauthorizedAccessException {
        setInitialTag(BugTag.NOTABUG);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.NEW);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagNotABugAssigned() throws UnauthorizedAccessException {
        setInitialTag(BugTag.NOTABUG);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.ASSIGNED);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagNotABugClosed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.NOTABUG);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.CLOSED);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagNotABugClosedNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.NOTABUG);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.CLOSED);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagNotABugDuplicate() throws UnauthorizedAccessException {
        setInitialTag(BugTag.NOTABUG);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.DUPLICATE);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagNotABugDuplicateNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.NOTABUG);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.DUPLICATE);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagNotABugNotABug() throws UnauthorizedAccessException {
        setInitialTag(BugTag.NOTABUG);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.NOTABUG);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagNotABugNotABugNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.NOTABUG);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.NOTABUG);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagNotABugResolved() throws UnauthorizedAccessException {
        setInitialTag(BugTag.NOTABUG);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.RESOLVED);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagNotABugResolvedNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.NOTABUG);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.RESOLVED);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagNotABugUnderReview() throws UnauthorizedAccessException {
        setInitialTag(BugTag.NOTABUG);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.UNDERREVIEW);
    }


    // -- Move from RESOLVED to any other tag to make sure tags work correctly --
    @Test (expected = IllegalStateException.class)
    public void updateBugTagResolvedNew() throws UnauthorizedAccessException {
        setInitialTag(BugTag.RESOLVED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.NEW);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagResolvedAssigned() throws UnauthorizedAccessException {
        setInitialTag(BugTag.RESOLVED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.ASSIGNED);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagResolvedClosed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.RESOLVED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.CLOSED);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagResolvedClosedNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.RESOLVED);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.CLOSED);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagResolvedDuplicate() throws UnauthorizedAccessException {
        setInitialTag(BugTag.RESOLVED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.DUPLICATE);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagResolvedDuplicateNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.RESOLVED);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.DUPLICATE);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagResolvedNotABug() throws UnauthorizedAccessException {
        setInitialTag(BugTag.RESOLVED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.NOTABUG);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagResolvedNotABugNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.RESOLVED);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.NOTABUG);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagResolvedResolved() throws UnauthorizedAccessException {
        setInitialTag(BugTag.RESOLVED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.RESOLVED);
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagResolvedResolvedNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.RESOLVED);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.RESOLVED);
    }

    @Test (expected = IllegalStateException.class)
    public void updateBugTagResolvedUnderReview() throws UnauthorizedAccessException {
        setInitialTag(BugTag.RESOLVED);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.UNDERREVIEW);
    }


    // -- Move from UNDERREVIEW to any other tag to make sure tags work correctly --
    @Test (expected = IllegalStateException.class)
    public void updateBugTagUnderReviewNew() throws UnauthorizedAccessException {
        setInitialTag(BugTag.UNDERREVIEW);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.NEW);
    }

    @Test
    public void updateBugTagUnderReviewAssigned() throws UnauthorizedAccessException {
        setInitialTag(BugTag.UNDERREVIEW);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.ASSIGNED);
        assertEquals(BugTag.ASSIGNED, bugReport.getBugTag());
    }

    @Test
    public void updateBugTagUnderReviewClosed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.UNDERREVIEW);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.CLOSED);
        assertEquals(BugTag.CLOSED, bugReport.getBugTag());
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagUnderReviewClosedNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.UNDERREVIEW);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.CLOSED);
    }

    @Test
    public void updateBugTagUnderReviewDuplicate() throws UnauthorizedAccessException {
        setInitialTag(BugTag.UNDERREVIEW);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.DUPLICATE);
        assertEquals(BugTag.DUPLICATE, bugReport.getBugTag());
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagUnderReviewDuplicateNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.UNDERREVIEW);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.DUPLICATE);
    }

    @Test
    public void updateBugTagUnderReviewNotABug() throws UnauthorizedAccessException {
        setInitialTag(BugTag.UNDERREVIEW);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.NOTABUG);
        assertEquals(BugTag.NOTABUG, bugReport.getBugTag());
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagUnderReviewNotABugNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.UNDERREVIEW);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.NOTABUG);
    }

    @Test
    public void updateBugTagUnderReviewResolved() throws UnauthorizedAccessException {
        setInitialTag(BugTag.UNDERREVIEW);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.RESOLVED);
        assertEquals(BugTag.RESOLVED, bugReport.getBugTag());
    }

    @Test (expected = UnauthorizedAccessException.class)
    public void updateBugTagUnderReviewResolvedNotAllowed() throws UnauthorizedAccessException {
        setInitialTag(BugTag.UNDERREVIEW);
        bugTrap.getUserManager().loginAs(issuer);
        bugReport.updateBugTag(BugTag.RESOLVED);
    }

    @Test
    public void updateBugTagUnderReviewUnderReview() throws UnauthorizedAccessException {
        setInitialTag(BugTag.UNDERREVIEW);
        bugTrap.getUserManager().loginAs(lead);
        bugReport.updateBugTag(BugTag.UNDERREVIEW);
        assertEquals(BugTag.UNDERREVIEW, bugReport.getBugTag());
    }
}
