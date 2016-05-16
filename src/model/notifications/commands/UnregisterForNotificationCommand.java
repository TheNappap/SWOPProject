package model.notifications.commands;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.Command;
import model.notifications.Observable;
import model.notifications.forms.UnregisterNotificationForm;
import model.notifications.observers.Observer;
import model.notifications.observers.ObserverWithMailbox;

public class UnregisterForNotificationCommand extends Command {

    private UnregisterNotificationForm form;

    public UnregisterForNotificationCommand(BugTrap bugTrap, UnregisterNotificationForm form) {
        super(bugTrap, form);
        this.form = form;
    }

    @Override
    public void execute() throws UnauthorizedAccessException {
        ObserverWithMailbox observer = (ObserverWithMailbox)form.getRegistration();
        observer.getObserves().detach(observer);
        getBugTrap().getNotificationManager().removeObserver(observer);
    }
}
