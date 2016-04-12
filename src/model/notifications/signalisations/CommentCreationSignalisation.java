package model.notifications.signalisations;

import model.bugreports.IBugReport;
import model.notifications.RegistrationType;

public class CommentCreationSignalisation extends Signalisation{

	public CommentCreationSignalisation(IBugReport bugReport) {
		super(RegistrationType.CREATE_COMMENT, bugReport);
	}

}
