package model.notifications.forms;

import model.Form;

public class ShowChronologicalNotificationForm implements Form {

	private int nbOfNotifications;
	
	public void setNbOfNotifications(int nbOfNotifications) {
		this.nbOfNotifications = nbOfNotifications;
	}
	
	public int getNbOfNotifications() {
		return nbOfNotifications;
	}
	
	@Override
	public void allVarsFilledIn() {
		if (nbOfNotifications <= 0) throw new IllegalArgumentException("Number of notifications should be strictly positive.");
	}

}
