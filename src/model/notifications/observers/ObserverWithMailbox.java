package model.notifications.observers;

import model.notifications.Mailbox;
import model.notifications.Observable;
import model.notifications.signalisations.Signalisation;

/**
 * An abstract class for an observer, an observer can observe an observable object.
 */
public abstract class ObserverWithMailbox {

	private final Mailbox mailbox;
	private final Observable observes;
	
	public ObserverWithMailbox(Mailbox mailbox, Observable observes) {
		this.mailbox = mailbox;
		this.observes = observes;
	}
	
	public abstract void signal(Signalisation signalisation);
	
	public Mailbox getMailbox() {
		return mailbox;
	}
}
