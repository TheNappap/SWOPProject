package model.notifications;

import model.bugreports.IBugReport;
import model.bugreports.comments.Comment;
import model.projects.ISystem;

public abstract class Observer {

	private Mailbox mailbox;
	
	public Observer(Mailbox mailbox) {
		this.mailbox = mailbox;
	}
	
	public void signal(Comment comment, ISystem system) {

	}

	public void signal(IBugReport bugReport) {

	}
	
	public Mailbox getMailbox() {
		return mailbox;
	}
}
