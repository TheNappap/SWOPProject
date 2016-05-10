package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.users.IUser;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.bugreports.filters.FilterType;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;

public class SelectBugReportUseCaseTest extends BugTrapTest {

	@Test
	public void selectBugReportTest() {
		IUser[] users = new IUser[]{issuer, lead, prog, tester};
		
		//Log in.
		for (IUser user : users) {
			userController.loginAs(user);
	
			//1. The system shows a list of possible searching modes:
			FilterType[] types = bugReportController.getFilterTypes();
			
			//2. The issuer selects a searching mode and provides the required search parameters.
			FilterType type = types[0];
			String searchingString = "Clippy";
			
			//3. The system shows an ordered list of bug reports that matched the search query.
			List<IBugReport> list = null;
			try {
				list = bugReportController.getOrderedList(new FilterType[] { type }, new String[] { searchingString });
			} catch (UnauthorizedAccessException e) { fail("not authorised"); }
	
			//4. The issuer selects a bug report from the ordered list.
			IBugReport bugReport = list.get(0);
			
			//Confirm.
			assertEquals("Clippy bug!", bugReport.getTitle());
		}
	}
	
	@Test
	public void unauthorisedTest() {
		try {
			bugReportController.getOrderedList(new FilterType[]{}, new String[]{});
			fail("Must be logged in.");
		} catch (UnauthorizedAccessException e) { }
		
		userController.loginAs(admin);
		try {
			bugReportController.getOrderedList(null, null);
			fail("Can't be an admin.");
		} catch (UnauthorizedAccessException e) { }
	}

}
