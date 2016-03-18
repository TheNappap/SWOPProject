package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.Project;
import model.projects.ProjectTeam;
import model.projects.Version;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectForkForm;
import model.users.Administrator;
import model.users.Developer;
import model.users.User;

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
		User admin = bugTrap.getUserManager().getUser("ADMIN");
		bugTrap.getUserManager().loginAs(admin);
		
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
		Project p = bugTrap.getProjectManager().createProject(form);
		
		
		assertEquals(p.getName(), "name");
		assertEquals(p.getDescription(), "descr");
		assertEquals(p.getStartDate(), new Date(2005, 2, 12));
		assertEquals(p.getVersion(), new Version(1, 0, 0));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void createForkProjectTest() {
		//login
		Administrator admin = bugTrap.getUserManager().getAdmins().get(0);
		bugTrap.getUserManager().loginAs(admin);
		
		//create fork
		//step 1a
		List<Project> projects =  bugTrap.getProjectManager().getProjects();
		
		//step 2a
		Project project = projects.get(0);
		
		//step 3a
		ProjectForkForm form = null;
		try {System.out.print(bugTrap == null);
			form = bugTrap.getFormFactory().makeProjectForkForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		//step 4a
		form.setProject(project);
		form.setStartDate(new Date(2010, 3, 21));
		form.setBudgetEstimate(1234);
		form.setVersion(new Version(2, 0, 1));
				
		//step 5a
		List<Developer> devs = bugTrap.getUserManager().getDevelopers();
		Developer dev = devs.get(0);
		form.setLeadDeveloper(dev);
		bugTrap.getProjectManager().createFork(form);
		//TODO show project

		Project fork = bugTrap.getProjectManager().getProjects().get(1);
		assertEquals(fork.getName(), project.getName());
		assertEquals(fork.getDescription(), project.getDescription());
		assertEquals(fork.getTeam(), project.getTeam());
		assertEquals(fork.getVersion(), new Version(2, 0, 1));
		assertEquals(fork.getCreationDate(), project.getCreationDate());
		assertEquals(fork.getStartDate(), new Date(2010, 3, 21));
		assertEquals(fork.getBudgetEstimate(), 346, 0.01);
	}
}
