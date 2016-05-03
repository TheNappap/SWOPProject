package model.notifications.signalisations;

import model.bugreports.IBugReport;
import model.notifications.NotificationType;

public class CommentCreationSignalisation extends Signalisation{

	public CommentCreationSignalisation(IBugReport bugReport) {
		super(NotificationType.CREATE_COMMENT, bugReport);
	}

}
