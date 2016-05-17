package model.notifications.observers;

import model.notifications.Mailbox;
import model.notifications.NotificationType;
import model.notifications.Observable;
import model.notifications.signalisations.Signalisation;

public class ProjectForkObserver extends ObserverWithMailbox {

    public ProjectForkObserver(Mailbox mailbox, Observable observes) {
        super(mailbox, observes);
    }

    @Override
    public void signal(Signalisation signalisation) {
        if (signalisation.getType() == getNotificationType()) {
            getMailbox().addNotification("BLABLABLAAAAAAAAAAAAAAA"); // TODO: Add useful text here
        }
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.PROJECT_FORK;
    }
}
