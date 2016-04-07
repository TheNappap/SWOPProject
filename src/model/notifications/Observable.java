package model.notifications;

import java.util.Observer;

public interface Observable {

	public String getInfo();
	public void attach(Observer observer);
	public void detach(Observer observer);
	public void notifyObservers();
}
