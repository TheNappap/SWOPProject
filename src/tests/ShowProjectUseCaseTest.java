package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.ProjectController;
import controllers.UserController;
import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.Project;
import model.projects.Subsystem;
import model.projects.Version;
import model.projects.forms.ProjectCreationForm;
import model.users.Administrator;
import model.users.Developer;
import model.users.UserCategory;
import model.users.UserManager;

public class ShowProjectUseCaseTest {
	
	private ProjectController projectController;
	private UserController userController;
	private BugTrap bugTrap;
	private Developer lead;

	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		projectController = new ProjectController(bugTrap);
		userController = new UserController(bugTrap);
		lead = new Developer("John", "Johnny", "Johnson", "Boss");
		
		//add user
		UserManager userMan = (UserManager) userController.getBugTrap().getUserManager();
		userMan.createUser(UserCategory.DEVELOPER, "", "", "", "Dev");
		userMan.createUser(UserCategory.ADMIN, "", "", "", "ADMIN");
		Administrator admin =  (Administrator) userController.getUserList(UserCategory.ADMIN).get(0);
		userController.loginAs(admin);
		
		ProjectCreationForm form = projectController.getProjectCreationForm();
		form.setBudgetEstimate(5000);
		form.setDescription("Setup project");
		form.setLeadDeveloper(lead);
		form.setName("Project X");
		form.setStartDate(new Date(12));
		
		projectController.createProject(form);
	}

	@Test
	public void test() {
		
		List<Project> list;
		try {
			//step 1
			//user indicates he wants to inspect a project
			//step 2
			list = projectController.getProjectList();
			//step 3
			Project project = list.get(0);
			//step 4
			ArrayList<Subsystem> subsystems = project.getSubsystems();
			
			Assert.assertEquals("Project X", project.getName());
			Assert.assertEquals("Setup project", project.getDescription());
			Assert.assertEquals(new Date(12), project.getStartDate());
			Assert.assertEquals(5000, project.getBudgetEstimate(), 0.001);
			Assert.assertEquals(new Version(1, 0, 0), project.getVersion());
			Assert.assertEquals(lead, project.getTeam().getLeadDeveloper());
			Assert.assertEquals(0, subsystems.size());
		} catch (UnauthorizedAccessException e) {
			fail("no user logged in");
		}
	}

}
