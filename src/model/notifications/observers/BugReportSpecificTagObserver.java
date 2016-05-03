package model.notifications.observers;

import model.bugreports.bugtag.BugTag;
import model.notifications.Mailbox;
import model.notifications.NotificationType;
import model.notifications.Observable;
import model.notifications.signalisations.Signalisation;

public class BugReportSpecificTagObserver extends BugReportChangeObserver {

	private BugTag bugTag;
	
	public BugReportSpecificTagObserver(Mailbox mailbox, Observable observable, BugTag bugTag) {
		super(mailbox, observable);
		
		this.bugTag = bugTag;
	}

	@Override
	public void signal(Signalisation signalisation) {
		if (signalisation.getType() == NotificationType.BUGREPORT_CHANGE && signalisation.getBugReport().getBugTag() == this.bugTag) {
			getMailbox().addNotification("The bugreport '" + signalisation.getBugReport().getTitle() + "' has received the tag " + signalisation.getBugReport().getBugTag());
		}
	}
}
