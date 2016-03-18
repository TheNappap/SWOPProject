package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import model.BugTrap;
import model.FormFactory;
import model.bugreports.bugtag.BugTag;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.Project;
import model.projects.ProjectTeam;
import model.projects.Version;
import model.projects.forms.ProjectCreationForm;
import model.users.Developer;

public class CreateProjectUseCaseTest {

	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		//add users
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void createNewProjectTest() {
		//step 1 & 2
		ProjectCreationForm form = null;
		try {System.out.print(bugTrap == null);
			form = bugTrap.getFormFactory().makeProjectCreationForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		//step 3
		form.setName("name");
		form.setDescription("descr");
		form.setStartDate(new Date(2005, 2, 12));
		form.setBudgetEstimate(1234);
		
		//step 4
		List<Developer> devs = bugTrap.getUserManager().getDevelopers();
		
		//step 5
		Developer dev = devs.get(0);
		form.setLeadDeveloper(dev);
		
		//step 6
		bugTrap.getProjectManager().createProject(form);
		//TODO show project
		
		Project p = bugTrap.getProjectManager().getProjects().get(0);
		assertEquals(p.getName(), "name");
		assertEquals(p.getDescription(), "descr");
		assertEquals(p.getCreationDate(), new Date(2003, 4, 2));
		assertEquals(p.getStartDate(), new Date(2005, 2, 12));
		assertEquals(p.getTeam(), new ProjectTeam());
		assertEquals(p.getVersion(), new Version(1, 0, 0));
	}

	/*@SuppressWarnings("deprecation")
	@Test
	public void createForkProjectTest() {
		Project project = projectManager.createProject("name", "descr", new Date(2003, 4, 2), new Date(2005, 2, 12), 1234, new ProjectTeam(), new Version(1, 0, 0));

		Project fork = projectManager.createFork(project, 346, new Version(2, 0, 1), new Date(2010, 3, 21));
		assertEquals(fork.getName(), project.getName());
		assertEquals(fork.getDescription(), project.getDescription());
		assertEquals(fork.getTeam(), project.getTeam());
		assertEquals(fork.getVersion(), new Version(2, 0, 1));
		assertEquals(fork.getCreationDate(), project.getCreationDate());
		assertEquals(fork.getStartDate(), new Date(2010, 3, 21));
		assertEquals(fork.getBudgetEstimate(), 346, 0.01);
	}*/
}
