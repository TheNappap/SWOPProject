package tests;

import controllers.ProjectController;
import controllers.UserController;
import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.Project;
import model.projects.Subsystem;
import model.projects.Version;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.SubsystemCreationForm;
import model.users.Administrator;
import model.users.Developer;
import model.users.UserCategory;
import model.users.UserManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.fail;

public class SubsystemUsecaseTests {

	private ProjectController projectController;
	private UserController userController;
	private BugTrap bugTrap;
	private Developer lead;
	private Project emptyProject;
	private Project subbedProject;
	private Subsystem sub;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		projectController = new ProjectController(bugTrap);
		userController = new UserController(bugTrap);
		lead = new Developer("John", "Johnny", "Johnson", "Boss");
		
		//add user
		UserManager userMan = (UserManager) userController.getBugTrap().getUserManager();
		userMan.createUser(UserCategory.ADMIN, "", "", "", "ADMIN");
		userMan.createUser(UserCategory.DEVELOPER, "", "", "", "Dev");
		Administrator admin =  (Administrator) userController.getUserList(UserCategory.ADMIN).get(0);
		userController.loginAs(admin);
		
		ProjectCreationForm form = projectController.getProjectCreationForm();
		form.setBudgetEstimate(5000);
		form.setDescription("This is a very descriptive description!");
		form.setLeadDeveloper(lead);
		form.setName("Project X");
		form.setStartDate(new Date(1302));
		projectController.createProject(form);
		emptyProject = projectController.getProjectList().get(0);
		
		form = projectController.getProjectCreationForm();
		form.setBudgetEstimate(500000);
		form.setDescription("This is a somewhat descriptive description!");
		form.setLeadDeveloper(lead);
		form.setName("Project Y");
		form.setStartDate(new Date(1302));
		projectController.createProject(form);
		subbedProject = projectController.getProjectList().get(1);
		
		SubsystemCreationForm sform = projectController.getSubsystemCreationForm();
		sform.setName("Sub");
		sform.setDescription("A test subsystem");
		sform.setParent(subbedProject);
		projectController.createSubsystem(sform);
		
		sub = subbedProject.getSubsystems().get(0);
	}
	
	@Test
	public void testCreateSubsystemInEmptyProject() {
		SubsystemCreationForm form;
		try {
			form = projectController.getSubsystemCreationForm();
			form.setName("Sub");
			form.setDescription("A test subsystem");
			form.setParent(emptyProject);
			projectController.createSubsystem(form);
			
			Assert.assertEquals(1, emptyProject.getSubsystems().size());
			Subsystem sub = emptyProject.getSubsystems().get(0);
			
			Assert.assertTrue(sub.getDescription().equals("A test subsystem"));
			Assert.assertTrue(sub.getName().equals("Sub"));
			Assert.assertEquals(emptyProject, sub.getParent());
			Assert.assertEquals(emptyProject, sub.getProject());
			Assert.assertEquals(0, sub.getSubsystems().size());
			Assert.assertTrue(sub.getVersion().equals(new Version(1, 0, 0)));
		} catch (UnauthorizedAccessException e) {
			fail("not logged in as admin");
		}
	}

	@Test
	public void testCreateSubsystemInNonEmptyProject() {
		Assert.assertEquals(0, sub.getSubsystems().size());
		
		SubsystemCreationForm form;
		try {
			form = projectController.getSubsystemCreationForm();
			form.setName("Sub");
			form.setDescription("A test subsystem");
			form.setParent(sub);
			Assert.assertEquals(subbedProject, form.getProject());
			projectController.createSubsystem(form);
			
			Assert.assertEquals(1, sub.getSubsystems().size());
			
			Subsystem created = sub.getSubsystems().get(0);
			Assert.assertEquals(sub, created.getParent());
			Assert.assertEquals(subbedProject, created.getProject());
		} catch (UnauthorizedAccessException e) {
			fail("not logged in as admin");
		}
	}
}
