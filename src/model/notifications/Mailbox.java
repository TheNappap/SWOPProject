package model.notifications;

import java.util.ArrayList;
import java.util.List;

import model.users.IUser;

public class Mailbox {
	
    private IUser user;
    private List<INotification> notificationList;

    public Mailbox() {
    }

    public void addNotification(String text) {
    	throw new UnsupportedOperationException();
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
