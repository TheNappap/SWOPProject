package tests;

import static org.junit.Assert.assertEquals;
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

public class SelectBugReportUseCaseTest {

	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		
		//add user
		IUser dev = bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().loginAs(dev);
		//add project
		IProject project = bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		//add subsystem to project
		ISubsystem subsystem = bugTrap.getProjectManager().createSubsystem("name", "description", project, project, Version.firstVersion());
		bugTrap.getProjectManager().createSubsystem("name2", "description2", project, project, Version.firstVersion());
		//add bugreport (for dependency)
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), subsystem, dev, new ArrayList<>(), new ArrayList<>(), new New());
		
		bugTrap.getUserManager().logOff();
	}

	@Test
	public void selectBugReportTest() {
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);

		//step 1
		FilterType[] types = null;
		try {
			types = bugTrap.getBugReportManager().getFilterTypes();
		} catch (UnauthorizedAccessException e) {
			e.printStackTrace();
			fail("not authorized");
		}
		//step 2
		FilterType type = types[0];
		String searchingString = "B1";
		//step 3
		List<IBugReport> list = null;
		try {
			list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { type }, new String[] { searchingString });
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 4
		IBugReport bugReport = list.get(0);
		
		assertEquals("B1", bugReport.getTitle());	
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
