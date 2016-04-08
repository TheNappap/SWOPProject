package model.notifications;


public interface Observable {

	public String getInfo();
	public void attach(Observer observer);
	public void detach(Observer observer);
	public void notifyObservers();
}
