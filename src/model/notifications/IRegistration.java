package model.notifications;

import model.users.IUser;

/**
 * Interface to represent registrations for notifications.
 */
public interface IRegistration {
    public NotificationType getNotificationType();

    public Observable getObserves();

    public IUser getUser();
}
