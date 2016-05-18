package tests.bugreporttests;


import static org.junit.Assert.assertEquals;
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
import model.bugreports.PatchSection;
import model.bugreports.TargetMilestone;
import model.bugreports.TestSection;
import model.bugreports.bugtag.BugTag;
import model.bugreports.comments.Comment;
import model.notifications.observers.Observer;
import model.projects.ISubsystem;
import model.users.IUser;
import model.users.Issuer;
import tests.BugTrapTest;

public class BugReportTest extends BugTrapTest {

	private BugReport bugReport;
	
	private String title = "BugReport";
	private String description = "Awesome BugReport";
	private ISubsystem subsystem;
	private List<IBugReport> dependsOn = new ArrayList<>();
	private List<IUser> assignees = new ArrayList<>();
	private List<Comment> comments = new ArrayList<>();
	private List<Observer> observers = new ArrayList<>();
	private IUser issuedBy = new Issuer(null, null, null, null);
	private Date creationDate = new Date();
	private BugTag bugTag = BugTag.NEW;
	private String stackTrace = "stack";
	private String errorMessage = "error";
	private String reproduction = "reproduction";
	private TargetMilestone targetMilestone = new TargetMilestone();
	private int impactFactor = 3;
	
	private TestSection testSection = new TestSection();
	private PatchSection patchSection = new PatchSection();
	
	
	@Before
	public void setUp() throws UnauthorizedAccessException {
		super.setUp();
		subsystem = excel;
		bugReport = new BugReport(bugTrap, title, description, subsystem, dependsOn, assignees, comments, issuedBy, creationDate, observers, bugTag, stackTrace, errorMessage, reproduction, targetMilestone, impactFactor, testSection, patchSection);
	}

	@Test
	public void constructorTest() {
		assertTrue(bugReport.getTitle().equals(title));
		assertTrue(bugReport.getDescription().equals(description));
		assertEquals(bugReport.getSubsystem(), subsystem);
		assertEquals(bugReport.getDependsOn(), dependsOn);
		assertEquals(bugReport.getComments(), comments);
		assertEquals(bugReport.getIssuedBy(), issuedBy);
		assertEquals(bugReport.getCreationDate(), creationDate);
		assertEquals(bugReport.getBugTag(), bugTag);
		assertEquals(bugReport.getErrorMessage(), errorMessage);
		assertEquals(bugReport.getReproduction(), reproduction);
		assertEquals(bugReport.getTargetMilestone(), targetMilestone);
		assertEquals(bugReport.getStackTrace(), stackTrace);
	}
	
	@Test
	public void addCommentTest() {
		assertEquals(0, bugReport.getComments().size());
		bugReport.addComment("foobar");
		assertEquals(1, bugReport.getComments().size());
		assertTrue(bugReport.getComments().get(0).getText().equals("foobar"));
	}

	@Test
	public void assignDeveloperTest() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(lead);

		assertEquals(0, bugReport.getAssignees().size());
		bugReport.assignDeveloper(prog);
		assertEquals(1, bugReport.getAssignees().size());
		assertEquals(bugReport.getAssignees().get(0), prog);
	}

	@Test (expected = IllegalArgumentException.class)
	public void assignAdminTest() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(lead);
		bugReport.assignDeveloper(admin);
	}

	@Test (expected = IllegalArgumentException.class)
	public void assignIssuerTest() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(lead);
		bugReport.assignDeveloper(issuer);
	}
	
	@Test
	public void updateBugTagTest() {
		BugTag assignedTag 		= BugTag.ASSIGNED;
		BugTag closedTag 		= BugTag.CLOSED;
		BugTag duplicateTag 	= BugTag.DUPLICATE;
		BugTag newTag 			= BugTag.NEW;
		BugTag notABugTag 		= BugTag.NOTABUG;
		BugTag resolvedTag 		= BugTag.RESOLVED;
		BugTag underReviewTag 	= BugTag.UNDERREVIEW;

		bugTrap.getUserManager().loginAs(lead);

		//From New to New is allowed.
		try {
			bugReport.updateBugTag(newTag);
		} catch (UnauthorizedAccessException e) {
			fail();
		}
		//From New to InProgress is allowed.
		try {
			bugReport.updateBugTag(underReviewTag);
		} catch (UnauthorizedAccessException e) {
			fail();
		}
		//Walking around in InProgress is allowed.
		try {
			bugReport.updateBugTag(assignedTag);
		} catch (UnauthorizedAccessException e) {
			fail();
		}
		try {
			bugReport.updateBugTag(underReviewTag);
		} catch (UnauthorizedAccessException e) {
			fail();
		}

		//Going back to New is not allowed.
		try { bugReport.updateBugTag(newTag); fail(); } catch (IllegalStateException e) { } catch (UnauthorizedAccessException e) { }
		
		//From InProgress to Closed is allowed.
		try {
			bugReport.updateBugTag(duplicateTag);
		} catch (UnauthorizedAccessException e) {
			fail();
		}
		//Walking around in Closed is not allowed.
		try { bugReport.updateBugTag(resolvedTag); fail(); } catch (IllegalStateException e) { } catch (UnauthorizedAccessException e) { }
		try { bugReport.updateBugTag(closedTag); fail(); } catch (IllegalStateException e) { } catch (UnauthorizedAccessException e) { }
		try { bugReport.updateBugTag(notABugTag); fail(); } catch (IllegalStateException e) { } catch (UnauthorizedAccessException e) { }
		
		//Going back to previous states is not allowed.
		try { bugReport.updateBugTag(newTag); fail(); } catch (IllegalStateException e) { } catch (UnauthorizedAccessException e) { }
		try { bugReport.updateBugTag(underReviewTag); fail(); } catch (IllegalStateException e) { } catch (UnauthorizedAccessException e) { }
		try { bugReport.updateBugTag(assignedTag); fail(); } catch (IllegalStateException e) { } catch (UnauthorizedAccessException e) { }
	}

	@Test
	public void compareTest() {
		assertEquals(-1, clippyBug.compareTo(wordBug));
		assertEquals(1, wordArtBug.compareTo(clippyBug));

		assertEquals(0, bugReport.compareTo(bugReport));
		assertEquals(0, bugReport.compareTo(bugReport));

		assertEquals(-1, bugReport.compareTo(clippyBug));
		assertEquals(1, clippyBug.compareTo(bugReport));
	}
	
	@Test
	public void addComment() {
		((BugReport) wordArtBug).addComment("Who uses WortArt or Comic Sans anyway?");
		assertEquals(1, wordArtBug.getComments().size());
		assertEquals("Who uses WortArt or Comic Sans anyway?", wordArtBug.getComments().get(0).getText());
	}

	@Test
	public void proposeTest() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(tester);
		((BugReport) wordArtBug).addTest("<code here>");

		assertEquals(1, wordArtBug.getTests().size());
		assertEquals("<code here>", wordArtBug.getTests().get(0).getTest());
	}

	@Test (expected = UnauthorizedAccessException.class)
	public void propseTestNotAllowed() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(lead);
		((BugReport) wordArtBug).addTest("test");
	}

	@Test
	public void proposePatch() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(tester);
		((BugReport) wordArtBug).updateBugTag(BugTag.ASSIGNED);
		((BugReport) wordArtBug).addTest("test");

		bugTrap.getUserManager().loginAs(prog);
		((BugReport) wordArtBug).proposePatch("<code here>");

		assertEquals(1, wordArtBug.getPatches().size());
		assertEquals("<code here>", wordArtBug.getPatches().get(0).getPatch());
	}

	@Test (expected = UnauthorizedAccessException.class)
	public void proposePatchNotAllowedAsLead() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(tester);
		((BugReport) wordArtBug).updateBugTag(BugTag.ASSIGNED);
		((BugReport) wordArtBug).addTest("test");

		bugTrap.getUserManager().loginAs(lead);
		((BugReport) wordArtBug).proposePatch("patch");
	}

	@Test (expected = UnauthorizedAccessException.class)
	public void proposePatchNotAllowedAsTester() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(tester);
		((BugReport) wordArtBug).updateBugTag(BugTag.ASSIGNED);
		((BugReport) wordArtBug).addTest("test");

		bugTrap.getUserManager().loginAs(tester);
		((BugReport) wordArtBug).proposePatch("test");
	}
}
