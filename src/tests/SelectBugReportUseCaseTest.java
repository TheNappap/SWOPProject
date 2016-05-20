package tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.IBugReport;
import model.bugreports.filters.FilterType;
import model.users.IUser;

public class SelectBugReportUseCaseTest extends BugTrapTest {

	@Test
	public void selectBugReportTest() throws UnauthorizedAccessException {
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
			list = bugReportController.getOrderedList(new FilterType[] { type }, new String[] { searchingString });

			//4. The issuer selects a bug report from the ordered list.
			IBugReport bugReport = list.get(0);
			
			//Confirm.
			assertEquals("Clippy bug!", bugReport.getTitle());
		}
	}

	@Test (expected = UnauthorizedAccessException.class)
	public void notLoggedIn() throws UnauthorizedAccessException {
		bugReportController.getOrderedList(new FilterType[]{}, new String[]{});
	}
	
	@Test (expected = UnauthorizedAccessException.class)
	public void unauthorisedTest() throws UnauthorizedAccessException {
		userController.loginAs(admin);
		bugReportController.getOrderedList(null, null);
	}
}
