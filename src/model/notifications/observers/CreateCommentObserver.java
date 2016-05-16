package model.notifications.observers;

import model.notifications.Mailbox;
import model.notifications.NotificationType;
import model.notifications.Observable;
import model.notifications.signalisations.Signalisation;

public class CreateCommentObserver extends ObserverWithMailbox {
	
	public CreateCommentObserver(Mailbox mailbox, Observable commentable) {
		super(mailbox, commentable);
	}


	@Override
	public void signal(Signalisation signalisation) {
		if (signalisation.getType() == getNotificationType()) {
			getMailbox().addNotification("New comment on '" + signalisation.getBugReport().getTitle());
		}
	}

	@Override
	public NotificationType getNotificationType() {
		return NotificationType.CREATE_COMMENT;
	}
}
