package tests;

import static org.junit.Assert.assertEquals;
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
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectForkForm;
import model.users.IUser;

public class CreateProjectUseCaseTest {

	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		//add users
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void createNewProjectTest() {
		IUser admin = bugTrap.getUserManager().getUser("ADMIN");
		bugTrap.getUserManager().loginAs(admin);
		
		//step 1 & 2
		ProjectCreationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProjectCreationForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		//step 3
		form.setName("name");
		form.setDescription("descr");
		form.setStartDate(new Date(2005, 2, 12));
		form.setBudgetEstimate(1234);
		
		//step 4
		List<IUser> devs = bugTrap.getUserManager().getDevelopers();
		
		//step 5
		IUser dev = devs.get(0);
		form.setLeadDeveloper(dev);
		
		//step 6
		IProject p = bugTrap.getProjectManager().createProject(form);
		
		
		assertEquals(p.getName(), "name");
		assertEquals(p.getDescription(), "descr");
		assertEquals(p.getStartDate(), new Date(2005, 2, 12));
		assertEquals(1234, p.getBudgetEstimate(), 0.01);
		assertEquals(p.getVersion(), new Version(1, 0, 0));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void createForkProjectTest() {
		//login
		IUser admin = bugTrap.getUserManager().getAdmins().get(0);
		bugTrap.getUserManager().loginAs(admin);
		//addProject
		bugTrap.getProjectManager().createProject("name", "description", new Date(2005, 1, 2), new Date(2005, 2, 12), 1234, new ProjectTeam(), new Version(1, 0, 0));
		
		//create fork
		//step 1a
		List<IProject> projects = null;
		try {
			projects = bugTrap.getProjectManager().getProjects();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		//step 2a
		IProject project = projects.get(0);
		
		//step 3a
		ProjectForkForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProjectForkForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		//step 4a
		form.setProject(project);
		form.setStartDate(new Date(2010, 3, 21));
		form.setBudgetEstimate(1234);
		form.setVersion(new Version(2, 0, 1));
				
		//step 5a
		List<IUser> devs = bugTrap.getUserManager().getDevelopers();
		IUser dev = devs.get(0);
		form.setLeadDeveloper(dev);
		IProject fork = bugTrap.getProjectManager().createFork(form);

		assertEquals(fork.getName(), project.getName());
		assertEquals(fork.getDescription(), project.getDescription());
		assertEquals(fork.getVersion(), new Version(2, 0, 1));
		assertEquals(fork.getCreationDate(), project.getCreationDate());
		assertEquals(fork.getStartDate(), new Date(2010, 3, 21));
		assertEquals(1234, fork.getBudgetEstimate(), 0.01);
	}
	
	@Test
	public void notAuthorizedTest() {
		try {
			bugTrap.getFormFactory().makeProjectCreationForm();
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
		}
		try {
			bugTrap.getFormFactory().makeProjectForkForm();
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
			ProjectCreationForm form = bugTrap.getFormFactory().makeProjectCreationForm();
			bugTrap.getProjectManager().createProject(form);
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
		}
		catch (NullPointerException e) {
		}
		catch (IllegalArgumentException e) {
		}
		
		try {
			ProjectForkForm form = bugTrap.getFormFactory().makeProjectForkForm();
			bugTrap.getProjectManager().createFork(form);
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
		}
		catch (NullPointerException e){
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
			bugTrap.getProjectManager().createProject(null);
			fail("should throw exception");
		}
		catch (IllegalArgumentException e) {
		}
		
		try {
			bugTrap.getProjectManager().createFork(null);
			fail("should throw exception");
		}
		catch (IllegalArgumentException e){
		}
	}
	
}
