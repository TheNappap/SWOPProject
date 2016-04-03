package model.projects.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.projects.forms.ProjectDeleteForm;

public class DeleteProjectCommand extends Command {
    private ProjectDeleteForm form;

    public DeleteProjectCommand(BugTrap bugTrap, ProjectDeleteForm form) {
        super(bugTrap);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        getBugTrap().getProjectManager().deleteProject(form.getProject());
    }
}
