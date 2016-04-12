package tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import model.BugTrap;
import model.users.IUser;
import model.users.exceptions.NoUserWithUserNameException;


public class LoginUseCaseTest {
	
	private BugTrap bugTrap;

	@Before
	public void setUp() throws Exception {
		//Create system.
		bugTrap = new BugTrap();
		
		//Add users
		bugTrap.getUserManager().createAdmin("", "", "", "ADMIN");
		bugTrap.getUserManager().createIssuer("", "", "", "ISSUER");
		bugTrap.getUserManager().createDeveloper("", "", "", "DEV");
	}

	@Test
	public void logInAdminTest() {
		//1. The user indicates if he wants to log in as an administrator, issuer or
		//developer.
		List<IUser> list = bugTrap.getUserManager().getAdmins();
		//2. The system shows an overview of the users of the selected category.
		IUser user = list.get(0);
		//3. The user selects one of the shown users.
		String message = bugTrap.getUserManager().loginAs(user);
		//4. The system greets the user
		Assert.assertEquals("User: ADMIN successfully logged in.",message);
	}
	
	@Test
	public void logInIssuer() {
		//1. The user indicates if he wants to log in as an administrator, issuer or
		//developer.
		List<IUser> list = bugTrap.getUserManager().getIssuers();
		//2. The system shows an overview of the users of the selected category.
		IUser user = list.get(0);
		//3. The user selects one of the shown users.
		String message = bugTrap.getUserManager().loginAs(user);
		//4. The system greets the user
		Assert.assertEquals("User: ISSUER successfully logged in.",message);
	}
	
	@Test
	public void logInDeveloper() {
		//1. The user indicates if he wants to log in as an administrator, issuer or
		//developer.
		List<IUser> list = bugTrap.getUserManager().getDevelopers();
		//2. The system shows an overview of the users of the selected category.
		IUser user = list.get(0);
		//3. The user selects one of the shown users.
		String message = bugTrap.getUserManager().loginAs(user);
		//4. The system greets the user
		Assert.assertEquals("User: DEV successfully logged in.",message);
	}
	
	@Test (expected = NoUserWithUserNameException.class)
	public void loginNoUserTest() {
		//1. The user indicates if he wants to log in as an administrator, issuer or
		//developer.
		//2. The system shows an overview of the users of the selected category.
		//3. The user selects one of the shown users.
		
		//Evil
		IUser user = null;
		
		bugTrap.getUserManager().loginAs(user);
	}

}
