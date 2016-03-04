package controllers;

import java.util.ArrayList;

import model.BugTrap;
import model.bugreports.BugReport;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.BugReportAssignForm;
import model.bugreports.forms.BugReportCreationForm;
import model.bugreports.forms.BugReportUpdateForm;
import model.bugreports.forms.CommentCreationForm;

public class BugReportController extends Controller {

	public BugReportController(BugTrap bugTrap) {
		super(bugTrap);
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
	
	public BugReportUpdateForm getBugReportUpdateForm() {
		return new BugReportUpdateForm();
	}

	public ArrayList<BugReport> getBugReportList() {
		return getBugTrap().getBugReportDAO().getBugReportList();
	}

	public ArrayList<BugReport> getOrderedList(FilterType[] types, String[] arguments) {
		return getBugTrap().getBugReportDAO().getOrderedList(types, arguments);
	}

	public void createBugReport(BugReportCreationForm form) {
		form.allVarsFilledIn();
		
		getBugTrap().getBugReportDAO().addBugReport(form);
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