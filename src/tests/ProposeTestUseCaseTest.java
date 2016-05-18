package tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.IBugReport;
import model.bugreports.forms.BugReportAssignForm;
import model.bugreports.forms.ProposeTestForm;
import model.users.IUser;

public class ProposeTestUseCaseTest extends BugTrapTest {

	@Before
	public void setUp() throws UnauthorizedAccessException {
		super.setUp();
	}
	
	@Test
	public void proposeTestUseCase() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(tester);
		
		//Assign a dev so BugReport becomes ASSIGNED.
		BugReportAssignForm assignForm = bugReportController.getBugReportAssignForm();
		assignForm.setBugReport(bugReportController.getBugReportList().get(0));
		assignForm.setDeveloper(tester);
		
		bugReportController.assignToBugReport(assignForm);

		//1. The developer indicates that he wants to submit a Test for some bug report.
		ProposeTestForm form = null;
		form = bugReportController.getProposeTestForm();

		//2. Include use case Select Bug Report.
		List<IBugReport> bugReports = bugReportController.getBugReportList();
		IBugReport bugReport = bugReports.get(0);
		
//		3. The system shows the form for uploading the patch.
//		4. The developer provides the details for uploading the patch.
		form.setTest("<code>");
		form.setBugReport(bugReport);
	
//		5. The system attaches the patch to the bug report.
		bugReportController.proposeTest(form);

		assertEquals(1, bugReport.getTests().size());
		assertEquals("<code>", bugReport.getTests().get(0).getTest());
	}
	
	@Test (expected = UnauthorizedAccessException.class)
	public void proposeTestProgrammer() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(prog);
		
		ProposeTestForm form = null;
		try {
			form = bugReportController.getProposeTestForm();
		}
		catch (UnauthorizedAccessException e) { fail("Must be developer"); }

		form.setBugReport(bugReportController.getBugReportList().get(0));
		form.setTest("test");

		bugReportController.proposeTest(form);
	}

	@Test (expected = UnauthorizedAccessException.class)
	public void proposeTestAsLead() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(lead);

		ProposeTestForm form = null;
		try {
			form = bugReportController.getProposeTestForm();
		}
		catch (UnauthorizedAccessException e) { fail("Must be developer"); }

		form.setBugReport(bugReportController.getBugReportList().get(0));
		form.setTest("test");

		bugReportController.proposeTest(form);
	}
}
