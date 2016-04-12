package model.notifications;

import model.notifications.observers.Observer;

/**
 * Interface for objects that are observable.
 */
public interface Observable {

	/**
	 * Attach the given Observer to this Observable.
	 * @param observer The Observer to attach.
	 */
	public void attach(Observer observer);
	
	/**
	 * Detach the given Observer from this Observable.
	 * @param observer The Observer to detach.
	 */
	public void detach(Observer observer);
}
