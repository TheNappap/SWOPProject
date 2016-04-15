package model.notifications.observers;

import model.notifications.Mailbox;
import model.notifications.Observable;
import model.notifications.RegistrationType;
import model.notifications.signalisations.Signalisation;

public class CreateCommentObserver extends ObserverWithMailbox {
	
	public CreateCommentObserver(Mailbox mailbox, Observable commentable) {
		super(mailbox, commentable);
	}


	@Override
	public void signal(Signalisation signalisation) {
		if (signalisation.getType() == RegistrationType.CREATE_COMMENT) {
			getMailbox().addNotification("New comment on '" + signalisation.getBugReport().getTitle());
		}
	}
}
