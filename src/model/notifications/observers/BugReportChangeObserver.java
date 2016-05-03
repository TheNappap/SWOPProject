package model.notifications.observers;

import model.notifications.Mailbox;
import model.notifications.NotificationType;
import model.notifications.Observable;
import model.notifications.signalisations.Signalisation;

public class BugReportChangeObserver extends ObserverWithMailbox {

	/**
	 * 
	 * @param mailbox
	 * @param observable
	 */
	public BugReportChangeObserver(Mailbox mailbox, Observable observable) {
		super(mailbox, observable);
	}

	@Override
	public void signal(Signalisation signalisation) {
		if (signalisation.getType() == NotificationType.BUGREPORT_CHANGE) {
			getMailbox().addNotification("The bugreport '" + signalisation.getBugReport().getTitle() + "' has received the tag " + signalisation.getBugReport().getBugTag());
		}
	}
}
