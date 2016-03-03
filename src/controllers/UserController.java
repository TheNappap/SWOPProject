package controllers;

import java.util.ArrayList;

import model.BugTrap;
import model.users.User;
import model.users.UserCategory;
import model.users.UserDAO;

public class UserController extends Controller{
	
	public UserController(BugTrap bugTrap) {
		super(bugTrap);
		userManager = getBugTrap().getUserDAO();
	}
	
	private UserDAO userManager;
	
	public ArrayList<User> getUserList(UserCategory userCategory) {
		return userManager.getUserList(userCategory);
	}
	
	public String loginAs(User loggingUser) {
		return userManager.loginAs(loggingUser);
	}
	
	public boolean isLoggedIn(User user) {
		return userManager.isLoggedIn(user);
	}
}
