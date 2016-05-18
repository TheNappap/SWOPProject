package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.IBugReport;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.ProposePatchForm;

public class ProposePatchUseCaseTest extends BugTrapTest {

	@Test
	public void proposePatchUseCaseTest() {
		//Log in as programmer.
		userController.loginAs(prog);
		
		//1.
		ProposePatchForm form = null;
		try {
			form = bugReportController.getProposePatchForm();
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
	}
	
	@Test (expected =  UnauthorizedAccessException.class)
	public void devNotTesterFailTest() throws UnauthorizedAccessException {
		//Log in as not programmer.
		userController.loginAs(tester);
		
		//1.
		ProposePatchForm form = null;
		form = bugReportController.getProposePatchForm();

		//step 2 SELECT BUGREPORT USE CASE
		IBugReport bugReport = null;
		String searchingString = "Clippy";
		List<IBugReport> list = null;
		list = bugReportController.getOrderedList(new FilterType[] { FilterType.CONTAINS_STRING }, new String[] { searchingString });
		bugReport = list.get(0);	
		//3
		form.setBugReport(bugReport);
		//4
		form.setPatch("patching for life");
		//5
		bugReportController.proposePatch(form);
	}
	
	@Test (expected = UnauthorizedAccessException.class)
	public void authorisationTest() throws UnauthorizedAccessException {
		//Can't propose test when not logged in.
		bugReportController.getProposePatchForm();
	}
	
	@Test (expected = NullPointerException.class)
	public void varsNotFilledTest() throws UnauthorizedAccessException {
		//Log in as Administrator.
		userController.loginAs(tester);
		
		bugReportController.getProposePatchForm().allVarsFilledIn();
	}
}
