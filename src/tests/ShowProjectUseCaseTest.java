package tests;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import model.users.IUser;
import org.junit.Before;
import org.junit.Test;

import model.projects.IProject;
import model.projects.Version;

public class ShowProjectUseCaseTest extends BugTrapTest {

	@Test
	public void showProjectTest() {
		IUser[] users = new IUser[] {issuer, prog, lead, admin};
		
		//All users should be able to show Projects.
		for (IUser user : users) {
			//Log in as User.
			userController.loginAs(user);
				
			//1. The user indicates he wants to take a look at some project.
			//2. The system shows a list of all projects.
			List<IProject> list = projectController.getProjectList();
			
			//3. The user selects a project.
			IProject project = list.get(0);
			//4. The system shows a detailed overview of the selected project and all its subsystems.
			
			//Confirm.
			assertEquals("Office",				project.getName());
			assertEquals("This project is huge. Lots of subsystems",			project.getDescription());
			assertEquals(new Date(1302),		project.getStartDate());
			assertEquals(1234, 					project.getBudgetEstimate(), 0.01);
			assertEquals(new Version(1, 0, 0),	project.getVersion());
			assertEquals(3, 					project.getSubsystems().size());
		}
	}
}
