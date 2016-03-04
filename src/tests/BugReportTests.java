package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import model.BugTrap;
import model.bugreports.BugReport;
import model.bugreports.BugTag;
import model.bugreports.builders.BugReportBuilder;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.BugReportCreationForm;
import model.projects.Subsystem;
import model.users.Developer;
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
			.setSubsystem(new Subsystem(null, null, null, null, null))
			.getBugReport();
			fail();
		} catch (AssertionError e) { }
		try{
			bugReportBuilder.setTitle("Project")
			.setDescription("Very long description.")
			.setSubsystem(new Subsystem(null, null, null, null, null))
			.setDependsOn(new ArrayList<BugReport>())
			.getBugReport();
			fail();
		} catch (AssertionError e) { }
		try{
			bugReportBuilder.setTitle("Project")
			.setDescription("Very long description.")
			.setSubsystem(new Subsystem(null, null, null, null, null))
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
		form.setSubsystem(new Subsystem(null, null, null, null, null));
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
	public void getOrderedListTitleDescTest() {
		fillWithBugReports();
		
		ArrayList<BugReport> filteredTitle = bugTrap.getBugReportDAO().getOrderedList(new FilterType[]{FilterType.CONTAINS_STRING}, new String[]{"0"});
		
		assertEquals(1, filteredTitle.size());
		assertEquals("Project title 0", filteredTitle.get(0).getTitle());
		
		ArrayList<BugReport> filteredDesc = bugTrap.getBugReportDAO().getOrderedList(new FilterType[]{FilterType.CONTAINS_STRING}, new String[]{"Very"});
	
		assertEquals(5, filteredDesc.size());
	}
	
	@Test
	public void getOrderedListIssuedByTest() {
		fillWithBugReports();
		
		ArrayList<BugReport> filtered = bugTrap.getBugReportDAO().getOrderedList(new FilterType[]{FilterType.FILED_BY_USER}, new String[]{"Mathijs"});
		
		assertEquals(2, filtered.size());
	}
	
	@Test
	public void getOrderedListAssignedTo() {
		fillWithBugReports();
		
		Developer dev1 = new Developer(null,null,null, "John");
		Developer dev2 = new Developer(null,null,null, "Doe");
		
		bugTrap.getBugReportDAO().getBugReportList().get(0).assignDeveloper(dev1);
		bugTrap.getBugReportDAO().getBugReportList().get(0).assignDeveloper(dev2);
		bugTrap.getBugReportDAO().getBugReportList().get(1).assignDeveloper(dev1);
		bugTrap.getBugReportDAO().getBugReportList().get(2).assignDeveloper(dev2);
		
		ArrayList<BugReport> filtered = bugTrap.getBugReportDAO().getOrderedList(new FilterType[]{FilterType.ASSIGNED_TO_USER}, new String[]{"John"});
		
		assertEquals(2, filtered.size());
	}
	
	@Test
	public void initialCommentsTest() {
		fillWithBugReports();
		
		BugReport bugReport0 = bugTrap.getBugReportDAO().getBugReportList().get(0);
		BugReport bugReport1 = bugTrap.getBugReportDAO().getBugReportList().get(1);
		
		bugReport0.addComment("Nice project!");
		
		assertTrue(bugReport0.getComments().get(0).getCreationDate() instanceof Date);

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

	@Test
	public void updateBugReport() {
		fillWithBugReports();
		
		BugReport bugReport0 = bugTrap.getBugReportDAO().getBugReportList().get(0);
		
		assertEquals(BugTag.NEW, bugReport0.getBugTag());
		
		bugReport0.updateBugTag(BugTag.NOT_A_BUG);
		
		assertEquals(BugTag.NOT_A_BUG, bugReport0.getBugTag());
		
	}
	
	@Test
	public void assignDeveloper() {
		fillWithBugReports();
		
		BugReport bugReport0 = bugTrap.getBugReportDAO().getBugReportList().get(0);
		
		assertEquals(0, bugReport0.getAssignees().size());
		
		Developer dev = new Developer(null, null, null, null);
		bugReport0.assignDeveloper(dev);
		
		assertEquals(1, bugReport0.getAssignees().size());
		assertEquals(dev, bugReport0.getAssignees().get(0));
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
			
			bugTrap.getBugReportDAO().addBugReport(form);
		}
	}
}
