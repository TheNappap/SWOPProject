package tests;

import static org.junit.Assert.fail;

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
	public void setUp() {
		super.setUp();
	}
	
	@Test
	public void proposeTestUseCase() {
		bugTrap.getUserManager().loginAs(tester);
		
		//Assign a dev so BugReport becomes ASSIGNED.
		BugReportAssignForm assignForm = null;
		try {
			assignForm = bugReportController.getBugReportAssignForm();
		} catch (UnauthorizedAccessException e1) { fail("Must be developer"); }
		
		assignForm.setBugReport(bugReportController.getBugReportList().get(0));
		assignForm.setDeveloper(tester);
		
		try {
			bugReportController.assignToBugReport(assignForm);
		} catch (UnauthorizedAccessException e1) { fail("must de dev"); }
		
		//1. The developer indicates that he wants to submit a Test for some bug report.
		ProposeTestForm form = null;
		try {
			form = bugReportController.getProposeTestForm();
		} catch (UnauthorizedAccessException e) { fail("Must be tester"); }
		
		//2. Include use case Select Bug Report.
		List<IBugReport> bugReports = bugReportController.getBugReportList();
		IBugReport bugReport = bugReports.get(0);
		
//		3. The system shows the form for uploading the patch.
//		4. The developer provides the details for uploading the patch.
		form.setTest("Tekst");
		form.setBugReport(bugReport);
	
//		5. The system attaches the patch to the bug report.
		try {
			bugReportController.proposeTest(form);
		} catch (UnauthorizedAccessException e) { fail("Must be tester"); }
	}
	
	@Test
	public void proposeTestAsNotTester() {
		IUser[] unauthorisedUsers = new IUser[]{prog, lead};
		
		for (IUser user : unauthorisedUsers) {
			bugTrap.getUserManager().loginAs(user);
		
			ProposeTestForm form = null;
			try {
				form = bugReportController.getProposeTestForm();
			}
			catch (UnauthorizedAccessException e) { fail("Must be developer"); }
			
			form.setBugReport(bugReportController.getBugReportList().get(0));
			form.setTest("test");
			
			try {
				bugReportController.proposeTest(form);
				fail("Must be Tester.");
			} catch (UnauthorizedAccessException e) { }
		}
	}

}
