package bugreports;

import java.util.ArrayList;

import bugreports.forms.BugReportAssignForm;
import bugreports.forms.BugReportCreationForm;
import bugreports.forms.BugReportUpdateForm;
import bugreports.forms.CommentCreationForm;

public class BugReportController {

	public static BugReportCreationForm getBugReportCreationForm() {
		return new BugReportCreationForm();
	}

	public static CommentCreationForm getCommentCreationForm() {
		return new CommentCreationForm();
	}

	public static BugReportAssignForm getBugReportAssignForm() {
		return new BugReportAssignForm();
	}

	public static ArrayList<BugReport> getBugReportList() {
		return BugReportManager.getBugReportList();
	}

	/**
	 * 
	 * @param mode
	 */
	public static void getOrderedList(SearchMode mode) {
		// TODO - implement BugReportController.getOrderedList
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public static void createBugReport(BugReportCreationForm form) {
		if (!form.allVarsFilledIn()) throw new IllegalArgumentException("Not all vars in the form are filled in.");
		
		BugReportManager.createBugReport(form);
	}

	/**
	 * 
	 * @param form
	 */
	public static void createComment(CommentCreationForm form) {
		if (!form.allVarsFilledIn()) throw new IllegalArgumentException("Not all vars in the form are filled in.");
		
		BugReportManager.createComment(form);
	}

	/**
	 * 
	 * @param form
	 */
	public static void updateBugReport(BugReportUpdateForm form) {
		// TODO - implement BugReportController.updateBugReport
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public static void assignToBugReport(BugReportAssignForm form) {
		// TODO - implement BugReportController.assignToBugReport
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param bugReport
	 */
	public static void inspectBugReport(BugReport bugReport) {
		// TODO - implement BugReportController.inspectBugReport
		throw new UnsupportedOperationException();
	}

}