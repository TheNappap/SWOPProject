package tests;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.IProject;
import model.projects.Version;
import model.projects.forms.ProjectUpdateForm;

public class UpdateProjectUseCaseTest {

	private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		//Make system.
		bugTrap = new BugTrap();
		
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
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		
		
		//1. The administrator indicates he wants to update a project.
		ProjectUpdateForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProjectUpdateForm();
			//2. The system shows a list of all projects.
			List<IProject> list = bugTrap.getProjectManager().getProjects();
			//3. The administrator selects a project.
			IProject project = list.get(0);
			//4/5. The system shows a form to update the project details: name, description, starting date and budget estimate.
			form.setProject(project);
			form.setBudgetEstimate(10000);
			form.setDescription("project");
			form.setName("Project S");
			form.setStartDate(new Date(3000));
			//6. The system updates the project.
			bugTrap.getProjectManager().updateProject(form.getProject(), form.getName(), form.getDescription(), form.getBudgetEstimate(), form.getStartDate());
			project = bugTrap.getProjectManager().getProjects().get(0);
			
			//Confirm modifications.
			Assert.assertEquals("Project S", 	project.getName());
			Assert.assertEquals("project", 		project.getDescription());
			Assert.assertEquals(new Date(3000), project.getStartDate());
			Assert.assertEquals(10000, 			project.getBudgetEstimate(), 0.001);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
	}

	@Test
	public void notAuthorizedTest() {
		//Must be logged in to Update.
		try {
			bugTrap.getFormFactory().makeProjectUpdateForm();
			fail("Can't update when no-one logged in.");
		} catch (UnauthorizedAccessException e) { }
		//Developer can't update.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		try {
			bugTrap.getFormFactory().makeProjectUpdateForm();
			fail("Can't update as Developer.");
		} catch (UnauthorizedAccessException e) { }
		//Issuer can't update.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ISSUER"));
		try {
			bugTrap.getFormFactory().makeProjectUpdateForm();
		} catch (UnauthorizedAccessException e) { }
	}
	
	@Test
	public void varsNotFilledTest() {
		//login
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		
		try {
			ProjectUpdateForm form = bugTrap.getFormFactory().makeProjectUpdateForm();
			bugTrap.getProjectManager().updateProject(form.getProject(), form.getName(), form.getDescription(), form.getBudgetEstimate(), form.getStartDate());
			fail("Can't update Project with null values.");
		} 
		catch (UnauthorizedAccessException e) { fail("not authorized"); }
		catch (IllegalArgumentException e) { }
	}
	
	@Test
	public void nullFormTest() {
		//login
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		
		try {
			bugTrap.getProjectManager().updateProject(null, null, null, 0, null);
			fail("Can't update with null values.");
		}
		catch (IllegalArgumentException e) { } 
		catch (UnauthorizedAccessException e) { fail("not authorized"); }
	}
}
