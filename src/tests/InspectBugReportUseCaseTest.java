package tests;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import org.junit.Before;
import org.junit.Test;

import model.BugTrap;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.bugreports.filters.FilterType;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;

import static org.junit.Assert.*;

public class InspectBugReportUseCaseTest extends BugTrapTest {

	@Test
	public void inspectBugReportTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(issuer);

		//1. The issuer indicates he wants to inspect some bug report.
		//2. Include use case Select Bug Report.
		List<IBugReport> list = null;
		try {
			list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { bugTrap.getBugReportManager().getFilterTypes()[0] }, new String[] { "Clippy" });
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}
		//3. The system shows a detailed overview of the selected bug report and all its comments.
		IBugReport bugReport = list.get(0);
		
		//Confirm.
		assertEquals("Clippy bug!", bugReport.getTitle());
		assertEquals("Clippy only pops up once an hour. Should be more.", bugReport.getDescription());
		assertFalse(bugReport.getComments().isEmpty());
		assertEquals(lead, bugReport.getIssuedBy());
		assertTrue(bugReport.getAssignees().isEmpty());	
	}

}
