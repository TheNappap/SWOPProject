package users;

public class Administrator extends User{

	Administrator(String firstName, String middleName, String lastName, String userName) {
		super(firstName, middleName, lastName, userName);
	}

	@Override
	UserCategory getCategory() {
		return UserCategory.ADMIN;
	}
}
