package model.projects.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.projects.Subsystem;
import model.projects.forms.SplitSubsystemForm;

public class SplitSubsystemCommand extends Command {

    private SplitSubsystemForm form;

    /**
     * Command that splits a subsystem
     * @param bugTrap BugTrap system.
     * @param form SplitSubsystemForm.
     */
    public SplitSubsystemCommand(BugTrap bugTrap, SplitSubsystemForm form) {
        super(bugTrap, form);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        form.allVarsFilledIn();
        ((Subsystem) form.getSubsystem()).split(form.getName1(), form.getName2(), form.getDescription1(), form.getDescription2(), form.getBugReports1(), form.getSubsystems1());
    }
}
