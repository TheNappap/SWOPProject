package tests;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import controllers.UserController;
import model.BugTrap;
import model.users.User;
import model.users.UserCategory;
import model.users.UserManager;

public class LoginUseCaseTest {
	
	UserController controller;

	@Before
	public void setUp() throws Exception {
		controller = new UserController(new BugTrap());
		//add user
		UserManager userMan = (UserManager) controller.getBugTrap().getUserDAO();
		userMan.createUser(UserCategory.ADMIN, "", "", "", "ADMIN");
	}

	@Test
	public void loginTest() {
		//step 1 & 2
		ArrayList<User> list = controller.getUserList(UserCategory.ADMIN);
		//step 3
		String message = controller.loginAs(list.get(0));
		//step 4
		Assert.assertEquals("User: ADMIN successfully logged in.O",message);
	}

}
