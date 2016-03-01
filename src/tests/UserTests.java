package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import users.User;
import users.UserCategory;
import users.UserController;
import users.UserManager;

public class UserTests {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		UserManager.addUser(UserCategory.ADMIN, "Richard", "Rosie", "Reese", "RRR");
		UserManager.addUser(UserCategory.ISSUER, "Lindsey", "Lida", "Linkovic", "LLL");
		UserManager.addUser(UserCategory.DEVELOPER, "Carl", "Casey", "Carver", "CCC");
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void adminTest() {
		User user = UserController.getUserList(UserCategory.ADMIN).get(0);
		Assert.assertEquals(user.getCategory(), UserCategory.ADMIN);
		Assert.assertEquals(user.getFirstName(), "Richard");
		Assert.assertEquals(user.getMiddleName(), "Rosie");
		Assert.assertEquals(user.getLastName(), "Reese");
		Assert.assertEquals(user.getUserName(), "RRR");
	}
	
	@Test
	public void issuerTest() {
		User user = UserController.getUserList(UserCategory.ISSUER).get(0);
		Assert.assertEquals(user.getCategory(), UserCategory.ISSUER);
		Assert.assertEquals(user.getFirstName(), "Lindsey");
		Assert.assertEquals(user.getMiddleName(), "Lida");
		Assert.assertEquals(user.getLastName(), "Linkovic");
		Assert.assertEquals(user.getUserName(), "LLL");
	}
	
	@Test
	public void devTest() {
		User user = UserController.getUserList(UserCategory.DEVELOPER).get(0);
		Assert.assertEquals(user.getCategory(), UserCategory.DEVELOPER);
		Assert.assertEquals(user.getFirstName(), "Carl");
		Assert.assertEquals(user.getMiddleName(), "Casey");
		Assert.assertEquals(user.getLastName(), "Carver");
		Assert.assertEquals(user.getUserName(), "CCC");
	}
	
	@Test
	public void loginSuccesTest() {
		User user = UserController.getUserList(UserCategory.ADMIN).get(0);
		Assert.assertFalse(UserController.isLoggedIn(user));
		String message = UserController.loginAs(user);
		Assert.assertEquals("User: RRR successfully logged in.", message);
		Assert.assertTrue(UserController.isLoggedIn(user));
		message = UserController.loginAs(user);
		Assert.assertEquals("User: RRR is already logged in.", message);
		Assert.assertTrue(UserController.isLoggedIn(user));
	}

}
