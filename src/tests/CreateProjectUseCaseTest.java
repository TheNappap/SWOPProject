package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import model.Milestone;
import model.projects.AchievedMilestone;
import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.projects.IProject;
import model.projects.Version;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectForkForm;
import model.users.IUser;

public class CreateProjectUseCaseTest {

	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		//Make System
		bugTrap = new BugTrap();
		
		//Add User
		bugTrap.getUserManager().createIssuer("", "", "", "ISSUER");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
	}

	@SuppressWarnings("deprecation")
	@Test
	public void createNewProjectTest() {
		//Log in as a administrator, they create Projects.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		
		//Holds the project we're creating.
		IProject project = null; 
		
		//1. The administrator indicates he wants to create a new project.
		//2.  The system shows a form to enter the project details: name, description, starting date and budget estimate.
		ProjectCreationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProjectCreationForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
		
		//3. The administrator enters all the project details
		form.setName("name");
		form.setDescription("descr");
		form.setStartDate(new Date(2005, 2, 12));
		form.setBudgetEstimate(1234);
		
		//4. The system shows a list of possible lead developers
		List<IUser> devs = bugTrap.getUserManager().getDevelopers();
		
		//5. The administrator selects a lead developer.
		IUser dev = devs.get(0);
		form.setLeadDeveloper(dev);
		
		//6. The system creates the project and shows an overview.
		Date creationDate = new Date();
		bugTrap.getProjectManager().createProject(form.getName(), form.getDescription(), creationDate, form.getStartDate(), form.getBudgetEstimate(), form.getLeadDeveloper(), Version.firstVersion());
		project = bugTrap.getProjectManager().getProjects().get(0);

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
		assertEquals(creationDate,				project.getCreationDate());
		//-Has one Achieved Milestone: M0
		assertEquals("M0",						project.getAchievedMilestone().toString());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void createForkProjectTest() {
		//Log in as an Administrator, they create forks.
		IUser admin = bugTrap.getUserManager().getAdmins().get(0);
		bugTrap.getUserManager().loginAs(admin);
		
		//Add some project to the system to fork.
		bugTrap.getProjectManager().createProject("name", "description", new Date(2005, 1, 2), new Date(2005, 2, 12), 1234, null, new Version(1, 0, 0));
		bugTrap.getProjectManager().declareAchievedMilestone(bugTrap.getProjectManager().getProjects().get(0), Arrays.asList(new Integer[] {2, 1, 2, 1, 2, 1}));

		//1. The system shows a list of existing projects
		List<IProject> projects = null;
		projects = bugTrap.getProjectManager().getProjects();

		//2. The administrator selects an existing project
		IProject project = projects.get(0);

		//3. The system shows a form to enter the missing project details: new incremented version identifier, starting date and budget estimate.
		ProjectForkForm form = null;
		try {
			form = bugTrap.getFormFactory().makeProjectForkForm();
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}

		//4. The administrator enters all the missing project details.
		form.setProject(project);
		form.setStartDate(new Date(2010, 3, 21));
		form.setBudgetEstimate(1234);
		form.setVersion(new Version(2, 0, 1));

		//5. The system shows a list of possible lead developers.
		List<IUser> devs = bugTrap.getUserManager().getDevelopers();
		
		//6. The administrator selects a lead developer.
		form.setLeadDeveloper(devs.get(0));
		
		//7. The system creates the project and shows an overview.
		bugTrap.getProjectManager().createFork(form.getProject(), form.getBudgetEstimate(), form.getVersion(), form.getStartDate());
		IProject fork = bugTrap.getProjectManager().getProjects().get(1);

		//Confirm.
		assertEquals(fork, project);
		assertFalse(fork == project);
		assertEquals("M0", fork.getAchievedMilestone().toString());
	}
	
	@Test
	public void notAuthorizedTest() {
		//Can't make project/forks when not logged in.
		try {
			bugTrap.getFormFactory().makeProjectCreationForm();
			fail("Should be logged in!");
		} catch (UnauthorizedAccessException e) { }
		try {
			bugTrap.getFormFactory().makeProjectForkForm();
			fail("Should be logged in!");
		} catch (UnauthorizedAccessException e) { }
		
		//Developers shouldn't be able to make projects/forks. 
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		try {
			bugTrap.getFormFactory().makeProjectCreationForm();
			fail("Developer's can't create Projects!");
		} catch (UnauthorizedAccessException e) { }
		try {
			bugTrap.getFormFactory().makeProjectForkForm();
			fail("Developer's can't fork Projects!");
		} catch (UnauthorizedAccessException e) { }
		
		//Issuers shouldn't be able to make projects/forks. 
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ISSUER"));
		try {
			bugTrap.getFormFactory().makeProjectCreationForm();
			fail("Issuers can't create Projects!");
		} catch (UnauthorizedAccessException e) { }
		try {
			bugTrap.getFormFactory().makeProjectForkForm();
			fail("Issuer's can't fork Projects!");
		} catch (UnauthorizedAccessException e) { }
	}

	@Test
	public void invalidInputTest() {
		//login
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		
		try {
			bugTrap.getProjectManager().createProject(null, null, null, null, 0, null, null);
			fail("should throw exception");
		}
		catch (IllegalArgumentException e) { }
		try {
			bugTrap.getProjectManager().createFork(null, 0, null, null);
			fail("should throw exception");
		}
		catch (IllegalArgumentException e){
		}
	}
}
