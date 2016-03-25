package model.users;

/**
 * Interface for User object.
 */
public interface IUser {

    boolean isAdmin();
    boolean isIssuer();
    boolean isDeveloper();

    String getFirstName();
    String getMiddleName();
    String getLastName();
    String getUserName();
}
