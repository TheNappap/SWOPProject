package controllers;

import java.util.ArrayList;

import model.BugTrap;
import model.users.User;
import model.users.UserCategory;
import model.users.UserManager;

public class UserController extends Controller {
	
	public UserController(BugTrap bugTrap) {
		super(bugTrap);
	}

	public static ArrayList<User> getUserList(UserCategory userCategory) {
		return UserManager.getUserList(userCategory);
	}
	
	public static String loginAs(User loggingUser) {
		return UserManager.loginAs(loggingUser);
	}
}
