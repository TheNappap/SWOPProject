package model.notifications.observers;

import model.notifications.signalisations.Signalisation;

/**
 * Observer
 */
public interface Observer {
    void signal(Signalisation signalisation);
}
