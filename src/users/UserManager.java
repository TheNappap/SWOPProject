package users;

import java.util.ArrayList;

public class UserManager {

	private static ArrayList<User> userList;
	private static ArrayList<User> loggedInUsers;
	
	/**
	 * Checks if the given User is logged in or not.
	 * @param user The user to check.
	 * @return true if the given User is logged in, false if (s)he's not.
	 */
	static boolean isLoggedIn(User user) {
		for (User loggedInUser : getLoggedInUsers())
			if (loggedInUser.getUserName() == user.getUserName())
				return true;
		return false;
	}
	
	/**
	 * Create, fill and return an ArrayList containing all Users from userList that are from the given UserCategory.
	 * @param userCategory The UserCategory for which to make the user list.
	 * @return An ArrayList containing the Users from userList that are from the given UserCategory.
	 */
	static ArrayList<User> getUserList(UserCategory userCategory) {
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
	static String loginAs(User loggingUser) {
		if (isLoggedIn(loggingUser))
			return "User: " + loggingUser.getUserName() + " is already logged in.";
		
		for (User user : getUserList()) 
			if (user.getUserName() == loggingUser.getUserName()) {
				getLoggedInUsers().add(loggingUser);
				return "User: " + loggingUser.getUserName() + " successfully logged in.";
			}
		
		throw new IllegalArgumentException();
	}
	
	//Getters and setters.
	
	static ArrayList<User> getUserList() {
		return userList;
	}
	
	static ArrayList<User> getLoggedInUsers() {
		return loggedInUsers;
	}
}
