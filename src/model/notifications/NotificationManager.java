package model.notifications;

import java.util.ArrayList;
import java.util.List;

import controllers.exceptions.UnauthorizedAccessException;
import model.BugTrap;
import model.bugreports.bugtag.BugTag;
import model.notifications.builders.RegistrationBuilder;
import model.users.IUser;

/**
 * Class that stores and manages mailboxes and registrations.
 */
public class NotificationManager {

	private final BugTrap bugTrap;

    private final List<Mailbox> mailboxes;
    private final List<Registration> registrations;

    /**
	 * Constructor.
	 */
    public NotificationManager(BugTrap bugTrap) {
        this.bugTrap = bugTrap;
        this.mailboxes = new ArrayList<Mailbox>();
        this.registrations = new ArrayList<Registration>();
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
     * Registers for notifications with a given type, observable and a specific tag.
     * The tag is only used if the type is BUGREPORT_SPECIFIC_TAG
     * @param registrationType the type
     * @param observable the object that is observed
     * @param tag the specific tag
     * @throws UnauthorizedAccessException
     */
    public void registerForNotification(RegistrationType registrationType, Observable observable, BugTag tag) throws UnauthorizedAccessException {
        if (!bugTrap.isLoggedIn())
            throw new UnauthorizedAccessException("You must be logged in to register for notifications.");

        IUser user = bugTrap.getUserManager().getLoggedInUser();
        Mailbox box = getMailboxForUser(user);
        Registration registration =  (new RegistrationBuilder()).setObservable(observable)
                                                                .setType(registrationType).setTag(tag)
                                                                .setMailbox(box)
                                                                .getRegistration();
        this.registrations.add(registration);
    }
    
    /**
     * Unregisters for notifications with a given registration
     * @param registration the given registration
     * @throws UnauthorizedAccessException
     */
    public void unregisterForNotification(Registration registration) throws UnauthorizedAccessException {
        if (!bugTrap.isLoggedIn())
            throw new UnauthorizedAccessException("You must be logged in to unregister for notifications.");

        registration.getObservable().detach(registration.getObserver());
        this.registrations.remove(registration);
    }

    /**
     * Returns the registrations of the logged in user
     * @return a list of registrations for the logged ins user
     * @throws UnauthorizedAccessException
     */
    public List<Registration> getRegistrationsLoggedInUser() throws UnauthorizedAccessException {
        if (!bugTrap.isLoggedIn())
            throw new UnauthorizedAccessException("You must be logged in to retrieve a list of registrations for notifications.");

        ArrayList<Registration> regs = new ArrayList<>();
    	for (Registration r : this.registrations)
            if (r.getUser() == bugTrap.getUserManager().getLoggedInUser())
                regs.add(r);

        return regs;
    }
    
    /**
     * Deletes all the registrations for a given observable
     * @param obs the given observable
     * @throws UnauthorizedAccessException
     */
    public void deleteRegistrationsForObservable(Observable obs) throws UnauthorizedAccessException {
        if (!bugTrap.isLoggedIn())
            throw new UnauthorizedAccessException("You must be logged in to retrieve a list of registrations.");
        
        for (int i = 0; i < registrations.size(); i++) {
			Registration reg = registrations.get(i);
			if(reg.getObservable() == obs){
        		reg.terminate();
        		registrations.remove(reg);
        	}
		}
        		
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

	/**
	 * Returns a list of registration types
	 * @return list of registration types
	 */
	public RegistrationType[] getRegistrationTypes() {
		return RegistrationType.values();
	}
}
