package model.users;

import java.util.ArrayList;

import model.users.exceptions.NotUniqueUserNameException;

public class UserManager  implements UserDAO {
	
	
	public UserManager(){
		userList = new ArrayList<User>();
		setLoggedInUser(null);
	}
	
	private ArrayList<User> userList;
	private User loggedInUser;
	
	/**
	 * Checks if the given User is logged in or not.
	 * @param user The user to check.
	 * @return true if the given User is logged in, false if (s)he's not.
	 */
	@Override
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
	private void logOff(){
		setLoggedInUser(null);
	}
	
	//USERS
	
	public ArrayList<User> getUserList() {
		return userList;
	}
	
	@Override
	public User getLoggedInUser() {
		return loggedInUser;
	}
	
	private void setLoggedInUser(User user){
		loggedInUser = user;
	}
	
	
	/**
	 * Create, fill and return an ArrayList containing all Users from userList that are from the given UserCategory.
	 * @param userCategory The UserCategory for which to make the user list.
	 * @return An ArrayList containing the Users from userList that are from the given UserCategory.
	 */

	@Override
	public ArrayList<User> getUserList(UserCategory userCategory) {
		ArrayList<User> userList = new ArrayList<User>();
		
		for (User user : getUserList()) 
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
		
		User user;
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
	
}
