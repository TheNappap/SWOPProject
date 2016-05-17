package tests.usertests;

import org.junit.Assert;
import org.junit.Test;

import model.users.Developer;
import model.users.IUser;
import model.users.exceptions.NoUserWithUserNameException;
import model.users.exceptions.NotUniqueUserNameException;
import tests.BugTrapTest;

public class UserManagerTests extends BugTrapTest {

	@Test
	public void adminCreateTest() {
		bugTrap.getUserManager().createAdmin("Richard", "Rosie", "Reese", "RRR");
		IUser admin = bugTrap.getUserManager().getAdmins().get(bugTrap.getUserManager().getAdmins().size() - 1);
		Assert.assertTrue(admin.isAdmin());
		Assert.assertEquals(admin.getFirstName(), "Richard");
		Assert.assertEquals(admin.getMiddleName(), "Rosie");
		Assert.assertEquals(admin.getLastName(), "Reese");
		Assert.assertEquals(admin.getUserName(), "RRR");
	}
	
	@Test
	public void issuerCreateTest() {
		bugTrap.getUserManager().createIssuer("Lindsey", "Lida", "Linkovic", "LLL");
		IUser issuer = bugTrap.getUserManager().getIssuers().get(bugTrap.getUserManager().getIssuers().size() - 1);
		Assert.assertTrue(issuer.isIssuer());
		Assert.assertEquals(issuer.getFirstName(), "Lindsey");
		Assert.assertEquals(issuer.getMiddleName(), "Lida");
		Assert.assertEquals(issuer.getLastName(), "Linkovic");
		Assert.assertEquals(issuer.getUserName(), "LLL");
	}
	
	@Test
	public void devCreateTest() {
		bugTrap.getUserManager().createDeveloper("Carl", "Casey", "Carver", "CCC");
		IUser dev = bugTrap.getUserManager().getDevelopers().get(bugTrap.getUserManager().getDevelopers().size() - 1);
		Assert.assertTrue(dev.isDeveloper());
		Assert.assertEquals(dev.getFirstName(), "Carl");
		Assert.assertEquals(dev.getMiddleName(), "Casey");
		Assert.assertEquals(dev.getLastName(), "Carver");
		Assert.assertEquals(dev.getUserName(), "CCC");
	}
	
	@Test (expected = NotUniqueUserNameException.class)
	public void createAdminFailUserNameExistsTest() {
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
	}
	
	@Test (expected = NotUniqueUserNameException.class)
	public void createIssuerFailUserNameExistsTest() {
		bugTrap.getUserManager().createIssuer("", "", "", "ISSUER");
	}
	
	@Test (expected = NotUniqueUserNameException.class)
	public void createDeveloperFailUserNameExistsTest() {
		bugTrap.getUserManager().createDeveloper("", "", "", "TESTER");
	}
	
	@Test
	public void loginSuccesTest() {
		Assert.assertFalse(bugTrap.getUserManager().isLoggedIn(admin));
		String message = bugTrap.getUserManager().loginAs(admin);
		Assert.assertEquals("User: ADMIN successfully logged in.", message);
		Assert.assertTrue(bugTrap.getUserManager().isLoggedIn(admin));
		message = bugTrap.getUserManager().loginAs(admin);
		Assert.assertEquals("User: ADMIN is already logged in.", message);
		Assert.assertTrue(bugTrap.getUserManager().isLoggedIn(admin));
		Assert.assertFalse(bugTrap.getUserManager().isLoggedIn(lead));
	}
	
	@Test (expected = NoUserWithUserNameException.class)
	public void loginFailTest() {
		Developer d = new Developer("", "", "", "");
		bugTrap.getUserManager().loginAs(d);
	}
	
	@Test
	public void userNameExistsTest() {
		boolean succes = bugTrap.getUserManager().userNameExists("ADMIN");
		boolean fail = bugTrap.getUserManager().userNameExists("NotExistingUser");
		Assert.assertTrue(succes);
		Assert.assertFalse(fail);
	}
	
	@Test
	public void userExistsTest() {
		boolean succes = bugTrap.getUserManager().userExists(admin);
		boolean fail = bugTrap.getUserManager().userExists(new Developer("", "", "",""));
		Assert.assertTrue(succes);
		Assert.assertFalse(fail);
	}
	
	@Test
	public void getAdminsTest() {
		int nb = bugTrap.getUserManager().getAdmins().size();
		Assert.assertEquals(1, nb);

		bugTrap.getUserManager().createAdmin("", "", "", "");
		nb = bugTrap.getUserManager().getAdmins().size();
		Assert.assertEquals(2, nb);
	}
	
	@Test
	public void getIssuersTest() {
		int nb = bugTrap.getUserManager().getIssuers().size();
		Assert.assertEquals(4, nb); //lead, programmer, tester and issuer

		bugTrap.getUserManager().createIssuer("", "", "", "");
		nb = bugTrap.getUserManager().getIssuers().size();
		Assert.assertEquals(5, nb);
	}
	
	@Test
	public void getDeveloperTest() {
		int nb = bugTrap.getUserManager().getDevelopers().size();
		Assert.assertEquals(3, nb);

		bugTrap.getUserManager().createDeveloper("", "", "", "");
		nb = bugTrap.getUserManager().getDevelopers().size();
		Assert.assertEquals(4, nb);
	}
	
	@Test
	public void getUserSuccesTest() {
		IUser user = bugTrap.getUserManager().getUser("ADMIN");
		Assert.assertEquals(admin, user);
		user = bugTrap.getUserManager().getUser("ISSUER");
		Assert.assertEquals(issuer, user);
		user = bugTrap.getUserManager().getUser("PROGRAMMER");
		Assert.assertEquals(prog, user);
	}
	
	@Test (expected = NoUserWithUserNameException.class)
	public void getUserFailTest() {
		bugTrap.getUserManager().getUser("NotAUser");
	}

}
