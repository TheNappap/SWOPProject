package model.notifications;

import model.users.IUser;

import java.util.ArrayList;
import java.util.List;

public class Mailbox {
	
    private IUser user;
    private List<INotification> notificationList;

    public Mailbox() {
    }

    public void addNotification(String text) {
    	
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
