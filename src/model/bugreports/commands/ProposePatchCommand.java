package model.bugreports.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.bugreports.BugReport;
import model.bugreports.forms.ProposePatchForm;

public class ProposePatchCommand extends Command {

    private ProposePatchForm form;

    public ProposePatchCommand(BugTrap bugTrap, ProposePatchForm form) {
        super(bugTrap, form);
        this.form = form;
    }


    @Override
    public void execute() throws UnauthorizedAccessException {
        ((BugReport) form.getBugReport()).proposePatch(form.getPatch());
    }
}
