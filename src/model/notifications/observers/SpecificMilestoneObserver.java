package model.notifications.observers;

import model.notifications.Mailbox;
import model.notifications.NotificationType;
import model.notifications.Observable;
import model.notifications.signalisations.Signalisation;
import model.projects.AchievedMilestone;

public class SpecificMilestoneObserver extends MilestoneObserver{

    private AchievedMilestone milestone;

    public SpecificMilestoneObserver(Mailbox mailbox, Observable observes, AchievedMilestone milestone) {
        super(mailbox, observes);
        this.milestone = milestone;
    }

    @Override
    public void signal(Signalisation signalisation) {
        if (signalisation.getType() == NotificationType.ACHIEVED_MILESTONE && signalisation.getSystem().getAchievedMilestone().compareTo(milestone) == 0) {
            getMailbox().addNotification("The system " + signalisation.getSystem().getName() + " has achieved the milestone " + milestone);
        }
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.ACHIEVED_SPECIFIC_MILESTONE;
    }
}
