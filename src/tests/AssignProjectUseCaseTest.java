package tests;


import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import model.projects.*;
import model.users.IUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.FormFactory;
import model.projects.forms.ProjectAssignForm;
import model.users.Developer;
import model.users.User;

public class AssignProjectUseCaseTest {

	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		
		//add user
		Developer dev = bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV2");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		
		ProjectTeam team = new ProjectTeam();
		IProject project = bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, team, new Version(1, 0, 0));
		project.setLeadDeveloper(dev);
	}

	@Test
	public void assignProgrammerToProjectTest(){
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
		//step 2 TODO get logged in user (is dev?)
		List<IProject> projects = bugTrap.getProjectManager().getProjectsForLeadDeveloper((Developer) dev);
		//step 3
		IProject project =  projects.get(0);
		form.setProject(project);
		//step 4
		List<IUser> devs = bugTrap.getUserManager().getDevelopers();
		//step 5
		IUser developer = devs.get(1);
		form.setDeveloper(developer);
		//step 6 TODO get roles not yet assigned to developer?
		//bugTrap.getProjectManager().getRoles();
		//step 7
		form.setRole(Role.PROGRAMMER);

		bugTrap.getProjectManager().assignToProject(form);

		Assert.assertEquals(project.getProgrammers().get(0), developer);
	}

	@Test (expected = UnsupportedOperationException.class)
	public void assignLeadToProjectTest() throws UnauthorizedAccessException {
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
		//step 2 TODO get logged in user (is dev?)
		List<IProject> projects = bugTrap.getProjectManager().getProjectsForLeadDeveloper((Developer) dev);
		//step 3
		IProject project =  projects.get(0);
		form.setProject(project);
		//step 4
		List<IUser> devs = bugTrap.getUserManager().getDevelopers();
		//step 5
		IUser developer = devs.get(1);
		form.setDeveloper(developer);
		//step 6 TODO get roles not yet assigned to developer?
		//bugTrap.getProjectManager().getRoles();
		//step 7
		form.setRole(Role.LEAD);

		bugTrap.getProjectManager().assignToProject(form);

		Assert.assertEquals(project.getLeadDeveloper(), developer);
	}

	@Test 
	public void assignTesterToProjectTest() throws UnauthorizedAccessException {
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
		//step 2 TODO get logged in user (is dev?)
		List<IProject> projects = bugTrap.getProjectManager().getProjectsForLeadDeveloper((Developer) dev);
		//step 3
		IProject project =  projects.get(0);
		form.setProject(project);
		//step 4
		List<IUser> devs = bugTrap.getUserManager().getDevelopers();
		//step 5
		IUser developer = devs.get(1);
		form.setDeveloper(developer);
		//step 6 TODO get roles not yet assigned to developer?
		//bugTrap.getProjectManager().getRoles();
		//step 7
		form.setRole(Role.TESTER);

		bugTrap.getProjectManager().assignToProject(form);

		Assert.assertEquals(project.getTesters().get(0), developer);
	}
}
