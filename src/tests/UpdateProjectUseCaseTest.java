package tests;

import static org.junit.Assert.fail;

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
import model.projects.Version;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectUpdateForm;
import model.users.Administrator;
import model.users.Developer;
import model.users.UserCategory;
import model.users.UserManager;

public class UpdateProjectUseCaseTest {

	private ProjectController projectController;
	private UserController userController;
	private BugTrap bugTrap;
	private Developer lead;
	private Developer colleague;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		projectController = new ProjectController(bugTrap);
		userController = new UserController(bugTrap);
		lead = new Developer("John", "Johnny", "Johnson", "Boss");
		colleague = new Developer("Donald", "D", "Duck", "Quack!");
		
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
	public void updateProjectTest() {
		try{
			//step 1
			ProjectUpdateForm form = projectController.getProjectUpdateForm();
			//step 2
			List<Project> list = projectController.getProjectList();
			//step 3
			Project project = list.get(0);
			//step 4
			form.setProject(project);
			//step 5
			form.setBudgetEstimate(10000);
			form.setDescription("project");
			form.setName("Project S");
			form.setStartDate(new Date(1302));
			form.setVersion(new Version(2, 0, 0));
			form.setLeadDeveloper(colleague);
			//step 6
			projectController.updateProject(form);	
			
			Assert.assertEquals("Project S", project.getName());
			Assert.assertEquals("project", project.getDescription());
			Assert.assertEquals(new Date(1302), project.getStartDate());
			Assert.assertEquals(10000, project.getBudgetEstimate(), 0.001);
			Assert.assertEquals(new Version(2, 0, 0), project.getVersion());
			Assert.assertEquals(colleague, project.getTeam().getLeadDeveloper());
		}
		catch(UnauthorizedAccessException eo){
			fail("admin not logged in");
		}
	}

}
