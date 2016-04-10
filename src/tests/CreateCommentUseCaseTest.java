package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
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
import model.bugreports.comments.Comment;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.CommentCreationForm;
import model.projects.IProject;
import model.projects.ISubsystem;
import model.projects.Version;

public class CreateCommentUseCaseTest {

	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		//Create System.
		bugTrap = new BugTrap();
		
		//Add Users.
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		bugTrap.getUserManager().createIssuer("", "", "" , "ISSUER");
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		
		//Add Project.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("ADMIN"));
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		//Add Subsystem.
		bugTrap.getProjectManager().createSubsystem("name", "description", project, project);
		ISubsystem subsystem = bugTrap.getProjectManager().getSubsystemWithName("name");
		bugTrap.getProjectManager().createSubsystem("name2", "description2", project, project);
		//Add BugReport.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), subsystem, bugTrap.getUserManager().getLoggedInUser(), new ArrayList<>(), new ArrayList<>(), BugTag.NEW);
		IBugReport bugreport = bugTrap.getBugReportManager().getBugReportList().get(0);
		//Add Comment.
		bugTrap.getBugReportManager().addComment(bugreport, "comment1", bugreport);
		bugTrap.getUserManager().logOff();
	}

	@Test
	public void createCommentOnBugReportTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));		
				
		//1. The issuer indicates he wants to create a comment
		CommentCreationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeCommentCreationForm();
		} catch (UnauthorizedAccessException e1) { fail("not authorized"); }
		
		//2. Include use case Select Bug Report.
		List<IBugReport> list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { bugTrap.getBugReportManager().getFilterTypes()[0] }, new String[] { "B1" });
		
		//3. The system shows a list of all comments of the selected bug report.
		list.get(0).getComments();
		
		//4. The issuer indicates if he wants to comment directly on the bug report or on some other comment.
		form.setCommentable(list.get(0));
		form.setBugReport(list.get(0));
		
		//5. The system asks for the text of the comment.
		//6. The issuer writes his comment.
		form.setText("comment2");
		
		//7. The system adds the comment to the selected use case.
		bugTrap.getBugReportManager().addComment(form.getCommentable(), form.getText(), form.getBugReport());

		//Confirm.
		assertEquals(2, list.get(0).getComments().size());
		assertTrue(list.get(0).getComments().get(1).getText().equals("comment2"));
	}
	
	@Test
	public void createCommentOnCommentTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));		
				
		//1. The issuer indicates he wants to create a comment.
		CommentCreationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeCommentCreationForm();
		} catch (UnauthorizedAccessException e1) { fail("not authorized"); }
		
		//2. Include use case Select Bug Report.
		List<IBugReport> list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { bugTrap.getBugReportManager().getFilterTypes()[0] }, new String[] { "B1" });
		
		//3. The system shows a list of all comments of the selected bug report.
		List<Comment> comments = list.get(0).getComments();
		
		//4. The issuer indicates if he wants to comment directly on the bug report or on some other comment.
		form.setCommentable(comments.get(0));
		form.setBugReport(list.get(0));
		
		//5. The system asks for the text of the comment.
		//6. The issuer writes his comment.
		form.setText("comment2");
		
		//7. The system adds the comment to the selected use case.
		bugTrap.getBugReportManager().addComment(form.getCommentable(), form.getText(), form.getBugReport());

		//Confirm.
		assertEquals(1, comments.get(0).getComments().size());
		assertTrue(comments.get(0).getComments().get(0).getText().equals("comment2"));
	}
	
	@Test
	public void notAuthorizedTest() {
		try {
			bugTrap.getFormFactory().makeCommentCreationForm();
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) { }
	}
	
	@Test
	public void varsNotFilledTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		
		try {
			CommentCreationForm form = bugTrap.getFormFactory().makeCommentCreationForm();
			bugTrap.getBugReportManager().addComment(form.getCommentable(), form.getText(), form.getBugReport());
			fail("should throw exception");
		} 
		catch (UnauthorizedAccessException e) { fail("not authorized"); }
		catch (IllegalArgumentException e) { }
	}
	
	@Test
	public void nullFormTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(bugTrap.getUserManager().getUser("DEV"));
		
		try {
			bugTrap.getBugReportManager().addComment(null, null, null);
			fail("should throw exception");
		}
		catch (IllegalArgumentException e) { }
	}
}
