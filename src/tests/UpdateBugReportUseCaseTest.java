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

public class UpdateBugReportUseCaseTest {

private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		
		//add user
		IUser dev = bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createDeveloper("", "", "", "ISSUER");
		IUser admin = bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().loginAs(admin);
		
		//add project
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		//add subsystem to project
		bugTrap.getProjectManager().createSubsystem("name", "description", project, project);
		ISubsystem subsystem = bugTrap.getProjectManager().getSubsystemWithName("name");
		bugTrap.getProjectManager().createSubsystem("name2", "description2", project, project);
		bugTrap.getUserManager().loginAs(dev);
		//add bugreport (for dependency)
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), subsystem, dev, new ArrayList<>(), new ArrayList<>(), BugTag.NEW);
		bugTrap.getUserManager().logOff();
	}

	@Test
	public void updateBugReportTest() {
		//Log in as Issuer.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ISSUER"));
		
		
		//1.
		BugReportUpdateForm form = null;
		try {
			form = bugTrap.getFormFactory().makeBugReportUpdateForm();
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
		list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { type }, new String[] { searchingString });
		bugReport = list.get(0);

		//step 3
		form.setBugReport(bugReport);
		BugTag tag = BugTag.RESOLVED;
		form.setBugTag(tag);
		//step 4
		The system asks for the corresponding information for that tag. 
		//step 5
		The issuer provides the requested information.
		
		//step 6
		bugTrap.getBugReportManager().updateBugReport(form.getBugReport(), form.getBugTag());

		Assert.assertEquals(tag, bugReport.getBugTag());
	}
	
	@Test
	public void noPermissionToAssignTest() {
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);
		
		
		//step 2 SELECT BUGREPORT USE CASE
		FilterType[] types = null;
		IBugReport bugReport = null;
		types = bugTrap.getBugReportManager().getFilterTypes();
		FilterType type = types[0];
		String searchingString = "B1";
		List<IBugReport> list = null;
		list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { type }, new String[] { searchingString });
		bugReport = list.get(0);

		//step 3
		BugTag tag = BugTag.RESOLVED;
		
		IUser admin = bugTrap.getUserManager().getUser("ADMIN");
		bugTrap.getUserManager().loginAs(admin);
		//step 4a
		The issuer does not have the permission to assign the tag
		1. The use case ends here.		
	}

	@Test
	public void notAuthorizedTest() {
		try {
			bugTrap.getFormFactory().makeBugReportUpdateForm();
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
		}
	}
	
	@Test
	public void varsNotFilledTest() {
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);
		
		try {
			BugReportUpdateForm form = bugTrap.getFormFactory().makeBugReportUpdateForm();
			bugTrap.getBugReportManager().updateBugReport(form.getBugReport(), form.getBugTag());
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
		}
		catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void nullFormTest() {
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);
		
		try {
			bugTrap.getBugReportManager().updateBugReport(null, null);
			fail("should throw exception");
		}
		catch (IllegalArgumentException e) {
		}
	}
}
