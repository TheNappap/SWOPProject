package model.notifications.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.notifications.Mailbox;
import model.notifications.forms.RegisterNotificationForm;
import model.notifications.observers.ObserverWithMailbox;

public class RegisterForNotificationCommand extends Command {

    private RegisterNotificationForm form;

    public RegisterForNotificationCommand(BugTrap bugTrap, RegisterNotificationForm form){
        super(bugTrap, form);

        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        Mailbox box = getBugTrap().getNotificationManager().getMailboxForUser(getBugTrap().getUserManager().getLoggedInUser());
        ObserverWithMailbox observer = form.getRegistrationType().createObserver(box, form.getObservable(), form.getTag());
        form.getObservable().attach(observer);
        getBugTrap().getNotificationManager().addObserver(observer);
    }
}
