package model.notifications.observers;

import model.notifications.Signalisation;

/**
 * Observer
 */
public interface Observer {
    void signal(Signalisation signalisation);

}
