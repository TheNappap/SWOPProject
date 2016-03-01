package bugreports.controllers;

import java.util.ArrayList;

import bugreports.BugReport;
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
		return getBugReportList();
	}

	public ArrayList<BugReport> getOrderedList(FilterType[] types, String[] arguments) {
		return getOrderedList(types, arguments);
	}

	public void createBugReport(BugReportCreationForm form) {
		form.allVarsFilledIn();
		
		createBugReport(form);
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

}