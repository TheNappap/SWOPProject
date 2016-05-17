package tests;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;
import model.projects.health.HealthIndicator;
import model.users.IUser;

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
			List<ISubsystem> subsystems = project.getAllDirectOrIndirectSubsystems();
			List<HealthIndicator> indicators = project.getHealthIndicators();
			
			//Confirm.
			assertEquals("Office",				project.getName());
			assertEquals("This project is huge. Lots of subsystems",			project.getDescription());
			assertEquals(new Date(1302),		project.getStartDate());
			assertEquals(1234, 					project.getBudgetEstimate(), 0.01);
			assertEquals(new Version(1, 0, 0),	project.getVersion());
			assertEquals(3, 					project.getSubsystems().size());
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(0));
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(1));
			assertEquals(HealthIndicator.SATISFACTORY, 					indicators.get(2));
			
			
			ISubsystem subsystem = subsystems.get(0);
			indicators = subsystem.getHealthIndicators();
			assertEquals("Word",				subsystem.getName());
			assertEquals(3, 					subsystem.getSubsystems().size());
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(0));
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(1));
			assertEquals(HealthIndicator.SATISFACTORY, 					indicators.get(2));
			
			subsystem = subsystems.get(1);
			indicators = subsystem.getHealthIndicators();
			assertEquals("Word Art",				subsystem.getName());
			assertEquals(0, 					subsystem.getSubsystems().size());
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(0));
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(1));
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(2));
			
			subsystem = subsystems.get(2);
			indicators = subsystem.getHealthIndicators();
			assertEquals("Comic Sans",				subsystem.getName());
			assertEquals(0, 					subsystem.getSubsystems().size());
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(0));
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(1));
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(2));
			
			subsystem = subsystems.get(3);
			indicators = subsystem.getHealthIndicators();
			assertEquals("Clippy",				subsystem.getName());
			assertEquals(0, 					subsystem.getSubsystems().size());
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(0));
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(1));
			assertEquals(HealthIndicator.SATISFACTORY, 					indicators.get(2));
			
			subsystem = subsystems.get(4);
			indicators = subsystem.getHealthIndicators();
			assertEquals("Excel",				subsystem.getName());
			assertEquals(1, 					subsystem.getSubsystems().size());
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(0));
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(1));
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(2));
			
			subsystem = subsystems.get(5);
			indicators = subsystem.getHealthIndicators();
			assertEquals("ExcelTable",				subsystem.getName());
			assertEquals(0, 					subsystem.getSubsystems().size());
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(0));
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(1));
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(2));
			
			subsystem = subsystems.get(6);
			indicators = subsystem.getHealthIndicators();
			assertEquals("PowerPoint",				subsystem.getName());
			assertEquals(0, 					subsystem.getSubsystems().size());
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(0));
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(1));
			assertEquals(HealthIndicator.HEALTHY, 					indicators.get(2));
		}
	}
}
