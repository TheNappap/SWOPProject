package tests;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.ProjectController;
import controllers.UserController;
import model.BugTrap;
import model.projects.Project;
import model.projects.Version;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectUpdateForm;
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
		
		ProjectCreationForm form = projectController.getProjectCreationForm();
		form.setBudgetEstimate(5000);
		form.setDescription("Setup project");
		form.setLeadDeveloper(lead);
		form.setName("Project X");
		form.setStartDate(new Date(12));
		
		projectController.createProject(form);
		
		//add user
		UserManager userMan = (UserManager) userController.getBugTrap().getUserDAO();
		userMan.createUser(UserCategory.DEVELOPER, "", "", "", "Dev");
	}

	@Test
	public void updateProjectTest() {
		//step 1
		ProjectUpdateForm form = projectController.getProjectUpdateForm();
		//step 2
		ArrayList<Project> list = projectController.getProjectList();
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

}
