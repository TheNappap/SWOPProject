package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.IBugReport;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.ProposeTestForm;

public class ProposeTestUseCaseTest extends BugTrapTest {

	@Test
	public void proposeTestUseCaseTest() {
		//Log in as tester.
		bugTrap.getUserManager().loginAs(tester);
		
		//1.
		ProposeTestForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProposeTestForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 2 SELECT BUGREPORT USE CASE
		IBugReport bugReport = null;
		String searchingString = "Clippy";
		List<IBugReport> list = null;
		try {
			list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { FilterType.CONTAINS_STRING }, new String[] { searchingString });
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}
		bugReport = list.get(0);	
		//3
		form.setBugReport(bugReport);
		//4
		form.setTest("test,test");
		//5
		try {
			bugReportController.proposeTest(form);
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
		bugTrap.getUserManager().loginAs(prog);
		
		//1.
		ProposeTestForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProposeTestForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 2 SELECT BUGREPORT USE CASE
		IBugReport bugReport = null;
		String searchingString = "Clippy";
		List<IBugReport> list = null;
		list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { FilterType.CONTAINS_STRING }, new String[] { searchingString });
		bugReport = list.get(0);	
		//3
		form.setBugReport(bugReport);
		//4
		form.setTest("test,test");
		//5
		bugReportController.proposeTest(form);
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
		//Log in as tester.
		bugTrap.getUserManager().loginAs(tester);
		
		try {
			bugTrap.getFormFactory().makeProposeTestForm().allVarsFilledIn();
			fail("should throw exception");
		} 
		catch (UnauthorizedAccessException e) 	{ fail("not authorized"); }
		catch (NullPointerException e) 			{ }
	}

}
