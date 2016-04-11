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

public class BugReportManagerTest {

	BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		IUser dev = bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		IUser admin = bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().loginAs(admin);
		
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, dev, new Version(1, 0, 0));
		
		bugTrap.getUserManager().loginAs(dev);
	}

	@Test
	public void constructorTest() {
		assertNotNull(bugTrap.getBugReportManager().getBugReportList());
	}
	
	@Test
	public void addBugReportTest() {
		String title = "BugReport";
		String description = "Terrible bug";
		Date creationDate = new Date();
		Subsystem subsystem = new Subsystem(null, null, null, null, null, null);
		Issuer issuer = new Issuer(null, null, null, null);
		List<IBugReport> dependencies = new ArrayList<IBugReport>();
		List<IUser> assignees = new ArrayList<IUser>();
		BugTag tag = BugTag.NEW;
		bugTrap.getBugReportManager().addBugReport(title, description, creationDate, subsystem, issuer, dependencies, assignees, tag);

		assertEquals(1, bugTrap.getBugReportManager().getBugReportList().size());

		IBugReport added = null;
		added = bugTrap.getBugReportManager().getBugReportList().get(0);

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
		bugTrap.getBugReportManager().addBugReport("Urgent!!!", "This is a BugReport", new Date(), new Subsystem(null, null, null, null, null, null), new Issuer(null, null, null, "Michael"), new ArrayList<>(), new ArrayList<>(), BugTag.NEW);
		bugTrap.getBugReportManager().addBugReport("Some BugReport", "Low Priority", new Date(), new Subsystem(null, null, null, null, null, null), new Issuer(null, null, null,"George"), new ArrayList<>(), new ArrayList<>(), BugTag.NEW);
		bugTrap.getBugReportManager().addBugReport("CRITICAL", "BEEP BOOP BEEP", new Date(), new Subsystem(null, null, null, null, null, null), new Issuer(null, null, null, "George"), new ArrayList<>(), new ArrayList<>(), BugTag.NEW);

		List<IBugReport> ordered = null;
		ordered = bugTrap.getBugReportManager().getOrderedList(new FilterType[]{FilterType.CONTAINS_STRING, FilterType.FILED_BY_USER}, new String[]{"BugReport", "Michael"});

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
		Project p = (Project)bugTrap.getProjectManager().getProjects().get(0);
		bugTrap.getBugReportManager().addBugReport("I'm a BugReport", "Yes I Am", new Date(), new Subsystem(null, null, p, null, p, null), new Issuer(null, null, null, null), new ArrayList<>(), new ArrayList<>(), BugTag.NEW);

		IBugReport added = null;
		added = bugTrap.getBugReportManager().getBugReportList().get(0);

		try { bugTrap.getBugReportManager().assignToBugReport(added, notDev); fail(); } 
		catch (IllegalArgumentException e) { }

		assertEquals(0, added.getAssignees().size());
		bugTrap.getBugReportManager().assignToBugReport(added, developer);
		assertEquals(1, added.getAssignees().size());
	}
	
	@Test
	public void updateBugTagTest() {
		Project p = (Project)bugTrap.getProjectManager().getProjects().get(0);
		bugTrap.getBugReportManager().addBugReport("I'm a BugReport", "Yes I Am", new Date(), new Subsystem(null, null, p, null, p, null), new Issuer(null, null, null, null), new ArrayList<>(), new ArrayList<>(), BugTag.NEW);

		IBugReport added = null;
		added = bugTrap.getBugReportManager().getBugReportList().get(0);

		assertEquals(added.getBugTag(), BugTag.NEW);
		
		bugTrap.getBugReportManager().updateBugReport(added, BugTag.ASSIGNED);

		assertEquals(added.getBugTag(), BugTag.ASSIGNED);
	}
	
	@Test
	public void getBugReportsForProject() {
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		bugTrap.getBugReportManager().getBugReportsForProject(project);
	}
}
