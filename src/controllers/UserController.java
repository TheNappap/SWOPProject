package controllers;

import java.util.ArrayList;

import model.BugTrap;
import model.users.User;
import model.users.UserCategory;
import model.users.UserDAO;

public class UserController extends Controller{
	
	public UserController(BugTrap bugTrap) {
		super(bugTrap);
	}
	
	private UserDAO userManager;
	
	public ArrayList<User> getUserList(UserCategory userCategory) {
		return getBugTrap().getUserDAO().getUserList(userCategory);
	}
	
	public String loginAs(User loggingUser) {
		return getBugTrap().getUserDAO().loginAs(loggingUser);
	}
	
	public boolean isLoggedIn(User user) {
		return getBugTrap().getUserDAO().isLoggedIn(user);
	}
}
