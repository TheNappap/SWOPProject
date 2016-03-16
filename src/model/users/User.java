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
	
	protected abstract UserCategory getCategory();
	
	public boolean isAdmin(){
		return getCategory() == UserCategory.ADMIN;
	}
	
	public boolean isIssuer(){
		return getCategory() == UserCategory.ISSUER || getCategory() == UserCategory.DEVELOPER;
	}
	
	public boolean isDeveloper(){
		return getCategory() == UserCategory.DEVELOPER;
	}
		
	
	//Getters and Setters
	
	public String getFirstName() {
		return firstName;
	}
	
	private void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getMiddleName() {
		return middleName;
	}
	
	private void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	private void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	private void setUserName(String userName) {
		this.userName = userName;
	}

}
