package model.projects.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.projects.System;
import model.projects.forms.DeclareAchievedMilestoneForm;

public class DeclareAchievedMilestoneCommand extends Command {

    private DeclareAchievedMilestoneForm form;

    public DeclareAchievedMilestoneCommand(BugTrap bugTrap, DeclareAchievedMilestoneForm form) {
        super(bugTrap, form);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        ((System) form.getSystem()).declareAchievedMilestone(form.getNumbers());
    }
}
