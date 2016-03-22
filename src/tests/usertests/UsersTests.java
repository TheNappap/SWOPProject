package tests.usertests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.users.Administrator;
import model.users.Developer;
import model.users.Issuer;

public class UsersTests {
	
	private Administrator admin;
	private Issuer issuer;
	private Developer dev;

	@Before
	public void setUp() throws Exception {
		admin = new Administrator("Richard", "Rosie", "Reese", "RRR");
		issuer = new Issuer("Lindsey", "Lida", "Linkovic", "LLL");
		dev = new Developer("Carl", "Casey", "Carver", "CCC");
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

}
