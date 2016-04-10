package model.notifications.builders;

import model.bugreports.bugtag.BugTag;
import model.notifications.Mailbox;
import model.notifications.Observable;
import model.notifications.Registration;
import model.notifications.RegistrationType;
import model.users.IUser;

public class RegistrationBuilder {

    private RegistrationType type;
    private Observable observable;
    public Mailbox box;
    private BugTag tag;
    private IUser user;

    /**
     * This class is used to construct Registration objects
     */
    public RegistrationBuilder() {

    }

    public RegistrationBuilder setObservable(Observable observable) {
        this.observable = observable;
        return this;
    }

    public RegistrationBuilder setTag(BugTag tag) {
        this.tag = tag;
        return this;
    }

    public RegistrationBuilder setType(RegistrationType type) {
        this.type = type;
        return this;
    }

    public RegistrationBuilder setUser(IUser user) {
        this.user = user;
        return this;
    }

    public RegistrationBuilder setMailbox(Mailbox box) {
        this.box = box;
        return this;
    }

    public Registration getRegistration() {
        validate();
        return new Registration(type, observable, tag, user, box);
    }

    private void validate() {
        if (user == null) throw new NullPointerException("User may not be null.");
        if (observable == null) throw new NullPointerException("Observable may not be null.");
        if (type == null) throw new NullPointerException("Registration type may not be null.");
        if (box == null) throw new NullPointerException("Mailbox may not be null.");
        if (type == RegistrationType.BUGREPORT_SPECIFIC_TAG && tag == null) throw new NullPointerException("Tag may not be null.");
    }
}
