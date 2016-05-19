package model.notifications.observers;

import model.notifications.Mailbox;
import model.notifications.NotificationType;
import model.notifications.Observable;
import model.notifications.Signalisation;

public class ProjectForkObserver extends ObserverWithMailbox {

    public ProjectForkObserver(Mailbox mailbox, Observable observes) {
        super(mailbox, observes);
    }

    @Override
    public void signal(Signalisation signalisation) {
        if (signalisation.getType() == getNotificationType()) {
            getMailbox().addNotification("The project " + signalisation.getSystem().getName() + " has been forked.");
        }
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.PROJECT_FORK;
    }
}
