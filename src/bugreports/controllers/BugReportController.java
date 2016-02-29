package bugreports.controllers;

import java.util.ArrayList;

import bugreports.BugReport;
import bugreports.BugReportManager;
import bugreports.filters.FilterType;
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

	public static void createBugReport(BugReportCreationForm form) {
		form.allVarsFilledIn();
		
		BugReportManager.createBugReport(form);
	}

	public static void createComment(CommentCreationForm form) {
		form.allVarsFilledIn();

		form.getCommentable().addComment(form.getText());
	}

	public static void updateBugReport(BugReportUpdateForm form) {
		form.allVarsFilledIn();
		
		form.getBugReport().updateBugTag(form.getBugTag());
	}

	public static void assignToBugReport(BugReportAssignForm form) {
		form.allVarsFilledIn();
		
		form.getBugReport().assignDeveloper(form.getDeveloper());
	}
	
	/**
	 * 
	 * @param mode
	 */
	public static void getOrderedList(FilterType[] mode, String[] arguments) {
		// TODO - implement BugReportController.getOrderedList
		throw new UnsupportedOperationException();
	}

}