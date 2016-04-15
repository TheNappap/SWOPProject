package model.projects.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.projects.forms.ProjectUpdateForm;

public class UpdateProjectCommand extends Command {
    private ProjectUpdateForm form;

    /**
     * Command that updates a Project when executed.
     * @param bugTrap BugTrap system.
     * @param form ProjectUpdateForm.
     */
    public UpdateProjectCommand(BugTrap bugTrap, ProjectUpdateForm form) {
        super(bugTrap);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        form.allVarsFilledIn();
        getBugTrap().getProjectManager().updateProject(form.getProject(), form.getName(), form.getDescription(), form.getBudgetEstimate(), form.getStartDate());
    }
}
