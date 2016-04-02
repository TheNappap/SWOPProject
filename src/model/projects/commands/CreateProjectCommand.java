package model.projects.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.projects.Version;
import model.projects.forms.ProjectCreationForm;

import java.util.Date;

public class CreateProjectCommand extends Command {

    private ProjectCreationForm form;

    public CreateProjectCommand(BugTrap bugTrap, ProjectCreationForm form) {
        super(bugTrap);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        form.allVarsFilledIn();
        getBugTrap().getProjectManager().createProject(form.getName(), form.getDescription(), new Date(), form.getStartDate(), form.getBudgetEstimate(), form.getLeadDeveloper(), Version.firstVersion());
    }
}
