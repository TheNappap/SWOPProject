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
import model.bugreports.bugtag.New;
import model.bugreports.forms.BugReportCreationForm;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;
import model.projects.forms.SubsystemCreationForm;
import model.users.IUser;

public class CreateBugReportUseCaseTest {

	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		
		//add user
		IUser dev = bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		//add project
		IProject project = bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		//add subsystem to project
		ISubsystem subsystem = bugTrap.getProjectManager().createSubsystem("name", "description", project, project, Version.firstVersion());
		bugTrap.getProjectManager().createSubsystem("name2", "description2", project, project, Version.firstVersion());
		//add bugreport (for dependency)
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), subsystem, dev, new ArrayList<>(), new ArrayList<>(), new New());
		
	}

	@Test
	public void createBugReportTest() {
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);		
				
		
		//step 1
		BugReportCreationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeBugReportCreationForm();
		} catch (UnauthorizedAccessException e1) {
			fail("not authorized");
			e1.printStackTrace();
		}
		//step 2
		List<IProject> projects = null;
		try {
			projects = bugTrap.getProjectManager().getProjects();
		} catch (UnauthorizedAccessException e1) {
			fail("not authorized");
			e1.printStackTrace();
		}
		//step 3
		IProject project = projects.get(0);
		//step 4
		List<ISubsystem> subsystems = project.getAllDirectOrIndirectSubsystems();
		//step 5
		ISubsystem subsystem = subsystems.get(0);
		form.setSubsystem(subsystem);
		//step 6
		form.setIssuer(dev);

		//step 7
		form.setTitle("Bug");
		form.setDescription("a Bug");
		//step 8
		List<IBugReport> bugReports = null;
		try {
			bugReports = bugTrap.getBugReportManager().getBugReportsForProject(project);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 9
		List<IBugReport> dependencies = new ArrayList<IBugReport>();
		dependencies.add(bugReports.get(0));
		form.setDependsOn(dependencies);
		//step 10
		IBugReport bugReport = null;
		try {
			bugReport = bugTrap.getBugReportManager().addBugReport("Bug", "a Bug", new Date(1302), subsystem, dev, dependencies, new ArrayList<IUser>(), new New());
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}

		Assert.assertEquals("Bug",bugReport.getTitle());
		Assert.assertEquals("a Bug",bugReport.getDescription());
		Assert.assertEquals(subsystem,bugReport.getSubsystem());
		Assert.assertEquals(project,bugReport.getSubsystem().getProject());
		Assert.assertEquals(dependencies,bugReport.getDependsOn());
		Assert.assertEquals(dev,bugReport.getIssuedBy());	
	}
	
	@Test
	public void notAuthorizedTest() {
		try {
			bugTrap.getFormFactory().makeSubsystemCreationForm();
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
		}
	}
	
	@Test
	public void varsNotFilledTest() {
		//login
		IUser admin = bugTrap.getUserManager().getAdmins().get(0);
		bugTrap.getUserManager().loginAs(admin);
		
		try {
			SubsystemCreationForm form = bugTrap.getFormFactory().makeSubsystemCreationForm();
			bugTrap.getProjectManager().createSubsystem(form.getName(), form.getDescription(), form.getProject(), form.getParent(), Version.firstVersion());
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
		IUser admin = bugTrap.getUserManager().getAdmins().get(0);
		bugTrap.getUserManager().loginAs(admin);
		
		try {
			bugTrap.getProjectManager().createSubsystem(null, null, null, null, null);
			fail("should throw exception");
		}
		catch (IllegalArgumentException e) {
		}
	}

}
