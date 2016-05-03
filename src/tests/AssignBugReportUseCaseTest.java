package tests;


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
import model.bugreports.bugtag.BugTag;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.BugReportAssignForm;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;
import model.users.Developer;
import model.users.IUser;

public class AssignBugReportUseCaseTest {

	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		//Make System.
		bugTrap = new BugTrap();
		
		//Add Users.
		IUser admin = bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		Developer lead = bugTrap.getUserManager().createDeveloper("", "", "", "LEAD");
		Developer prog = bugTrap.getUserManager().createDeveloper("", "", "", "PROG");
		Developer tester = bugTrap.getUserManager().createDeveloper("", "", "", "TESTER");
		bugTrap.getUserManager().loginAs(admin);
		
		//Add Project, assign some people.
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		project.setLeadDeveloper(lead);
		project.addProgrammer(prog);
		project.addTester(tester);
		//Add Subsystem.
		bugTrap.getProjectManager().createSubsystem("name", "description", project, project);
		ISubsystem subsystem = bugTrap.getProjectManager().getSubsystemWithName("name");
		bugTrap.getProjectManager().createSubsystem("name2", "description2", project, project);
		bugTrap.getUserManager().loginAs(lead);
		//Add BugReport.
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), subsystem, lead, new ArrayList<>(), new ArrayList<>(), BugTag.NEW);
		
		//Log off.
		bugTrap.getUserManager().logOff();	
	}

	@Test
	public void assignBugReportAsLeadTest() throws UnauthorizedAccessException {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("LEAD"));
		
		//1. The developer indicates he wants to assign a developer to a bug report.
		BugReportAssignForm form = null;
		try {
			form = bugTrap.getFormFactory().makeBugReportAssignForm();
		} catch (UnauthorizedAccessException e) { fail("not authorized");	}
		
		//2. Include use case Select Bug Report.
		List<IBugReport> list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { bugTrap.getBugReportManager().getFilterTypes()[0] }, new String[] { "B1" });
		form.setBugReport(list.get(0));
		
		//3. The system shows a list of developers that are involved in the project.
		List<IUser> devs = list.get(0).getSubsystem().getProject().getAllDevelopers();
		
		//4. The logged in developer selects one or more of the developers to assign
		//to the selected bug report on top of those already assigned.
		form.setDeveloper(devs.get(0));
		
		//5. The systems assigns the selected developers to the selected bug report.
		bugTrap.getBugReportManager().assignToBugReport(form.getBugReport(), form.getDeveloper());

		assertTrue(list.get(0).getAssignees().get(0) == form.getDeveloper());
	}
	
	@Test
	public void assignBugReportAsTesterTest() throws UnauthorizedAccessException {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("TESTER"));

		//1. The developer indicates he wants to assign a developer to a bug report.
		BugReportAssignForm form = null;
		try {
			form = bugTrap.getFormFactory().makeBugReportAssignForm();
		} catch (UnauthorizedAccessException e) { fail("not authorized"); }
		
		//2. Include use case Select Bug Report
		List<IBugReport> list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { bugTrap.getBugReportManager().getFilterTypes()[0] }, new String[] { "B1" });
		form.setBugReport(list.get(0));
		
		//3. The system shows a list of developers that are involved in the project.
		List<IUser> devs = list.get(0).getSubsystem().getProject().getAllDevelopers();
		
		//4. The logged in developer selects one or more of the developers to assign to the selected bug report on top of those already assigned.
		form.setDeveloper(devs.get(0));
		
		//5. The systems assigns the selected developers to the selected bug report.
		bugTrap.getBugReportManager().assignToBugReport(form.getBugReport(), form.getDeveloper());

		assertTrue(list.get(0).getAssignees().get(0) == form.getDeveloper());
	}
	
	@Test (expected = UnauthorizedAccessException.class)
	public void loggedInDevIsNotLeadOrTester() throws UnauthorizedAccessException {
		//login
		IUser dev = bugTrap.getUserManager().getUser("PROG");
		bugTrap.getUserManager().loginAs(dev);
		
		//step 1
		BugReportAssignForm form = null;
		try {
			form = bugTrap.getFormFactory().makeBugReportAssignForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 2 SELECT BUGREPORT USE CASE
		FilterType[] types = null;
		IBugReport bugReport = null;

		types = bugTrap.getBugReportManager().getFilterTypes();
		FilterType type = types[0];
		String searchingString = "B1";
		List<IBugReport> list = null;
		list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { type }, new String[] { searchingString });
		bugReport = list.get(0);

		form.setBugReport(bugReport);
		
		//step 3a
		IProject project =  bugReport.getSubsystem().getProject();
		IUser loggedInUser = bugTrap.getUserManager().getLoggedInUser();
		form.setDeveloper(loggedInUser);
		if(!project.isLead(loggedInUser) && !project.isTester(loggedInUser)){
			//step 2
			//...
		}
		else
			fail("should not be lead or tester");
		
		bugTrap.getBugReportManager().assignToBugReport(form.getBugReport(), form.getDeveloper());

		assertTrue(list.get(0).getAssignees().get(0) == form.getDeveloper());
			
	}

	@Test
	public void notAuthorizedTest() {
		try {
			bugTrap.getFormFactory().makeBugReportAssignForm();
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
		}
	}
	
	@Test (expected = NullPointerException.class)
	public void varsNotFilledTest() throws UnauthorizedAccessException {
		//login
		IUser dev = bugTrap.getUserManager().getUser("LEAD");
		bugTrap.getUserManager().loginAs(dev);
		
		BugReportAssignForm form = bugTrap.getFormFactory().makeBugReportAssignForm();
		bugTrap.getBugReportManager().assignToBugReport(form.getBugReport(), form.getDeveloper());
	}
	
	@Test (expected = NullPointerException.class)
	public void nullFormTest() throws UnauthorizedAccessException {
		//login
		IUser dev = bugTrap.getUserManager().getUser("LEAD");
		bugTrap.getUserManager().loginAs(dev);
		bugTrap.getBugReportManager().assignToBugReport(null, null);
	}
}
