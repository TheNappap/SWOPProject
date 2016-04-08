package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.bugreports.forms.BugReportCreationForm;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;
import model.projects.forms.SubsystemCreationForm;
import model.users.IUser;

public class CreateBugReportUseCaseTest {

	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		//Make System.
		bugTrap = new BugTrap();
		
		//Make Users.
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createIssuer("", "", "", "ISSUER");
		
		//Log in as Administrator and create project/subsystem.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		bugTrap.getProjectManager().createSubsystem("name", "description", project, project);
		ISubsystem subsystem = bugTrap.getProjectManager().getSubsystemWithName("name");
		bugTrap.getProjectManager().createSubsystem("name2", "description2", project, project);
		
		//Log in as Developer, add BugReport and log off.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), subsystem, bugTrap.getUserManager().getUser("DEV"), new ArrayList<>(), new ArrayList<>(), BugTag.NEW);
		bugTrap.getUserManager().logOff();
	}

	@Test
	public void createBugReportTest() {
		String[] users = new String[]{"DEV", "ISSUER"};
		//Log in as Developer.
		
		for (int iter = 0; iter < users.length; iter++) {
			IUser user = bugTrap.getUserManager().getUser(users[iter]);
			bugTrap.getUserManager().loginAs(user);		
					
			
			//1. The issuer indicates he wants to file a bug report.
			BugReportCreationForm form = null;
			try {
				form = bugTrap.getFormFactory().makeBugReportCreationForm();
			} catch (UnauthorizedAccessException e) { fail("not authorized"); }
			
			//2. The system shows a list of projects.
			List<IProject> projects = null;
			try {
				projects = bugTrap.getProjectManager().getProjects();
			} catch (UnauthorizedAccessException e) { fail("not authorized"); }
			
			//3. The issuer selects a project.
			IProject project = projects.get(0);
			
			//4. The system shows a list of subsystems of the selected project.
			List<ISubsystem> subsystems = project.getAllDirectOrIndirectSubsystems();
			
			//5. The issuer selects a subsystem.	
			ISubsystem subsystem = subsystems.get(0);
			form.setSubsystem(subsystem);
			
			//6. The system shows the bug report creation form.
			form.setIssuer(user);
			
			//7. The issuer enters the bug report details: title and description.
			form.setTitle("Bug");
			form.setDescription("a Bug");
			
			//8. The system asks which of the optional attributes of a bug report the
			//issuer wants to add: how to reproduce the bug, a stack trace or an
			//error message.
			//9. The issuer enters the selected optional attributes as text
			List<String> optionals = new ArrayList<String>();
			optionals.add("Reproduce by calling (...) with parameter (...)");
			optionals.add("Exception in thread \"main\" java.lang.NullPointerException");
			optionals.add("ERROR! You messed up!");
			form.setOptionals(optionals);
			
			List<IBugReport> bugReports = null;
			try {
				bugReports = bugTrap.getBugReportManager().getBugReportsForProject(project);
			} catch (UnauthorizedAccessException e) { fail("not authorized"); }
			
			//12. The system shows a list of possible dependencies of this bug report.
			//These are the bug reports of the same project
			//13. The issuer selects the dependencies.
			List<IBugReport> dependencies = new ArrayList<IBugReport>();
			dependencies.add(bugReports.get(0));
			form.setDependsOn(dependencies);
			
			//14. The system creates the bug report
			try {
				bugTrap.getBugReportManager().addBugReport("Bug", "a Bug", new Date(1302), subsystem, user, dependencies, new ArrayList<IUser>(), BugTag.NEW);
			} catch (UnauthorizedAccessException e) { fail("not authorized"); }
			
			//Confirm.
			IBugReport bugReport = null;
			try {
				bugReport = bugTrap.getBugReportManager().getBugReportList().get(iter + 1);
			} catch (UnauthorizedAccessException e) { fail("Not Authorised."); }
			
			assertEquals("Bug",				bugReport.getTitle());
			assertEquals("a Bug",			bugReport.getDescription());
			assertEquals(subsystem,			bugReport.getSubsystem());
			assertEquals(project,			bugReport.getSubsystem().getProject());
			assertEquals(dependencies,		bugReport.getDependsOn());
			assertEquals(user,				bugReport.getIssuedBy());	
		}
	}
	
	@Test
	public void notAuthorizedTest() {
		//Can't create BugReport when not logged in.
		try {
			bugTrap.getFormFactory().makeSubsystemCreationForm();
			fail("Can't create BugReport when not logged in.");
		} catch (UnauthorizedAccessException e) { }
		
		//Can't create BugReport as Administrator.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		try {
			bugTrap.getFormFactory().makeSubsystemCreationForm();
			fail("Can't create BugReport as Administrator");
		} catch (UnauthorizedAccessException e) { }
	}
	
	@Test
	public void varsNotFilledTest() {
		//login
		IUser admin = bugTrap.getUserManager().getAdmins().get(0);
		bugTrap.getUserManager().loginAs(admin);
		
		try {
			SubsystemCreationForm form = bugTrap.getFormFactory().makeSubsystemCreationForm();
			bugTrap.getProjectManager().createSubsystem(form.getName(), form.getDescription(), form.getProject(), form.getParent());
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
		}
		catch (IllegalArgumentException e) {
		}
	}
	
	@Test
	public void nullFormTest() {
		//login
		IUser admin = bugTrap.getUserManager().getAdmins().get(0);
		bugTrap.getUserManager().loginAs(admin);
		
		try {
			bugTrap.getProjectManager().createSubsystem(null, null, null, null);
			fail("should throw exception");
		}
		catch (IllegalArgumentException e) {
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
	}

}
