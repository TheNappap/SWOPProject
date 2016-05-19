package model.notifications.observers;

import model.notifications.IRegistration;
import model.notifications.Mailbox;
import model.notifications.NotificationType;
import model.notifications.Observable;
import model.notifications.Signalisation;
import model.users.IUser;

/**
 * An abstract class for an observer, an observer can observe an observable object.
 */
public abstract class ObserverWithMailbox implements Observer, IRegistration {

	private Mailbox mailbox;
	private Observable observes;
	
	public ObserverWithMailbox(Mailbox mailbox, Observable observes) {
		this.mailbox = mailbox;
		this.observes = observes;
		observes.attach(this);
	}
	
	public abstract void signal(Signalisation signalisation);

	@Override
	public abstract NotificationType getNotificationType();
	
	protected Mailbox getMailbox() {
		return mailbox;
	}

	@Override
	public Observable getObserves() {
		return observes;
	}

	@Override
	public IUser getUser() {
		return getMailbox().getUser();
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
