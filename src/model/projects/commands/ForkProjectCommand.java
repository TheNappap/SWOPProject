package model.projects.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.projects.forms.ProjectForkForm;

public class ForkProjectCommand extends Command {

    private ProjectForkForm form;

    public ForkProjectCommand(BugTrap bugTrap, ProjectForkForm form) {
        super(bugTrap);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        form.allVarsFilledIn();
        getBugTrap().getProjectManager().createFork(form.getProject(), form.getBudgetEstimate(), form.getVersion(), form.getStartDate());
    }
}