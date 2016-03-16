package tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.users.Administrator;
import model.users.User;
import model.users.UserManager;
import model.users.exceptions.NoUserWithUserNameException;


public class LoginUseCaseTest {
	
	UserManager userManager;

	@Before
	public void setUp() throws Exception {
		//add user
		userManager = new UserManager();
		userManager.createAdmin("", "", "", "ADMIN");
	}

	@Test
	public void loginSuccesTest() {
		//step 1
		List<Administrator> list = userManager.getAdmins();
		//step 2
		User user = list.get(0);
		//step 3
		String message = userManager.loginAs(user);
		//step 4
		Assert.assertEquals("User: ADMIN successfully logged in.",message);
	}
	
	@Test (expected = NoUserWithUserNameException.class)
	public void loginNoUserTest() {
		//step 3, wrong input
		userManager.loginAs(null);
	}

}
