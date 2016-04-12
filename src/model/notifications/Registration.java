package model.notifications;

import model.bugreports.bugtag.BugTag;
import model.notifications.observers.Observer;
import model.users.IUser;

/**
 * Represents a registration for a certain notification
 */
public class Registration {
    private final RegistrationType registrationType;
    private final Observable observable;
    private final Observer observer;
    private final Mailbox box;

    public Registration(RegistrationType type, Observable observable, Mailbox box, BugTag tag) {
        this.registrationType = type;
        this.observable = observable;
        this.box = box;

        this.observer = type.createObserver(box, observable, tag);

        this.observable.attach(this.observer);
    }

    public Mailbox getMailbox() {
        return box;
    }
    
    public IUser getUser() {
        return getMailbox().getUser();
    }

    public Observable getObservable() {
        return observable;
    }

    public Observer getObserver() {
        return observer;
    }

    public RegistrationType getRegistrationType() {
        return registrationType;
    }
}
