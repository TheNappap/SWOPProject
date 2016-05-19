package tests;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.BugReportUpdateForm;
import model.users.IUser;

public class UpdateBugReportUseCaseTest extends BugTrapTest {

	@Test
	public void updateBugReportTest() throws UnauthorizedAccessException {
		//Log in as Issuer.
		userController.loginAs(lead);
		
		//1.
		BugReportUpdateForm form = bugReportController.getBugReportUpdateForm();

		//step 2 SELECT BUGREPORT USE CASE
		IBugReport bugReport = null;
		String searchingString = "Clippy";
		List<IBugReport> list = null;
		list = bugReportController.getOrderedList(new FilterType[] { FilterType.CONTAINS_STRING }, new String[] { searchingString });
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
		userController.loginAs(dev);

		bugReportController.getBugReportUpdateForm();
	}

	@Test (expected = UnauthorizedAccessException.class)
	public void notAuthorizedTest() throws UnauthorizedAccessException {
		bugReportController.getBugReportUpdateForm();
	}
	
	@Test (expected = NullPointerException.class)
	public void varsNotFilledTest() throws UnauthorizedAccessException {
		//login
		userController.loginAs(lead);

		BugReportUpdateForm form = bugReportController.getBugReportUpdateForm();
		bugReportController.updateBugReport(form);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void nullFormTest() throws UnauthorizedAccessException {
		//login
		userController.loginAs(lead);

		bugReportController.updateBugReport(null);
	}
}
