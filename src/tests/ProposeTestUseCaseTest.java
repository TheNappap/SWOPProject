package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import model.bugreports.forms.ProposeTestForm;
import model.projects.Version;

public class ProposeTestUseCaseTest {

	private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		//Make System.
		bugTrap = new BugTrap();
		
		//Make Users.
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createDeveloper("", "", "", "TESTER");
		
		//Log in as Administrator, create Project/Subsystem and log off.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		bugTrap.getProjectManager().createSubsystem("name", "description", bugTrap.getProjectManager().getProjects().get(0), bugTrap.getProjectManager().getProjects().get(0));
		bugTrap.getUserManager().logOff();
		
		//Log in as Developer, add BugReport, add tester and log off.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), bugTrap.getProjectManager().getProjects().get(0).getSubsystems().get(0), bugTrap.getUserManager().getUser("DEV"), new ArrayList<>(), new ArrayList<>(), BugTag.NEW);
		bugTrap.getProjectManager().getProjects().get(0).addTester(bugTrap.getUserManager().getUser("TESTER"));
		bugTrap.getUserManager().logOff();
	}

	@Test
	public void proposeTestUseCaseTest() {
		//Log in as tester.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("TESTER"));
		
		//1.
		ProposeTestForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProposeTestForm();
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
		//3
		form.setBugReport(bugReport);
		//4
		form.setTest("test,test");
		//5
		try {
			bugTrap.getBugReportManager().proposeTest(form.getBugReport(), form.getTest());
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		//confirm
		assertEquals("test,test", bugReport.getTests().get(0).getTest());
		assertFalse(bugReport.getTests().get(0).isAccepted());
	}
	
	@Test (expected =  UnauthorizedAccessException.class)
	public void devNotTesterFailTest() throws UnauthorizedAccessException {
		//Log in as not tester.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		
		//1.
		ProposeTestForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProposeTestForm();
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
		//3
		form.setBugReport(bugReport);
		//4
		form.setTest("test,test");
		//5
		bugTrap.getBugReportManager().proposeTest(form.getBugReport(), form.getTest());		
	}
	
	@Test
	public void authorisationTest() {
		//Can't propose test when not logged in.
		try {
			bugTrap.getFormFactory().makeProposePatchForm();
			fail("Can't propose test when not logged in.");
		} catch (UnauthorizedAccessException e) { }
	}
	
	@Test
	public void varsNotFilledTest() {
		//Log in as Administrator.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		
		try {
			bugTrap.getFormFactory().makeProposeTestForm().allVarsFilledIn();
			fail("should throw exception");
		} 
		catch (UnauthorizedAccessException e) 	{ fail("not authorized"); }
		catch (NullPointerException e) 			{ }
	}

}
