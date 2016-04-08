package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.IProject;
import model.projects.Version;
import model.projects.forms.ProjectDeleteForm;

public class DeleteProjectUseCaseTest {

	private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		//Make system.
		bugTrap = new BugTrap();
		
		//Add Users.
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createIssuer("", "", "", "ISSUER");
		
		//Log in as Administrator, create project and log off.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
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
			
			//Is Project gone?
			assertTrue(bugTrap.getProjectManager().getProjects().isEmpty());
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
		catch (IllegalArgumentException e) { } 
		catch (UnauthorizedAccessException e) { fail("not authorized"); }
	}
}
