package model.users;

public class Developer extends Issuer {

	public Developer(String firstName, String middleName, String lastName, String userName) {
		super(firstName, middleName, lastName, userName);
	}

	@Override
	protected UserCategory getCategory() {
		return UserCategory.DEVELOPER;
	}
}
