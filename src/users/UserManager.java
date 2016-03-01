package users;

import java.util.ArrayList;

public class UserManager {
	
	
	public UserManager(){
		userList = new ArrayList<User>();
		loggedInUsers = new ArrayList<User>();
	}
	
	private ArrayList<User> userList;
	private ArrayList<User> loggedInUsers;
	
	/**
	 * Checks if the given User is logged in or not.
	 * @param user The user to check.
	 * @return true if the given User is logged in, false if (s)he's not.
	 */
	boolean isLoggedIn(User user) {
		for (User loggedInUser : getLoggedInUsers())
			if (loggedInUser.getUserName().equals(user.getUserName()))
				return true;
		return false;
	}
	
	/**
	 * Create, fill and return an ArrayList containing all Users from userList that are from the given UserCategory.
	 * @param userCategory The UserCategory for which to make the user list.
	 * @return An ArrayList containing the Users from userList that are from the given UserCategory.
	 */
	ArrayList<User> getUserList(UserCategory userCategory) {
		ArrayList<User> userList = new ArrayList<User>();
		
		for (User user : getUserList()) 
			if (user.getCategory() == userCategory)
				userList.add(user);
		
		return userList;
	}

	/**
	 * Log the given User in the system.
	 * @param loggingUser The User to log in
	 * @throws IllegalArgumentException If the given User doesn't exist in the system.
	 * @return A message specifying if the given User is logged in or that (s)he was already logged in.
	 */
	String loginAs(User loggingUser) {
		if(!userExists(loggingUser)) 
			throw new IllegalArgumentException("the given user does not exist in the system");
		
		if (isLoggedIn(loggingUser))
			return "User: " + loggingUser.getUserName() + " is already logged in.";
		
		for (User user : getUserList()) 
			if (user.getUserName().equals(loggingUser.getUserName())) {
				getLoggedInUsers().add(loggingUser);
				return "User: " + loggingUser.getUserName() + " successfully logged in.";
			}
		
		throw new IllegalArgumentException("something went wrong");
	}
	
	/**
	 * Log the given User off 
	 * @param loggingUser The User to log in
	 * @throws IllegalArgumentException If the given User doesn't exist in the system.
	 * @return A message specifying if the given User is logged off or that (s)he was already logged off.
	 */
	String logOff(User loggingUser){
		if(!userExists(loggingUser)) 
			throw new IllegalArgumentException("the given user does not exist in the system");
		
		if(isLoggedIn(loggingUser)){
			for (User user : getLoggedInUsers()) 
				if (user.getUserName().equals(loggingUser.getUserName())) {
					getLoggedInUsers().remove(loggingUser);
					return "User: " + loggingUser.getUserName() + " successfully logged off.";
				}
		}
		else{
			return "User: " + loggingUser.getUserName() + " is already logged off.";
		}
		
		throw new IllegalArgumentException("something went wrong");
	}
	
	//Getters and setters.
	
	ArrayList<User> getUserList() {
		return userList;
	}
	
	ArrayList<User> getLoggedInUsers() {
		return loggedInUsers;
	}
	
	/**
	 * returns if a given user exists
	 * @param user
	 * @return
	 */
	public boolean userExists(User user){
		for (User u : getUserList()) 
			if (u.getUserName().equals(user.getUserName())) {
				return true;
			}
		return false;
	}
	
	//ADDING USERS
	
	/**
	 * Creates a user
	 * @param uc user category
	 * @param fn first name
	 * @param mn middle name
	 * @param ln last name
	 * @param un user name
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
	 * removes a user
	 * @param user
	 */
	public void removeUser(User user){
		if(isLoggedIn(user)){
			logOff(user);
		}
		getUserList().remove(user);
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
	
}
