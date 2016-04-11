package model.notifications;

/**
 * This class represents a notification in BugTrap.
 */
public class Notification implements INotification {

    private String text;
    private boolean isRead;

    public Notification(String text) {
        this.text = text;
        this.isRead = false;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public boolean isRead() {
        return isRead;
    }

    @Override
    public void markAsRead() {
        isRead = true;
    }
}
