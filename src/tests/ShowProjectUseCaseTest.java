package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.ProjectTeam;
import model.projects.Version;
import model.users.IUser;

public class ShowProjectUseCaseTest {
	
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
	public void showProjectTest() throws UnauthorizedAccessException {
		//login
		IUser admin = bugTrap.getUserManager().getUser("ADMIN");
		bugTrap.getUserManager().loginAs(admin);

		//step 1
		//user indicates he wants to inspect a project
		//step 2
		List<IProject> list = null;
		try {
			list = bugTrap.getProjectManager().getProjects();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 3
		IProject project = list.get(0);
		//step 4
		List<ISubsystem> subsystems = project.getSubsystems();
		
		assertEquals(project.getName(), "name");
		assertEquals(project.getDescription(), "description");
		assertEquals(project.getStartDate(), new Date(1302));
		assertEquals(1234, project.getBudgetEstimate(), 0.01);
		assertEquals(project.getVersion(), new Version(1, 0, 0));
		Assert.assertEquals(0, subsystems.size());
	}
	
	@Test
	public void notAuthorizedTest() {
		try {
			bugTrap.getProjectManager().getProjects();
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
		}
	}

}
