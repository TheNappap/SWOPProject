package tests;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.IBugReport;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.BugReportAssignForm;
import model.projects.IProject;
import model.users.IUser;

public class AssignBugReportUseCaseTest extends BugTrapTest {

	@Test
	public void assignBugReportAsLeadTest() throws UnauthorizedAccessException {
		//Log in.
		bugTrap.getUserManager().loginAs(lead);
		
		//1. The developer indicates he wants to assign a developer to a bug report.
		BugReportAssignForm form = null;
		try {
			form = bugTrap.getFormFactory().makeBugReportAssignForm();
		} catch (UnauthorizedAccessException e) { fail("not authorized");	}
		
		//2. Include use case Select Bug Report.
		List<IBugReport> list = bugReportController.getBugReportList();
		form.setBugReport(list.get(0));
		
		//3. The system shows a list of developers that are involved in the project.
		List<IUser> devs = list.get(0).getSubsystem().getProject().getAllDevelopers();
		
		//4. The logged in developer selects one or more of the developers to assign
		//to the selected bug report on top of those already assigned.
		form.setDeveloper(devs.get(0));
		
		//5. The systems assigns the selected developers to the selected bug report.
		bugReportController.assignToBugReport(form);

		assertTrue(list.get(0).getAssignees().get(0) == form.getDeveloper());
	}
	
	@Test
	public void assignBugReportAsTesterTest() throws UnauthorizedAccessException {
		//Log in.
		bugTrap.getUserManager().loginAs(tester);

		//1. The developer indicates he wants to assign a developer to a bug report.
		BugReportAssignForm form = null;
		try {
			form = bugTrap.getFormFactory().makeBugReportAssignForm();
		} catch (UnauthorizedAccessException e) { fail("not authorized"); }
		
		//2. Include use case Select Bug Report
		List<IBugReport> list = bugReportController.getBugReportList();
		form.setBugReport(list.get(0));
		
		//3. The system shows a list of developers that are involved in the project.
		List<IUser> devs = list.get(0).getSubsystem().getProject().getAllDevelopers();
		
		//4. The logged in developer selects one or more of the developers to assign to the selected bug report on top of those already assigned.
		form.setDeveloper(devs.get(0));
		
		//5. The systems assigns the selected developers to the selected bug report.
		bugReportController.assignToBugReport(form);

		assertTrue(list.get(0).getAssignees().get(0) == form.getDeveloper());
	}
	
	@Test (expected = UnauthorizedAccessException.class)
	public void loggedInDevIsNotLeadOrTester() throws UnauthorizedAccessException {
		//login
		bugTrap.getUserManager().loginAs(prog);
		
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

		types = bugTrap.getBugReportManager().getFilterTypes();
		FilterType type = types[0];
		String searchingString = "B1";
		List<IBugReport> list = null;
		list = bugReportController.getBugReportList();
		bugReport = list.get(0);

		form.setBugReport(bugReport);
		
		//step 3a
		IProject project =  bugReport.getSubsystem().getProject();
		IUser loggedInUser = bugTrap.getUserManager().getLoggedInUser();
		form.setDeveloper(loggedInUser);
		if(!project.isLead(loggedInUser) && !project.isTester(loggedInUser)){
			//step 2
			//...
		}
		else
			fail("should not be lead or tester");

		bugReportController.assignToBugReport(form);

		assertTrue(list.get(0).getAssignees().get(0) == form.getDeveloper());
			
	}

	@Test
	public void notAuthorizedTest() {
		try {
			bugTrap.getFormFactory().makeBugReportAssignForm();
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
		}
	}
	
	@Test (expected = NullPointerException.class)
	public void varsNotFilledTest() throws UnauthorizedAccessException {
		//login
		IUser dev = bugTrap.getUserManager().getUser("LEAD");
		bugTrap.getUserManager().loginAs(dev);
		
		BugReportAssignForm form = bugTrap.getFormFactory().makeBugReportAssignForm();
		bugReportController.assignToBugReport(form);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void nullFormTest() throws UnauthorizedAccessException {
		//login
		IUser dev = bugTrap.getUserManager().getUser("LEAD");
		bugTrap.getUserManager().loginAs(dev);
		bugReportController.assignToBugReport(null);
	}
}
