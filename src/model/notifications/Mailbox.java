package model.notifications;

import java.util.ArrayList;
import java.util.List;

import model.users.IUser;

/**
 * This class collects notifications for a user.
 */
public class Mailbox {
	
    private IUser user;
    private List<INotification> notificationList;

    public Mailbox(IUser user) {
        this.user = user;
        this.notificationList = new ArrayList<>();
    }

    public void addNotification(String text) {
    	Notification notification = new Notification(text);
        notificationList.add(0, notification);
    }

    public List<INotification> getNotifications(int nbOfNotifications) {
    	ArrayList<INotification> notifications = new ArrayList<>();
        for (int i = 0; i < nbOfNotifications && i < this.notificationList.size(); i++) {
            notifications.add(this.notificationList.get(i));
        }
        return notifications;
    }
    
    public IUser getUser() {
        return user;
    }

    public List<INotification> getNotifications() {
        ArrayList<INotification> notifs = new ArrayList<>();
        for (INotification n : notificationList)
            notifs.add(n);
        return notifs;
    }
}
