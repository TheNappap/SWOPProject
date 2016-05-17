package model.projects.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.projects.Subsystem;
import model.projects.forms.MergeSubsystemForm;

public class MergeSubsystemCommand extends Command {

    private MergeSubsystemForm form;

    /**
     * Command that merges two subsystems
     * @param bugTrap BugTrap system.
     * @param form MergeSubsystemForm.
     */
    public MergeSubsystemCommand(BugTrap bugTrap, MergeSubsystemForm form) {
        super(bugTrap, form);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        form.allVarsFilledIn();
        ((Subsystem) form.getSubsystem1()).merge(form.getName(), form.getDescription(), form.getSubsystem2());
    }
}
