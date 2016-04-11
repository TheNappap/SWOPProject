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
import model.bugreports.forms.ProposePatchForm;
import model.projects.Version;

public class ProposePatchUseCaseTest {

	private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		//Make System.
		bugTrap = new BugTrap();
		
		//Make Users.
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createDeveloper("", "", "", "PROG");
		
		//Log in as Administrator, create Project/Subsystem and log off.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		bugTrap.getProjectManager().createSubsystem("name", "description", bugTrap.getProjectManager().getProjects().get(0), bugTrap.getProjectManager().getProjects().get(0));
		bugTrap.getUserManager().logOff();
		
		//Log in as Developer, add BugReport, add tester and log off.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), bugTrap.getProjectManager().getProjects().get(0).getSubsystems().get(0), bugTrap.getUserManager().getUser("DEV"), new ArrayList<>(), new ArrayList<>(), BugTag.NEW);
		bugTrap.getProjectManager().getProjects().get(0).addProgrammer(bugTrap.getUserManager().getUser("PROG"));
		bugTrap.getUserManager().logOff();
	}

	@Test
	public void proposePatchUseCaseTest() {
		//Log in as tester.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("PROG"));
		
		//1.
		ProposePatchForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProposePatchForm();
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
		form.setPatch("patching for life");
		//5
		try {
			bugTrap.getBugReportManager().proposePatch(form.getBugReport(), form.getPatch());
		} catch (UnauthorizedAccessException e) {
			e.printStackTrace();
			fail("not authorized");
		}
		
		//confirm
		assertEquals("patching for life", bugReport.getPatches().get(0).getPatch());
		assertFalse(bugReport.getPatches().get(0).isAccepted());
	}
	
	@Test (expected =  UnauthorizedAccessException.class)
	public void devNotTesterFailTest() throws UnauthorizedAccessException {
		//Log in as not tester.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		
		//1.
		ProposePatchForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProposePatchForm();
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
		form.setPatch("patching for life");
		//5
		bugTrap.getBugReportManager().proposePatch(form.getBugReport(), form.getPatch());		
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
			bugTrap.getFormFactory().makeProposePatchForm().allVarsFilledIn();
			fail("should throw exception");
		} 
		catch (UnauthorizedAccessException e) 	{ fail("not authorized"); }
		catch (NullPointerException e) 			{ }
	}

}
