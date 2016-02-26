package users;

public class Issuer extends User {

	Issuer(String firstName, String middleName, String lastName, String userName) {
		super(firstName, middleName, lastName, userName);
	}

	@Override
	UserCategory getCategory() {
		return UserCategory.ISSUER;
	}
}