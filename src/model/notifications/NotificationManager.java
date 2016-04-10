package model.notifications;

import java.util.ArrayList;
import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.bugtag.BugTag;
import model.notifications.builders.RegistrationBuilder;
import model.users.IUser;

public class NotificationManager {

	private final BugTrap bugTrap;

    private final List<Mailbox> mailboxes;
    private final List<Registration> registrations;

    public NotificationManager(BugTrap bugTrap) {
        this.bugTrap = bugTrap;
        this.mailboxes = new ArrayList<Mailbox>();
        this.registrations = new ArrayList<Registration>();
    }

    public Mailbox getMailboxForUser(IUser user) {
        for(Mailbox box : mailboxes) {
            if (box.getUser() == user)
                return box;
        }

        // No mailbox yet
        Mailbox box = new Mailbox(user);
        this.mailboxes.add(box);

        return box;
    }
    
    public void registerForNotification(RegistrationType registrationType, Observable observable, BugTag tag) throws UnauthorizedAccessException {
        if (!bugTrap.isLoggedIn())
            throw new UnauthorizedAccessException("You must be logged in to register for notifications.");

        IUser user = bugTrap.getUserManager().getLoggedInUser();
        Mailbox box = getMailboxForUser(user);
        Registration registration =  (new RegistrationBuilder()).setUser(user)
                                                                .setObservable(observable)
                                                                .setType(registrationType).setTag(tag)
                                                                .setMailbox(box)
                                                                .getRegistration();
        this.registrations.add(registration);
    }
    
    public void unregisterForNotification(Registration registration) throws UnauthorizedAccessException {
        if (!bugTrap.isLoggedIn())
            throw new UnauthorizedAccessException("You must be logged in to unregister for notifications.");

        registration.getObservable().detach(registration.getObserver());
        this.registrations.remove(registration);
    }

    public List<Registration> getRegistrationsLoggedInUser() throws UnauthorizedAccessException {
        if (!bugTrap.isLoggedIn())
            throw new UnauthorizedAccessException("You must be logged in to retrieve a list of registrations for notifications.");

        ArrayList<Registration> regs = new ArrayList<>();
    	for (Registration r : this.registrations)
            if (r.getUser() == bugTrap.getUserManager().getLoggedInUser())
                regs.add(r);

        return regs;
    }
}
