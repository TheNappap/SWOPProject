package tests;

import static org.junit.Assert.assertEquals;
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
import model.bugreports.bugtag.New;
import model.bugreports.filters.FilterType;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;
import model.users.IUser;

public class InspectBugReportUseCaseTest {

	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		
		//add user
		IUser dev = bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		IUser admin = bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().loginAs(admin);

		//add project
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		//add subsystem to project
		bugTrap.getProjectManager().createSubsystem("name", "description", project, project, Version.firstVersion());
		ISubsystem subsystem = bugTrap.getProjectManager().getSubsystemWithName("name");
		bugTrap.getProjectManager().createSubsystem("name2", "description2", project, project, Version.firstVersion());
		
		bugTrap.getUserManager().loginAs(dev);
		//add bugreport (for dependency)
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), subsystem, dev, new ArrayList<>(), new ArrayList<>(), new New());
		bugTrap.getUserManager().logOff();		
	}

	@Test
	public void inspectBugReportTest() {
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);

		//SELECT BUGREPORT USE CASE
		FilterType[] types = null;
		IBugReport bugReport = null;
		try {
			types = bugTrap.getBugReportManager().getFilterTypes();
			FilterType type = types[0];
			String searchingString = "B1";
			List<IBugReport> list = null;
			list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { type }, new String[] { searchingString });
			bugReport = list.get(0);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		//Inspect Bug report
		
		assertEquals("B1", bugReport.getTitle());
		assertEquals("B1 is a bug", bugReport.getDescription());	
		assertTrue(bugReport.getComments().isEmpty());	
		assertEquals(dev, bugReport.getIssuedBy());	
		assertTrue(bugReport.getAssignees().isEmpty());	
	}

	@Test
	public void notAuthorizedTest() {
		try {
			bugTrap.getBugReportManager().getOrderedList(new FilterType[] { FilterType.CONTAINS_STRING },new String[] { "" });
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
		}
	}

}
