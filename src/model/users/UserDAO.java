package model.users;

import java.util.ArrayList;

public interface UserDAO {

	public ArrayList<User> getUserList(UserCategory userCategory);
	public boolean isLoggedIn(User user);
	public String loginAs(User user);
	
}
