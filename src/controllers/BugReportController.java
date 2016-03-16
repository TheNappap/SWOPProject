package controllers;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.BugReport;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.BugReportAssignForm;
import model.bugreports.forms.BugReportCreationForm;
import model.bugreports.forms.BugReportUpdateForm;
import model.bugreports.forms.CommentCreationForm;

import java.util.ArrayList;

public class BugReportController extends Controller {

	public BugReportController(BugTrap bugTrap) {
		super(bugTrap);
	}
	
	public BugReportCreationForm getBugReportCreationForm() throws UnauthorizedAccessException{

		if (!getBugTrap().isIssuerLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an issuer to perform this action.");
		
		return new BugReportCreationForm();
	}

	public CommentCreationForm getCommentCreationForm() throws UnauthorizedAccessException{

		if (!getBugTrap().isIssuerLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an issuer to perform this action.");
		
		return new CommentCreationForm();
	}

	public BugReportAssignForm getBugReportAssignForm() throws UnauthorizedAccessException{

		if (!getBugTrap().isDeveloperLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an developer to perform this action.");
		
		return new BugReportAssignForm();
	}
	
	public BugReportUpdateForm getBugReportUpdateForm() throws UnauthorizedAccessException{
		if (!getBugTrap().isDeveloperLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an developer to perform this action.");
		
		return new BugReportUpdateForm();
	}

	public ArrayList<BugReport> getBugReportList() throws UnauthorizedAccessException{

		if (!getBugTrap().isIssuerLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an issuer to perform this action.");
		
		return getBugTrap().getBugReportDAO().getBugReportList();
	}

	public ArrayList<BugReport> getOrderedList(FilterType[] types, String[] arguments) throws UnauthorizedAccessException {

		if (!getBugTrap().isIssuerLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an issuer to perform this action.");
		
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