package model.users;

import model.users.exceptions.NoUserWithUserNameException;
import model.users.exceptions.NotUniqueUserNameException;

import java.util.ArrayList;
import java.util.List;

/**
 * The user manager is responsible for managing the users in the system.
 *
 */
public class UserManager{
	
	
	public UserManager(){
		userList = new ArrayList<User>();
		loggedInUser = null;
	}
	
	private List<User> userList;
	private User loggedInUser;
	
	/**
	 * Creates an administrator
	 * @param fn first name
	 * @param mn middle name
	 * @param ln last name
	 * @param un user name
	 * @throws NotUniqueUserNameException if the given user name already exists
	 * @return Administrator new admin
	 */
	public Administrator createAdmin(String fn, String mn, String ln, String un) {
		if(userNameExists(un)){
			throw new NotUniqueUserNameException();
		}
		
		Administrator user = new Administrator(fn, mn, ln, un);
		userList.add(user);
		return user;
	}
	
	/**
	 * Creates an issuer
	 * @param fn first name
	 * @param mn middle name
	 * @param ln last name
	 * @param un user name
	 * @throws NotUniqueUserNameException if the given user name already exists
	 * @return Issuer new issuer
	 */
	public Issuer createIssuer(String fn, String mn, String ln, String un) {
		if(userNameExists(un)){
			throw new NotUniqueUserNameException();
		}
		
		Issuer user = new Issuer(fn, mn, ln, un);
		userList.add(user);
		return user;
	}
	
	/**
	 * Creates a developer
	 * @param fn first name
	 * @param mn middle name
	 * @param ln last name
	 * @param un user name
	 * @throws NotUniqueUserNameException if the given user name already exists
	 */
	public Developer createDeveloper(String fn, String mn, String ln, String un) {
		if(userNameExists(un)){
			throw new NotUniqueUserNameException();
		}
		
		Developer user = new Developer(fn, mn, ln, un);
		userList.add(user);
		return user;
	}
	
	/**
	 * Checks if the given User is logged in or not.
	 * @param user The user to check.
	 * @return true if the given User is logged in, false if (s)he's not.
	 */
	public boolean isLoggedIn(IUser user) {
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
	public String loginAs(IUser loggingUser) {
		if(!userExists(loggingUser)) 
			throw new NoUserWithUserNameException();

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
	public List<IUser> getUserList() {
		List<IUser> users = new ArrayList<IUser>();
		for (User s : userList) {
			users.add(s);
		}
		return users;
	}
	
	/**
	 * gets the currently logged in user, returns NULL if no user is logged in
	 * @return logged in user
	 */
	public IUser getLoggedInUser() {
		return loggedInUser;
	}
	
	/**
	 * sets the logged in user to the given user
	 * @param user to set as logged in
	 */
	private void setLoggedInUser(IUser user){
		User userImpl = null;
		for (User u : userList) {
			if(u.getUserName().equals(user.getUserName())){
				userImpl = u;
			}
		}
		loggedInUser = userImpl;
	}
	
	
	/**
	 * Returns a list of all admins
	 * @return admin list
	 */
	public List<IUser> getAdmins() {
		List<IUser> adminList = new ArrayList<IUser>();
		
		for (IUser user : getUserList())
			if (user.isAdmin())
				adminList.add((Administrator) user);
		
		return adminList;
	}
	
	/**
	 * Returns a list of all issuers
	 * @return issuer list
	 */
	public List<IUser> getIssuers() {
		List<IUser> issuerList = new ArrayList<IUser>();
		
		for (IUser user : getUserList())
			if (user.isIssuer())
				issuerList.add(user);
		
		return issuerList;
	}
	
	/**
	 * Returns a list of all devs
	 * @return dev list
	 */
	public List<IUser> getDevelopers() {
		List<IUser> devList = new ArrayList<IUser>();
		
		for (IUser user : getUserList())
			if (user.isDeveloper())
				devList.add(user);
		
		return devList;
	}
	
	/**
	 * Checks if a given username is unique
	 * @param userName
	 * @return if the given username exists
	 */
	public boolean userNameExists(String userName){
		for(IUser user : getUserList()){
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
	public boolean userExists(IUser user){
		for (IUser u : getUserList())
			if (u == user) {
				return true;
			}
		return false;
	}
	
	/**
	 * Gets the user with given username
	 * @param userName
	 * @return The user with the given username. Null if no such user exists.
	 * @throws NoUserWithUserNameException
	 */
	public IUser getUser(String userName) {
		for(IUser user : getUserList()){
			if(user.getUserName().equals(userName)){
				return user;
			}
		}
		throw new NoUserWithUserNameException();
	}
}
