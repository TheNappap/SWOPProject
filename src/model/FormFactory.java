package model;

import controllers.exceptions.UnauthorizedAccessException;
import model.bugreports.forms.BugReportAssignForm;
import model.bugreports.forms.BugReportCreationForm;
import model.bugreports.forms.BugReportUpdateForm;
import model.bugreports.forms.CommentCreationForm;
import model.bugreports.forms.ProposePatchForm;
import model.bugreports.forms.ProposeTestForm;
import model.notifications.forms.RegisterNotificationForm;
import model.notifications.forms.ShowChronologicalNotificationForm;
import model.notifications.forms.UnregisterNotificationForm;
import model.projects.forms.DeclareAchievedMilestoneForm;
import model.projects.forms.ProjectAssignForm;
import model.projects.forms.ProjectCreationForm;
import model.projects.forms.ProjectDeleteForm;
import model.projects.forms.ProjectForkForm;
import model.projects.forms.ProjectUpdateForm;
import model.projects.forms.SubsystemCreationForm;

/**
 * Builds forms and checks the authorization to do so.
 */
public class FormFactory {
	
	public FormFactory(BugTrap bugTrap) {
		this.bugTrap = bugTrap;
	}
	
	private final BugTrap bugTrap;
	
	/**
	 * Return the BugTrap system.
	 * @return
	 */
	public BugTrap getBugTrap() {
		return bugTrap;
	}
	
	/**
	 * Creates and returns a project creation form
	 * @return project creation form
	 * @throws UnauthorizedAccessException if the logged in user is not admin
	 */
	public ProjectCreationForm makeProjectCreationForm() throws UnauthorizedAccessException{
		if (!getBugTrap().isAdminLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an administrator to perform this action.");
		
		return new ProjectCreationForm();
	}
	
	/**
	 * Creates and returns a project fork form
	 * @return project fork form
	 * @throws UnauthorizedAccessException if the logged in user is not admin
	 */
	public ProjectForkForm makeProjectForkForm() throws UnauthorizedAccessException{
		if (!getBugTrap().isAdminLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an administrator to perform this action.");
		
		return new ProjectForkForm();
	}
	
	/**
	 * Creates and returns a project update form
	 * @return project update form
	 * @throws UnauthorizedAccessException if the logged in user is not admin
	 */
	public ProjectUpdateForm makeProjectUpdateForm() throws UnauthorizedAccessException{
		if (!getBugTrap().isAdminLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an administrator to perform this action.");
		
		return new ProjectUpdateForm();
	}
	
	/**
	 * Creates and returns a project delete form
	 * @return project delete form
	 * @throws UnauthorizedAccessException if the logged in user is not admin
	 */
	public ProjectDeleteForm makeProjectDeleteForm() throws UnauthorizedAccessException{
		if (!getBugTrap().isAdminLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an administrator to perform this action.");
		
		return new ProjectDeleteForm();
	}
	
	/**
	 * Creates and returns a project assign form
	 * @return project assign form
	 * @throws UnauthorizedAccessException if the logged in user is not dev
	 */
	public ProjectAssignForm makeProjectAssignForm() throws UnauthorizedAccessException{
		if (!getBugTrap().isDeveloperLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as a dev to perform this action.");
		
		return new ProjectAssignForm();
	}
	
	/**
	 * Creates and returns a subsystem creation form
	 * @return subsystem creation form
	 * @throws UnauthorizedAccessException if the logged in user is not admin
	 */
	public SubsystemCreationForm makeSubsystemCreationForm() throws UnauthorizedAccessException{
		if (!getBugTrap().isAdminLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an administrator to perform this action.");
		
		return new SubsystemCreationForm();
	}
	
	/**
	 * Creates and returns a bugreport creation form
	 * @return bugreport creation form
	 * @throws UnauthorizedAccessException if the logged in user is not issuer
	 */
	public BugReportCreationForm makeBugReportCreationForm() throws UnauthorizedAccessException{
		if (!getBugTrap().isIssuerLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an issuer to perform this action.");
		
		return new BugReportCreationForm();
	}
	
	/**
	 * Creates and returns a bugreport update form
	 * @return bugreport update form
	 * @throws UnauthorizedAccessException if the logged in user is not dev
	 */
	public BugReportUpdateForm makeBugReportUpdateForm() throws UnauthorizedAccessException{
		if (!getBugTrap().isDeveloperLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as a dev to perform this action.");
		
		return new BugReportUpdateForm();
	}
	
	/**
	 * Creates and returns a bugreport assign form
	 * @return bugreport assign form
	 * @throws UnauthorizedAccessException if the logged in user is not dev
	 */
	public BugReportAssignForm makeBugReportAssignForm() throws UnauthorizedAccessException{
		if (!getBugTrap().isDeveloperLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as a dev to perform this action.");
		
		return new BugReportAssignForm();
	}
	
	/**
	 * Creates and returns a comment creation form
	 * @return comment creation form
	 * @throws UnauthorizedAccessException if the logged in user is not issuer
	 */
	public CommentCreationForm makeCommentCreationForm() throws UnauthorizedAccessException{
		if (!getBugTrap().isIssuerLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an issuer to perform this action.");
		
		return new CommentCreationForm();
	}
	
	/**
	 * Creates and returns a register notification form
	 * @return register notification form
	 * @throws UnauthorizedAccessException if no user is logged in
	 */
	public RegisterNotificationForm makeRegisterNotificationForm() throws UnauthorizedAccessException {
		if (!getBugTrap().isLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in to perform this action.");
		
		return new RegisterNotificationForm();
	}
	
	/**
	 * Creates and returns a unregister notification form
	 * @return unregister notification form
	 * @throws UnauthorizedAccessException if no user is logged in
	 */
	public UnregisterNotificationForm makeUnregisterNotificationForm() throws UnauthorizedAccessException {
		if (!getBugTrap().isLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in to perform this action.");

		return new UnregisterNotificationForm();
	}
	
	/**
	 * Creates and returns a show chronological notification form
	 * @return show chronological notification form
	 * @throws UnauthorizedAccessException if no user is logged in
	 */
	public ShowChronologicalNotificationForm makeShowChronologicalNotificationForm() throws UnauthorizedAccessException {
		if (!getBugTrap().isLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in to perform this action.");

		return new ShowChronologicalNotificationForm();
	}

	/**
	 * Creates and returns a propose test form
	 * @return propose test form
	 * @throws UnauthorizedAccessException if the logged in user is not dev
	 */
	public ProposeTestForm makeProposeTestForm() throws UnauthorizedAccessException {
		if (!getBugTrap().isDeveloperLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an issuer to perform this action.");
		
		return new ProposeTestForm();
	}
	
	/**
	 * Creates and returns a propose patch form
	 * @return propose patch form
	 * @throws UnauthorizedAccessException if the logged in user is not dev
	 */
	public ProposePatchForm makeProposePatchForm() throws UnauthorizedAccessException {
		if (!getBugTrap().isDeveloperLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an developer to perform this action.");
		
		return new ProposePatchForm();
	}
	
	/**
	 * Creates and returns a declare achieved milestone form
	 * @return propose patch form
	 * @throws UnauthorizedAccessException if the logged in user is not dev
	 */
	public DeclareAchievedMilestoneForm makeDeclareAchievedMilestoneForm() throws UnauthorizedAccessException {
		if (!getBugTrap().isDeveloperLoggedIn())
			throw new UnauthorizedAccessException("You need to be logged in as an developer to perform this action.");
		
		return new DeclareAchievedMilestoneForm();
	}
}
