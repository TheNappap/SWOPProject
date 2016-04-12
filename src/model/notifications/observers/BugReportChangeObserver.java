package model.notifications.observers;

import model.bugreports.IBugReport;
import model.notifications.Mailbox;
import model.notifications.Observable;
import model.notifications.RegistrationType;
import model.notifications.signalisations.Signalisation;

public class BugReportChangeObserver extends Observer {

	public BugReportChangeObserver(Mailbox mailbox, Observable observable) {
		super(mailbox, observable);
	}

	@Override
	public void signal(Signalisation signalisation) {
		if (signalisation.getType() == RegistrationType.BUGREPORT_CHANGE) {
			getMailbox().addNotification("The bugreport '" + signalisation.getBugReport().getTitle() + "' has received the tag " + signalisation.getBugReport().getBugTag());
		}
	}
}
