package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controllers.UserController;
import model.BugTrap;
import model.users.Administrator;
import model.users.Developer;
import model.users.Issuer;
import model.users.UserCategory;
import model.users.UserManager;
import model.users.exceptions.NotUniqueUserNameException;

public class UserTests {
	
	private BugTrap bugTrap;
	private UserManager userManager;
	private UserController userController;
	private Administrator admin;
	private Issuer issuer;
	private Developer dev;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		bugTrap = new BugTrap();
		userManager = (UserManager) bugTrap.getUserDAO();
		userController = new UserController(bugTrap);
		
		userManager.createUser(UserCategory.ADMIN, "Richard", "Rosie", "Reese", "RRR");
		userManager.createUser(UserCategory.ISSUER, "Lindsey", "Lida", "Linkovic", "LLL");
		userManager.createUser(UserCategory.DEVELOPER, "Carl", "Casey", "Carver", "CCC");
		
		admin = (Administrator) userController.getUserList(UserCategory.ADMIN).get(0);
		issuer = (Issuer) userController.getUserList(UserCategory.ISSUER).get(0);
		dev = (Developer) userController.getUserList(UserCategory.DEVELOPER).get(0);
	}

	@Test
	public void adminTest() {
		Assert.assertEquals(admin.getCategory(), UserCategory.ADMIN);
		Assert.assertEquals(admin.getFirstName(), "Richard");
		Assert.assertEquals(admin.getMiddleName(), "Rosie");
		Assert.assertEquals(admin.getLastName(), "Reese");
		Assert.assertEquals(admin.getUserName(), "RRR");
	}
	
	@Test
	public void issuerTest() {
		Assert.assertEquals(issuer.getCategory(), UserCategory.ISSUER);
		Assert.assertEquals(issuer.getFirstName(), "Lindsey");
		Assert.assertEquals(issuer.getMiddleName(), "Lida");
		Assert.assertEquals(issuer.getLastName(), "Linkovic");
		Assert.assertEquals(issuer.getUserName(), "LLL");
	}
	
	@Test
	public void devTest() {
		Assert.assertEquals(dev.getCategory(), UserCategory.DEVELOPER);
		Assert.assertEquals(dev.getFirstName(), "Carl");
		Assert.assertEquals(dev.getMiddleName(), "Casey");
		Assert.assertEquals(dev.getLastName(), "Carver");
		Assert.assertEquals(dev.getUserName(), "CCC");
	}
	
	@Test (expected = NotUniqueUserNameException.class)
	public void createUserFailUserNameExistsTest() {
		userManager.createUser(UserCategory.DEVELOPER, "", "", "", "RRR");
	}
	
	@Test
	public void removeUserTest() {
		userController.loginAs(admin);
		userManager.removeUser(admin);
		userManager.removeUser(issuer);
	}
	
	@Test
	public void loginSuccesTest() {
		Assert.assertFalse(userController.isLoggedIn(admin));
		String message = userController.loginAs(admin);
		Assert.assertEquals("User: RRR successfully logged in.", message);
		Assert.assertTrue(userController.isLoggedIn(admin));
		message = userController.loginAs(admin);
		Assert.assertEquals("User: RRR is already logged in.", message);
		Assert.assertTrue(userController.isLoggedIn(admin));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void loginFailTest() {
		userManager.removeUser(admin);
		userController.loginAs(admin);
	}
	
	@Test
	public void logoffSuccesTest() {
		Assert.assertFalse(userController.isLoggedIn(admin));
		String message = userController.loginAs(admin);
		Assert.assertEquals("User: RRR successfully logged in.", message);
		Assert.assertTrue(userController.isLoggedIn(admin));
		
		message = userManager.logOff(admin);
		Assert.assertEquals("User: RRR successfully logged off.", message);
		Assert.assertFalse(userController.isLoggedIn(admin));

		message = userManager.logOff(admin);
		Assert.assertEquals("User: RRR is already logged off.", message);
		Assert.assertFalse(userController.isLoggedIn(admin));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void logoffFailTest() {
		userController.loginAs(admin);
		userManager.removeUser(admin);
		userManager.logOff(admin);
	}


}
