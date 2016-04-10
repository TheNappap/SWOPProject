package model.notifications;

import model.bugreports.IBugReport;
import model.bugreports.bugtag.BugTag;
import model.users.IUser;

/**
 * Represents a registration for a certain notification
 */
public class Registration {
    private RegistrationType registrationType;
    private Observable observable;
    private Mailbox mailbox;
    private Observer observer;
    private IUser user;
    private BugTag tag;

    public Registration(RegistrationType type, Observable observable, BugTag tag, IUser user, Mailbox box) {
        this.registrationType = type;
        this.observable = observable;
        this.user = user;
        this.tag = tag;
        this.mailbox = box;

        switch (registrationType) {
            case BUGREPORT_CHANGE:
                this.observer = new BugReportObserver(box, observable);
                break;
            case BUGREPORT_SPECIFIC_TAG:
                this.observer = new BugReportSpecificTagObserver(box, (IBugReport)observable, tag);
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

    void setObservable(Observable observable) {
        this.observable = observable;
    }

    void setUser(IUser user) {
        this.user = user;
    }

    public IUser getUser() {
        return user;
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
