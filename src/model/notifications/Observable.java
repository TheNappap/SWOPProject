package model.notifications;


public interface Observable {

	public void attach(Observer observer);
	public void detach(Observer observer);
}
