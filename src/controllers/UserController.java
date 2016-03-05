package controllers;

import java.util.ArrayList;

import model.BugTrap;
import model.users.User;
import model.users.UserCategory;

public class UserController extends Controller{
	
	public UserController(BugTrap bugTrap) {
		super(bugTrap);
	}
	
	public ArrayList<User> getUserList(UserCategory userCategory) {
		return getBugTrap().getUserDAO().getUserList(userCategory);
	}
	
	public String loginAs(User loggingUser) {
		return getBugTrap().getUserDAO().loginAs(loggingUser);
	}
	
	public boolean isLoggedIn(User user) {
		return getBugTrap().getUserDAO().isLoggedIn(user);
	}
	
	public boolean userNameExists(String userName) {
		return getBugTrap().getUserDAO().userNameExists(userName);
	}
	
	public boolean userExists(User user) {
		return getBugTrap().getUserDAO().userExists(user);
	}
	
	public User getLoggedInUser() {
		return getBugTrap().getUserDAO().getLoggedInUser();
	}
	
	public void logOff() {
		getBugTrap().getUserDAO().logOff();
	}
}
