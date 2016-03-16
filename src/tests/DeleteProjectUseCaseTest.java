package tests;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.ProjectController;
import controllers.UserController;
import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.Project;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectDeleteForm;
import model.users.Administrator;
import model.users.Developer;
import model.users.UserCategory;
import model.users.UserManager;

public class DeleteProjectUseCaseTest {

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
	public void DeleteProjectTest() {
		
		try {
			//step 1
			ProjectDeleteForm form =  projectController.getProjectDeleteForm();
			//step 2
			List<Project> list = projectController.getProjectList();
			//step 3
			form.setProject(list.get(0));
			//step 4
			projectController.deleteProject(form);
		} catch (UnauthorizedAccessException e) {
			fail("admin not logged in");
		}
	}

}
