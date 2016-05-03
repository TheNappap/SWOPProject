package tests;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.projects.IProject;
import model.projects.Version;
import model.projects.forms.ProjectUpdateForm;

public class UpdateProjectUseCaseTest extends UseCaseTest {
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		//Add Users.
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createIssuer("", "", "", "ISSUER");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		
		//Log in as an Administrator, make a Project and log off.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		bugTrap.getUserManager().logOff();
	}

	@Test
	public void updateProjectTest() {
		//login
		userController.loginAs(userController.getAdmins().get(0));
		
		
		//1. The administrator indicates he wants to update a project.
		ProjectUpdateForm form = null;
		try {
			form = projectController.getProjectUpdateForm();
		} catch (UnauthorizedAccessException e) { fail("not authorized"); }
		
		//2. The system shows a list of all projects.
		List<IProject> list = projectController.getProjectList();
		
		//3. The administrator selects a project.
		IProject project = list.get(0);
		
		//4/5. The system shows a form to update the project details: name, description, starting date and budget estimate.
		form.setProject(project);
		form.setBudgetEstimate(10000);
		form.setDescription("project");
		form.setName("Project S");
		form.setStartDate(new Date(3000));
		
		//6. The system updates the project.
		try {
			projectController.updateProject(form);
			project = projectController.getProjectList().get(0);
		} catch (UnauthorizedAccessException e) { fail("not authorised."); }
		
		//Confirm modifications.
		Assert.assertEquals("Project S", 	project.getName());
		Assert.assertEquals("project", 		project.getDescription());
		Assert.assertEquals(new Date(3000), project.getStartDate());
		Assert.assertEquals(10000, 			project.getBudgetEstimate(), 0.001);
	}

	@Test
	public void notAuthorizedTest() {
		//Must be logged in to Update.
		try {
			projectController.getProjectUpdateForm();
			fail("Can't update when no-one logged in.");
		} catch (UnauthorizedAccessException e) { }
		
		//Developer can't update.
		userController.loginAs(userController.getDevelopers().get(0));
		try {
			projectController.getProjectUpdateForm();
			fail("Can't update as Developer.");
		} catch (UnauthorizedAccessException e) { }
		
		//Issuer can't update.
		userController.loginAs(userController.getIssuers().get(0));
		try {
			projectController.getProjectUpdateForm();
		} catch (UnauthorizedAccessException e) { }
	}
	
	@Test
	public void varsNotFilledTest() {
		//login
		userController.loginAs(userController.getAdmins().get(0));
		
		try {
			ProjectUpdateForm form = projectController.getProjectUpdateForm();
			projectController.updateProject(form);
			fail("Can't update Project with null values.");
		} 
		catch (UnauthorizedAccessException e) { fail("not authorized"); }
		catch (IllegalArgumentException e) { }
		catch (NullPointerException e) { }
	}
}
