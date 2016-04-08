package tests;


import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.IProject;
import model.projects.Role;
import model.projects.Version;
import model.projects.forms.ProjectAssignForm;
import model.users.Developer;
import model.users.IUser;

public class AssignProjectUseCaseTest {

	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		
		//add user
		IUser admin =  bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		Developer lead = bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV2");
		bugTrap.getUserManager().loginAs(admin);
		
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		project.setLeadDeveloper(lead);
	}

	@Test
	public void assignProgrammerToProjectTest() throws UnauthorizedAccessException {
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);

		//step 1
		ProjectAssignForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProjectAssignForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 2
		List<IProject> projects = bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper();
		//step 3
		IProject project =  projects.get(0);
		form.setProject(project);
		//step 4
		List<IUser> devs = bugTrap.getUserManager().getDevelopers();
		//step 5
		IUser developer = devs.get(1);
		form.setDeveloper(developer);
		//step 6
		List<Role> roles = project.getRolesNotAssignedTo(developer);
		//step 7
		Role role = roles.get(0);//Programmer
		assertTrue(role == Role.PROGRAMMER);
		form.setRole(role);

		bugTrap.getProjectManager().assignToProject(form.getProject(), form.getDeveloper(), form.getRole());

		Assert.assertEquals(project.getProgrammers().get(0), developer);
	}

	@Test (expected = UnsupportedOperationException.class)
	public void assignLeadToProjectTest(){
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);
				
		
		//step 1
		ProjectAssignForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProjectAssignForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 2
		List<IProject> projects = null;
		try {
			projects = bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 3
		IProject project =  projects.get(0);
		form.setProject(project);
		//step 4
		List<IUser> devs = bugTrap.getUserManager().getDevelopers();
		//step 5
		IUser developer = devs.get(1);
		form.setDeveloper(developer);
		//step 6
		//step 7
		form.setRole(Role.LEAD);

		bugTrap.getProjectManager().assignToProject(form.getProject(), form.getDeveloper(), form.getRole());

		Assert.assertEquals(project.getLeadDeveloper(), developer);
	}

	@Test 
	public void assignTesterToProjectTest(){
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);
				
		
		//step 1
		ProjectAssignForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProjectAssignForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 2
		List<IProject> projects = null;
		try {
			projects = bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 3
		IProject project =  projects.get(0);
		form.setProject(project);
		//step 4
		List<IUser> devs = bugTrap.getUserManager().getDevelopers();
		//step 5
		IUser developer = devs.get(1);
		form.setDeveloper(developer);
		//step 6
		List<Role> roles = project.getRolesNotAssignedTo(developer);
		//step 7
		Role role = roles.get(1);//Tester
		assertTrue(role == Role.TESTER);
		form.setRole(role);

		bugTrap.getProjectManager().assignToProject(form.getProject(), form.getDeveloper(), form.getRole());

		Assert.assertEquals(project.getTesters().get(0), developer);
	}
	
	@Test
	public void developerIsNowhereLeadTest() {
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV2");

		bugTrap.getUserManager().loginAs(dev);
		
		//step 2a
		try {
			bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper();
			fail("should throw exception");
		} catch (UnsupportedOperationException e) {
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
	}
	
	@Test
	public void notAuthorizedTest() {
		try {
			bugTrap.getFormFactory().makeProjectAssignForm();
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
		}
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void varsNotFilledTest() throws UnauthorizedAccessException {
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);
		
		ProjectAssignForm form = bugTrap.getFormFactory().makeProjectAssignForm();
		bugTrap.getProjectManager().assignToProject(form.getProject(), form.getDeveloper(), form.getRole());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void nullFormTest() {
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);
		
		bugTrap.getProjectManager().assignToProject(null, null, null);
	}
}
