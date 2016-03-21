package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.IBugReport;
import model.bugreports.bugtag.New;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.BugReportAssignForm;
import model.bugreports.forms.BugReportCreationForm;
import model.bugreports.forms.BugReportUpdateForm;
import model.bugreports.forms.CommentCreationForm;
import model.users.IUser;

/**
 * Controller for all BugReport related things.
 * Controllers are the interface that is available to developers
 * creating e.g. a BugTrap UI.
 */
public class BugReportController extends Controller {

	public BugReportController(BugTrap bugTrap) {
		super(bugTrap);
	}
	
	public BugReportCreationForm getBugReportCreationForm() throws UnauthorizedAccessException{
		return getBugTrap().getFormFactory().makeBugReportCreationForm();
	}

	public CommentCreationForm getCommentCreationForm() throws UnauthorizedAccessException{
		return getBugTrap().getFormFactory().makeCommentCreationForm();
	}

	public BugReportAssignForm getBugReportAssignForm() throws UnauthorizedAccessException{
		return getBugTrap().getFormFactory().makeBugReportAssignForm();
	}
	
	public BugReportUpdateForm getBugReportUpdateForm() throws UnauthorizedAccessException{
		return getBugTrap().getFormFactory().makeBugReportUpdateForm();
	}

	public List<IBugReport> getBugReportList() throws UnauthorizedAccessException{
		if (!getBugTrap().isIssuerLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an issuer to perform this action.");
		
		return getBugTrap().getBugReportManager().getBugReportList();
	}

	public List<IBugReport> getOrderedList(FilterType[] types, String[] arguments) throws UnauthorizedAccessException {

		if (!getBugTrap().isIssuerLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an issuer to perform this action.");
		
		return getBugTrap().getBugReportManager().getOrderedList(types, arguments);
	}

	public void createBugReport(BugReportCreationForm form) {
		form.allVarsFilledIn();
		//String title, String description, Date creationDate, ISubsystem subsystem, IUser issuer, List<IBugReport> dependencies, BugTag tag
		getBugTrap().getBugReportManager().addBugReport(form.getTitle(), form.getDescription(), new Date(), form.getSubsystem(), form.getIssuer(), form.getDependsOn(), new ArrayList<IUser>(), new New());
	}

	public void createComment(CommentCreationForm form) {
		form.allVarsFilledIn();

		form.getCommentable().addComment(form.getText());
	}

	public void updateBugReport(BugReportUpdateForm form) {
		form.allVarsFilledIn();
		
		getBugTrap().getBugReportManager().updateBugReport(form.getBugReport(), form.getBugTag());
	}

	public void assignToBugReport(BugReportAssignForm form) {
		form.allVarsFilledIn();
		
		getBugTrap().getBugReportManager().assignToBugReport(form.getBugReport(), form.getDeveloper());
	}
}