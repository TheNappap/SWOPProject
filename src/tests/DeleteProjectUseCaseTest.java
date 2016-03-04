package tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import controllers.ProjectController;
import controllers.UserController;
import model.BugTrap;
import model.projects.forms.ProjectCreationForm;
import model.users.Developer;
import model.users.UserCategory;
import model.users.UserManager;

public class DeleteProjectUseCaseTest {

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
	public void DeleteProjectTest() {
		//step 1
	}

}
