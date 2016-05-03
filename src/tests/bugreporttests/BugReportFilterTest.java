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

public class BugReportFilterTest {

	BugReportFilter bugReportFilter;
	
	@Before
	public void setUp() throws Exception {
		List<IBugReport> bugReports = new ArrayList<IBugReport>();
		List<IUser> assignees1 = new ArrayList<IUser>();
		List<IUser> assignees2 = new ArrayList<IUser>();
		List<IUser> assignees3 = new ArrayList<IUser>();
		
		IUser developer1 = new Developer(null, null, null, "Louis");
		IUser developer2 = new Developer(null, null, null, "Armstrong");
		IUser developer3 = new Developer(null, null, null, "Duncan");
		
		assignees1.add(developer1); assignees1.add(developer2); assignees1.add(developer3);
		assignees2.add(developer2); assignees2.add(developer3);
		assignees3.add(developer1); assignees3.add(developer3);
		
		BugReport bugReport1 = new BugReport(null, "This is a BugReport", "Typo, low priority", null, null, assignees1, null, new Issuer(null, null, null, "George"), null, null, BugTag.NEW, null, null, null, null, null, null);
		BugReport bugReport2 = new BugReport(null, "Urgent!!!!", "Please take a look at this BugReport!", null, null, assignees2, null, new Issuer(null, null, null, "Michael"), null, null, BugTag.NEW, null, null, null, null, null, null);
		BugReport bugReport3 = new BugReport(null, "CRITICAL ERROR", "BEEP BOOP", null, null, assignees3, null, new Issuer(null, null, null, "George"), null, null, BugTag.NEW, null, null, null, null, null, null);
		
 		bugReports.add(bugReport1); bugReports.add(bugReport2); bugReports.add(bugReport3);
		
		bugReportFilter = new BugReportFilter(bugReports);
		
	}

	@Test
	public void byTitleOrDescrTest() {
		String param = "BugReport";
		List<IBugReport> result = bugReportFilter.filter(FilterType.CONTAINS_STRING, param);
		
		assertEquals(2, result.size());
		
		for (IBugReport bugReport : result)
			assertTrue(bugReport.getTitle().contains(param) || bugReport.getDescription().contains(param));
	}
	
	@Test
	public void filedByUserTest() {
		String param = "George";
		List<IBugReport> result = bugReportFilter.filter(FilterType.FILED_BY_USER, param);
		
		assertEquals(2, result.size());
		
		for (IBugReport bugReport : result) 
			assertTrue(bugReport.getIssuedBy().getUserName().equals(param));
	}
	
	@Test
	public void assignedToUserTest() {
		String param = "Armstrong";
		List<IBugReport> result = bugReportFilter.filter(FilterType.ASSIGNED_TO_USER, param);
		
		assertEquals(2, result.size());
		
		boolean contains = false;
		
		for (IBugReport bugReport : result) 
			for (IUser user : bugReport.getAssignees())
				if (user.getUserName().equals(param)) {
					contains = true; break;
				}
		assertTrue(contains);
	}

}
