package tests.bugreporttests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.bugreports.BugReport;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.bugreports.filters.BugReportFilter;
import model.bugreports.filters.FilterType;
import model.users.Developer;
import model.users.IUser;
import model.users.Issuer;
import tests.BugTrapTest;

public class BugReportFilterTest extends BugTrapTest {

	BugReportFilter bugReportFilter;
	
	@Before
	public void setUp() {
		super.setUp();

		// Add some additional bug reports

		this.bugReportFilter = new BugReportFilter(this.bugReportController.getBugReportList());
	}

	@Test
	public void byTitleOrDescrTest() {
		String param = "Clippy";
		List<IBugReport> result = bugReportFilter.filter(FilterType.CONTAINS_STRING, param);
		
		assertEquals(2, result.size());
		
		for (IBugReport bugReport : result)
			assertTrue(bugReport.getTitle().contains(param) || bugReport.getDescription().contains(param));

		param = "Word";
		result = bugReportFilter.filter(FilterType.CONTAINS_STRING, param);
		assertEquals(1, result.size());

		param = "Somerandomwords";
		result = bugReportFilter.filter(FilterType.CONTAINS_STRING, param);
		assertEquals(0, result.size());
	}
	
	@Test
	public void filedByUserTest() {
		String param = lead.getUserName();
		List<IBugReport> result = bugReportFilter.filter(FilterType.FILED_BY_USER, param);
		
		assertEquals(3, result.size());
		
		for (IBugReport bugReport : result) 
			assertTrue(bugReport.getIssuedBy().getUserName().equals(param));

		param = prog.getUserName();
		result = bugReportFilter.filter(FilterType.FILED_BY_USER, param);
		assertEquals(0, result.size());
	}
	
	@Test
	public void assignedToUserTest() {
		String param = prog.getUserName();
		List<IBugReport> result = bugReportFilter.filter(FilterType.ASSIGNED_TO_USER, param);
		
		assertEquals(3, result.size());
		
		boolean contains = false;
		
		for (IBugReport bugReport : result) 
			for (IUser user : bugReport.getAssignees())
				if (user.getUserName().equals(param)) {
					contains = true;
					break;
				}
		assertTrue(contains);
	}
}
