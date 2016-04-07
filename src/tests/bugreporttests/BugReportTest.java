package tests.bugreporttests;


import model.bugreports.BugReport;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.bugreports.comments.Comment;
import model.notifications.BugReportObserver;
import model.projects.ISubsystem;
import model.projects.Subsystem;
import model.users.Administrator;
import model.users.Developer;
import model.users.IUser;
import model.users.Issuer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class BugReportTest {

	BugReport bugReport;
	
	String title = "BugReport";
	String description = "Awesome BugReport";
	ISubsystem subsystem = new Subsystem(null, null, null, null, null, null);
	List<IBugReport> dependsOn = new ArrayList<>();
	List<IUser> assignees = new ArrayList<>();
	List<Comment> comments = new ArrayList<>();
	List<BugReportObserver> observers = new ArrayList<>();
	IUser issuedBy = new Issuer(null, null, null, null);
	Date creationDate = new Date();
	BugTag bugTag = BugTag.NEW;
	List<String> optionals = new ArrayList<String>();
	
	
	@Before
	public void setUp() throws Exception {
		bugReport = new BugReport(title, description, subsystem, dependsOn, assignees, comments, issuedBy, creationDate, observers, bugTag.createState(), optionals);
	}

	@Test
	public void constructorTest() {
		assertTrue(bugReport.getTitle().equals(title));
		assertTrue(bugReport.getDescription().equals(description));
		assertEquals(bugReport.getSubsystem(), subsystem);
		assertEquals(bugReport.getDependsOn(), dependsOn);
		assertEquals(bugReport.getComments(), comments);
		assertEquals(bugReport.getIssuedBy(), issuedBy);
		assertEquals(bugReport.getCreationDate(), creationDate);
		assertEquals(bugReport.getBugTag(), bugTag);
		fail(); //optionals
	}
	
	@Test
	public void addCommentTest() {
		assertEquals(0, bugReport.getComments().size());
		bugReport.addComment("foobar");
		assertEquals(1, bugReport.getComments().size());
		assertTrue(bugReport.getComments().get(0).getText().equals("foobar"));
	}
	
	@Test
	public void assignDeveloperTest() {
		IUser admin 	= new Administrator(null, null, null, null);
		IUser issuer 	= new Issuer(null, null, null, null);
		IUser developer = new Developer(null, null, null, null);
		
		try { bugReport.assignDeveloper(admin); fail(); } catch (IllegalArgumentException e) {}
		try { bugReport.assignDeveloper(issuer); fail(); } catch (IllegalArgumentException e) {}
		
		assertEquals(0, bugReport.getAssignees().size());
		bugReport.assignDeveloper(developer);
		assertEquals(1, bugReport.getAssignees().size());
		assertEquals(bugReport.getAssignees().get(0), developer);
	}
	
	@Test
	public void updateBugTagTest() {
		BugTag assignedTag 		= BugTag.ASSIGNED;
		BugTag closedTag 		= BugTag.CLOSED;
		BugTag duplicateTag 	= BugTag.DUPLICATE;
		BugTag newTag 			= BugTag.NEW;
		BugTag notABugTag 		= BugTag.NOTABUG;
		BugTag resolvedTag 		= BugTag.RESOLVED;
		BugTag underReviewTag 	= BugTag.UNDERREVIEW;
		
		//From New to New is allowed.
		bugReport.updateBugTag(newTag);
		//From New to InProgress is allowed.
		bugReport.updateBugTag(underReviewTag);
		//Walking around in InProgress is allowed.
		bugReport.updateBugTag(assignedTag);
		bugReport.updateBugTag(underReviewTag);
		
		//Going back to New is not allowed.
		try { bugReport.updateBugTag(newTag); fail(); } catch (IllegalStateException e) { }
		
		//From InProgress to Closed is allowed.
		bugReport.updateBugTag(duplicateTag);
		//Walking around in Closed is not allowed.
		try { bugReport.updateBugTag(resolvedTag); fail(); } catch (IllegalStateException e) { }
		try { bugReport.updateBugTag(closedTag); fail(); } catch (IllegalStateException e) { }
		try { bugReport.updateBugTag(notABugTag); fail(); } catch (IllegalStateException e) { }
		
		//Going back to previous states is not allowed.
		try { bugReport.updateBugTag(newTag); fail(); } catch (IllegalStateException e) { }
		try { bugReport.updateBugTag(underReviewTag); fail(); } catch (IllegalStateException e) { }
		try { bugReport.updateBugTag(assignedTag); fail(); } catch (IllegalStateException e) { }
	}

	@Test
	public void compareTest() {
		BugReport other = new BugReport("CugReport", null, null, null, null, null, null, null, null, null, null);
		assertEquals(-1, bugReport.compareTo(other));
		assertEquals(1, other.compareTo(bugReport));
		
		BugReport other2 = new BugReport("BugReport", null, null, null, null, null, null, null, null, null, null);
		assertEquals(0, bugReport.compareTo(other2));
		assertEquals(0, other2.compareTo(bugReport));
		
		
	}
}
