package controllers;

import java.util.List;

import model.BugTrap;
import model.users.IUser;

/**
 * Controller for all User related things.
 * Controllers are the interface that is available to developers
 * creating e.g. a BugTrap UI.
 */
public class UserController extends Controller {
	
	public UserController(BugTrap bugTrap) {
		super(bugTrap);
	}
	
	/**
	 * Returns all administrators
	 * @return a list of administrators
	 */
	public List<IUser> getAdmins() {
		return getBugTrap().getUserManager().getAdmins();
	}
	
	/**
	 * Returns all issuers
	 * @return a list of issuers
	 */
	public List<IUser> getIssuers() {
		return getBugTrap().getUserManager().getIssuers();
	}
	
	/**
	 * Returns all developers
	 * @return a list of developers
	 */
	public List<IUser> getDevelopers() {
		return getBugTrap().getUserManager().getDevelopers();
	}
	
	/**
	 * Logs in the given user
	 * @param loggingUser the given user that wants to log in
	 * @return a greeting
	 */
	public String loginAs(IUser loggingUser) {
		return getBugTrap().getUserManager().loginAs(loggingUser);
	}
	
	/**
	 * Returns if a given user is logged in
	 * @param user given user
	 * @return if given user is logged in
	 */
	public boolean isLoggedIn(IUser user) {
		return getBugTrap().getUserManager().isLoggedIn(user);
	}
	
	/**
	 * Returns if the given user name already exists in the system
	 * @param userName the given user name
	 * @return if the given user name exists
	 */
	public boolean userNameExists(String userName) {
		return getBugTrap().getUserManager().userNameExists(userName);
	}
	
	/**
	 * Returns if a given user exists in the system
	 * @param user given user
	 * @return if the given user exists
	 */
	public boolean userExists(IUser user) {
		return getBugTrap().getUserManager().userExists(user);
	}
	
	/**
	 * Returns the currently logged in user
	 * @return the logged in user
	 */
	public IUser getLoggedInUser() {
		return getBugTrap().getUserManager().getLoggedInUser();
	}
	
	/**
	 * Logs off the currently logged in user.
	 * If no user is logged in, nothing happens
	 */
	public void logOff() {
		getBugTrap().getUserManager().logOff();
	}
}
