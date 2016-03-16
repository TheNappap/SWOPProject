package model.users;

import java.util.ArrayList;
import java.util.List;

import model.users.exceptions.NoUserWithUserNameException;
import model.users.exceptions.NotUniqueUserNameException;

public class UserManager{
	
	
	public UserManager(){
		userList = new ArrayList<UserImpl>();
		loggedInUser = null;
	}
	
	private List<UserImpl> userList;
	private UserImpl loggedInUser;
	
	/**
	 * Checks if the given User is logged in or not.
	 * @param user The user to check.
	 * @return true if the given User is logged in, false if (s)he's not.
	 */
	public boolean isLoggedIn(User user) {
		if(getLoggedInUser() == null)
			return false;
		if (getLoggedInUser().equals(user))
			return true;
		return false;
	}

	/**
	 * Log the given User in the system.
	 * @param loggingUser The User to log in
	 * @throws IllegalArgumentException If the given User doesn't exist in the system.
	 * @return A message specifying if the given User is logged in or that (s)he was already logged in.
	 */
	public String loginAs(User loggingUser) {
		if(!userExists(loggingUser)) 
			throw new IllegalArgumentException("the given user does not exist in the system");

		if (isLoggedIn(loggingUser))
			return "User: " + loggingUser.getUserName() + " is already logged in.";

		logOff();
		setLoggedInUser(loggingUser);
		return "User: " + loggingUser.getUserName() + " successfully logged in.";
	}
	
	/**
	 * Log the current user off 
	 */
	public void logOff(){
		loggedInUser = null;
	}
	
	//USERS
	
	/**
	 * returns a copy of the user list
	 * @return user list
	 */
	public List<User> getUserList() {
		List<User> users = new ArrayList<User>();
		for (User s : userList) {
			users.add(s);
		}
		return users;
	}
	
	/**
	 * returns a copy of the user implementation list
	 * @return user list
	 */
	public List<UserImpl> getUserImplList() {
		List<UserImpl> users = new ArrayList<UserImpl>();
		for (UserImpl s : userList) {
			users.add(s);
		}
		return users;
	}
	
	/**
	 * gets the currently logged in user
	 * @return logged in user
	 */
	public UserImpl getLoggedInUser() {
		return loggedInUser;
	}
	
	/**
	 * sets the logged in user to the given user
	 * @param user to set as logged in
	 * @throws NoUserWithUserNameException if the given username does not exist
	 */
	private void setLoggedInUser(User user){
		if(!userNameExists(user.getUserName()))
			throw new NoUserWithUserNameException();
		
		UserImpl userImpl = null;
		for (UserImpl u : userList) {
			if(u.getUserName().equals(user.getUserName())){
				userImpl = u;
			}
		}
		loggedInUser = userImpl;
	}
	
	
	/**
	 * Create, fill and return an ArrayList containing all Users from userList that are from the given UserCategory.
	 * @param userCategory The UserCategory for which to make the user list.
	 * @return An ArrayList containing the Users from userList that are from the given UserCategory.
	 */
	public List<User> getUserList(UserCategory userCategory) {
		List<User> userList = new ArrayList<User>();
		
		for (UserImpl user : getUserImplList()) 
			if (user.getCategory() == userCategory)
				userList.add(user);
		
		return userList;
	}
	
	/**
	 * Creates a user
	 * @param uc user category
	 * @param fn first name
	 * @param mn middle name
	 * @param ln last name
	 * @param un user name
	 * @throws IllegalArgumentException if the given user category is not supported
	 * @throws NotUniqueUserNameException if the given user name already exists
	 */
	public void createUser(UserCategory uc, String fn, String mn, String ln, String un) {
		if(userNameExists(un)){
			throw new NotUniqueUserNameException();
		}
		
		UserImpl user;
		switch (uc) {
		case ADMIN:
			user = new Administrator(fn, mn, ln, un);
			break;
		case ISSUER:
			user = new Issuer(fn, mn, ln, un);
			break;
		case DEVELOPER:
			user = new Developer(fn, mn, ln, un);
			break;

		default:
			throw new IllegalArgumentException("user category not supported");
		}
		
		userList.add(user);
	}
	
	/**
	 * Checks if a given username is unique
	 * @param userName
	 * @return if the given username exists
	 */
	public boolean userNameExists(String userName){
		for(User user: getUserList()){
			if(user.getUserName().equals(userName)){
				return true;
			}
		}
		return false;
	}
	

	/**
	 * returns true if a given user exists in the system
	 * @param user
	 * @return true if the given user exists
	 */
	public boolean userExists(User user){
		for (User u : getUserList()) 
			if (u == user) {
				return true;
			}
		return false;
	}
	
	/**
	 * Gets the user with given username
	 * @param userName
	 * @return The user with the given username. Null if no such user exists.
	 */
	public User getUser(String userName) {
		for(User user: getUserList()){
			if(user.getUserName().equals(userName)){
				return user;
			}
		}
		return null;
	}
}
