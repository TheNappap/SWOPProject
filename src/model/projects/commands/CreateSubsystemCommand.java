package model.projects.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.projects.System;
import model.projects.forms.SubsystemCreationForm;

public class CreateSubsystemCommand extends Command {
    private SubsystemCreationForm form;

    /**
     * Command that creates Subsystem when executed.
     * @param bugTrap BugTrap system.
     * @param form SubsystemCreationForm.
     */
    public CreateSubsystemCommand(BugTrap bugTrap, SubsystemCreationForm form) {
        super(bugTrap, form);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        form.allVarsFilledIn();
        ((System)form.getParent()).createSubsystem(form.getName(), form.getDescription());
    }
}
