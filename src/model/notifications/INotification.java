package model.notifications;

/**
 * Interface for the Notification object.
 */
public interface INotification {
    String getText();
    boolean isRead();
    void markAsRead();
}
