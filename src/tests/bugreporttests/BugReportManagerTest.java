package tests.bugreporttests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.Assigned;
import model.bugreports.bugtag.BugTag;
import model.bugreports.bugtag.New;
import model.bugreports.filters.FilterType;
import model.projects.Subsystem;
import model.projects.Version;
import model.users.Administrator;
import model.users.Developer;
import model.users.IUser;
import model.users.Issuer;

public class BugReportManagerTest {

	BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
	}

	@Test
	public void constructorTest() {
		try {
			assertNotNull(bugTrap.getBugReportManager().getBugReportList());
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
	}
	
	@Test
	public void addBugReportTest() {
		String title = "BugReport";
		String description = "Terrible bug";
		Date creationDate = new Date();
		Subsystem subsystem = new Subsystem(null, null, null, null, null);
		Issuer issuer = new Issuer(null, null, null, null);
		List<IBugReport> dependencies = new ArrayList<IBugReport>();
		List<IUser> assignees = new ArrayList<IUser>();
		BugTag tag = new New();
		try {
			bugTrap.getBugReportManager().addBugReport(title, description, creationDate, subsystem, issuer, dependencies, assignees, tag);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		try {
			assertEquals(1, bugTrap.getBugReportManager().getBugReportList().size());
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		IBugReport added = null;
		try {
			added = bugTrap.getBugReportManager().getBugReportList().get(0);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		assertTrue(added.getTitle().equals(title));
		assertTrue(added.getDescription().equals(description));
		assertEquals(creationDate, added.getCreationDate());
		assertEquals(subsystem, added.getSubsystem());
		assertEquals(issuer, added.getIssuedBy());
		assertEquals(dependencies, added.getDependsOn());
		assertEquals(assignees, added.getAssignees());
		assertEquals(tag, added.getBugTag());
	}
	
	@Test
	public void getOrderedListTest() {
		try {
			bugTrap.getBugReportManager().addBugReport("Urgent!!!", "This is a BugReport", new Date(), new Subsystem(null, null, null, null, null), new Issuer(null, null, null, "Michael"), new ArrayList<>(), new ArrayList<>(), new New());
			bugTrap.getBugReportManager().addBugReport("Some BugReport", "Low Priority", new Date(), new Subsystem(null, null, null, null, null), new Issuer(null, null, null,"George"), new ArrayList<>(), new ArrayList<>(), new New());
			bugTrap.getBugReportManager().addBugReport("CRITICAL", "BEEP BOOP BEEP", new Date(), new Subsystem(null, null, null, null, null), new Issuer(null, null, null, "George"), new ArrayList<>(), new ArrayList<>(), new New());
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		List<IBugReport> ordered = null;
		try {
			ordered = bugTrap.getBugReportManager().getOrderedList(new FilterType[]{FilterType.CONTAINS_STRING, FilterType.FILED_BY_USER}, new String[]{"BugReport", "Michael"});
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		//Correct list returned.
		//Filters themselves are tested in BugReportFilterTest.
		assertEquals(1, ordered.size());
		
		IBugReport filtered = ordered.get(0);
		
		//Is it the correct one?
		assertTrue(filtered.getTitle().equals("Urgent!!!"));
		assertTrue(filtered.getDescription().equals("This is a BugReport"));
	}

	@Test
	public void assignToBugReportTest() {
		IUser developer = new Developer(null, null, null, "Jacques");
		IUser notDev = new Administrator(null, null, null, "John von Neumann");
		try {
			bugTrap.getBugReportManager().addBugReport("I'm a BugReport", "Yes I am", new Date(), new Subsystem(null, null, null, null, null), new Issuer(null, null, null, null), new ArrayList<>(), new ArrayList<>(), new New());
		} catch (UnauthorizedAccessException e1) {
			fail("not authorized");
			e1.printStackTrace();
		}
		IBugReport added = null;
		try {
			added = bugTrap.getBugReportManager().getBugReportList().get(0);
		} catch (UnauthorizedAccessException e1) {
			fail("not authorized");
			e1.printStackTrace();
		}
		
		try { bugTrap.getBugReportManager().assignToBugReport(added, notDev); fail(); } 
		catch (IllegalArgumentException e) { } 
		catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		assertEquals(0, added.getAssignees().size());
		try {
			bugTrap.getBugReportManager().assignToBugReport(added, developer);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		assertEquals(1, added.getAssignees().size());
	}
	
	@Test
	public void updateBugTagTest() {
		try {
			bugTrap.getBugReportManager().addBugReport("I'm a BugReport", "Yes I Am", new Date(), new Subsystem(null, null, null, null, null), new Issuer(null, null, null, null), new ArrayList<>(), new ArrayList<>(), new New());
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		IBugReport added = null;
		try {
			added = bugTrap.getBugReportManager().getBugReportList().get(0);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		assertTrue(added.getBugTag().isNew());
		
		try {
			bugTrap.getBugReportManager().updateBugReport(added, new Assigned());
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		assertTrue(added.getBugTag().isInProgress());
	}
	
	@Test
	public void getBugReportsForProject() {
		try {
			bugTrap.getProjectManager().createProject("ProjectName", "Desc", new Date(), new Date(), 0, new Developer(null, null, null, null), new Version(0, 0, 0));
			bugTrap.getProjectManager().getProjects().get(0);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
	}
}
