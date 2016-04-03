package model.notifications;

public interface INotification {
    String getText();
    boolean isRead();
    void markAsRead();
}
