package model.notifications;

public abstract class Observer {

	private final Mailbox mailbox;
	private final Observable observes;
	
	public Observer(Mailbox mailbox, Observable observes) {
		this.mailbox = mailbox;
		this.observes = observes;
	}
	
	public abstract void signal();
	
	public Mailbox getMailbox() {
		return mailbox;
	}
	
	public Observable observes() {
		return observes;
	}
}
