package tests;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.IProject;
import model.projects.Version;
import model.projects.forms.ProjectDeleteForm;
import model.users.IUser;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class DeleteProjectUseCaseTest {

	private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		//add users
		IUser admin = bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().loginAs(admin);
		
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		bugTrap.getUserManager().logOff();
	}

	@Test
	public void DeleteProjectTest() {
		//login
		IUser admin = bugTrap.getUserManager().getUser("ADMIN");
		bugTrap.getUserManager().loginAs(admin);
		
		try {
			assertFalse(bugTrap.getProjectManager().getProjects().isEmpty());
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
				
		//step 1
		ProjectDeleteForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProjectDeleteForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 2
		List<IProject> list = null;
		try {
			list = bugTrap.getProjectManager().getProjects();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 3
		form.setProject(list.get(0));
		//step 4
		try {
			bugTrap.getProjectManager().deleteProject(form.getProject());
		} catch (UnauthorizedAccessException e1) {
			fail("not authorized");
			e1.printStackTrace();
		}
		
		try {
			assertTrue(bugTrap.getProjectManager().getProjects().isEmpty());
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
	}
	
	@Test
	public void notAuthorizedTest() {
		try {
			bugTrap.getFormFactory().makeProjectDeleteForm();
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
			ProjectDeleteForm form = bugTrap.getFormFactory().makeProjectDeleteForm();
			bugTrap.getProjectManager().deleteProject(form.getProject());
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
			bugTrap.getProjectManager().deleteProject(null);
			fail("should throw exception");
		}
		catch (IllegalArgumentException e) {
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
	}
}
