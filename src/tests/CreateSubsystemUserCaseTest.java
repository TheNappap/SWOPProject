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
import model.projects.Subsystem;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.SubsystemCreationForm;
import model.users.Administrator;
import model.users.Developer;
import model.users.UserCategory;
import model.users.UserManager;

public class CreateSubsystemUserCaseTest {

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
	public void createSubsystemTest() {
		try {
			//step 1
			SubsystemCreationForm form =  projectController.getSubsystemCreationForm();
			//step 2
			ArrayList<Project> list = projectController.getProjectList();
			//step 3
			Project system = list.get(0);
			//step 4
			form.setParent(system);
			//step 5
			form.setDescription("Subsystem");
			form.setName("sub X");
			//step 6
			projectController.createSubsystem(form);;
			
			Subsystem subsystem = system.getSubsystems().get(0);
			Assert.assertTrue(subsystem.getName().equals("sub X"));
			Assert.assertTrue(subsystem.getDescription().equals("Subsystem"));
			Assert.assertEquals(system, subsystem.getParent());
		} catch (UnauthorizedAccessException e) {
			fail("not logged in as admin");
		}
	}

}
