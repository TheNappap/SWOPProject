package model.notifications;

import model.bugreports.comments.Comment;
import model.projects.ISystem;

public class CreateBugReportObserver extends Observer {

	public CreateBugReportObserver(Mailbox mailbox) {
		super(mailbox);
	}

	@Override
	public void signal(Comment comment, ISystem system) {

	}

}
