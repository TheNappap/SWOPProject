package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.BugTrap;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.bugreports.filters.FilterType;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;

public class InspectBugReportUseCaseTest {

	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		//Make System.
		bugTrap = new BugTrap();
		
		//Add Users.
		bugTrap.getUserManager().createIssuer("", "", "", "ISSUER");
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");

		//Add Project
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		//Add Subsystem to Project
		bugTrap.getProjectManager().createSubsystem("name", "description", project, project);
		ISubsystem subsystem = bugTrap.getProjectManager().getSubsystemWithName("name");
		bugTrap.getProjectManager().createSubsystem("name2", "description2", project, project);
		//Add BugReport.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ISSUER"));
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), subsystem, bugTrap.getUserManager().getUser("ISSUER"), new ArrayList<>(), new ArrayList<>(), BugTag.NEW);
		//Log off.
		bugTrap.getUserManager().logOff();		
	}

	@Test
	public void inspectBugReportTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ISSUER"));

		//1. The issuer indicates he wants to inspect some bug report.
		//2. Include use case Select Bug Report.
		List<IBugReport> list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { bugTrap.getBugReportManager().getFilterTypes()[0] }, new String[] { "B1" });		
		//3. The system shows a detailed overview of the selected bug report and all its comments.
		IBugReport bugReport = list.get(0);
		
		//Confirm.
		assertEquals("B1", bugReport.getTitle());
		assertEquals("B1 is a bug", bugReport.getDescription());	
		assertTrue(bugReport.getComments().isEmpty());	
		assertEquals(bugTrap.getUserManager().getUser("ISSUER"), bugReport.getIssuedBy());	
		assertTrue(bugReport.getAssignees().isEmpty());	
	}

}
