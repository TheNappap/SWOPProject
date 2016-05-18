package tests;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.forms.BugReportAssignForm;
import model.bugreports.forms.ProposePatchForm;
import model.bugreports.forms.ProposeTestForm;
import model.users.IUser;

public class ProposePatchUseCaseTest extends BugTrapTest {

	@Before
	public void setUp() {
		super.setUp();
		
		bugTrap.getUserManager().loginAs(tester);
		
		//Assign a dev so BugReport becomes ASSIGNED.
		BugReportAssignForm assignForm = null;
		try {
			assignForm = bugReportController.getBugReportAssignForm();
		} catch (UnauthorizedAccessException e1) { fail("Must be developer"); }
		
		assignForm.setBugReport(clippyBug);
		assignForm.setDeveloper(prog);
		
		try {
			bugReportController.assignToBugReport(assignForm);
		} catch (UnauthorizedAccessException e1) { fail("must de dev"); }
		
		//Add a Test so Patches can be added.
		ProposeTestForm form = null;
		try {
			form = bugReportController.getProposeTestForm();
		} catch (UnauthorizedAccessException e) { fail("Must be tester"); }

		form.setTest("Tekst");
		form.setBugReport(clippyBug);
	
		try {
			bugReportController.proposeTest(form);
		} catch (UnauthorizedAccessException e) { fail("Must be tester"); }
	
		bugTrap.getUserManager().logOff();
	}
	
	@Test
	public void proposePatchUseCaseTest() {
		bugTrap.getUserManager().loginAs(prog);
		
		//1. The developer indicates that he wants to submit a patch for some bug report.
		ProposePatchForm form = null;
		
		try {
			form = bugReportController.getProposePatchForm();
		} catch (UnauthorizedAccessException e) {fail();}
		
		//2. Include use case Select Bug Report
		bugReportController.getBugReportList();

//		3. The system shows the form for uploading the patch.
//		4. The developer provides the details for uploading the patch.
		form.setBugReport(clippyBug);
		form.setPatch("This is a fix");
		
		try {
			bugReportController.proposePatch(form);
		} catch (UnauthorizedAccessException e) { fail(e.getMessage()); }
	}
	
	@Test
	public void proposePatchAsNonProg() {
		IUser[] nonProgs = new IUser[]{tester, lead};
		
		for (IUser user : nonProgs) {
			bugTrap.getUserManager().loginAs(user);
			
			ProposePatchForm form = null;
			
			try {
				form = bugReportController.getProposePatchForm();
			} catch (UnauthorizedAccessException e) {fail();}
			
			bugReportController.getBugReportList();

			form.setBugReport(clippyBug);
			form.setPatch("This is a fix");
			
			try {
				bugReportController.proposePatch(form);
				fail("Should be tester/assigned");
			} catch (UnauthorizedAccessException e) {  }
		}
	}
	
}
