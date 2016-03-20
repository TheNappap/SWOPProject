package tests;


import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.FormFactory;
import model.projects.Project;
import model.projects.ProjectManager;
import model.projects.ProjectTeam;
import model.projects.Role;
import model.projects.Version;
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
		team.addMember(dev, Role.LEAD);
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, team, new Version(1, 0, 0));
		
	}

	@Test
	public void assignProgrammerToProjectTest(){
		//login
		User dev = bugTrap.getUserManager().getUser("DEV");
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
		List<Project> projects = bugTrap.getProjectManager().getProjectsForLeadDeveloper((Developer) dev);
		//step 3
		Project project =  projects.get(0);
		form.setProject(project);
		//step 4
		List<Developer> devs = bugTrap.getUserManager().getDevelopers();
		//step 5
		Developer developer = devs.get(1);
		form.setDeveloper(developer);
		//step 6 TODO get roles not yet assigned to developer?
		//bugTrap.getProjectManager().getRoles();
		//step 7
		form.setRole(Role.PROGRAMMER);

		bugTrap.getProjectManager().assignToProject(form);

		Assert.assertEquals(project.getTeam().getProgrammers().get(0), developer);
	}

	@Test
	public void assignLeadToProjectTest() throws UnauthorizedAccessException {
		//login
				User dev = bugTrap.getUserManager().getUser("DEV");
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
				List<Project> projects = bugTrap.getProjectManager().getProjectsForLeadDeveloper((Developer) dev);
				//step 3
				Project project =  projects.get(0);
				form.setProject(project);
				//step 4
				List<Developer> devs = bugTrap.getUserManager().getDevelopers();
				//step 5
				Developer developer = devs.get(1);
				form.setDeveloper(developer);
				//step 6 TODO get roles not yet assigned to developer?
				//bugTrap.getProjectManager().getRoles();
				//step 7
				form.setRole(Role.LEAD);

				bugTrap.getProjectManager().assignToProject(form);

				Assert.assertEquals(project.getTeam().getLeadDeveloper(), developer);
			}

	@Test
	public void assignTesterToProjectTest() throws UnauthorizedAccessException {
		//login
				User dev = bugTrap.getUserManager().getUser("DEV");
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
				List<Project> projects = bugTrap.getProjectManager().getProjectsForLeadDeveloper((Developer) dev);
				//step 3
				Project project =  projects.get(0);
				form.setProject(project);
				//step 4
				List<Developer> devs = bugTrap.getUserManager().getDevelopers();
				//step 5
				Developer developer = devs.get(1);
				form.setDeveloper(developer);
				//step 6 TODO get roles not yet assigned to developer?
				//bugTrap.getProjectManager().getRoles();
				//step 7
				form.setRole(Role.TESTER);

				bugTrap.getProjectManager().assignToProject(form);

				Assert.assertEquals(project.getTeam().getTesters().get(0), developer);
			}
}
