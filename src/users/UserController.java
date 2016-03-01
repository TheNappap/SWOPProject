package users;

import java.util.ArrayList;

public class UserController {
	
	public UserController(UserManager userManager){
		this.userManager = userManager;
	}
	
	private UserManager userManager;
	
	public ArrayList<User> getUserList(UserCategory userCategory) {
		return userManager.getUserList(userCategory);
	}
	
	public String loginAs(User loggingUser) {
		return userManager.loginAs(loggingUser);
	}
	
	public String logOff(User loggingUser) {
		return userManager.logOff(loggingUser);
	}
	
	public boolean isLoggedIn(User user) {
		return userManager.isLoggedIn(user);
	}
}
