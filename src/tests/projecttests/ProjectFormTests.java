package tests.projecttests;


import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.ProjectController;
import controllers.UserController;
import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Role;
import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectUpdateForm;
import model.projects.forms.SubsystemCreationForm;
import model.users.Developer;
import model.users.IUser;
import model.users.UserManager;

public class ProjectFormTests {
	
	ProjectController projectController;
	UserController userController;
	ProjectAssignForm assignForm;
	ProjectCreationForm creationForm;
	ProjectUpdateForm updateForm;
	SubsystemCreationForm subSystemCreationForm;
	IProject project;
	ISubsystem  subsystem;
	Developer dev;

	@Before
	public void setUp() throws Exception {
		BugTrap bugTrap = new BugTrap();
		projectController = new ProjectController(bugTrap);
		userController = new UserController(bugTrap);
		
		//add user
		UserManager userMan = (UserManager) userController.getBugTrap().getUserManager();
		userMan.createDeveloper("", "", "", "Dev");
		userMan.createAdmin("", "", "", "ADMIN");
		IUser admin = userController.getAdmins().get(0);
		userController.loginAs(admin);
		
		dev = new Developer("","","","dev");
		
		ProjectCreationForm form = projectController.getProjectCreationForm();
		form.setBudgetEstimate(5000);
		form.setDescription("Setup project");
		form.setLeadDeveloper(dev);
		form.setName("Project X");
		form.setStartDate(new Date(12));
		
		projectController.createProject(form);
		project = projectController.getProjectList().get(0);
		
		creationForm = projectController.getProjectCreationForm();
		updateForm = projectController.getProjectUpdateForm();
		subSystemCreationForm = projectController.getSubsystemCreationForm();
		Developer dev =  (Developer) userController.getDevelopers().get(0);
		userController.loginAs(dev);
		assignForm = projectController.getProjectAssignForm();
		userController.loginAs(admin);
	}
	
	@Test
	public void projectCreationFormSuccesTest() {
	 	ProjectCreationForm form;
		try {
			form = projectController.getProjectCreationForm();
			form.setBudgetEstimate(5000);
			form.setDescription("This is a very descriptive description!");
			form.setLeadDeveloper(dev);
			form.setName("Project X");
			form.setStartDate(new Date(1302));
			
			projectController.createProject(form);
			
			Assert.assertEquals("Project X", form.getName());
			Assert.assertEquals("This is a very descriptive description!", form.getDescription());
			Assert.assertEquals(5000, form.getBudgetEstimate(), 0.001);
			Assert.assertEquals(new Date(1302), form.getStartDate());
			Assert.assertEquals(dev, form.getLeadDeveloper());
		} catch (UnauthorizedAccessException e) {
			fail("admin not logged in");
		}
	}
	
	@Test (expected = NullPointerException.class)
	public void creationFormSetNameTest() {
		creationForm.setName(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void creationFormSetDescriptionTest() {
		creationForm.setDescription(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void creationFormSetBudgetEstimateTest() {
		creationForm.setBudgetEstimate(-5);
	}
	
	@Test (expected = NullPointerException.class)
	public void creationFormSetStartDateTest() {
		creationForm.setStartDate(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void creationFormSetLeadDeveloperTest() {
		creationForm.setLeadDeveloper(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void creationFormNameNotSetTest() {
		creationForm.allVarsFilledIn();
	}
	
	@Test (expected = NullPointerException.class)
	public void creationFormDescriptionNotSetTest() {
		creationForm.setName("");
		creationForm.allVarsFilledIn();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void creationFormBudgetEstimateNotSetTest() {
		creationForm.setName("");
		creationForm.setDescription("");
		creationForm.allVarsFilledIn();
	}
	
	@Test (expected = NullPointerException.class)
	public void creationFormStartDateNotSetTest() {
		creationForm.setName("");
		creationForm.setDescription("");
		creationForm.setBudgetEstimate(100);
		creationForm.allVarsFilledIn();
	}
	
	@Test (expected = NullPointerException.class)
	public void creationFormLeadDevNotSetTest() {
		creationForm.setName("");
		creationForm.setDescription("");
		creationForm.setBudgetEstimate(100);
		creationForm.setStartDate(new Date(1302));
		creationForm.allVarsFilledIn();
	}
	
	@Test
	public void updateFormSuccesTest() {
		Date d = new Date();
		
		try {
			updateForm = projectController.getProjectUpdateForm();
			updateForm.setProject(project);
			updateForm.setBudgetEstimate(10000);
			updateForm.setDescription("Updated!");
			updateForm.setName("Project Y");
			updateForm.setStartDate(d);

			projectController.updateProject(updateForm);
			
			Assert.assertEquals("Project Y", updateForm.getName());
			Assert.assertEquals("Updated!", updateForm.getDescription());
			Assert.assertEquals(10000, updateForm.getBudgetEstimate(), 0.001);
			Assert.assertEquals(d, updateForm.getStartDate());
		} catch (UnauthorizedAccessException e) {
			fail("admin not logged in");
		}
	}
	
	@Test (expected = NullPointerException.class)
	public void updateFormSetProjectTest() {
		updateForm.setProject(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void updateFormSetNameTest() {
		updateForm.setName(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void updateFormSetDescriptionTest() {
		updateForm.setDescription(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void updateFormSetBudgetEstimateTest() {
		updateForm.setBudgetEstimate(-5);
	}
	
	@Test (expected = NullPointerException.class)
	public void updateFormSetStartDateTest() {
		updateForm.setStartDate(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void updateFormNameNotSetTest() {
		updateForm.allVarsFilledIn();
	}
	
	@Test (expected = NullPointerException.class)
	public void updateFormDescriptionNotSetTest() {
		updateForm.setName("");
		updateForm.allVarsFilledIn();
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void updateFormBudgetEstimateNotSetTest() {
		updateForm.setName("");
		updateForm.setDescription("");
		updateForm.allVarsFilledIn();
	}
	
	@Test (expected = NullPointerException.class)
	public void updateFormStartDateNotSetTest() {
		updateForm.setName("");
		updateForm.setDescription("");
		updateForm.setBudgetEstimate(100);
		updateForm.allVarsFilledIn();
	}
	
	@Test (expected = NullPointerException.class)
	public void updateFormLeadDevNotSetTest() {
		updateForm.setName("");
		updateForm.setDescription("");
		updateForm.setBudgetEstimate(100);
		updateForm.setStartDate(new Date(1302));
		updateForm.allVarsFilledIn();
	}
	
	@Test (expected = NullPointerException.class)
	public void updateFormProjectNotSetTest() {
		updateForm.setName("");
		updateForm.setDescription("");
		updateForm.setBudgetEstimate(100);
		updateForm.setStartDate(new Date(1302));
		updateForm.allVarsFilledIn();
	}
	
	@Test
	public void assignFormSuccesTest() {
		assignForm.setProject(project);
		assignForm.setDeveloper(dev);
		assignForm.setRole(Role.TESTER);
		Assert.assertEquals(project, assignForm.getProject());
		Assert.assertEquals(dev, assignForm.getDeveloper());
		Assert.assertEquals(Role.TESTER, assignForm.getRole());
	}
	
	@Test (expected = NullPointerException.class)
	public void assignFormSetProjectTest() {
		assignForm.setProject(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void assignFormSetDeveloperTest() {
		assignForm.setDeveloper(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void assignFormSetRoleTest() {
		assignForm.setRole(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void assignFormProjectNotSetTest() {
		assignForm.allVarsFilledIn();
	}
	
	@Test (expected = NullPointerException.class)
	public void assignFormDeveloperNotSetTest() {
		assignForm.setProject(project);
		assignForm.allVarsFilledIn();
	}
	
	@Test (expected = NullPointerException.class)
	public void assignFormRoleNotSetTest() {
		assignForm.setProject(project);
		assignForm.setDeveloper(dev);
		assignForm.allVarsFilledIn();
	}
	
	@Test
	public void subSystemCreationFormSuccesTest() {
		try {
			subSystemCreationForm = projectController.getSubsystemCreationForm();
		} catch (UnauthorizedAccessException e) {
			fail("not logged in as admin");
		}
		subSystemCreationForm.setName("Sub");
		subSystemCreationForm.setDescription("A test subsystem");
		subSystemCreationForm.setParent(project);
		try {
			projectController.createSubsystem(subSystemCreationForm);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		subsystem = project.getSubsystems().get(0);
		
		Assert.assertEquals("Sub", subSystemCreationForm.getName());
		Assert.assertEquals("A test subsystem", subSystemCreationForm.getDescription());
		Assert.assertEquals(project, subSystemCreationForm.getParent());
		Assert.assertEquals(project, subSystemCreationForm.getProject());
	}
	
	@Test (expected = NullPointerException.class)
	public void subSystemCreationFormSetNameTest() {
		subSystemCreationForm.setName(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void subSystemCreationFormSetDesciptionTest() {
		subSystemCreationForm.setDescription(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void subSystemCreationFormSetParentTest() {
		subSystemCreationForm.setParent(null);
	}
	
	@Test (expected = NullPointerException.class)
	public void subSystemCreationFormNameNotSetTest() {
		subSystemCreationForm.allVarsFilledIn();
	}
	
	@Test (expected = NullPointerException.class)
	public void subSystemCreationFormDescriptionNotSetTest() {
		subSystemCreationForm.setName("");
		subSystemCreationForm.allVarsFilledIn();
	}
	
	@Test (expected = NullPointerException.class)
	public void subSystemCreationFormParentNotSetTest() {
		subSystemCreationForm.setName("");
		subSystemCreationForm.setDescription("");
		subSystemCreationForm.allVarsFilledIn();
	}
}
