package model.notifications.observers;

import model.notifications.Mailbox;
import model.notifications.NotificationType;
import model.notifications.Observable;
import model.notifications.signalisations.Signalisation;

public class SystemVersionUpdateObserver extends ObserverWithMailbox {

    public SystemVersionUpdateObserver(Mailbox mailbox, Observable observes) {
        super(mailbox, observes);
    }

    @Override
    public void signal(Signalisation signalisation) {
        if (signalisation.getType() == getNotificationType()) {
            getMailbox().addNotification("The system " + signalisation.getSystem().getName() + " has achieved version " + signalisation.getSystem().getVersion());
        }
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.PROJECT_VERSION_UPDATE;
    }
}
