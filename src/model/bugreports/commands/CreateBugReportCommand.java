package model.bugreports.commands;

import java.util.ArrayList;
import java.util.Date;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.bugreports.bugtag.New;
import model.bugreports.forms.BugReportCreationForm;
import model.users.IUser;

/**
 * Created by matth on 01-Apr-16.
 */
public class CreateBugReportCommand extends Command {

    private BugReportCreationForm form;

    public CreateBugReportCommand(BugTrap bugTrap, BugReportCreationForm form) {
        super(bugTrap);
        
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        form.allVarsFilledIn();
        getBugTrap().getBugReportManager().addBugReport(form.getTitle(), form.getDescription(), new Date(), form.getSubsystem(), form.getIssuer(), form.getDependsOn(), new ArrayList<IUser>(), new New());
    }
}
