package tests;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.ProjectController;
import controllers.UserController;
import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.Project;
import model.projects.forms.ProjectCreationForm;
import model.users.Administrator;
import model.users.Developer;
import model.users.User;
import model.users.UserCategory;
import model.users.UserManager;

public class CreateProjectUseCaseTest {

	private ProjectController projectController;
	private UserController userController;
	private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		projectController = new ProjectController(bugTrap);
		userController = new UserController(bugTrap);
		
		//add user
		UserManager userMan = (UserManager) userController.getBugTrap().getUserDAO();
		userMan.createUser(UserCategory.DEVELOPER, "", "", "", "Dev");
		userMan.createUser(UserCategory.ADMIN, "", "", "", "ADMIN");
		Administrator admin =  (Administrator) userController.getUserList(UserCategory.ADMIN).get(0);
		userController.loginAs(admin);
	}

	@Test
	public void createProjectTest() {
		
		try {
			//step 1
			ProjectCreationForm form = projectController.getProjectCreationForm();
			//step 2 & 3
			form.setBudgetEstimate(5000);
			form.setDescription("Setup project");
			form.setName("Project S");
			form.setStartDate(new Date(1302));
			//step 4
			ArrayList<User> list = userController.getUserList(UserCategory.DEVELOPER);
			//step 5
			Developer lead = (Developer) list.get(0);
			form.setLeadDeveloper(lead);
			//step 6
			projectController.createProject(form);

			Project project = projectController.getProjectList().get(0);
			Assert.assertEquals("Project S", project.getName());
			Assert.assertEquals("Setup project", project.getDescription());
			Assert.assertEquals(new Date(1302), project.getStartDate());
			Assert.assertEquals(5000, project.getBudgetEstimate(), 0.001);
		} catch (UnauthorizedAccessException e) {
			fail("admin not logged in");
		}
	}

}
