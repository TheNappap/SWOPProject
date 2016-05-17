package tests;


import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.projects.IProject;
import model.projects.Role;
import model.projects.forms.ProjectAssignForm;
import model.users.IUser;

public class AssignProjectUseCaseTest extends BugTrapTest {

	@Test
	public void assignProgrammerToProjectTest() {
		//Log in.	
		bugTrap.getUserManager().loginAs(lead);

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
		bugTrap.getUserManager().getDevelopers();
		
		//5. The lead developer selects one of these other developers.
		IUser developer = tester;
		form.setDeveloper(developer);
		
		//6. The system shows a list of possible (i.e. not yet assigned) roles for the selected developer.
		List<Role> roles = project.getRolesNotAssignedTo(developer);
		
		//7. The lead developer selects a role.
		Role role = roles.get(0);//Programmer
		assertTrue(role == Role.PROGRAMMER);
		form.setRole(role);

		//8. The systems assigns the selected role to the selected developer.
		try {
			projectController.assignToProject(form);
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}

		//Confirm.
		Assert.assertEquals(project.getProgrammers().get(1), developer);
	}

	@Test
	public void assignLeadToProjectTest(){
		//Log in.
		bugTrap.getUserManager().loginAs(lead);

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
			projectController.assignToProject(form);
			fail("Should not be allowed.");
		} catch (UnsupportedOperationException e) {
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}

		assertNotEquals(developer, project.getLeadDeveloper());
	}

	@Test 
	public void assignTesterToProjectTest(){
		//Log in.
		bugTrap.getUserManager().loginAs(lead);

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
		bugTrap.getUserManager().getDevelopers();
		
		//5. The lead developer selects one of these other developers.
		IUser developer = prog;
		form.setDeveloper(developer);
		
		//6. The system shows a list of possible (i.e. not yet assigned) roles for the selected developer.
		List<Role> roles = project.getRolesNotAssignedTo(developer);
		
		//7. The lead developer selects a role.
		Role role = roles.get(0); //Tester
		assertTrue(role == Role.TESTER);
		form.setRole(role);

		//8. The systems assigns the selected role to the selected developer.
		try {
			projectController.assignToProject(form);
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}

		//Confirm.
		Assert.assertTrue(project.getTesters().contains(developer));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void developerIsNowhereLeadTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(tester);
		
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
	
	@Test (expected = NullPointerException.class)
	public void varsNotFilledTest() throws UnauthorizedAccessException {
		//login
		bugTrap.getUserManager().loginAs(lead);
		
		ProjectAssignForm form = bugTrap.getFormFactory().makeProjectAssignForm();
		projectController.assignToProject(form);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void nullFormTest() {
		//login
		bugTrap.getUserManager().loginAs(lead);

		try {
			projectController.assignToProject(null);
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}
	}
}
