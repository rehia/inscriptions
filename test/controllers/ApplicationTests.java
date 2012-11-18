package controllers;

import static helpers.PlayTestHelpers.*;
import static org.junit.Assert.*;

import helpers.FakeDataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Inscription;

import org.codehaus.jackson.JsonNode;
import org.junit.Before;
import org.junit.Test;

import play.mvc.*;
import play.test.*;
import play.data.validation.Validation;
import play.libs.Json;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ApplicationTests {

	@Test
	public void testIndex() {
		running(fakeApplicationOverloaded(), new Runnable() {
			@Override
			public void run() {
				Result result = routeAndCall(fakeRequest(GET, "/"));
				assertThat(status(result)).isEqualTo(OK);
			}
		});
	}

	@Test
	public void shouldSubmitInscriptionCorrectly() {
		Inscription inscription = givenIHaveAValidInscription();

		Result result = whenTheInscriptionIsSubmited(inscription);

		thenTheInscriptionIsValid(result);
	}

	private Inscription givenIHaveAValidInscription() {
		return FakeDataProvider.getAnExistingInscription();
	}

	private Result whenTheInscriptionIsSubmited(Inscription inscription) {
		Result result = null;

		// c'est moche, mais seule façon trouvée pour passer des données et éviter d'encapsuler tous les appels 
		final List<Object> arguments = new ArrayList();
		arguments.add(result);
		arguments.add(inscription);

		running(fakeApplicationOverloaded(), new Runnable() {
			@Override
			public void run() {
				JsonNode node = Json.toJson((Inscription) arguments.get(1));
				FakeRequest fakeRequest = fakeRequest(POST, "/").withSession("connectedUser", "")
						.withJsonBody(node);
				arguments.set(0, routeAndCall(fakeRequest));
			}
		});

		return (Result) arguments.get(0);
	}

	private void thenTheInscriptionIsValid(Result result) {
		assertThat(status(result)).isEqualTo(OK).describedAs(contentAsString(result));
		assertThat(contentAsString(result)).contains(("inscription valid"));
		verify(Application.repository).save(any(Inscription.class));
	}

	@Test
	public void shouldNotSubmitInscriptionWhenInvalid() {

		Inscription inscription = givenIHaveAValidInscription();
		inscription = givenInscriptionIsInvalid(inscription);

		Result result = whenTheInscriptionIsSubmited(inscription);

		thenTheInscriptionIsNotValid(result);
	}

	private Inscription givenInscriptionIsInvalid(Inscription inscription) {
		inscription.email = null;
		controllers.Application.inscriptionForm.reject("email",
				"email is required");

		return inscription;
	}

	private void thenTheInscriptionIsNotValid(Result result) {
		assertThat(status(result)).isEqualTo(BAD_REQUEST);
		assertThat(contentAsString(result)).contains("inscription non valide");
	}

}
