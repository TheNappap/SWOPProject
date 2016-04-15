package model.bugreports.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.bugreports.forms.BugReportAssignForm;

public class AssignBugReportCommand extends Command {
    private BugReportAssignForm form;

    /**
     * Command to assign Developers to BugReport.
     * @param bugTrap BugTrap system.
     * @param form BugReportAssignForm
     */
    public AssignBugReportCommand(BugTrap bugTrap, BugReportAssignForm form) {
        super(bugTrap);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        form.allVarsFilledIn();
        getBugTrap().getBugReportManager().assignToBugReport(form.getBugReport(), form.getDeveloper());
    }
}
