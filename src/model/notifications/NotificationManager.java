package model.notifications;

import java.util.ArrayList;
import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.notifications.observers.ObserverWithMailbox;
import model.users.IUser;

/**
 * Class that stores and manages mailboxes and registrations.
 */
public class NotificationManager {

	private final BugTrap bugTrap;

    private final List<Mailbox> mailboxes;
    private final List<ObserverWithMailbox> observers;

    /**
	 * Constructor.
	 */
    public NotificationManager(BugTrap bugTrap) {
        this.bugTrap = bugTrap;
        this.mailboxes = new ArrayList<Mailbox>();
        this.observers = new ArrayList<ObserverWithMailbox>();
    }

    /**
     * Gets the mailbox of a given user and creates one if none exists
     * @param user given user
     * @return the mailbox of the given user
     */
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

    /**
     * Returns specific number of last received notifications for the logged in user
     * @param nbOfNotifications the number of notifications
     * @return a list of notifications with length of the given number
     * @throws UnauthorizedAccessException
     */
	public List<INotification> getNotifications(int nbOfNotifications) throws UnauthorizedAccessException {
		 if (!bugTrap.isLoggedIn())
	            throw new UnauthorizedAccessException("You must be logged in to retrieve notifications.");
		
		IUser user = bugTrap.getUserManager().getLoggedInUser();
		Mailbox box = getMailboxForUser(user);
		
		return box.getNotifications(nbOfNotifications);
	}

    public List<IRegistration> getRegistrationsLoggedInUser() throws UnauthorizedAccessException {
        IUser user = bugTrap.getUserManager().getLoggedInUser();
        if (user == null)
            throw new UnauthorizedAccessException("You need to be logged in to get a list of registrations for notifications.");

        List<IRegistration> regs = new ArrayList<>();
        for (ObserverWithMailbox o : observers) {
            if (o.getUser() == user) {
                regs.add(o);
            }
        }
        return regs;
    }

    public void addObserver(ObserverWithMailbox observerWithMailbox) {
        observers.add(observerWithMailbox);
    }

    public void removeObserver(ObserverWithMailbox observerWithMailbox) {
        observers.remove(observerWithMailbox);
        observerWithMailbox.terminate();
    }

    public void removeObservable(Observable observable) {
        for (int i = 0; i < observers.size(); i++) {
            ObserverWithMailbox o = observers.get(i);
            if (o.getObserves() == observable) {
            	removeObserver(o);
                i--;
            }
        }
    }
}
