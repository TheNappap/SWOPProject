package tests.notificationtests;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.bugtag.BugTag;
import model.bugreports.forms.BugReportCreationForm;
import model.bugreports.forms.BugReportUpdateForm;
import model.bugreports.forms.CommentCreationForm;
import model.notifications.INotification;
import model.notifications.NotificationType;
import model.notifications.forms.RegisterNotificationForm;
import model.notifications.forms.ShowChronologicalNotificationForm;
import model.projects.AchievedMilestone;
import model.projects.Version;
import model.projects.forms.DeclareAchievedMilestoneForm;
import model.projects.forms.ProjectForkForm;
import model.projects.forms.ProjectUpdateForm;
import org.junit.Before;
import org.junit.Test;

import model.notifications.Notification;
import tests.BugTrapTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class NotificationTests extends BugTrapTest {
	
	private Notification not;
	
	@Before
	public void setUp() {
		super.setUp();
		not = new Notification("not");
	}
	
	@Test
	public void constructorTest(){
		not = new Notification("not");
		assertEquals("not", not.getText());
		assertFalse(not.isRead());
	}
	
	@Test
	public void markReadTest(){
		assertFalse(not.isRead());
		
		not.markAsRead();

		assertTrue(not.isRead());
	}

	@Test
	public void bugReportChangeNotification() throws UnauthorizedAccessException {
		// Login and register for notification
		userController.loginAs(lead);

		// Register for notification
		RegisterNotificationForm form = notificationController.getRegisterNotificationForm();
		form.setObservable(office);
		form.setNotificationType(NotificationType.BUGREPORT_CHANGE);
		notificationController.registerNotification(form);

		// Change a bug report
		BugReportUpdateForm updateForm = bugReportController.getBugReportUpdateForm();
		updateForm.setBugReport(clippyBug);
		updateForm.setBugTag(BugTag.ASSIGNED);
		bugReportController.updateBugReport(updateForm);

		// Check if notification is received
		ShowChronologicalNotificationForm notificationForm = notificationController.getShowChronologicalNotificationForm();
		notificationForm.setNbOfNotifications(1);
		List<INotification> notificationList = notificationController.showNotifications(notificationForm);

		assertEquals(1, notificationList.size());
		assertEquals("The bugreport '" + clippyBug.getTitle() + "' has received the tag " + BugTag.ASSIGNED, notificationList.get(0).getText());
	}

	@Test
	public void bugReportSpecificChangeNotification() throws UnauthorizedAccessException {
		// Login and register for notification
		userController.loginAs(lead);

		// Register for notification
		RegisterNotificationForm form = notificationController.getRegisterNotificationForm();
		form.setObservable(office);
		form.setNotificationType(NotificationType.BUGREPORT_SPECIFIC_TAG);
		form.setTag(BugTag.ASSIGNED);
		notificationController.registerNotification(form);

		// Change a bug report
		BugReportUpdateForm updateForm = bugReportController.getBugReportUpdateForm();
		updateForm.setBugReport(clippyBug);
		updateForm.setBugTag(BugTag.ASSIGNED);
		bugReportController.updateBugReport(updateForm);

		updateForm = bugReportController.getBugReportUpdateForm();
		updateForm.setBugReport(clippyBug);
		updateForm.setBugTag(BugTag.CLOSED);
		bugReportController.updateBugReport(updateForm);

		// Check if notification is received. Should be the assigned, as closed is not requested.
		ShowChronologicalNotificationForm notificationForm = notificationController.getShowChronologicalNotificationForm();
		notificationForm.setNbOfNotifications(1);
		List<INotification> notificationList = notificationController.showNotifications(notificationForm);

		assertEquals(1, notificationList.size());
		assertEquals("The bugreport '" + clippyBug.getTitle() + "' has received the tag " + BugTag.ASSIGNED, notificationList.get(0).getText());
	}

	@Test
	public void createCommentNotification() throws UnauthorizedAccessException {
		// Login and register for notification
		userController.loginAs(lead);

		// Register for notification
		RegisterNotificationForm form = notificationController.getRegisterNotificationForm();
		form.setObservable(office);
		form.setNotificationType(NotificationType.CREATE_COMMENT);
		notificationController.registerNotification(form);

		// Post a comment
		CommentCreationForm commentForm = bugReportController.getCommentCreationForm();
		commentForm.setCommentable(wordBug);
		commentForm.setText("This is text");
		bugReportController.createComment(commentForm);

		// Check if notification has arrived
		ShowChronologicalNotificationForm notificationForm = notificationController.getShowChronologicalNotificationForm();
		notificationForm.setNbOfNotifications(1);
		List<INotification> notificationList = notificationController.showNotifications(notificationForm);

		assertEquals(1, notificationList.size());
		assertEquals("New comment on '" + wordBug.getTitle(), notificationList.get(0).getText());
	}

	@Test
	public void createBugReportNotification() throws UnauthorizedAccessException {
		// Login and register for notification
		userController.loginAs(lead);

		// Register for notification
		RegisterNotificationForm form = notificationController.getRegisterNotificationForm();
		form.setObservable(office);
		form.setNotificationType(NotificationType.CREATE_BUGREPORT);
		notificationController.registerNotification(form);

		// Create a bugreport
		BugReportCreationForm bugForm = bugReportController.getBugReportCreationForm();
		bugForm.setTitle("bug");
		bugForm.setDescription("Buggy bug");
		bugForm.setImpactFactor(1);
		bugForm.setIssuer(issuer);
		bugForm.setSubsystem(excel);
		bugForm.setDependsOn(new ArrayList<>());
		bugReportController.createBugReport(bugForm);

		// Check if notification has arrived
		ShowChronologicalNotificationForm notificationForm = notificationController.getShowChronologicalNotificationForm();
		notificationForm.setNbOfNotifications(1);
		List<INotification> notificationList = notificationController.showNotifications(notificationForm);

		assertEquals(1, notificationList.size());
		assertEquals("New bug report: 'bug'", notificationList.get(0).getText());
	}

	@Test
	public void achievedMilestoneNotification() throws UnauthorizedAccessException {
		// Login and register for notification
		userController.loginAs(lead);

		// Register for notification
		RegisterNotificationForm form = notificationController.getRegisterNotificationForm();
		form.setObservable(office);
		form.setNotificationType(NotificationType.ACHIEVED_MILESTONE);
		notificationController.registerNotification(form);

		// Declare achieved milestone
		DeclareAchievedMilestoneForm declareForm = projectController.getDeclareAchievedMilestoneForm();
		declareForm.setSystem(excelTable);
		declareForm.setNumbers(Arrays.asList(5, 1, 2, 3));
		projectController.declareAchievedMilestone(declareForm);

		// Check if notification has arrived
		ShowChronologicalNotificationForm notificationForm = notificationController.getShowChronologicalNotificationForm();
		notificationForm.setNbOfNotifications(1);
		List<INotification> notificationList = notificationController.showNotifications(notificationForm);

		assertEquals(1, notificationList.size());
		assertEquals("The system " + excelTable.getName() + " has achieved milestone M5.1.2.3", notificationList.get(0).getText());
	}

	@Test
	public void achievedSpecificMilestoneNotification() throws UnauthorizedAccessException {
		// Login and register for notification
		userController.loginAs(lead);

		// Register for notification
		RegisterNotificationForm form = notificationController.getRegisterNotificationForm();
		form.setObservable(office);
		form.setNotificationType(NotificationType.ACHIEVED_SPECIFIC_MILESTONE);
		form.setMilestone(new AchievedMilestone(Arrays.asList(5, 1, 2, 3)));
		notificationController.registerNotification(form);

		// Declare achieved milestone
		DeclareAchievedMilestoneForm declareForm = projectController.getDeclareAchievedMilestoneForm();
		declareForm.setSystem(excelTable);
		declareForm.setNumbers(Arrays.asList(5, 1, 2, 3));
		projectController.declareAchievedMilestone(declareForm);

		declareForm = projectController.getDeclareAchievedMilestoneForm();
		declareForm.setSystem(excelTable);
		declareForm.setNumbers(Arrays.asList(5, 3, 2, 3));
		projectController.declareAchievedMilestone(declareForm);

		// Check if notification has arrived. Only the first declaration should drop notification.
		ShowChronologicalNotificationForm notificationForm = notificationController.getShowChronologicalNotificationForm();
		notificationForm.setNbOfNotifications(1);
		List<INotification> notificationList = notificationController.showNotifications(notificationForm);

		assertEquals(1, notificationList.size());
		assertEquals("The system " + excelTable.getName() + " has achieved the milestone M5.1.2.3", notificationList.get(0).getText());
	}

	@Test
	public void projectVersionChangedNotification() throws UnauthorizedAccessException {
		// Login and register for notification
		userController.loginAs(admin);

		// Register for notification
		RegisterNotificationForm form = notificationController.getRegisterNotificationForm();
		form.setObservable(office);
		form.setNotificationType(NotificationType.PROJECT_VERSION_UPDATE);
		notificationController.registerNotification(form);

		// Update the project
		ProjectUpdateForm updateForm = projectController.getProjectUpdateForm();
		updateForm.setProject(office);
		updateForm.setStartDate(new Date());
		updateForm.setVersion(new Version(55, 44, 33));
		updateForm.setBudgetEstimate(33333);
		updateForm.setName("Office 2017");
		updateForm.setDescription("Something with your office.");
		projectController.updateProject(updateForm);

		// Check if notification has arrived. Only the first declaration should drop notification.
		ShowChronologicalNotificationForm notificationForm = notificationController.getShowChronologicalNotificationForm();
		notificationForm.setNbOfNotifications(1);
		List<INotification> notificationList = notificationController.showNotifications(notificationForm);

		assertEquals(1, notificationList.size());
		assertEquals("The system " + office.getName() + " has achieved version 55.44.33", notificationList.get(0).getText());
	}

	@Test
	public void projectForkNotification() throws UnauthorizedAccessException {
		// Login and register for notification
			userController.loginAs(admin);

		// Register for notification
		RegisterNotificationForm form = notificationController.getRegisterNotificationForm();
		form.setObservable(office);
		form.setNotificationType(NotificationType.PROJECT_FORK);
		notificationController.registerNotification(form);

		// Fork the project
		ProjectForkForm forkForm = projectController.getProjectForkForm();
		forkForm.setProject(office);
		forkForm.setVersion(new Version(3, 1, 4));
		forkForm.setBudgetEstimate(100000);
		forkForm.setLeadDeveloper(prog);
		forkForm.setStartDate(new Date());
		projectController.forkProject(forkForm);

		// Check for notification
		ShowChronologicalNotificationForm notificationForm = notificationController.getShowChronologicalNotificationForm();
		notificationForm.setNbOfNotifications(1);
		List<INotification> notificationList = notificationController.showNotifications(notificationForm);

		assertEquals(1, notificationList.size());
		assertEquals("The project " + office.getName() + " has been forked.", notificationList.get(0).getText());
	}
}
