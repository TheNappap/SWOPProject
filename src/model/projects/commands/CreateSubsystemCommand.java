package model.projects.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.projects.Version;
import model.projects.forms.SubsystemCreationForm;

public class CreateSubsystemCommand extends Command {
    private SubsystemCreationForm form;

    public CreateSubsystemCommand(BugTrap bugTrap, SubsystemCreationForm form) {
        super(bugTrap);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        form.allVarsFilledIn();
        getBugTrap().getProjectManager().createSubsystem(form.getName(), form.getDescription(), form.getProject(), form.getParent(), Version.firstVersion());
    }
}
