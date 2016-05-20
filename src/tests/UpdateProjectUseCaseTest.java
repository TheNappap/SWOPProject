package tests;

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
	public void updateProjectTest() throws UnauthorizedAccessException {
		//login
		userController.loginAs(admin);

		//1. The administrator indicates he wants to update a project.
		ProjectUpdateForm form = projectController.getProjectUpdateForm();

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
		projectController.updateProject(form);
		project = projectController.getProjectList().get(0);

		//Confirm modifications.
		Assert.assertEquals("Project S", 	project.getName());
		Assert.assertEquals("project", 		project.getDescription());
		Assert.assertEquals(new Date(3000), project.getStartDate());
		Assert.assertEquals(10000, 			project.getBudgetEstimate(), 0.001);
	}

	@Test (expected = UnauthorizedAccessException.class)
	public void notLoggedInTest() throws UnauthorizedAccessException {
		projectController.getProjectUpdateForm();
	}

	@Test (expected = UnauthorizedAccessException.class)
	public void developerNotAllowedTest() throws UnauthorizedAccessException {
		userController.loginAs(lead);
		projectController.getProjectUpdateForm();
	}

	@Test (expected = UnauthorizedAccessException.class)
	public void issuerNotAllowedTest() throws UnauthorizedAccessException {
		userController.loginAs(issuer);
		projectController.getProjectUpdateForm();
	}
	
	@Test (expected = NullPointerException.class)
	public void varsNotFilledTest() throws UnauthorizedAccessException {
		//login
		userController.loginAs(admin);
		
		ProjectUpdateForm form = projectController.getProjectUpdateForm();
		projectController.updateProject(form);
	}
}
