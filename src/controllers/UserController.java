package controllers;

import java.util.List;

import model.BugTrap;
import model.users.Administrator;
import model.users.Developer;
import model.users.Issuer;
import model.users.User;

public class UserController extends Controller {
	
	public UserController(BugTrap bugTrap) {
		super(bugTrap);
	}
	
	public List<Administrator> getAdmins() {
		return getBugTrap().getUserManager().getAdmins();
	}
	
	public List<Issuer> getIssuers() {
		return getBugTrap().getUserManager().getIssuers();
	}
	
	public List<Developer> getDevelopers() {
		return getBugTrap().getUserManager().getDevelopers();
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
