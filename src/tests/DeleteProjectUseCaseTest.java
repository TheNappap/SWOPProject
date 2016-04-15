package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.notifications.RegistrationType;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;
import model.projects.forms.ProjectDeleteForm;

public class DeleteProjectUseCaseTest {

	private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		//Make System.
		bugTrap = new BugTrap();
		
		//Make Users.
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createIssuer("", "", "", "ISSUER");
		
		//Log in as Administrator and create project/subsystem.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		bugTrap.getProjectManager().createSubsystem("name", "description", project, project);
		ISubsystem subsystem = bugTrap.getProjectManager().getSubsystemWithName("name");
		bugTrap.getProjectManager().createSubsystem("name2", "description2", project, project);
		
		//Log in as Developer, add BugReport and log off.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), subsystem, bugTrap.getUserManager().getUser("DEV"), new ArrayList<>(), new ArrayList<>(), BugTag.NEW);
		IBugReport bugreport = bugTrap.getBugReportManager().getBugReportsForSystem(subsystem).get(0);
		bugTrap.getUserManager().logOff();
		
		//Log in as Administrator and register for notifications.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getNotificationManager().registerForNotification(RegistrationType.CREATE_BUGREPORT, project, null);
		bugTrap.getNotificationManager().registerForNotification(RegistrationType.BUGREPORT_CHANGE, subsystem, null);
		bugTrap.getNotificationManager().registerForNotification(RegistrationType.CREATE_COMMENT, bugreport, null);
		
		assertEquals(3, bugTrap.getNotificationManager().getRegistrationsLoggedInUser().size());
		bugTrap.getUserManager().logOff();
	}

	@Test
	public void DeleteProjectTest() {
		//Log in as Administrator.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		
		try { 
			//Make sure there's a project.
			assertFalse(bugTrap.getProjectManager().getProjects().isEmpty());
					
			//1. The administrator indicates he wants to delete a project.
			ProjectDeleteForm form = bugTrap.getFormFactory().makeProjectDeleteForm();
			
			//2. The system shows a list of all projects.
			List<IProject> list = bugTrap.getProjectManager().getProjects();
			
			//3. The administrator selects a project.
			form.setProject(list.get(0));
			
			//4. The system deletes a project and recursively all subsystems that are
			//part of the project. All bug reports fore those subsystem are also
			//removed from BugTrap.
			bugTrap.getProjectManager().deleteProject(form.getProject());
			
			//confirm
			assertTrue(bugTrap.getProjectManager().getProjects().isEmpty());
			assertTrue(bugTrap.getBugReportManager().getBugReportList().isEmpty());
			assertTrue(bugTrap.getNotificationManager().getRegistrationsLoggedInUser().isEmpty());
		} catch (UnauthorizedAccessException e) { fail("not authorized"); }
	}
	
	@Test
	public void notAuthorizedTest() {
		//Should be logged in to delete Projects.
		try {
			bugTrap.getFormFactory().makeProjectDeleteForm();
			fail("Can't be not logged in to delete Projects.");
		} catch (UnauthorizedAccessException e) { }
		
		//Developer can't delete Projects.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		try {
			bugTrap.getFormFactory().makeProjectDeleteForm();
			fail("Developer can't delete projects.");
		} catch (UnauthorizedAccessException e) { }
		
		//Issuer can't delete Projects.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ISSUER"));
		try {
			bugTrap.getFormFactory().makeProjectDeleteForm();
			fail("Issuer can't delete projects.");
		} catch (UnauthorizedAccessException e) { }
	}
	
	@Test
	public void varsNotFilledTest() {
		//Log in as Administrator.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		
		try {
			bugTrap.getProjectManager().deleteProject(bugTrap.getFormFactory().makeProjectDeleteForm().getProject());
			fail("Can't pass nulls.");
		} 
		catch (UnauthorizedAccessException e) { fail("not authorized"); }
		catch (IllegalArgumentException e) { }
	}
	
	@Test
	public void nullFormTest() {
		//Log in as Administrator.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		
		try {
			bugTrap.getProjectManager().deleteProject(null);
			fail("Can't pass nulls.");
		}
		catch (IllegalArgumentException e) { } catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
	}
}
