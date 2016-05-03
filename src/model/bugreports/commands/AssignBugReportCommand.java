package model.bugreports.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.bugreports.BugReport;
import model.bugreports.forms.BugReportAssignForm;

public class AssignBugReportCommand extends Command {
    private BugReportAssignForm form;

    public AssignBugReportCommand(BugTrap bugTrap, BugReportAssignForm form) {
        super(bugTrap);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        form.allVarsFilledIn();
        ((BugReport)form.getBugReport()).assignDeveloper(form.getDeveloper());
    }
}
