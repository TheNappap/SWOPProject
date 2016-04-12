package model.notifications;

import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
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

        switch (registrationType) {
            case BUGREPORT_CHANGE:
                this.observer = new BugReportObserver(box, observable);
                break;
            case BUGREPORT_SPECIFIC_TAG:
                this.observer = new BugReportSpecificTagObserver(box, observable, tag);
                break;
            case CREATE_BUGREPORT:
                this.observer = new CreateBugReportObserver(box, observable);
                break;
            case CREATE_COMMENT:
                this.observer = new CreateCommentObserver(box, observable);
                break;
        }

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
