package tests;

import controllers.ProjectController;
import controllers.UserController;
import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.Project;
import model.projects.Version;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectUpdateForm;
import model.users.Administrator;
import model.users.Developer;
import model.users.UserCategory;
import model.users.UserManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.fail;

public class UpdateProjectUseCaseTest {

	private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		
		//add user
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		
		ProjectCreationForm form = projectController.getProjectCreationForm();
		form.setBudgetEstimate(5000);
		form.setDescription("Setup project");
		form.setLeadDeveloper(lead);
		form.setName("Project X");
		form.setStartDate(new Date(12));
		
		projectController.createProject(form);
		
	}

	@Test
	public void updateProjectTest() {
		//step 1
		ProjectUpdateForm form = bugTrap.getFormFactory().makeProjectUpdateForm();
		//step 2
		List<Project> list = bugTrap.getProjectManager().getProjects();
		//step 3
		Project project = list.get(0);
		//step 4
		form.setProject(project);
		//step 5
		form.setBudgetEstimate(10000);
		form.setDescription("project");
		form.setName("Project S");
		form.setStartDate(new Date(1302));
		form.setVersion(new Version(2, 0, 0));
		form.setLeadDeveloper(colleague);
		//step 6
		projectController.updateProject(form);	
		
		Assert.assertEquals("Project S", project.getName());
		Assert.assertEquals("project", project.getDescription());
		Assert.assertEquals(new Date(1302), project.getStartDate());
		Assert.assertEquals(10000, project.getBudgetEstimate(), 0.001);
		Assert.assertEquals(new Version(2, 0, 0), project.getVersion());
		Assert.assertEquals(colleague, project.getTeam().getLeadDeveloper());
	}

}
