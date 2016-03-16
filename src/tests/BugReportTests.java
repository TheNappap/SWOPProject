package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import controllers.BugReportController;
import controllers.UserController;
import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.BugReport;
import model.bugreports.builders.BugReportBuilder;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.BugReportAssignForm;
import model.bugreports.forms.BugReportCreationForm;
import model.bugreports.forms.BugReportUpdateForm;
import model.bugreports.forms.CommentCreationForm;
import model.bugtag.BugTag;
import model.projects.Subsystem;
import model.users.Developer;
import model.users.Issuer;
import model.users.UserCategory;
import model.users.UserManager;

public class BugReportTests {

	private BugReportController controller;
	
	@Before
	public void setUp() throws Exception {
		BugTrap  bugTrap =  new BugTrap();
		controller = new BugReportController(bugTrap);
		UserController userController = new UserController(bugTrap);
		//add user
		UserManager userMan = (UserManager) userController.getBugTrap().getUserDAO();
		userMan.createUser(UserCategory.DEVELOPER, "", "", "", "Dev");
		Developer dev =  (Developer) userController.getUserList(UserCategory.DEVELOPER).get(0);
		userController.loginAs(dev);
	}

	@Test
	public void bugReportBuilderValidateTest() {
		BugReportBuilder bugReportBuilder = new BugReportBuilder();
		
		try{
			bugReportBuilder.getBugReport();
			fail();
		} catch (IllegalStateException e) { }
		try{
			bugReportBuilder.setTitle("Project")
							.getBugReport();
			fail();
		} catch (IllegalStateException e) { }
		try{
			bugReportBuilder.setTitle("Project")
							.setDescription("Very long description.")
							.getBugReport();
			fail();
		} catch (IllegalStateException e) { }
		try{
			bugReportBuilder.setTitle("Project")
			.setDescription("Very long description.")
			.setSubsystem(new Subsystem(null, null, null, null, null))
			.getBugReport();
			fail();
		} catch (IllegalStateException e) { }
		try{
			bugReportBuilder.setTitle("Project")
			.setDescription("Very long description.")
			.setSubsystem(new Subsystem(null, null, null, null, null))
			.setDependsOn(new ArrayList<BugReport>())
			.getBugReport();
			fail();
		} catch (IllegalStateException e) { }
		try{
			bugReportBuilder.setTitle("Project")
			.setDescription("Very long description.")
			.setSubsystem(new Subsystem(null, null, null, null, null))
			.setDependsOn(new ArrayList<BugReport>())
			.setIssuer(new Issuer(null, null, null, null))
			.setBugTag(BugTag.NEW)
			.setCreationDate(new Date())
			.getBugReport();
		} catch (IllegalStateException e) { 
			fail();
		}
	}
	
	@Test
	public void createBugReportTest() {
		BugReportCreationForm form = new BugReportCreationForm();
		
		form.setTitle("Project title");
		form.setDescription("Very long description.");
		form.setIssuer(new Issuer(null, null, null, null));
		form.setSubsystem(new Subsystem(null, null, null, null, null));
		form.setDependsOn(new ArrayList<BugReport>());
		
		controller.createBugReport(form);
		
		BugReport bugReport;
		try {
			bugReport = controller.getBugReportList().get(0);
		
			assertNotNull(bugReport.getCreationDate());
			assertNotNull(bugReport.getComments());
			assertNotNull(bugReport.getAssignees());
			assertNull(bugReport.getDuplicate());
			assertEquals(BugTag.NEW, bugReport.getBugTag());
		} catch (UnauthorizedAccessException e) {
			fail("not logged in as issuer");
		}
		
	}

	@Test
	public void getOrderedListTitleDescTest() {
		fillWithBugReports();
		
		ArrayList<BugReport> filteredTitle;
		try {
			filteredTitle = controller.getOrderedList(new FilterType[]{FilterType.CONTAINS_STRING}, new String[]{"0"});
			
			assertEquals(1, filteredTitle.size());
			assertEquals("Project title 0", filteredTitle.get(0).getTitle());
			
			ArrayList<BugReport> filteredDesc = controller.getOrderedList(new FilterType[]{FilterType.CONTAINS_STRING}, new String[]{"Very"});
		
			assertEquals(5, filteredDesc.size());
		} catch (UnauthorizedAccessException e) {
			fail("not logged in as issuer");
		}
	}
		
	@Test
	public void getOrderedListIssuedByTest() {
		fillWithBugReports();
		
		ArrayList<BugReport> filtered;
		try {
			filtered = controller.getOrderedList(new FilterType[]{FilterType.FILED_BY_USER}, new String[]{"Mathijs"});
			
			assertEquals(2, filtered.size());
		} catch (UnauthorizedAccessException e) {
			fail("not logged in as issuer");
		}
	}
	
	@Test
	public void getOrderedListAssignedTo() {
		fillWithBugReports();
		
		Developer dev1 = new Developer(null,null,null, "John");
		Developer dev2 = new Developer(null,null,null, "Doe");
		
		try {
			controller.getBugReportList().get(0).assignDeveloper(dev1);
			controller.getBugReportList().get(0).assignDeveloper(dev2);
			controller.getBugReportList().get(1).assignDeveloper(dev1);
			controller.getBugReportList().get(2).assignDeveloper(dev2);
			
			ArrayList<BugReport> filtered = controller.getOrderedList(new FilterType[]{FilterType.ASSIGNED_TO_USER}, new String[]{"John"});
			
			assertEquals(2, filtered.size());
		} catch (UnauthorizedAccessException e) {
			fail("not logged in as issuer");
		}
	}
	
	@Test
	public void initialCommentsTest() {
		fillWithBugReports();
		
		CommentCreationForm form;
		try {
			form = controller.getCommentCreationForm();
			
			BugReport bugReport0 = controller.getBugReportList().get(0);
			BugReport bugReport1 = controller.getBugReportList().get(1);
			
			form.setCommentable(bugReport0);
			form.setText("Nice project!");
			
			controller.createComment(form);
			
			assertTrue(bugReport0.getComments().get(0).getCreationDate() instanceof Date);

			assertEquals(1, bugReport0.getComments().size());
			assertEquals(0, bugReport1.getComments().size());
			assertEquals("Nice project!", bugReport0.getComments().get(0).getText());
		} catch (UnauthorizedAccessException e) {
			fail("not logged in as issuer");
		}
	}
	
	@Test
	public void ReplyCommentsTest() {
		fillWithBugReports();
		
		BugReport bugReport0;
		try {
			bugReport0 = controller.getBugReportList().get(0);
			
			bugReport0.addComment("Nice project!");
			bugReport0.getComments().get(0).addComment("Thanks for feedback.");
			
			assertEquals(1, bugReport0.getComments().size());
			assertEquals(1, bugReport0.getComments().get(0).getComments().size());
			assertEquals("Thanks for feedback.", bugReport0.getComments().get(0).getComments().get(0).getText());
		} catch (UnauthorizedAccessException e) {
			fail("not logged in as issuer");
		}
	}

	@Test
	public void updateBugReport() {
		fillWithBugReports();
		
		BugReportUpdateForm form = new BugReportUpdateForm();
		
		BugReport bugReport0;
		try {
			bugReport0 = controller.getBugReportList().get(0);
			assertEquals(BugTag.NEW, bugReport0.getBugTag());
			
			form.setBugReport(bugReport0);
			form.setBugTag(BugTag.NOT_A_BUG);
			
			controller.updateBugReport(form);
			
			assertEquals(BugTag.NOT_A_BUG, bugReport0.getBugTag());
		} catch (UnauthorizedAccessException e) {
			fail("not logged in as issuer");
		}
		
	}
	
	@Test
	public void assignDeveloper() {
		fillWithBugReports();
		
		BugReportAssignForm form;
		try {
			form = controller.getBugReportAssignForm();

			BugReport bugReport0 = controller.getBugReportList().get(0);
			
			assertEquals(0, bugReport0.getAssignees().size());
			
			Developer dev = new Developer(null, null, null, null);
			
			form.setBugReport(bugReport0);
			form.setDeveloper(dev);
			
			controller.assignToBugReport(form);
			
			assertEquals(1, bugReport0.getAssignees().size());
			assertEquals(dev, bugReport0.getAssignees().get(0));
		} catch (UnauthorizedAccessException e) {
			fail("not logged in as issuer");
		}
		
	}

	@Test
	public void BugReportAssignFormTest() {
		BugReportAssignForm form;
		try {
			form = controller.getBugReportAssignForm();
			
			try {
				form.setBugReport(null);
				fail();
			} catch (NullPointerException e) { }
			try {
				form.setDeveloper(null);
				fail();
			} catch (NullPointerException e) { }
			
			try {
				form.allVarsFilledIn();
				fail();
			} catch (NullPointerException e ) { }
			try {
				form.setBugReport(new BugReport(null, null, null, null, null, null, null, null, null, null));
				form.allVarsFilledIn();
				fail();
			} catch (NullPointerException e) { }
			try {
				form.setBugReport(new BugReport(null, null, null, null, null, null, null, null, null, null));
				form.setDeveloper(new Developer(null, null, null, null));
				form.allVarsFilledIn();
			} catch (NullPointerException e) {
				fail();
			}
		} catch (UnauthorizedAccessException e1) {
			fail("not logged in as issuer");
		}
	}
	
	@Test
	public void BugReportCreationFormTest() {
		BugReportCreationForm form;
		try {
			form = controller.getBugReportCreationForm();
			try {
				form.setDependsOn(null);
				fail();
			} catch (NullPointerException e) { }
			try {
				form.setDescription(null);
				fail();
			} catch (NullPointerException e) { }
			try {
				form.setIssuer(null);
				fail();
			} catch (NullPointerException e) { }
			try {
				form.setSubsystem(null);
			} catch (NullPointerException e) { }
			try {
				form.setTitle(null);
			} catch (NullPointerException e) { }
			try {
				form.allVarsFilledIn();
				fail();
			} catch (NullPointerException e) { }
			try {
				form.setDependsOn(new ArrayList<BugReport>());
				form.allVarsFilledIn();
				fail();
			} catch (NullPointerException e) { }
			try {
				form.setDependsOn(new ArrayList<BugReport>());
				form.setDescription("Very long description");
				form.allVarsFilledIn();
				fail();
			} catch (NullPointerException e) { }
			try {
				form.setDependsOn(new ArrayList<BugReport>());
				form.setDescription("Very long description");
				form.setTitle("Some title");
				form.allVarsFilledIn();
				fail();
			} catch (NullPointerException e) { }
			try {
				form.setDependsOn(new ArrayList<BugReport>());
				form.setDescription("Very long description");
				form.setTitle("Some title");
				form.setIssuer(new Issuer(null, null, null, null));
				form.allVarsFilledIn();
				fail();
			} catch (NullPointerException e) { }
			try {
				form.setDependsOn(new ArrayList<BugReport>());
				form.setDescription("Very long description");
				form.setTitle("Some title");
				form.setIssuer(new Issuer(null, null, null, null));
				form.setSubsystem(new Subsystem(null, null, null, null, null));
				form.allVarsFilledIn();
			} catch (NullPointerException e) {
				fail();
			}
		} catch (UnauthorizedAccessException e1) {
			fail("not logged in as issuer");
		}
		
	}
	
	@Test
	public void BugReportUpdateFormTest() {
		BugReportUpdateForm form;
		try {
			form = controller.getBugReportUpdateForm();
			try {
				form.setBugReport(null);
				fail();
			} catch (NullPointerException e) { }
			try {
				form.setBugTag(null);
				fail();
			} catch (NullPointerException e) { }
			try {
				form.allVarsFilledIn();
				fail();
			} catch (NullPointerException e) { }
			try {
				form.setBugReport(new BugReport(null, null, null, null, null, null, null, null, null, null));
				form.allVarsFilledIn();
				fail();
			} catch (NullPointerException e) { }
			try {
				form.setBugReport(new BugReport(null, null, null, null, null, null, null, null, null, null));
				form.setBugTag(BugTag.NEW);
				form.allVarsFilledIn();
			} catch (NullPointerException e) {
				fail();
			}
		} catch (UnauthorizedAccessException e1) {
			fail("not logged in as issuer");
		}
		
	}
	
	@Test
	public void CommentCreationFormTest() {
		CommentCreationForm form;
		try {
			form = controller.getCommentCreationForm();
			try {
				form.setCommentable(null);
				fail();
			} catch (NullPointerException e) { }
			try {
				form.setText(null);
				fail();
			} catch (NullPointerException e) { }
			try {
				form.allVarsFilledIn();
				fail();
			} catch (NullPointerException e) { }
			
			try {
				form.setCommentable(new BugReport(null, null, null, null, null, null, null, null, null, null));
				form.allVarsFilledIn();
				fail();
			} catch (NullPointerException e) { }
			try {
				form.setCommentable(new BugReport(null, null, null, null, null, null, null, null, null, null));
				form.setText("Nice!");
				form.allVarsFilledIn();
			} catch (NullPointerException e) {
				fail();
			}
		} catch (UnauthorizedAccessException e1) {
			fail("not logged in as issuer");
		}
		
	}
	
	@Test
	public void BugReportControllerTest() {

	}
	
	private void fillWithBugReports() {
		for (int i = 0; i < 5; i++) {
			BugReportCreationForm form = new BugReportCreationForm();
			
			Issuer issuer1 = new Issuer(null, null, null, "Mathijs");
			Issuer issuer2 = new Issuer(null, null, null, "Matthieu");
			
			form.setTitle("Project title " + i);
			form.setDescription("Very long description " + i);
			form.setSubsystem(new Subsystem(null, null, null, null, null));
			form.setDependsOn(new ArrayList<BugReport>());
			
			if (i == 0 || i == 3) 	form.setIssuer(issuer1);
			else					form.setIssuer(issuer2);
			
			controller.createBugReport(form);
		}
	}
	
}
