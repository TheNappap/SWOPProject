package users;

public class Developer extends Issuer {

	Developer(String firstName, String middleName, String lastName, String userName) {
		super(firstName, middleName, lastName, userName);
	}

	@Override
	UserCategory getCategory() {
		return UserCategory.DEVELOPER;
	}
}