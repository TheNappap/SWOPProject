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

public class CreateCommentUseCaseTest extends BugTrapTest{

	@Test
	public void createCommentOnBugReportTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(issuer);
				
		//1. The issuer indicates he wants to create a comment
		CommentCreationForm form = null;
		try {
			form = bugReportController.getCommentCreationForm();
		} catch (UnauthorizedAccessException e1) { fail("not authorized"); }
		
		//2. Include use case Select Bug Report.
		List<IBugReport> list = null;
		list = bugReportController.getBugReportList();

		//3. The system shows a list of all comments of the selected bug report.
		list.get(0).getComments();
		
		//4. The issuer indicates if he wants to comment directly on the bug report or on some other comment.
		form.setCommentable(list.get(0));
		
		//5. The system asks for the text of the comment.
		//6. The issuer writes his comment.
		form.setText("No! Clippy will become annoying :o");
		
		//7. The system adds the comment to the selected use case.
		try {
			bugReportController.createComment(form);
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}

		//Confirm.
		assertEquals(2, list.get(0).getComments().size());
		assertTrue(list.get(0).getComments().get(1).getText().equals("No! Clippy will become annoying :o"));
	}
	
	@Test
	public void createCommentOnCommentTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(prog);
				
		//1. The issuer indicates he wants to create a comment.
		CommentCreationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeCommentCreationForm();
		} catch (UnauthorizedAccessException e1) { fail("not authorized"); }
		
		//2. Include use case Select Bug Report.
		List<IBugReport> list = bugReportController.getBugReportList();
		
		//3. The system shows a list of all comments of the selected bug report.
		List<Comment> comments = list.get(0).getComments();
		
		//4. The issuer indicates if he wants to comment directly on the bug report or on some other comment.
		form.setCommentable(comments.get(0));
		
		//5. The system asks for the text of the comment.
		//6. The issuer writes his comment.
		form.setText("Aren't you exaggerating?");
		
		//7. The system adds the comment to the selected use case.
		try {
			bugReportController.createComment(form);
		} catch (UnauthorizedAccessException e) {
			fail(e.getMessage());
		}

		//Confirm.
		assertEquals(1, comments.get(0).getComments().size());
		assertTrue(comments.get(0).getComments().get(0).getText().equals("Aren't you exaggerating?"));
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
		bugTrap.getUserManager().loginAs(issuer);
		
		try {
			CommentCreationForm form = bugTrap.getFormFactory().makeCommentCreationForm();
			bugReportController.createComment(form);
			fail("should throw exception");
		} 
		catch (UnauthorizedAccessException e) { fail("not authorized"); }
		catch (IllegalArgumentException e) { }
	}
	
	@Test
	public void nullFormTest() {
		//Log in.
		bugTrap.getUserManager().loginAs(issuer);
		
		try {
			bugReportController.createComment(null);
			fail("should throw exception");
		}
		catch (IllegalArgumentException e) { }
		catch (UnauthorizedAccessException e) {
			fail("not authorized");
		}
	}
}
