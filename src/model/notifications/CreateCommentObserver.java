package model.notifications;

import model.bugreports.comments.Commentable;

public class CreateCommentObserver extends Observer {
	
	public CreateCommentObserver(Mailbox mailbox, Commentable commentable) {
		super(mailbox);
	}
	
	public void signal() { 
		getMailbox().addNotification("A new comment has been created!");
	}
}
