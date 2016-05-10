package model.projects.commands;

import java.util.Date;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.projects.Version;
import model.projects.forms.ProjectCreationForm;

public class CreateProjectCommand extends Command {

    private ProjectCreationForm form;

    /**
     * Command that creates a Project when executed.
     * @param bugTrap BugTrap system.
     * @param form ProjectCreationForm.
     */
    public CreateProjectCommand(BugTrap bugTrap, ProjectCreationForm form) {
        super(bugTrap, form);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        form.allVarsFilledIn();
        getBugTrap().getProjectManager().createProject(form.getName(), form.getDescription(), new Date(), form.getStartDate(), form.getBudgetEstimate(), form.getLeadDeveloper(), Version.firstVersion());
    }
}
