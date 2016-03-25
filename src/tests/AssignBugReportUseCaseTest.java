package tests;


import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.New;
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
		bugTrap = new BugTrap();
		
		//add user
		IUser admin = bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		Developer lead = bugTrap.getUserManager().createDeveloper("", "", "", "LEAD");
		Developer prog = bugTrap.getUserManager().createDeveloper("", "", "", "PROG");
		Developer tester = bugTrap.getUserManager().createDeveloper("", "", "", "TESTER");
		bugTrap.getUserManager().loginAs(admin);
		
		IProject project = bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		project.setLeadDeveloper(lead);
		project.addProgrammer(prog);
		project.addTester(tester);
		//add subsystem to project
		ISubsystem subsystem = bugTrap.getProjectManager().createSubsystem("name", "description", project, project, Version.firstVersion());
		bugTrap.getProjectManager().createSubsystem("name2", "description2", project, project, Version.firstVersion());
		bugTrap.getUserManager().loginAs(lead);
		//add bugreport (for dependency)
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), subsystem, lead, new ArrayList<>(), new ArrayList<>(), new New());
		bugTrap.getUserManager().logOff();	
	}

	@Test
	public void assignBugReportAsLeadTest() throws UnauthorizedAccessException {
		//login
		IUser dev = bugTrap.getUserManager().getUser("LEAD");
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
		form.setBugReport(bugReport);
		//step 3
		IProject project =  bugReport.getSubsystem().getProject();
		List<IUser> devs = project.getAllDevelopers();
		//step 4
		form.setDeveloper(devs.get(0));
		//step 5
		bugTrap.getBugReportManager().assignToBugReport(form.getBugReport(), form.getDeveloper());

		Assert.assertTrue(bugReport.getAssignees().get(0) == form.getDeveloper());
	}
	
	@Test
	public void assignBugReportAsTesterTest() throws UnauthorizedAccessException {
		//login
		IUser dev = bugTrap.getUserManager().getUser("TESTER");
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
		form.setBugReport(bugReport);
		//step 3
		IProject project =  bugReport.getSubsystem().getProject();
		List<IUser> devs = project.getAllDevelopers();
		//step 4
		form.setDeveloper(devs.get(0));
		//step 5
		bugTrap.getBugReportManager().assignToBugReport(form.getBugReport(), form.getDeveloper());

		Assert.assertTrue(bugReport.getAssignees().get(0) == form.getDeveloper());
	}
	
	@Test
	public void loggedInDevIsNotLeadOrTester() {
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
		form.setBugReport(bugReport);
		
		//step 3a
		IProject project =  bugReport.getSubsystem().getProject();
		IUser loggedInUser = bugTrap.getUserManager().getLoggedInUser();
		if(!project.isLead(loggedInUser) && !project.isTester(loggedInUser)){
			//step 2
			//...
		}
		else
			fail("should not be lead or tester");
			
	}

	@Test
	public void notAuthorizedTest() {
		try {
			bugTrap.getFormFactory().makeBugReportAssignForm();
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void varsNotFilledTest() throws UnauthorizedAccessException {
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);
		
		BugReportAssignForm form = bugTrap.getFormFactory().makeBugReportAssignForm();
		bugTrap.getBugReportManager().assignToBugReport(form.getBugReport(), form.getDeveloper());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void nullFormTest() {
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);
		
		try {
			bugTrap.getBugReportManager().assignToBugReport(null, null);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
	}
}
