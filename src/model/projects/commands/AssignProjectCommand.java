package model.projects.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.projects.forms.ProjectAssignForm;

public class AssignProjectCommand extends Command {

    private ProjectAssignForm form;

    /**
     * Command that assigns a Developer with Role when executed.
     * @param bugTrap BugTrap system.
     * @param form ProjectAssignForm.
     */
    public AssignProjectCommand(BugTrap bugTrap,ProjectAssignForm form) {
        super(bugTrap);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        form.allVarsFilledIn();

        getBugTrap().getProjectManager().assignToProject(form.getProject(), form.getDeveloper(), form.getRole());
    }
}
