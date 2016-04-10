package model.notifications;

import model.bugreports.comments.Commentable;

public class CreateCommentObserver extends Observer {
	
	public CreateCommentObserver(Mailbox mailbox, Commentable commentable) {
		super(mailbox, commentable);
	}


	@Override
	public void signal(String notificationText) {
		this.getMailbox().addNotification(notificationText);
	}
}
