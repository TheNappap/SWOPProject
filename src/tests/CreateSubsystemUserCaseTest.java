package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.forms.SubsystemCreationForm;

public class CreateSubsystemUserCaseTest extends BugTrapTest {

	@Test
	public void createSubsystemInProjectTest() {
		//Log in as Administrator.
		userController.loginAs(admin);

		try {
			//1. The administrator indicates he wants to create a new subsystem.
			SubsystemCreationForm form = projectController.getSubsystemCreationForm();
			//2. The system shows a list of projects and subsystems.
			List<IProject> list = projectController.getProjectList();
			//3. The administrator selects the project or subsystem that the new subsystem will be part of.
			IProject project = list.get(0);
			//4. The system shows the subsystem creation form.
			form.setParent(project);
			//5. The administrator enters the subsystem details: name and description
			form.setDescription("Subsystem");
			form.setName("sub X");
			//6. The system creates the subsystem
			projectController.createSubsystem(form);
			
			//Confirm.
			ISubsystem subsystem = projectController.getProjectList().get(0).getSubsystems().get(projectController.getProjectList().get(0).getSubsystems().size() - 1);
			assertTrue(subsystem.getName().equals("sub X"));
			assertTrue(subsystem.getDescription().equals("Subsystem"));
			assertEquals(project, subsystem.getParent());
		} catch (UnauthorizedAccessException e) { fail("not authorized"); }
	}
	
	@Test
	public void createSubsystemInSubsystemTest() {
		//Log in as Administrator.
		userController.loginAs(admin);
				
		try {
			//1. The administrator indicates he wants to create a new subsystem.
			SubsystemCreationForm form = projectController.getSubsystemCreationForm();
			//2. The system shows a list of projects and subsystems.
			List<IProject> list = projectController.getProjectList();
			//3. The administrator selects the project or subsystem that the new subsystem will be part of.
			ISubsystem system = list.get(0).getSubsystems().get(1);
			//4. The system shows the subsystem creation form.
			form.setParent(system);
			//5. The administrator enters the subsystem details: name and description.
			form.setDescription("Subsystem");
			form.setName("sub X");
			//6. The system creates the subsystem.
			projectController.createSubsystem(form);
			ISubsystem subsystem = system.getSubsystems().get(system.getSubsystems().size() - 1);
			
			//Confirm.
			assertTrue(subsystem.getName().equals("sub X"));
			assertTrue(subsystem.getDescription().equals("Subsystem"));
			assertEquals(system, subsystem.getParent());
		} catch (UnauthorizedAccessException e) { fail("not authorized"); }
	}
	
	@Test
	public void notAuthorizedTest() {
		//Can't be not logged in.
		try {
			projectController.getSubsystemCreationForm();
			fail("Can't be not logged in.");
		} catch (UnauthorizedAccessException e) { }
		
		//Can't be Issuer.
		userController.loginAs(issuer);
		try {
			projectController.getSubsystemCreationForm();
			fail("Can't be Issuer.");
		} catch (UnauthorizedAccessException e) { }
		
		//Can't be Developer.
		userController.loginAs(prog);
		try {
			projectController.getSubsystemCreationForm();
			fail("Can't be Developer.");
		} catch (UnauthorizedAccessException e) { }
	}
	
	@Test
	public void varsNotFilledTest() {
		//login
		userController.loginAs(admin);
		
		try {
			SubsystemCreationForm form = projectController.getSubsystemCreationForm();
			projectController.createSubsystem(form);
			fail("Can't pass nulls.");
		} 
		catch (UnauthorizedAccessException e) { fail("not authorized"); }
		catch (IllegalArgumentException e) { }
		catch (NullPointerException e) { }
	}
	
	@Test
	public void nullFormTest() {
		//Log in as Administrator.
		userController.loginAs(admin);
		
		try {
			projectController.createSubsystem(projectController.getSubsystemCreationForm());
			fail("Can't pass nulls.");
		}
		catch (IllegalArgumentException e) { }
		catch (NullPointerException e) { }
		catch (UnauthorizedAccessException e ) { fail("not authorized"); } 
	}
}
