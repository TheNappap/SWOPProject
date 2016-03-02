package controllers;

import java.util.ArrayList;

import model.BugTrap;
import model.users.User;
import model.users.UserCategory;

public class UserController extends Controller {
	
	public UserController(BugTrap bugTrap) {
		super(bugTrap);
	}

	public ArrayList<User> getUserList(UserCategory userCategory) {
		return getBugTrap().getUserDAO().getUserList(userCategory);
	}
	
	public String loginAs(User loggingUser) {
		return getBugTrap().getUserDAO().loginAs(loggingUser);
	}
}
