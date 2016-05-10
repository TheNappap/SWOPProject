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

public class ProposePatchUseCaseTest extends BugTrapTest {

	@Test
	public void proposePatchUseCaseTest() {
		//Log in as tester.
		bugTrap.getUserManager().loginAs(prog);
		
		//1.
		ProposePatchForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProposePatchForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 2 SELECT BUGREPORT USE CASE
		IBugReport bugReport = null;
		String searchingString = "Clippy";
		List<IBugReport> list = null;
		try {
			list = bugReportController.getOrderedList(new FilterType[] { FilterType.CONTAINS_STRING }, new String[] { searchingString });
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}
		bugReport = list.get(0);	
		//3
		form.setBugReport(bugReport);
		//4
		form.setPatch("patching for life");
		//5
		try {
			bugReportController.proposePatch(form);
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
		bugTrap.getUserManager().loginAs(prog);
		
		//1.
		ProposePatchForm form = null;
		form = bugTrap.getFormFactory().makeProposePatchForm();

		//step 2 SELECT BUGREPORT USE CASE
		IBugReport bugReport = null;
		String searchingString = "Clippy";
		List<IBugReport> list = null;
		list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { FilterType.CONTAINS_STRING }, new String[] { searchingString });
		bugReport = list.get(0);	
		//3
		form.setBugReport(bugReport);
		//4
		form.setPatch("patching for life");
		//5
		bugReportController.proposePatch(form);
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
		bugTrap.getUserManager().loginAs(tester);
		
		try {
			bugTrap.getFormFactory().makeProposePatchForm().allVarsFilledIn();
			fail("should throw exception");
		} 
		catch (UnauthorizedAccessException e) 	{ fail("not authorized"); }
		catch (NullPointerException e) 			{ }
	}

}
