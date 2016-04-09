package model.notifications;

import java.util.ArrayList;
import java.util.List;

import model.BugTrap;
import model.users.IUser;

public class NotificationManager {

	private final BugTrap bugTrap;

    private final List<Mailbox> mailboxes;
    private final List<Observer> observers;

    public NotificationManager(BugTrap bugTrap) {
        this.bugTrap = bugTrap;
        this.mailboxes = new ArrayList<Mailbox>();
        this.observers = new ArrayList<Observer>();
    }

    public Mailbox getMailboxForUser(IUser user) {
        for(Mailbox box : mailboxes) {
            if (box.getUser() == user)
                return box;
        }

        return null;
    }
    
    public void registerForNotification(Observable observable, Registration registration) {
    	throw new UnsupportedOperationException();
    }
    
    public void unregisterForNotification(Registration registration) {
    	throw new UnsupportedOperationException();
    }

    public List<Registration> getRegistrationsLoggedInUser() {
    	throw new UnsupportedOperationException();
    }
}
