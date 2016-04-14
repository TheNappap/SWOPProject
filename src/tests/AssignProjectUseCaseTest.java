package tests;


import static org.junit.Assert.assertNotEquals;
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
import model.users.IUser;

public class AssignProjectUseCaseTest {

	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		//Make System.
		bugTrap = new BugTrap();
		
		//Add Users.
		bugTrap.getUserManager().createIssuer("", "", "", "ISSUER");
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV2");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		
		//Add a Project
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		//Set Lead.
		bugTrap.getProjectManager().getProjects().get(0).setLeadDeveloper(bugTrap.getUserManager().getUser("DEV"));
		//Log off.
		bugTrap.getUserManager().logOff();
	}

	@Test
	public void assignProgrammerToProjectTest() {
		//Log in.	
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));

		//1. The developer indicates he wants to assign another developer.
		ProjectAssignForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProjectAssignForm();
		} catch (UnauthorizedAccessException e) { fail("not authorized"); }
		
		//2. The system shows a list of the projects in which the logged in user is assigned as lead developer.
		List<IProject> projects = bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper();
		
		//3. The lead developer selects one of his projects.
		IProject project =  projects.get(0);
		form.setProject(project);
		
		//4. The system shows a list of other developers to assign.
		List<IUser> devs = bugTrap.getUserManager().getDevelopers();
		
		//5. The lead developer selects one of these other developers.
		IUser developer = devs.get(1);
		form.setDeveloper(developer);
		
		//6. The system shows a list of possible (i.e. not yet assigned) roles for the selected developer.
		List<Role> roles = project.getRolesNotAssignedTo(developer);
		
		//7. The lead developer selects a role.
		Role role = roles.get(0);//Programmer
		assertTrue(role == Role.PROGRAMMER);
		form.setRole(role);

		//8. The systems assigns the selected role to the selected developer.
		bugTrap.getProjectManager().assignToProject(form.getProject(), form.getDeveloper(), form.getRole());

		//Confirm.
		Assert.assertEquals(project.getProgrammers().get(0), developer);
	}

	@Test
	public void assignLeadToProjectTest(){
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
				
		
		//1. The developer indicates he wants to assign another developer.
		ProjectAssignForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProjectAssignForm();
		} catch (UnauthorizedAccessException e) { fail("not authorized");	}
		
		//2. The system shows a list of the projects in which the logged in user is assigned as lead developer.
		List<IProject> projects = bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper();
		
		//3. The lead developer selects one of his projects.
		IProject project =  projects.get(0);
		form.setProject(project);
		
		//4. The system shows a list of other developers to assign.
		List<IUser> devs = bugTrap.getUserManager().getDevelopers();
		
		//5. The lead developer selects one of these other developers.
		IUser developer = devs.get(1);
		form.setDeveloper(developer);
		
		//6. The system shows a list of possible (i.e. not yet assigned) roles for the selected developer.
		//7. The lead developer selects a role.
		form.setRole(Role.LEAD);

		//8. The systems assigns the selected role to the selected developer.
		//Shouldn't work because someone else is Lead already.
		try {
			bugTrap.getProjectManager().assignToProject(form.getProject(), form.getDeveloper(), form.getRole());
		} catch (UnsupportedOperationException e) {  }
		
		assertNotEquals(developer, project.getLeadDeveloper());
	}

	@Test 
	public void assignTesterToProjectTest(){
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));

		//1. The developer indicates he wants to assign another developer.
		ProjectAssignForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProjectAssignForm();
		} catch (UnauthorizedAccessException e) { fail ("Not authorised"); }
		
		//2. The system shows a list of the projects in which the logged in user is assigned as lead developer.
		List<IProject> projects = bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper();
		
		//3. The lead developer selects one of his projects.
		IProject project =  projects.get(0);
		form.setProject(project);
		
		//4. The system shows a list of other developers to assign.
		List<IUser> devs = bugTrap.getUserManager().getDevelopers();
		
		//5. The lead developer selects one of these other developers.
		IUser developer = devs.get(1);
		form.setDeveloper(developer);
		
		//6. The system shows a list of possible (i.e. not yet assigned) roles for the selected developer.
		List<Role> roles = project.getRolesNotAssignedTo(developer);
		
		//7. The lead developer selects a role.
		Role role = roles.get(1);//Tester
		assertTrue(role == Role.TESTER);
		form.setRole(role);

		//8. The systems assigns the selected role to the selected developer.
		bugTrap.getProjectManager().assignToProject(form.getProject(), form.getDeveloper(), form.getRole());

		//Confirm.
		Assert.assertEquals(project.getTesters().get(0), developer);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void developerIsNowhereLeadTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV2"));
		
		//1. The developer indicates he wants to assign another developer.
		try {
			bugTrap.getFormFactory().makeProjectAssignForm();
		} catch (UnauthorizedAccessException e) { fail ("Not authorised"); }
		
		//2. The system shows a list of the projects in which the logged in user is assigned as lead developer.
		bugTrap.getProjectManager().getProjectsForSignedInLeadDeveloper();
	}
	
	@Test
	public void notAuthorizedTest() {
		try {
			bugTrap.getFormFactory().makeProjectAssignForm();
			fail("not authorised");
		} catch (UnauthorizedAccessException e) { }
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void varsNotFilledTest() throws UnauthorizedAccessException {
		//login
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		
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
