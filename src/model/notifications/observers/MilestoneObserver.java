package model.notifications.observers;

import model.notifications.Mailbox;
import model.notifications.NotificationType;
import model.notifications.Observable;
import model.notifications.Signalisation;

public class MilestoneObserver extends ObserverWithMailbox {

    public MilestoneObserver(Mailbox mailbox, Observable observes) {
        super(mailbox, observes);
    }

    @Override
    public void signal(Signalisation signalisation) {
        getMailbox().addNotification("The system " + signalisation.getSystem().getName() + " has achieved milestone " + signalisation.getSystem().getAchievedMilestone());
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.ACHIEVED_MILESTONE;
    }
}
