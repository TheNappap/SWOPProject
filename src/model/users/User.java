package model.users;

public abstract class User {

	private String firstName;
	private String middleName;
	private String lastName;
	private String userName;
	
	public User(String firstName, String middleName, String lastName, String userName) {
		setFirstName(firstName);
		setMiddleName(middleName);
		setLastName(lastName);
		setUserName(userName);
	}
	
	public abstract UserCategory getCategory();
	
	//Getters and Setters
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
