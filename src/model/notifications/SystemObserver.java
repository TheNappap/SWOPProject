package model.notifications;

import model.projects.ISystem;

public class SystemObserver extends Observer {

	public SystemObserver(Mailbox mailbox, ISystem system) {
		super(mailbox, system);
	}

	@Override
	public void signal() {
		throw new UnsupportedOperationException();
	}

}
