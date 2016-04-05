package tests;

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
import model.users.IUser;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class CreateCommentUseCaseTest {

	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		
		//add user
		IUser dev = bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
		IUser admin = bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().loginAs(admin);
		//add project
		bugTrap.getProjectManager().createProject("name", "description", new Date(1302), new Date(1302), 1234, null, new Version(1, 0, 0));
		IProject project = bugTrap.getProjectManager().getProjects().get(0);
		//add subsystem to project
		bugTrap.getProjectManager().createSubsystem("name", "description", project, project, Version.firstVersion());
		ISubsystem subsystem = bugTrap.getProjectManager().getSubsystemWithName("name");
		bugTrap.getProjectManager().createSubsystem("name2", "description2", project, project, Version.firstVersion());
		
		bugTrap.getUserManager().loginAs(dev);
		//add bugreport (for dependency)
		bugTrap.getBugReportManager().addBugReport("B1", "B1 is a bug", new Date(5), subsystem, dev, new ArrayList<>(), new ArrayList<>(), BugTag.NEW);
		IBugReport bugreport = bugTrap.getBugReportManager().getBugReportList().get(0);
		//add comment
		bugTrap.getBugReportManager().addComment(bugreport, "comment1");
		bugTrap.getUserManager().logOff();
	}

	@Test
	public void createCommentOnBugReportTest() {
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);		
				
		
		//step 1
		CommentCreationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeCommentCreationForm();
		} catch (UnauthorizedAccessException e1) {
			fail("not authorized");
			e1.printStackTrace();
		}
		//step 2 SELECT BUGREPORT USE CASE
		FilterType[] types = null;
		IBugReport bugReport = null;
		try {
			types = bugTrap.getBugReportManager().getFilterTypes();
			FilterType type = types[0];
			String searchingString = "B1";
			List<IBugReport> list = null;
			list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { type }, new String[] { searchingString });
			bugReport = list.get(0);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}	
		//step 3
		bugReport.getComments();
		//step 4
		form.setCommentable(bugReport);
		//step 5 & 6
		form.setText("comment2");
		//step 7
		try {
			bugTrap.getBugReportManager().addComment(form.getCommentable(), form.getText());
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}

		assertEquals(2, bugReport.getComments().size());
		assertTrue(bugReport.getComments().get(1).getText().equals("comment2"));
	}
	
	@Test
	public void createCommentOnCommentTest() {
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);		
				
		
		//step 1
		CommentCreationForm form = null;
		try {
			form = bugTrap.getFormFactory().makeCommentCreationForm();
		} catch (UnauthorizedAccessException e1) {
			fail("not authorized");
			e1.printStackTrace();
		}
		//step 2 SELECT BUGREPORT USE CASE
		FilterType[] types = null;
		IBugReport bugReport = null;
		try {
			types = bugTrap.getBugReportManager().getFilterTypes();
			FilterType type = types[0];
			String searchingString = "B1";
			List<IBugReport> list = null;
			list = bugTrap.getBugReportManager().getOrderedList(new FilterType[] { type }, new String[] { searchingString });
			bugReport = list.get(0);
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}	
		//step 3
		List<Comment> comments = bugReport.getComments();
		//step 4
		form.setCommentable(comments.get(0));
		//step 5 & 6
		form.setText("comment2");
		//step 7
		try {
			bugTrap.getBugReportManager().addComment(form.getCommentable(), form.getText());
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}

		assertEquals(1, comments.get(0).getComments().size());
		assertTrue(comments.get(0).getComments().get(0).getText().equals("comment2"));
	}
	
	@Test
	public void notAuthorizedTest() {
		try {
			bugTrap.getFormFactory().makeCommentCreationForm();
			fail("should throw exception");
		} catch (UnauthorizedAccessException e) {
		}
	}
	
	@Test
	public void varsNotFilledTest() {
		//login
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);
		
		try {
			CommentCreationForm form = bugTrap.getFormFactory().makeCommentCreationForm();
			bugTrap.getBugReportManager().addComment(form.getCommentable(), form.getText());
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
		IUser dev = bugTrap.getUserManager().getUser("DEV");
		bugTrap.getUserManager().loginAs(dev);
		
		try {
			bugTrap.getBugReportManager().addComment(null, null);
			fail("should throw exception");
		}
		catch (IllegalArgumentException e) {
		} catch (UnauthorizedAccessException e) {
			fail("not authorized");
			e.printStackTrace();
		}
	}

}
