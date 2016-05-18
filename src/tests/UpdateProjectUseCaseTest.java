package tests;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.projects.IProject;
import model.projects.Version;
import model.projects.forms.ProjectUpdateForm;

public class UpdateProjectUseCaseTest extends BugTrapTest {

	@Test
	public void updateProjectTest() {
		//login
		userController.loginAs(admin);

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
		form.setVersion(new Version(9,9,9));
		
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
		userController.loginAs(lead);
		try {
			projectController.getProjectUpdateForm();
			fail("Can't update as Developer.");
		} catch (UnauthorizedAccessException e) { }
		
		//Issuer can't update.
		userController.loginAs(issuer);
		try {
			projectController.getProjectUpdateForm();
			fail("Can't update as issuer");
		} catch (UnauthorizedAccessException e) { }
	}
	
	@Test
	public void varsNotFilledTest() {
		//login
		userController.loginAs(admin);
		
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
