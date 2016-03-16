package model.users;

public class Administrator extends UserImpl{

	public Administrator(String firstName, String middleName, String lastName, String userName) {
		super(firstName, middleName, lastName, userName);
	}

	@Override
	protected UserCategory getCategory() {
		return UserCategory.ADMIN;
	}
}
