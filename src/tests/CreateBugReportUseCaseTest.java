package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.IBugReport;
import model.bugreports.TargetMilestone;
import model.bugreports.bugtag.BugTag;
import model.bugreports.forms.BugReportCreationForm;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;
import model.users.IUser;

public class CreateBugReportUseCaseTest extends BugTrapTest {

	@Test
	public void createBugReportTest() {
		IUser[] users = new IUser[] {tester, issuer, prog, lead};
		
		for (int iter = 0; iter < users.length; iter++) {
			userController.loginAs(users[iter]);
			IUser user = userController.getLoggedInUser();		
			
			//1. The issuer indicates he wants to file a bug report.
			BugReportCreationForm form = null;
			try {
				form = bugReportController.getBugReportCreationForm();
			} catch (UnauthorizedAccessException e) { fail("not authorized"); }
			
			//2. The system shows a list of projects.
			List<IProject> projects = null;
			projects = projectController.getProjectList();

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
			form.setReproduction("Reproduce by calling (...) with parameter (...)");
			form.setStackTrace("Exception in thread \"main\" java.lang.NullPointerException");
			form.setErrorMessage("ERROR! You messed up!");
			form.setTargetMilestone(new TargetMilestone(Arrays.asList(new Integer[] { 1, 2, 3 })));
			

			List<IBugReport> bugReports = project.getAllBugReports();
			
			//12. The system shows a list of possible dependencies of this bug report.
			//These are the bug reports of the same project
			//13. The issuer selects the dependencies.
			List<IBugReport> dependencies = new ArrayList<IBugReport>();
			dependencies.add(bugReports.get(0));
			form.setDependsOn(dependencies);
			
			//14. The system creates the bug report
			try {
				bugReportController.createBugReport(form);
			} catch (UnauthorizedAccessException e) { fail("not authorized"); }

			//Confirm.
			IBugReport bugReport = bugReportController.getBugReportList().get(bugReportController.getBugReportList().size() - 1);
			
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
			bugReportController.getBugReportCreationForm();
			fail("Can't create BugReport when not logged in.");
		} catch (UnauthorizedAccessException e) { }
		
		//Can't create BugReport as Administrator.
		userController.loginAs("ADMIN");
		try {
			bugReportController.getBugReportCreationForm();
			fail("Can't create BugReport as Administrator");
		} catch (UnauthorizedAccessException e) { }
	}
}
