package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.forms.BugReportAssignForm;
import model.bugreports.forms.ProposePatchForm;
import model.bugreports.forms.ProposeTestForm;

public class ProposePatchUseCaseTest extends BugTrapTest {

	@Before
	public void setUp() throws UnauthorizedAccessException {
		super.setUp();
		
		bugTrap.getUserManager().loginAs(tester);
		
		//Assign a dev so BugReport becomes ASSIGNED.
		BugReportAssignForm assignForm = bugReportController.getBugReportAssignForm();
		assignForm.setBugReport(clippyBug);
		assignForm.setDeveloper(prog);
		
		bugReportController.assignToBugReport(assignForm);

		//Add a Test so Patches can be added.
		ProposeTestForm form = bugReportController.getProposeTestForm();

		form.setTest("<code>");
		form.setBugReport(clippyBug);
		bugReportController.proposeTest(form);

		bugTrap.getUserManager().logOff();
	}
	
	@Test
	public void proposePatchUseCaseTest() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(prog);
		
		//1. The developer indicates that he wants to submit a patch for some bug report.
		ProposePatchForm form =  bugReportController.getProposePatchForm();

		//2. Include use case Select Bug Report
		bugReportController.getBugReportList();

		//3. The system shows the form for uploading the patch.
		//4. The developer provides the details for uploading the patch.
		form.setBugReport(clippyBug);
		form.setPatch("This is a fix");
		
		bugReportController.proposePatch(form);

		assertEquals(1, clippyBug.getPatches().size());
		assertEquals("This is a fix", clippyBug.getPatches().get(0).getPatch());
	}
	
	@Test (expected = UnauthorizedAccessException.class)
	public void proposePatchAsTester() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(tester);
			
		ProposePatchForm form = bugReportController.getProposePatchForm();

		bugReportController.getBugReportList();

		form.setBugReport(clippyBug);
		form.setPatch("This is a fix");

		bugReportController.proposePatch(form);
	}

	@Test (expected = UnauthorizedAccessException.class)
	public void proposePatchAsLead() throws UnauthorizedAccessException {
		bugTrap.getUserManager().loginAs(lead);

		ProposePatchForm form = bugReportController.getProposePatchForm();

		bugReportController.getBugReportList();

		form.setBugReport(clippyBug);
		form.setPatch("This is a fix");

		bugReportController.proposePatch(form);
	}
}
