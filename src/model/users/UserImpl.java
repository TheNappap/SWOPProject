package model.users;

public abstract class UserImpl implements User{

	private String firstName;
	private String middleName;
	private String lastName;
	private String userName;
	
	public UserImpl(String firstName, String middleName, String lastName, String userName) {
		setFirstName(firstName);
		setMiddleName(middleName);
		setLastName(lastName);
		setUserName(userName);
	}
	
	public abstract UserCategory getCategory();
	
	public boolean isAdmin(){
		return getCategory() == UserCategory.ADMIN;
	}
	
	public boolean isIssuer(){
		return getCategory() == UserCategory.ISSUER;
	}
	
	public boolean isDeveloper(){
		return getCategory() == UserCategory.DEVELOPER;
	}
		
	
	//Getters and Setters
	
	@Override
	public String getFirstName() {
		return firstName;
	}
	
	void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Override
	public String getMiddleName() {
		return middleName;
	}
	
	void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	
	@Override
	public String getLastName() {
		return lastName;
	}
	
	void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String getUserName() {
		return userName;
	}
	
	void setUserName(String userName) {
		this.userName = userName;
	}
	
}
