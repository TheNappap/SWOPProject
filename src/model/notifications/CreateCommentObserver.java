package model.notifications;

import model.bugreports.comments.Comment;
import model.bugreports.comments.Commentable;
import model.projects.ISystem;

public class CreateCommentObserver extends Observer {
	
	public CreateCommentObserver(Mailbox mailbox, Commentable commentable) {
		super(mailbox);
	}

	@Override
	public void signalCommentCreation(Comment comment, ISystem system) {
		getMailbox().addNotification("A new comment has been created!");
	}
}
