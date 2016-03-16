package tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.ProjectController;
import controllers.UserController;
import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.Project;
import model.projects.Role;
import model.projects.Version;
import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectDeleteForm;
import model.projects.forms.ProjectUpdateForm;
import model.users.Administrator;
import model.users.Developer;
import model.users.UserCategory;
import model.users.UserManager;

public class ProjectUsecaseTests {

	private ProjectController projectController;
	private UserController userController;
	private BugTrap bugTrap;
	private Developer lead;
	private Developer colleague;
	private Project project;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		projectController = new ProjectController(bugTrap);
		lead = new Developer("John", "Johnny", "Johnson", "Boss");
		colleague = new Developer("Donald", "D", "Duck", "Quack!");
		userController = new UserController(bugTrap);
		
		//add user
		UserManager userMan = (UserManager) userController.getBugTrap().getUserManager();
		userMan.createUser(UserCategory.ADMIN, "", "", "", "ADMIN");
		userMan.createUser(UserCategory.DEVELOPER, "", "", "", "Dev");
		Administrator admin =  (Administrator) userController.getUserList(UserCategory.ADMIN).get(0);
		userController.loginAs(admin);
		
		ProjectCreationForm form = projectController.getProjectCreationForm();
		form.setBudgetEstimate(5000);
		form.setDescription("Setup project");
		form.setLeadDeveloper(lead);
		form.setName("Project S");
		form.setStartDate(new Date(1302));
		
		projectController.createProject(form);
		project = projectController.getProjectList().get(0);
		
	}
	
	@Test
	public void testCreateProject() {
	 	ProjectCreationForm form;
		try {
			form = projectController.getProjectCreationForm();
			form.setBudgetEstimate(5000);
			form.setDescription("This is a very descriptive description!");
			form.setLeadDeveloper(lead);
			form.setName("Project X");
			form.setStartDate(new Date(1302));
			

			Date cd = new Date();
			projectController.createProject(form);
			Project project = projectController.getProjectList().get(1);
			
			Assert.assertTrue(project.getName().equals("Project X"));
			Assert.assertTrue(project.getDescription().equals("This is a very descriptive description!"));
			Assert.assertEquals(5000, project.getBudgetEstimate(), 0.001);
			Assert.assertTrue(project.getCreationDate().equals(cd));
			Assert.assertNull(project.getParent());
			Assert.assertTrue(project.getStartDate().equals(new Date(1302)));
			Assert.assertEquals(lead,project.getTeam().getLeadDeveloper());
			Assert.assertTrue (project.getVersion().equals(new Version(1, 0, 0)));
		} catch (UnauthorizedAccessException e) {
			fail("admin not logged in");
		}

	}
	
	@Test
	public void testUpdateProject() {
		Date d = new Date();
		
		ProjectUpdateForm form;
		try {
			form = projectController.getProjectUpdateForm();
			form.setProject(project);
			form.setBudgetEstimate(10000);
			form.setDescription("Updated!");
			form.setLeadDeveloper(colleague);
			form.setName("Project Y");
			form.setStartDate(d);
			form.setVersion(new Version(2, 1, 0));
			
			projectController.updateProject(form);
			
			Assert.assertTrue(project.getName().equals("Project Y"));
			Assert.assertTrue(project.getDescription().equals("Updated!"));
			Assert.assertEquals(10000, project.getBudgetEstimate(), 0.001);
			Assert.assertNull(project.getParent());
			Assert.assertTrue(project.getStartDate().equals(d));
			Assert.assertEquals(colleague, project.getTeam().getLeadDeveloper());
			Assert.assertTrue(project.getVersion().equals(new Version(2, 1, 0)));
		} catch (UnauthorizedAccessException e) {
			fail("admin not logged in");
		}
	}
	
	@Test
	public void testDeleteProject() {
		ProjectDeleteForm form;
		try {
			form = projectController.getProjectDeleteForm();
			form.setProject(project);
			projectController.deleteProject(form);
			Assert.assertEquals(0, projectController.getProjectList().size());
		} catch (UnauthorizedAccessException e) {
			fail("admin not logged in");
		}
	}
	
	@Test
	public void testAssignTesterToProject() {
		Developer dev =  (Developer) userController.getUserList(UserCategory.DEVELOPER).get(0);
		userController.loginAs(dev);
		ProjectAssignForm form;
		try {
			form = projectController.getProjectAssignForm();
			form.setDeveloper(colleague);
			form.setRole(Role.TESTER);
			form.setProject(project);
			projectController.assignToProject(form);
		} catch (UnauthorizedAccessException e) {
			fail("Dev not logged in");
		}
		
		Assert.assertTrue(project.getTeam().getTesters().contains(colleague));
	}
	
	@Test
	public void testAssignProgrammerToProject() {
		Developer dev =  (Developer) userController.getUserList(UserCategory.DEVELOPER).get(0);
		userController.loginAs(dev);
		ProjectAssignForm form;
		try {
			form = projectController.getProjectAssignForm();
			form.setDeveloper(colleague);
			form.setRole(Role.PROGRAMMER);
			form.setProject(project);
			projectController.assignToProject(form);
		} catch (UnauthorizedAccessException e) {
			fail("Dev not logged in");
		}
		
		Assert.assertTrue(project.getTeam().getProgrammers().contains(colleague));
	}
	
	@Test
	public void testgetProjectsForDeveloper() {
		//lead
		for(Project project: projectController.getProjectsForLeadDeveloper(lead)){
			Assert.assertEquals(lead, project.getTeam().getLeadDeveloper());
		}
		//not lead
		Assert.assertEquals(0, projectController.getProjectsForLeadDeveloper(colleague).size());
	}
	
}
