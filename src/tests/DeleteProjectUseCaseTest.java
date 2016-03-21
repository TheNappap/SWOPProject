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
import model.projects.ProjectTeam;
import model.projects.Version;
import model.projects.forms.ProjectDeleteForm;
import model.users.IUser;

public class DeleteProjectUseCaseTest {

	private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		//add users
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, new ProjectTeam(), new Version(1, 0, 0));
	}

	@Test
	public void DeleteProjectTest() {
		assertFalse(bugTrap.getProjectManager().getProjects().isEmpty());
		
		//login
		IUser admin = bugTrap.getUserManager().getUser("ADMIN");
		bugTrap.getUserManager().loginAs(admin);
				
		//step 1
		ProjectDeleteForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProjectDeleteForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 2
		List<IProject> list = bugTrap.getProjectManager().getProjects();
		//step 3
		form.setProject(list.get(0));
		//step 4
		bugTrap.getProjectManager().deleteProject(form);
		
		assertTrue(bugTrap.getProjectManager().getProjects().isEmpty());
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
			bugTrap.getProjectManager().deleteProject(form);
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
		}
		catch (NullPointerException e) {
		}
	}
	
	@Test
	public void nullFormTest() {
		//login
		IUser admin = bugTrap.getUserManager().getAdmins().get(0);
		bugTrap.getUserManager().loginAs(admin);
		
		ProjectDeleteForm form = null;
		try {
			bugTrap.getProjectManager().deleteProject(form);
			fail("should throw exception");
		}
		catch (IllegalArgumentException e) {
		}
	}
}
