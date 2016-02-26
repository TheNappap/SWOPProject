package users;

public abstract class User {

	private String firstName;
	private String middleName;
	private String lastName;
	private String userName;
	
	User(String firstName, String middleName, String lastName, String userName) {
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
	
	void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	void setUserName(String userName) {
		this.userName = userName;
	}
	
}
