package bugreports;

import java.util.ArrayList;

import bugreports.forms.BugReportAssignForm;
import bugreports.forms.BugReportCreationForm;
import bugreports.forms.BugReportUpdateForm;
import bugreports.forms.CommentCreationForm;

public class BugReportController {

	public BugReportCreationForm getBugReportCreationForm() {
		// TODO - implement BugReportController.getBugReportCreationForm
		throw new UnsupportedOperationException();
	}

	public CommentCreationForm getCommentCreationForm() {
		return new CommentCreationForm();
	}

	public BugReportAssignForm getBugReportAssignForm() {
		return new BugReportAssignForm();
	}

	public ArrayList<BugReport> getBugReportList() {
		// TODO - implement BugReportController.getBugReportList
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param mode
	 */
	public void getOrderedList(SearchMode mode) {
		// TODO - implement BugReportController.getOrderedList
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public void createBugReport(BugReportCreationForm form) {
		if (!form.allVarsFilledIn()) throw new IllegalArgumentException("Not all vars in the form are filled in.");
		BugReportManager.createBugReport(form);
	}

	/**
	 * 
	 * @param form
	 */
	public void createComment(CommentCreationForm form) {
		if (!form.allVarsFilledIn()) throw new IllegalArgumentException("Not all vars in the form are filled in.");
		BugReportManager.createComment(form);
	}

	/**
	 * 
	 * @param form
	 */
	public void updateBugReport(BugReportUpdateForm form) {
		// TODO - implement BugReportController.updateBugReport
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public void assignToBugReport(BugReportAssignForm form) {
		// TODO - implement BugReportController.assignToBugReport
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param bugReport
	 */
	public void inspectBugReport(BugReport bugReport) {
		// TODO - implement BugReportController.inspectBugReport
		throw new UnsupportedOperationException();
	}

}