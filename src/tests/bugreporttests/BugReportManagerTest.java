package tests.bugreporttests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.bugreports.BugReport;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.bugreports.filters.FilterType;
import model.projects.IProject;
import model.projects.Project;
import model.projects.Subsystem;
import model.projects.Version;
import model.users.Administrator;
import model.users.Developer;
import model.users.IUser;
import model.users.Issuer;
import tests.BugTrapTest;

public class BugReportManagerTest extends BugTrapTest {

	@Test
	public void constructorTest() {
		assertNotNull(bugTrap.getBugReportManager().getBugReportList());
	}
	
	@Test
	public void addBugReportTest() {
		String title = "BugReport";
		String description = "Terrible bug";
		Subsystem subsystem = (Subsystem) excel;
		List<IBugReport> dependencies = new ArrayList<IBugReport>();
		List<IUser> assignees = new ArrayList<IUser>();
		BugTag tag = BugTag.NEW;
		int bugreportsBefore = bugTrap.getBugReportManager().getBugReportList().size();
		bugTrap.getBugReportManager().addBugReport(title, description, new Date(), subsystem, issuer, dependencies, assignees, tag, null, 4);

		assertEquals(bugreportsBefore + 1, bugTrap.getBugReportManager().getBugReportList().size());

		IBugReport added = bugTrap.getBugReportManager().getBugReportList().get(bugreportsBefore);

		assertTrue(added.getTitle().equals(title));
		assertTrue(added.getDescription().equals(description));
		assertTrue(Math.abs(added.getCreationDate().getTime() - new Date().getTime()) < 250);
		assertEquals(subsystem, added.getSubsystem());
		assertEquals(issuer, added.getIssuedBy());
		assertEquals(dependencies, added.getDependsOn());
		assertEquals(assignees, added.getAssignees());
		assertEquals(tag, added.getBugTag());
	}
	
	@Test
	public void getOrderedListTest() {
		bugTrap.getUserManager().loginAs(issuer);
		List<IBugReport> ordered = null;
		try {
			ordered = bugTrap.getBugReportManager().getOrderedList(new FilterType[]{FilterType.CONTAINS_STRING, FilterType.FILED_BY_USER}, new String[]{"Word", issuer.getUserName()});
		} catch (UnauthorizedAccessException e) {
			fail("Unauthorized.");
			e.printStackTrace();
		}

		//Correct list returned.
		//Filters themselves are tested in BugReportFilterTest.
		assertEquals(1, ordered.size());
		
		IBugReport filtered = ordered.get(0);
		
		//Is it the correct one?
		assertTrue(filtered.getTitle().equals("WordArt is not working"));
		assertTrue(filtered.getDescription().equals("When using Comic Sans, the Word Art does not work."));
	}

	@Test
	public void addComment() {
		bugTrap.getBugReportManager().addComment(wordArtBug, "Who uses WortArt or Comic Sans anyway?");
		assertEquals(1, wordArtBug.getComments().size());
		assertEquals("Who uses WortArt or Comic Sans anyway?", wordArtBug.getComments().get(0).getText());
	}

	@Test
	public void proposeTest() {
		bugTrap.getUserManager().loginAs(tester);
		try {
			bugTrap.getBugReportManager().proposeTest(wordArtBug, "<code here>");
		} catch (UnauthorizedAccessException e) {
			fail("Not authorized.");
			e.printStackTrace();
		}

		assertEquals(1, wordArtBug.getTests().size());
		assertEquals("<code here>", wordArtBug.getTests().get(0).getTest());
	}

	@Test (expected = UnauthorizedAccessException.class)
	public void propseTestNotAllowed() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(lead);
		bugTrap.getBugReportManager().proposeTest(wordArtBug, "test");
	}

	@Test
	public void proposePatch() {
		bugTrap.getUserManager().loginAs(prog);
		try {
			bugTrap.getBugReportManager().proposePatch(wordArtBug, "<code here>");
		} catch (UnauthorizedAccessException e) {
			fail("Not authorized.");
			e.printStackTrace();
		}

		assertEquals(1, wordArtBug.getPatches().size());
		assertEquals("<code here>", wordArtBug.getPatches().get(0).getPatch());
	}

	@Test (expected = UnauthorizedAccessException.class)
	public void proposePatchNotAllowed() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(lead);
		bugTrap.getBugReportManager().proposePatch(wordArtBug, "patch");
	}
}
