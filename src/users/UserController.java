package users;

import java.util.ArrayList;

public class UserController {
	
	public static ArrayList<User> getUserList(UserCategory userCategory) {
		return UserManager.getUserList(userCategory);
	}
	
	public static String loginAs(User loggingUser) {
		return UserManager.loginAs(loggingUser);
	}
}
