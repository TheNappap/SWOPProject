package model.notifications;

import model.projects.ISystem;

public class SystemObserver extends Observer {

	public SystemObserver(Mailbox mailbox, ISystem system) {
		super(mailbox, system);
	}

	@Override
	public void signal(String notificationText) {
		this.getMailbox().addNotification(notificationText);
	}

}
