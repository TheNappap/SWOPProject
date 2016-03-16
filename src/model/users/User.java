package model.users;

public interface User {
	
	public abstract String getFirstName();
	
	public abstract String getMiddleName();
	
	public abstract String getLastName();
	
	public abstract String getUserName();
	
	public abstract boolean isAdmin();
	
	public abstract boolean isIssuer();
	
	public abstract boolean isDeveloper();

}
