package tests;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import model.projects.*;
import model.users.IUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.forms.SubsystemCreationForm;
import model.users.Administrator;
import model.users.User;

public class CreateSubsystemUserCaseTest {

private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		
		//add user
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		//add project
		Project project = (Project)bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, new ProjectTeam(), new Version(1, 0, 0));
		//add subsystem to project
		bugTrap.getProjectManager().createSubsystem("name", "description", project, project, Version.firstVersion());
		
	}

	@Test
	public void createSubsystemInProjectTest() {
		//login
		IUser admin = bugTrap.getUserManager().getUser("ADMIN");
		bugTrap.getUserManager().loginAs(admin);		
				
		
		//step 1
		SubsystemCreationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeSubsystemCreationForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 2
		List<IProject> list = bugTrap.getProjectManager().getProjects();
		//step 3
		IProject project = list.get(0);
		//step 4
		form.setParent(project);
		//step 5
		form.setDescription("Subsystem");
		form.setName("sub X");
		//step 6
		ISubsystem subsystem = bugTrap.getProjectManager().createSubsystem(form);
		
		Assert.assertTrue(subsystem.getName().equals("sub X"));
		Assert.assertTrue(subsystem.getDescription().equals("Subsystem"));
		Assert.assertEquals(project, subsystem.getParent());
	}
	
	@Test
	public void createSubsystemInSubsystemTest() {
		//login
		IUser admin = bugTrap.getUserManager().getUser("ADMIN");
		bugTrap.getUserManager().loginAs(admin);			
				
		
		//step 1
		SubsystemCreationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeSubsystemCreationForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		//step 2
		List<IProject> list = bugTrap.getProjectManager().getProjects();
		//step 3
		ISubsystem system = list.get(0).getSubsystems().get(0);
		//step 4
		form.setParent(system);
		//step 5
		form.setDescription("Subsystem");
		form.setName("sub X");
		//step 6
		ISubsystem subsystem = bugTrap.getProjectManager().createSubsystem(form);
		
		Assert.assertTrue(subsystem.getName().equals("sub X"));
		Assert.assertTrue(subsystem.getDescription().equals("Subsystem"));
		Assert.assertEquals(system, subsystem.getParent());
	}
	
	@Test
	public void notAuthorizedTest() {
		try {
			bugTrap.getFormFactory().makeSubsystemCreationForm();
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
			SubsystemCreationForm form = bugTrap.getFormFactory().makeSubsystemCreationForm();
			bugTrap.getProjectManager().createSubsystem(form);
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
			bugTrap.getProjectManager().createSubsystem(null);
			fail("should throw exception");
		}
		catch (IllegalArgumentException e) {
		}
	}

}
