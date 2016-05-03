package model.bugreports.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.bugreports.BugReport;
import model.bugreports.forms.BugReportUpdateForm;

public class UpdateBugReportCommand extends Command {

    private BugReportUpdateForm form;

    public UpdateBugReportCommand(BugTrap bugTrap, BugReportUpdateForm form) {
        super(bugTrap);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        form.allVarsFilledIn();
        ((BugReport) form.getBugReport()).updateBugTag(form.getBugTag());
    }
}
