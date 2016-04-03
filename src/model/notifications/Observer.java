package model.notifications;

public abstract class Observer {

	private Mailbox mailbox;
	
	public Observer(Mailbox mailbox) {
		this.mailbox = mailbox;
	}
	
	public abstract void signal();
	
	public Mailbox getMailbox() {
		return mailbox;
	}
}
