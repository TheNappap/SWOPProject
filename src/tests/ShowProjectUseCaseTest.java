package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.IProject;
import model.projects.Version;

public class ShowProjectUseCaseTest {
	
	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		//Make system.
		bugTrap = new BugTrap();
		
		//Add Users.
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createIssuer("", "", "", "ISSUER");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		
		//Log in as Administrator, create Project, log off.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		bugTrap.getUserManager().logOff();
	}

	@Test
	public void showProjectTest() throws UnauthorizedAccessException {
		String[] users = new String[]{"ISSUER", "DEV", "ADMIN"};
		
		//All users should be able to show Projects.
		for (String user : users) {
			//Log in as Administrator.
			bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser(user));
				
			//1. The user indicates he wants to take a look at some project.
			//2. The system shows a list of all projects.
			List<IProject> list = bugTrap.getProjectManager().getProjects();
			//3. The user selects a project.
			IProject project = list.get(0);
			//4. The system shows a detailed overview of the selected project and all its subsystems.
			
			//Confirm.
			assertEquals("name",				project.getName());
			assertEquals("description",			project.getDescription());
			assertEquals(new Date(1302),		project.getStartDate());
			assertEquals(1234, 					project.getBudgetEstimate(), 0.01);
			assertEquals(new Version(1, 0, 0),	project.getVersion());
			assertEquals(0, 					project.getSubsystems().size());
		}
	}
}
