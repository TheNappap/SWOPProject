package tests;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import model.users.IUser;
import model.users.exceptions.NoUserWithUserNameException;


public class LoginUseCaseTest extends BugTrapTest {

	@Test
	public void logInAdminTest() {
		//1. The user indicates if he wants to log in as an administrator, issuer or
		//developer.
		//2. The system shows an overview of the users of the selected category.
		List<IUser> list = userController.getAdmins();
		//3. The user selects one of the shown users.
		IUser user = list.get(0);
		//4. The system greets the user.
		String message = userController.loginAs(user);
		
		Assert.assertEquals("User: ADMIN successfully logged in.", message);
	}
	
	@Test
	public void logInIssuer() {
		//1. The user indicates if he wants to log in as an administrator, issuer or
		//developer.
		//2. The system shows an overview of the users of the selected category.
		userController.getIssuers();
		//3. The user selects one of the shown users.
		IUser user = issuer;
		//4. The system greets the user.
		String message = userController.loginAs(user);
		
		Assert.assertEquals("User: ISSUER successfully logged in.", message);
	}
	
	@Test
	public void logInDeveloper() {
		//1. The user indicates if he wants to log in as an administrator, issuer or
		//developer.
		//2. The system shows an overview of the users of the selected category.
		List<IUser> list = userController.getDevelopers();
		//3. The user selects one of the shown users.
		IUser user = list.get(0);
		//4. The system greets the user.
		String message = userController.loginAs(user);
		
		Assert.assertEquals("User: LEAD successfully logged in.", message);
	}
	
	@Test (expected = NoUserWithUserNameException.class)
	public void loginNoUserTest() {
		//1. The user indicates if he wants to log in as an administrator, issuer or
		//developer.
		//2. The system shows an overview of the users of the selected category.
		//3. The user selects one of the shown users.
		
		//Evil
		IUser user = null;
		
		userController.loginAs(user);
	}

}
