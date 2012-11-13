package controllers;

import static helpers.Helpers.fakeApplicationOverloaded;
import static junit.framework.Assert.*;
import static play.test.Helpers.GET;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.routeAndCall;
import static play.test.Helpers.running;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import play.mvc.Result;
import play.test.FakeRequest;

import controllers.Application.Login;

public class AuthenticationTests {
	
	@Test
	public void shouldValidateLogin() {
		Login login = givenIHaveALoginFormWithAGoodPassword();
		
		String loginResult = whenIValidateTheLogin(login);
		
		thenThResultIsValidated(loginResult);
	}

	private Login givenIHaveALoginFormWithAGoodPassword() {
		Login login = new Login();
		login.password = "test";
		
		return login;
	}

	private String whenIValidateTheLogin(final Login login) {
		
		String result = null;

		// c'est moche, mais seule façon trouvée pour passer des données et
		// éviter d'encapsuler tous les appels
		final List<Object> arguments = new ArrayList();
		arguments.add(result);
		
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("admin.password", "test");

		running(fakeApplicationOverloaded(parameters), new Runnable() {
			@Override
			public void run() {
				arguments.set(0, login.validate());
			}
		});

		return (String) arguments.get(0);
	}

	private void thenThResultIsValidated(String loginResult) {
		assertNull(loginResult);
	}
	
	@Test
	public void shouldNotValidateLogin() {
		Login login = givenIHaveALoginFormWithAWrongPassword();
		
		String loginResult = whenIValidateTheLogin(login);
		
		thenThResultIsNotValidated(loginResult);
	}

	private Login givenIHaveALoginFormWithAWrongPassword() {
		Login login = new Login();
		login.password = "wrong";
		
		return login;
	}

	private void thenThResultIsNotValidated(String loginResult) {
		assertNotNull(loginResult);
	}

}
