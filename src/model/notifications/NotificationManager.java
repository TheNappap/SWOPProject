package model.notifications;

import model.BugTrap;
import model.bugreports.IBugReport;
import model.bugreports.comments.Comment;
import model.projects.ISystem;
import model.users.IUser;

import java.util.ArrayList;

public class NotificationManager {
    private final BugTrap bugTrap;

    private final ArrayList<Mailbox> mailboxes;

    private final ArrayList<Observer> observers;

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

    public void signalCommentCreation(Comment comment, ISystem system) {

    }

    public void signalBugReportCreation(IBugReport report) {

    }

    public void signalBugReportUpdate(IBugReport report) {

    }
}
