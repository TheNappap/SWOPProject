package bugreports;

import java.util.ArrayList;

import bugreports.forms.BugReportAssignForm;
import bugreports.forms.BugReportCreationForm;
import bugreports.forms.BugReportUpdateForm;
import bugreports.forms.CommentCreationForm;

public class BugReportManager {

	private static ArrayList<BugReport> bugReportList;

	/**
	 * 
	 * @param form
	 */
	static void createBugReport(BugReportCreationForm form) {
		BugReport bugReport = new BugReport(form.getTitle(), form.getDescription(), form.getSubsystem(), form.getDependsOn(), form.getIssuer());
		
		getBugReportList().add(bugReport);
	}

	/**
	 * 
	 * @param form
	 */
	static void updateBugReport(BugReportUpdateForm form) {
		// TODO - implement BugReportManager.updateBugReport
		throw new UnsupportedOperationException();
	}

	static ArrayList<BugReport> getBugReportList() {
		return BugReportManager.bugReportList;
	}

	/**
	 * 
	 * @param mode
	 */
	static ArrayList<BugReport> getOrderedList(SearchMode mode) {
		// TODO - implement BugReportManager.getOrderedList
		throw new UnsupportedOperationException();
	}

	static void createComment(CommentCreationForm form) {
		form.getCommentOn().createComment(form);
	}

	/**
	 * 
	 * @param form
	 */
	static void assignToBugReport(BugReportAssignForm form) {
		// TODO - implement BugReportManager.assignToBugReport
		throw new UnsupportedOperationException();
	}

}