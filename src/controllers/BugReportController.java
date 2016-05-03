package controllers;

import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.IBugReport;
import model.bugreports.commands.*;
import model.bugreports.filters.FilterType;
import model.bugreports.forms.*;
import model.projects.IProject;

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

	/**
	 * Returns a list of all bug reports
	 * @return bug report list
	 * @throws UnauthorizedAccessException
	 */
	public List<IBugReport> getBugReportList() {
		return getBugTrap().getBugReportManager().getBugReportList();
	}

	/**
	 * Returns a list of all bug reports for a given project
	 * @return bug report list
	 * @throws UnauthorizedAccessException
	 */
	public List<IBugReport> getBugReportsForProject(IProject project) throws UnauthorizedAccessException {
		return getBugTrap().getBugReportManager().getBugReportsForProject(project);
	}
	
	/**
	 * Returns a list of all filter types
	 * @return filter type list
	 * @throws UnauthorizedAccessException
	 */
	public FilterType[] getFilterTypes() {
		return getBugTrap().getBugReportManager().getFilterTypes();
	}

	/**
	 * Returns a filtered list of bug reports
	 * @param types filter types
	 * @param arguments optional arguments for the filter(s)
	 * @return a filtered list of bug reports
	 * @throws UnauthorizedAccessException
	 */
	public List<IBugReport> getOrderedList(FilterType[] types, String[] arguments) throws UnauthorizedAccessException {
		return getBugTrap().getBugReportManager().getOrderedList(types, arguments);
	}

	/**
	 * Creates a bug report with the information provided in the form.
	 * @param form BugReportCreationForm containing all the details about the creation.
	 * @throws UnauthorizedAccessException 
	 */
	public void createBugReport(BugReportCreationForm form) throws UnauthorizedAccessException {
		new CreateBugReportCommand(getBugTrap(), form).execute();
	}

	/**
	 * Creates a comment with the information provided in the form.
	 * @param form CommentCreationForm containing all the details about the creation.
	 * @throws UnauthorizedAccessException 
	 */
	public void createComment(CommentCreationForm form) throws UnauthorizedAccessException {
		new CreateCommentCommand(getBugTrap(), form).execute();
	}

	/**
	 * Updates a bug report with the information provided in the form.
	 * @param form BugReportUpdateForm containing all the details about the creation.
	 * @throws UnauthorizedAccessException 
	 */
	public void updateBugReport(BugReportUpdateForm form) throws UnauthorizedAccessException {
		new UpdateBugReportCommand(getBugTrap(), form).execute();
	}

	/**
	 * Assign a developer to a bug report with the information provided in the form.
	 * @param form BugReportAssignForm containing the details about the assignment.
	 * @throws UnauthorizedAccessException 
	 */
	public void assignToBugReport(BugReportAssignForm form) throws UnauthorizedAccessException {
		new AssignBugReportCommand(getBugTrap(), form).execute();
	}

	public ProposeTestForm getProposeTestForm() throws UnauthorizedAccessException {
		return getBugTrap().getFormFactory().makeProposeTestForm();
	}

	public ProposePatchForm getProposePatchForm() throws UnauthorizedAccessException {
		return getBugTrap().getFormFactory().makeProposePatchForm();
	}

	/**
	 * Propose a patch for a bug report.
	 * @param form ProposeTestForm containing the details about the test.
	 * @throws UnauthorizedAccessException
     */
	public void proposeTest(ProposeTestForm form) throws UnauthorizedAccessException {
		new ProposeTestCommand(getBugTrap(), form).execute();
	}

	/**
	 * Propose a test for the bug report.
	 * @param form ProposePatchForm containing all the details about the patch.
	 * @throws UnauthorizedAccessException
     */
	public void proposePatch(ProposePatchForm form) throws UnauthorizedAccessException {
		new ProposePatchCommand(getBugTrap(), form).execute();
	}
}