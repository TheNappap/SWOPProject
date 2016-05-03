package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.bugreports.filters.FilterType;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;

public class SelectBugReportUseCaseTest extends UseCaseTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		//Make Users.
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createIssuer("", "", "", "ISSUER");
	
		//Add a Project
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		//Add a Subsystem.
		bugTrap.getProjectManager().createSubsystem("name", "description", project, project);
		ISubsystem subsystem = bugTrap.getProjectManager().getSubsystemWithName("name");
		bugTrap.getProjectManager().createSubsystem("name2", "description2", project, project);
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ISSUER"));
		//Add a BugReport.
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), subsystem, bugTrap.getUserManager().getUser("ISSUER"), new ArrayList<>(), new ArrayList<>(), BugTag.NEW);
	
		//Log off.
		bugTrap.getUserManager().logOff();
	}

	@Test
	public void selectBugReportTest() {
		String[] users = new String[]{"ISSUER", "DEV"};
		
		//Log in.
		for (String user : users) {
			userController.loginAs(user);
	
			//1. The system shows a list of possible searching modes:
			FilterType[] types = bugReportController.getFilterTypes();
			
			//2. The issuer selects a searching mode and provides the required search parameters.
			FilterType type = types[0];
			String searchingString = "B1";
			
			//3. The system shows an ordered list of bug reports that matched the search query.
			List<IBugReport> list = null;
			try {
				list = bugReportController.getOrderedList(new FilterType[] { type }, new String[] { searchingString });
			} catch (UnauthorizedAccessException e) { fail("not authorised"); }
	
			//4. The issuer selects a bug report from the ordered list.
			IBugReport bugReport = list.get(0);
			
			//Confirm.
			assertEquals("B1", bugReport.getTitle());	
		}
	}
	
	@Test
	public void unauthorisedTest() {
		try {
			bugReportController.getOrderedList(new FilterType[]{}, new String[]{});
			fail("Must be logged in.");
		} catch (UnauthorizedAccessException e) { }
		
		userController.loginAs("ADMIN");
		try {
			bugReportController.getOrderedList(null, null);
			fail("Can't be an admin.");
		} catch (UnauthorizedAccessException e) { }
	}

}
