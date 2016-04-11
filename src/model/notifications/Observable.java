package model.notifications;

/**
 * Interface for objects that are observable.
 */
public interface Observable {

	public void attach(Observer observer);
	public void detach(Observer observer);
}
