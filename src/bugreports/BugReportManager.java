package bugreports;

import java.util.ArrayList;

import bugreports.forms.BugReportAssignForm;
import bugreports.forms.BugReportCreationForm;
import bugreports.forms.BugReportUpdateForm;
import bugreports.forms.CommentCreationForm;

public class BugReportManager {

	private ArrayList<BugReport> bugReportList;

	/**
	 * 
	 * @param form
	 */
	public void createBugReport(BugReportCreationForm form) {
		// TODO - implement BugReportManager.createBugReport
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public void updateBugReport(BugReportUpdateForm form) {
		// TODO - implement BugReportManager.updateBugReport
		throw new UnsupportedOperationException();
	}

	public ArrayList<BugReport> getBugReportList() {
		return this.bugReportList;
	}

	/**
	 * 
	 * @param mode
	 */
	public ArrayList<BugReport> getOrderedList(SearchMode mode) {
		// TODO - implement BugReportManager.getOrderedList
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public void createComment(CommentCreationForm form) {
		// TODO - implement BugReportManager.createComment
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param form
	 */
	public void assignToBugReport(BugReportAssignForm form) {
		// TODO - implement BugReportManager.assignToBugReport
		throw new UnsupportedOperationException();
	}

}