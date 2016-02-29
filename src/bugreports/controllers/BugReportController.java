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

	public BugReportController() {
		
	}
	
	public BugReportCreationForm getBugReportCreationForm() {
		return new BugReportCreationForm();
	}

	public CommentCreationForm getCommentCreationForm() {
		return new CommentCreationForm();
	}

	public BugReportAssignForm getBugReportAssignForm() {
		return new BugReportAssignForm();
	}

	public ArrayList<BugReport> getBugReportList() {
		return BugReportManager.getBugReportList();
	}

	public void createBugReport(BugReportCreationForm form) {
		form.allVarsFilledIn();
		
		BugReportManager.createBugReport(form);
	}

	public void createComment(CommentCreationForm form) {
		form.allVarsFilledIn();

		form.getCommentable().addComment(form.getText());
	}

	public void updateBugReport(BugReportUpdateForm form) {
		form.allVarsFilledIn();
		
		form.getBugReport().updateBugTag(form.getBugTag());
	}

	public void assignToBugReport(BugReportAssignForm form) {
		form.allVarsFilledIn();
		
		form.getBugReport().assignDeveloper(form.getDeveloper());
	}
	
	public void getOrderedList(FilterType[] types, String[] arguments) {
		BugReportManager.getOrderedList(types, arguments);
	}

}