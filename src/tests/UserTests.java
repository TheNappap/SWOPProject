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
		userManager = (UserManager) bugTrap.getUserManager();
		userController = new UserController(bugTrap);
		
		userManager.createUser(UserCategory.ADMIN, "Richard", "Rosie", "Reese", "RRR");
		userManager.createUser(UserCategory.ISSUER, "Lindsey", "Lida", "Linkovic", "LLL");
		userManager.createUser(UserCategory.DEVELOPER, "Carl", "Casey", "Carver", "CCC");
		
		admin = (Administrator) userController.getUserList(UserCategory.ADMIN).get(0);
		issuer = (Issuer) userController.getUserList(UserCategory.ISSUER).get(0);
		dev = (Developer) userController.getUserList(UserCategory.DEVELOPER).get(0);
	}

	@Test
	public void adminCreateTest() {
		Assert.assertEquals(admin.getCategory(), UserCategory.ADMIN);
		Assert.assertEquals(admin.getFirstName(), "Richard");
		Assert.assertEquals(admin.getMiddleName(), "Rosie");
		Assert.assertEquals(admin.getLastName(), "Reese");
		Assert.assertEquals(admin.getUserName(), "RRR");
	}
	
	@Test
	public void issuerCreateTest() {
		Assert.assertEquals(issuer.getCategory(), UserCategory.ISSUER);
		Assert.assertEquals(issuer.getFirstName(), "Lindsey");
		Assert.assertEquals(issuer.getMiddleName(), "Lida");
		Assert.assertEquals(issuer.getLastName(), "Linkovic");
		Assert.assertEquals(issuer.getUserName(), "LLL");
	}
	
	@Test
	public void devCreateTest() {
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
		Developer d = new Developer("", "", "", "");
		userController.loginAs(d);
	}
	
	@Test
	public void userNameExistsTest() {
		boolean succes = userController.userNameExists("RRR");
		boolean fail = userController.userNameExists("NotExistingUser");
		Assert.assertTrue(succes);
		Assert.assertFalse(fail);
	}
	
	@Test
	public void userExistsTest() {
		boolean succes = userController.userExists(admin);
		boolean fail = userController.userExists(new Developer("", "", "",""));
		Assert.assertTrue(succes);
		Assert.assertFalse(fail);
	}
	
	@Test
	public void getUserListTest() {
		int nbAdmins = userController.getUserList(UserCategory.ADMIN).size();
		Assert.assertEquals(1, nbAdmins);
		
		userManager.createUser(UserCategory.ADMIN, "", "", "", "");
		nbAdmins = userController.getUserList(UserCategory.ADMIN).size();
		Assert.assertEquals(2, nbAdmins);
	}

}
