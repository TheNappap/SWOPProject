package tests;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.projects.IProject;
import model.projects.Version;
import model.projects.forms.DeclareAchievedMilestoneForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectForkForm;
import model.users.IUser;

import static org.junit.Assert.*;

public class CreateProjectUseCaseTest extends BugTrapTest {

	@SuppressWarnings("deprecation")
	@Test
	public void createNewProjectTest() {
		//Log in as a administrator, they create Projects.
		bugTrap.getUserManager().loginAs(admin);
		
		//Holds the project we're creating.
		IProject project = null; 
		
		//1. The administrator indicates he wants to create a new project.
		//2.  The system shows a form to enter the project details: name, description, starting date and budget estimate.
		ProjectCreationForm form = null;
		try {
			form = projectController.getProjectCreationForm();
		} catch (UnauthorizedAccessException e) { fail("not authorized"); }
		
		//3. The administrator enters all the project details
		form.setName("name");
		form.setDescription("descr");
		form.setStartDate(new Date(2005, 2, 12));
		form.setBudgetEstimate(1234);
		
		//4. The system shows a list of possible lead developers
		List<IUser> devs = userController.getDevelopers();
		
		//5. The administrator selects a lead developer.
		IUser dev = devs.get(0);
		form.setLeadDeveloper(dev);
		
		//6. The system creates the project and shows an overview.
		Date creationDate = new Date();
		try {
			projectController.createProject(form);
			project = projectController.getProjectList().get(projectController.getProjectList().size() - 1);
		} catch (UnauthorizedAccessException e) { fail("not authorised"); }


		//Confirm.
		//-From input (form).
		assertEquals("name",					project.getName());
		assertEquals("descr", 					project.getDescription());
		assertEquals(new Date(2005, 2, 12), 	project.getStartDate());
		assertEquals(1234, 						project.getBudgetEstimate(), 0.01);
		//-First Version.
		assertEquals(Version.firstVersion(), 	project.getVersion());
		//-Has one Developer, the Lead.
		assertEquals(1,							project.getAllDevelopers().size());
		assertEquals(dev,						project.getAllDevelopers().get(0));
		assertEquals(dev,						project.getLeadDeveloper());
		//-Has no Programmers (yet).
		assertEquals(0,							project.getProgrammers().size());
		//-Has no Testers (yet).
		assertEquals(0,							project.getTesters().size());
		//-Has no Subsystems yet.
		assertEquals(0,							project.getAllDirectOrIndirectSubsystems().size());
		assertEquals(0,							project.getSubsystems().size());
		//-Has no parent system.
		assertEquals(null,						project.getParent());
		//-Has correct CreationDate.
		assertTrue(Math.abs(project.getCreationDate().getTime() - new Date().getTime()) < 250);
		//-Has one Achieved Milestone: M0
		assertEquals("M0",						project.getAchievedMilestone().toString());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void createForkProjectTest() {
		IUser admin = userController.getAdmins().get(0);
		IUser dev = userController.getDevelopers().get(0);
	
		userController.loginAs(admin);
		
		//1. The system shows a list of existing projects
		List<IProject> projects = projectController.getProjectList();

		//2. The administrator selects an existing project
		IProject project = projects.get(0);

		//3. The system shows a form to enter the missing project details: new incremented version identifier, starting date and budget estimate.
		ProjectForkForm form = null;
		try {
			form = projectController.getProjectForkForm();
		} catch (UnauthorizedAccessException e) { fail("not authorized"); }

		//4. The administrator enters all the missing project details.
		form.setProject(project);
		form.setStartDate(new Date(2010, 3, 21));
		form.setBudgetEstimate(1234);
		form.setVersion(new Version(2, 0, 1));

		//5. The system shows a list of possible lead developers.
		List<IUser> devs = userController.getDevelopers();
		
		//6. The administrator selects a lead developer.
		form.setLeadDeveloper(devs.get(0));
		
		//7. The system creates the project and shows an overview.
		try {
			projectController.forkProject(form);
		} catch (UnauthorizedAccessException e) { fail("not authorised"); }
		
		
		IProject fork = projectController.getProjectList().get(projectController.getProjectList().size()-1);

		//Confirm.
		assertEquals(fork, project);
		assertFalse(fork == project);
		assertEquals(new Date(2010,3,21), fork.getStartDate());
		assertEquals(1234				, fork.getBudgetEstimate(),1e-6);
		assertEquals(new Version(2, 0, 1), fork.getVersion());
		assertEquals("M0", fork.getAchievedMilestone().toString());
	}
	
	@Test
	public void notAuthorizedTest() {
		//Can't make project/forks when not logged in.
		try {
			projectController.getProjectCreationForm();
			fail("Should be logged in!");
		} catch (UnauthorizedAccessException e) { }
		try {
			projectController.getProjectForkForm();
			fail("Should be logged in!");
		} catch (UnauthorizedAccessException e) { }
		
		//Developers shouldn't be able to make projects/forks. 
		userController.loginAs(prog);
		try {
			projectController.getProjectCreationForm();
			fail("Developers can't create Projects!");
		} catch (UnauthorizedAccessException e) { }
		try {
			projectController.getProjectForkForm();
			fail("Developers can't fork Projects!");
		} catch (UnauthorizedAccessException e) { }
		
		//Issuers shouldn't be able to make projects/forks. 
		userController.loginAs(issuer);
		try {
			projectController.getProjectCreationForm();
			fail("Issuers can't create Projects!");
		} catch (UnauthorizedAccessException e) { }
		try {
			projectController.getProjectForkForm();
			fail("Issuer's can't fork Projects!");
		} catch (UnauthorizedAccessException e) { }
	}

	@Test
	public void invalidInputTest() {
		//login
		bugTrap.getUserManager().loginAs(admin);
		
		try {
			projectController.createProject(projectController.getProjectCreationForm());
			fail("should throw exception");
		}
		catch (NullPointerException e) { } 
		catch (UnauthorizedAccessException e) { fail("not authorised"); }
		
		try {
			projectController.forkProject(projectController.getProjectForkForm());
			fail("should throw exception");
		}
		catch (NullPointerException e){ }
		catch (IllegalArgumentException e) { }
		catch (UnauthorizedAccessException e) { fail("not authorised"); }
	}
}
