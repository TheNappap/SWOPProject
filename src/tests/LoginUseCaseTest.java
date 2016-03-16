package tests;

import controllers.UserController;
import model.BugTrap;
import model.users.User;
import model.users.UserCategory;
import model.users.UserManager;
import model.users.exceptions.NoUserWithUserNameException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class LoginUseCaseTest {
	
	UserController controller;

	@Before
	public void setUp() throws Exception {
		controller = new UserController(new BugTrap());
		//add user
		UserManager userMan = (UserManager) controller.getBugTrap().getUserManager();
		userMan.createUser(UserCategory.ADMIN, "", "", "", "ADMIN");
	}

	@Test
	public void loginSuccesTest() {
		//step 1
		List<User> list = controller.getUserList(UserCategory.ADMIN);
		//step 2
		User user = list.get(0);
		//step 3
		String message = controller.loginAs(user);
		//step 4
		Assert.assertEquals("User: ADMIN successfully logged in.",message);
	}
	
	@Test (expected = NoUserWithUserNameException.class)
	public void loginNoUserTest() {
		//step 3, wrong input
		controller.loginAs(null);
	}

}
