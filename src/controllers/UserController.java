package controllers;

import java.util.List;

import model.BugTrap;
import model.users.User;
import model.users.UserCategory;

public class UserController extends Controller {
	
	public UserController(BugTrap bugTrap) {
		super(bugTrap);
	}
	
	public List<User> getUserList(UserCategory userCategory) {
		return getBugTrap().getUserManager().getUserList(userCategory);
	}
	
	public String loginAs(User loggingUser) {
		return getBugTrap().getUserManager().loginAs(loggingUser);
	}
	
	public boolean isLoggedIn(User user) {
		return getBugTrap().getUserManager().isLoggedIn(user);
	}
	
	public boolean userNameExists(String userName) {
		return getBugTrap().getUserManager().userNameExists(userName);
	}
	
	public boolean userExists(User user) {
		return getBugTrap().getUserManager().userExists(user);
	}
	
	public User getLoggedInUser() {
		return getBugTrap().getUserManager().getLoggedInUser();
	}
	
	public void logOff() {
		getBugTrap().getUserManager().logOff();
	}
}
