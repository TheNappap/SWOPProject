package model.notifications.observers;

import model.notifications.Mailbox;
import model.notifications.Observable;
import model.notifications.signalisations.Signalisation;

/**
 * An abstract class for an observer, an observer can observe an observable object.
 */
public abstract class ObserverWithMailbox implements Observer {

	private Mailbox mailbox;
	private Observable observes;
	
	public ObserverWithMailbox(Mailbox mailbox, Observable observes) {
		this.mailbox = mailbox;
		this.observes = observes;
	}
	
	public abstract void signal(Signalisation signalisation);
	
	protected Mailbox getMailbox() {
		return mailbox;
	}
	
	protected Observable getObserves() {
		return observes;
	}
	
	/**
     * Terminates this observer
     */
	public void terminate() {
		mailbox = null;
		observes.detach(this);
		observes = null;
	}
}
