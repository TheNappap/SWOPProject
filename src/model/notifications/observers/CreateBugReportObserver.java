package model.notifications.observers;

import model.notifications.Mailbox;
import model.notifications.NotificationType;
import model.notifications.Observable;
import model.notifications.signalisations.Signalisation;

public class CreateBugReportObserver extends ObserverWithMailbox {

	public CreateBugReportObserver(Mailbox mailbox, Observable observable) {
		super(mailbox, observable);
	}

	@Override
	public void signal(Signalisation signalisation) {
		if (signalisation.getType() == NotificationType.CREATE_BUGREPORT) {
			getMailbox().addNotification("New bug report: '" + signalisation.getBugReport().getTitle() + "'");
		}
	}
}
