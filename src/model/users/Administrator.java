package model.users;

/**
 * Represents an administrator in the system.
 * An administrator is a user.
 *
 */
public class Administrator extends User{

	public Administrator(String firstName, String middleName, String lastName, String userName) {
		super(firstName, middleName, lastName, userName);
	}

	@Override
	protected UserCategory getCategory() {
		return UserCategory.ADMIN;
	}
}
