package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import controllers.ProjectController;
import controllers.UserController;
import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.Project;
import model.projects.Role;
import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.users.Administrator;
import model.users.Developer;
import model.users.User;
import model.users.UserCategory;
import model.users.UserManager;

public class AssignProjectUseCaseTest {

	private ProjectController projectController;
	private UserController userController;
	private BugTrap bugTrap;
	private Developer lead;
	private Project project;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		projectController = new ProjectController(bugTrap);
		userController = new UserController(bugTrap);
		
		//add user
		UserManager userMan = (UserManager) userController.getBugTrap().getUserDAO();
		userMan.createUser(UserCategory.ADMIN, "", "", "", "ADMIN");
		userMan.createUser(UserCategory.DEVELOPER, "", "", "", "Dev");
		Administrator admin =  (Administrator) userController.getUserList(UserCategory.ADMIN).get(0);
		userController.loginAs(admin);
		
		lead = (Developer) userController.getUserList(UserCategory.DEVELOPER).get(0);
		
		ProjectCreationForm form = projectController.getProjectCreationForm();
		form.setBudgetEstimate(5000);
		form.setDescription("Setup project");
		form.setLeadDeveloper(lead);
		form.setName("Project S");
		form.setStartDate(new Date(1302));
		
		projectController.createProject(form);
		project = projectController.getProjectList().get(0);
		
		lead = projectController.getProjectList().get(0).getTeam().getLeadDeveloper();
		userController.loginAs(lead);
		
	}

	@Test
	public void assignToProjectTest() {
		
		try {
			//step 1
			ProjectAssignForm form = projectController.getProjectAssignForm();
			//step 2
			ArrayList<Project> list =  projectController.getProjectsForLeadDeveloper(lead);
			//step 3
			Project  project = list.get(0);
			form.setProject(project);
			//step 4
			ArrayList<User> devs = userController.getUserList(UserCategory.DEVELOPER);
			//step 5
			Developer dev = (Developer) devs.get(0);
			form.setDeveloper(dev);
			//step 6
			Role[] roles = Role.values();
			//step 7
			Role role = roles[2];
			form.setRole(role);
			//step 8
			projectController.assignToProject(form);
			
			assertEquals(1, project.getTeam().getTesters().size());
		} catch (UnauthorizedAccessException e) {
			fail("developer not logged in");
		}
	}

}
