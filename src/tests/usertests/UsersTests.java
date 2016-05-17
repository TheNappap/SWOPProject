package tests.usertests;

import org.junit.Assert;
import org.junit.Test;

import tests.BugTrapTest;

public class UsersTests extends BugTrapTest {

	@Test
	public void adminTest() {
		Assert.assertTrue(admin.isAdmin());
		Assert.assertEquals(admin.getFirstName(), "Bill");
		Assert.assertEquals(admin.getMiddleName(), "");
		Assert.assertEquals(admin.getLastName(), "Gates");
		Assert.assertEquals(admin.getUserName(), "ADMIN");
	}
	
	@Test
	public void issuerTest() {
		Assert.assertTrue(issuer.isIssuer());
		Assert.assertEquals(issuer.getFirstName(), "Geen");
		Assert.assertEquals(issuer.getMiddleName(), "");
		Assert.assertEquals(issuer.getLastName(), "Idee");
		Assert.assertEquals(issuer.getUserName(), "ISSUER");
	}
	
	@Test
	public void devTest() {
		Assert.assertTrue(lead.isDeveloper());
		Assert.assertEquals(lead.getFirstName(), "Barack");
		Assert.assertEquals(lead.getMiddleName(), "");
		Assert.assertEquals(lead.getLastName(), "Obama");
		Assert.assertEquals(lead.getUserName(), "LEAD");

		Assert.assertTrue(prog.isDeveloper());
		Assert.assertEquals(prog.getFirstName(), "Edsger");
		Assert.assertEquals(prog.getMiddleName(), "W.");
		Assert.assertEquals(prog.getLastName(), "Dijkstra");
		Assert.assertEquals(prog.getUserName(), "PROGRAMMER");

		Assert.assertTrue(tester.isDeveloper());
		Assert.assertEquals(tester.getFirstName(), "Edsger");
		Assert.assertEquals(tester.getMiddleName(), "W.");
		Assert.assertEquals(tester.getLastName(), "Dijkstra");
		Assert.assertEquals(tester.getUserName(), "TESTER");
	}

}
