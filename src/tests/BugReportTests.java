package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import model.BugTrap;
import model.bugreports.BugReport;
import model.bugreports.BugTag;
import model.bugreports.builders.BugReportBuilder;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.BugReportCreationForm;
import model.bugreports.forms.CommentCreationForm;
import model.projects.Subsystem;
import model.users.Issuer;

public class BugReportTests {

	private BugTrap bugTrap;
	
	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		
	}

	@Test
	public void bugReportBuilderValidateTest() {
		BugReportBuilder bugReportBuilder = new BugReportBuilder();
		
		try{
			bugReportBuilder.getBugReport();
			fail();
		} catch (AssertionError e) { }
		try{
			bugReportBuilder.setTitle("Project")
							.getBugReport();
			fail();
		} catch (AssertionError e) { }
		try{
			bugReportBuilder.setTitle("Project")
							.setDescription("Very long description.")
							.getBugReport();
			fail();
		} catch (AssertionError e) { }
		try{
			bugReportBuilder.setTitle("Project")
			.setDescription("Very long description.")
			.setSubsystem(new Subsystem(null, null, null, null, null, null))
			.getBugReport();
			fail();
		} catch (AssertionError e) { }
		try{
			bugReportBuilder.setTitle("Project")
			.setDescription("Very long description.")
			.setSubsystem(new Subsystem(null, null, null, null, null, null))
			.setDependsOn(new ArrayList<BugReport>())
			.getBugReport();
			fail();
		} catch (AssertionError e) { }
		try{
			bugReportBuilder.setTitle("Project")
			.setDescription("Very long description.")
			.setSubsystem(new Subsystem(null, null, null, null, null, null))
			.setDependsOn(new ArrayList<BugReport>())
			.setIssuer(new Issuer(null, null, null, null))
			.getBugReport();
		} catch (AssertionError e) { 
			fail();
		}
	}
	
	@Test
	public void createBugReportTest() {
		BugReportCreationForm form = new BugReportCreationForm();
		
		form.setTitle("Project title");
		form.setDescription("Very long description.");
		form.setIssuer(new Issuer(null, null, null, null));
		form.setSubsystem(new Subsystem(null, null, null, null, null, null));
		form.setDependsOn(new ArrayList<BugReport>());
		
		bugTrap.getBugReportDAO().addBugReport(form);
		
		BugReport bugReport = bugTrap.getBugReportDAO().getBugReportList().get(0);
		
		assertNotNull(bugReport.getCreationDate());
		assertNotNull(bugReport.getComments());
		assertNotNull(bugReport.getAssignees());
		assertNull(bugReport.getDuplicate());
		assertEquals(BugTag.NEW, bugReport.getBugTag());
		
	}

	@Test
	public void getOrderedListTest() {
		fillWithBugReports();
		
		ArrayList<BugReport> filteredTitle = bugTrap.getBugReportDAO().getOrderedList(new FilterType[]{FilterType.CONTAINS_STRING}, new String[]{"0"});
		
		assertEquals(1, filteredTitle.size());
		assertEquals("Project title 0", filteredTitle.get(0).getTitle());
		
		ArrayList<BugReport> filteredDesc = bugTrap.getBugReportDAO().getOrderedList(new FilterType[]{FilterType.CONTAINS_STRING}, new String[]{"Very"});
	
		assertEquals(5, filteredDesc.size());
	}
	
	private void fillWithBugReports() {
		for (int i = 0; i < 5; i++) {
			BugReportCreationForm form = new BugReportCreationForm();
			
			form.setTitle("Project title " + i);
			form.setDescription("Very long description " + i);
			form.setIssuer(new Issuer(null, null, null, null));
			form.setSubsystem(new Subsystem(null, null, null, null, null, null));
			form.setDependsOn(new ArrayList<BugReport>());
			
			bugTrap.getBugReportDAO().addBugReport(form);
		}
	}
	
	@Test
	public void initialCommentsTest() {
		fillWithBugReports();
		
		BugReport bugReport0 = bugTrap.getBugReportDAO().getBugReportList().get(0);
		BugReport bugReport1 = bugTrap.getBugReportDAO().getBugReportList().get(1);
		
		bugReport0.addComment("Nice project!");
		
		assertEquals(1, bugReport0.getComments().size());
		assertEquals(0, bugReport1.getComments().size());
		assertEquals("Nice project!", bugReport0.getComments().get(0).getText());
	}
	
	@Test
	public void ReplyCommentsTest() {
		fillWithBugReports();
		
		BugReport bugReport0 = bugTrap.getBugReportDAO().getBugReportList().get(0);
		
		bugReport0.addComment("Nice project!");
		bugReport0.getComments().get(0).addComment("Thanks for feedback.");
		
		assertEquals(1, bugReport0.getComments().size());
		assertEquals(1, bugReport0.getComments().get(0).getComments().size());
		assertEquals("Thanks for feedback.", bugReport0.getComments().get(0).getComments().get(0).getText());
	}
}
