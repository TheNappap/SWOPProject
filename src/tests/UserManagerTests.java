package tests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.users.Developer;
import model.users.IUser;
import model.users.UserManager;
import model.users.exceptions.NoUserWithUserNameException;
import model.users.exceptions.NotUniqueUserNameException;

public class UserManagerTests {
	
	private UserManager userManager;
	private IUser admin;
	private IUser issuer;
	private IUser dev;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		userManager = new UserManager();
		
		userManager.createAdmin("Richard", "Rosie", "Reese", "RRR");
		userManager.createIssuer("Lindsey", "Lida", "Linkovic", "LLL");
		userManager.createDeveloper("Carl", "Casey", "Carver", "CCC");
		
		admin = userManager.getAdmins().get(0);
		issuer = userManager.getIssuers().get(0);
		dev = userManager.getDevelopers().get(0);
	}

	@Test
	public void adminCreateTest() {
		Assert.assertTrue(admin.isAdmin());
		Assert.assertEquals(admin.getFirstName(), "Richard");
		Assert.assertEquals(admin.getMiddleName(), "Rosie");
		Assert.assertEquals(admin.getLastName(), "Reese");
		Assert.assertEquals(admin.getUserName(), "RRR");
	}
	
	@Test
	public void issuerCreateTest() {
		Assert.assertTrue(issuer.isIssuer());
		Assert.assertEquals(issuer.getFirstName(), "Lindsey");
		Assert.assertEquals(issuer.getMiddleName(), "Lida");
		Assert.assertEquals(issuer.getLastName(), "Linkovic");
		Assert.assertEquals(issuer.getUserName(), "LLL");
	}
	
	@Test
	public void devCreateTest() {
		Assert.assertTrue(dev.isDeveloper());
		Assert.assertEquals(dev.getFirstName(), "Carl");
		Assert.assertEquals(dev.getMiddleName(), "Casey");
		Assert.assertEquals(dev.getLastName(), "Carver");
		Assert.assertEquals(dev.getUserName(), "CCC");
	}
	
	@Test (expected = NotUniqueUserNameException.class)
	public void createAdminFailUserNameExistsTest() {
		userManager.createAdmin("", "", "", "RRR");
	}
	
	@Test (expected = NotUniqueUserNameException.class)
	public void createIssuerFailUserNameExistsTest() {
		userManager.createIssuer("", "", "", "RRR");
	}
	
	@Test (expected = NotUniqueUserNameException.class)
	public void createDeveloperFailUserNameExistsTest() {
		userManager.createDeveloper("", "", "", "RRR");
	}
	
	@Test
	public void loginSuccesTest() {
		Assert.assertFalse(userManager.isLoggedIn(admin));
		String message = userManager.loginAs(admin);
		Assert.assertEquals("User: RRR successfully logged in.", message);
		Assert.assertTrue(userManager.isLoggedIn(admin));
		message = userManager.loginAs(admin);
		Assert.assertEquals("User: RRR is already logged in.", message);
		Assert.assertTrue(userManager.isLoggedIn(admin));
		Assert.assertFalse(userManager.isLoggedIn(dev));
	}
	
	@Test (expected = NoUserWithUserNameException.class)
	public void loginFailTest() {
		Developer d = new Developer("", "", "", "");
		userManager.loginAs(d);
	}
	
	@Test
	public void userNameExistsTest() {
		boolean succes = userManager.userNameExists("RRR");
		boolean fail = userManager.userNameExists("NotExistingUser");
		Assert.assertTrue(succes);
		Assert.assertFalse(fail);
	}
	
	@Test
	public void userExistsTest() {
		boolean succes = userManager.userExists(admin);
		boolean fail = userManager.userExists(new Developer("", "", "",""));
		Assert.assertTrue(succes);
		Assert.assertFalse(fail);
	}
	
	@Test
	public void getAdminsTest() {
		int nb = userManager.getAdmins().size();
		Assert.assertEquals(1, nb);
		
		userManager.createAdmin("", "", "", "");
		nb = userManager.getAdmins().size();
		Assert.assertEquals(2, nb);
	}
	
	@Test
	public void getIssuersTest() {
		int nb = userManager.getIssuers().size();
		Assert.assertEquals(2, nb); //issuer and dev
		
		userManager.createIssuer("", "", "", "");
		nb = userManager.getIssuers().size();
		Assert.assertEquals(3, nb);
	}
	
	@Test
	public void getDeveloperTest() {
		int nb = userManager.getDevelopers().size();
		Assert.assertEquals(1, nb);
		
		userManager.createDeveloper("", "", "", "");
		nb = userManager.getDevelopers().size();
		Assert.assertEquals(2, nb);
	}
	
	@Test
	public void getUserSuccesTest() {
		IUser user = userManager.getUser("RRR");
		Assert.assertEquals(admin, user);
		user = userManager.getUser("LLL");
		Assert.assertEquals(issuer, user);
		user = userManager.getUser("CCC");
		Assert.assertEquals(dev, user);
	}
	
	@Test (expected = NoUserWithUserNameException.class)
	public void getUserFailTest() {
		userManager.getUser("NotAUser");
	}

}
