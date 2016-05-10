package model.projects.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.projects.forms.ProjectDeleteForm;

public class DeleteProjectCommand extends Command {
    private ProjectDeleteForm form;

    /**
     * Command that deletes Project when executed.
     * @param bugTrap BugTrap system.
     * @param form ProjectDeleteForm.
     */
    public DeleteProjectCommand(BugTrap bugTrap, ProjectDeleteForm form) {
        super(bugTrap, form);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        getBugTrap().getProjectManager().deleteProject(form.getProject());
    }
}
