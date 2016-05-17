package model.bugreports.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.bugreports.BugReport;
import model.bugreports.forms.ProposeTestForm;

public class ProposeTestCommand extends Command {

    private ProposeTestForm form;

    public ProposeTestCommand(BugTrap bugTrap, ProposeTestForm form) {
        super(bugTrap, form);
        this.form = form;
    }


    @Override
    public void execute() throws UnauthorizedAccessException {
        ((BugReport) form.getBugReport()).proposeTest(form.getTest());
    }
}
