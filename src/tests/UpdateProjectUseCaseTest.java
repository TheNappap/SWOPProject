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
import model.projects.ProjectTeam;
import model.projects.Version;
import model.projects.forms.ProjectUpdateForm;
import model.users.IUser;

public class UpdateProjectUseCaseTest {

	private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		
		//add user
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		
	}

	@Test
	public void updateProjectTest() {
		//login
		IUser admin = bugTrap.getUserManager().getUser("ADMIN");
		bugTrap.getUserManager().loginAs(admin);
		
		
		//step 1
		ProjectUpdateForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProjectUpdateForm();
			//step 2
			List<IProject> list = bugTrap.getProjectManager().getProjects();
			//step 3
			IProject project = list.get(0);
			//step 4
			form.setProject(project);
			//step 5
			form.setBudgetEstimate(10000);
			form.setDescription("project");
			form.setName("Project S");
			form.setStartDate(new Date(3000));
			//step 6
			project = bugTrap.getProjectManager().updateProject(form.getProject(), form.getName(), form.getDescription(), form.getBudgetEstimate(), form.getStartDate());

			Assert.assertEquals("Project S", project.getName());
			Assert.assertEquals("project", project.getDescription());
			Assert.assertEquals(new Date(3000), project.getStartDate());
			Assert.assertEquals(10000, project.getBudgetEstimate(), 0.001);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
	}

	@Test
	public void notAuthorizedTest() {
		try {
			bugTrap.getFormFactory().makeProjectUpdateForm();
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
		}
	}
	
	@Test
	public void varsNotFilledTest() {
		//login
		IUser admin = bugTrap.getUserManager().getAdmins().get(0);
		bugTrap.getUserManager().loginAs(admin);
		
		try {
			ProjectUpdateForm form = bugTrap.getFormFactory().makeProjectUpdateForm();
			bugTrap.getProjectManager().updateProject(form.getProject(), form.getName(), form.getDescription(), form.getBudgetEstimate(), form.getStartDate());
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
		}
		catch (NullPointerException e) {
		}
	}
	
	@Test
	public void nullFormTest() {
		//login
		IUser admin = bugTrap.getUserManager().getAdmins().get(0);
		bugTrap.getUserManager().loginAs(admin);
		
		try {
			bugTrap.getProjectManager().updateProject(null, null, null, 0, null);
			fail("should throw exception");
		}
		catch (IllegalArgumentException e) {
		}
	}
}
