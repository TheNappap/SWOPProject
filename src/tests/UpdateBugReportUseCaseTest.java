package tests;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.BugReportUpdateForm;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;
import model.users.IUser;

public class UpdateBugReportUseCaseTest extends BugTrapTest {

	@Test
	public void updateBugReportTest() throws UnauthorizedAccessException {
		//Log in as Issuer.
		bugTrap.getUserManager().loginAs(lead);
		
		//1.
		BugReportUpdateForm form = null;
		try {
			form = bugReportController.getBugReportUpdateForm();
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

		//step 3
		form.setBugReport(bugReport);
		BugTag tag = BugTag.ASSIGNED;
		form.setBugTag(tag);
		//step 4
		//The system asks for the corresponding information for that tag.
		//step 5
		//The issuer provides the requested information.
		
		//step 6
		bugReportController.updateBugReport(form);

		Assert.assertEquals(tag, bugReport.getBugTag());
	}
	
	@Test (expected = UnauthorizedAccessException.class)
	public void noPermissionToAssignTest() throws UnauthorizedAccessException {
		//login
		IUser dev = issuer;
		bugTrap.getUserManager().loginAs(dev);

		BugReportUpdateForm form = null;
		form = bugReportController.getBugReportUpdateForm();
	}

	@Test (expected = UnauthorizedAccessException.class)
	public void notAuthorizedTest() throws UnauthorizedAccessException {
		bugTrap.getFormFactory().makeBugReportUpdateForm();
	}
	
	@Test (expected = NullPointerException.class)
	public void varsNotFilledTest() throws UnauthorizedAccessException {
		//login
		bugTrap.getUserManager().loginAs(lead);

		BugReportUpdateForm form = bugTrap.getFormFactory().makeBugReportUpdateForm();
		bugReportController.updateBugReport(form);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void nullFormTest() throws UnauthorizedAccessException {
		//login
		bugTrap.getUserManager().loginAs(lead);

		bugReportController.updateBugReport(null);
	}
}
