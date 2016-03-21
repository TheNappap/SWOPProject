package controllers;

import java.util.List;

import model.BugTrap;
import model.users.*;

/**
 * Controller for all User related things.
 * Controllers are the interface that is available to developers
 * creating e.g. a BugTrap UI.
 */
public class UserController extends Controller {
	
	public UserController(BugTrap bugTrap) {
		super(bugTrap);
	}
	
	public List<IUser> getAdmins() {
		return getBugTrap().getUserManager().getAdmins();
	}
	
	public List<IUser> getIssuers() {
		return getBugTrap().getUserManager().getIssuers();
	}
	
	public List<IUser> getDevelopers() {
		return getBugTrap().getUserManager().getDevelopers();
	}
	
	public String loginAs(IUser loggingUser) {
		return getBugTrap().getUserManager().loginAs(loggingUser);
	}
	
	public boolean isLoggedIn(IUser user) {
		return getBugTrap().getUserManager().isLoggedIn(user);
	}
	
	public boolean userNameExists(String userName) {
		return getBugTrap().getUserManager().userNameExists(userName);
	}
	
	public boolean userExists(IUser user) {
		return getBugTrap().getUserManager().userExists(user);
	}
	
	public IUser getLoggedInUser() {
		return getBugTrap().getUserManager().getLoggedInUser();
	}
	
	public void logOff() {
		getBugTrap().getUserManager().logOff();
	}
}
