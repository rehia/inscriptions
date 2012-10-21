package controllers;

import static org.junit.Assert.*;

import java.util.HashMap;
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

public class ApplicationTests {
			
	@Test
	public void testIndex() { 
        Result result = routeAndCall(fakeRequest(GET, "/"));
		assertThat(status(result)).isEqualTo(OK);
	}
	
	@Test
	public void shouldSubmitInscriptionCorrectly() {
		Inscription inscription = givenIHaveAValidInscription();
				
		Result result = whenTheInscriptionIsSubmited(inscription);
				
		thenTheInscriptionIsValid(result);
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
		controllers.Application.inscriptionForm.reject("email", "email is required");
		
		return inscription;
	}

	private void thenTheInscriptionIsNotValid(Result result) {
		assertThat(status(result)).isEqualTo(BAD_REQUEST);
		assertThat(contentAsString(result)).contains("inscription non valide");		
	}

	private void thenTheInscriptionIsValid(Result result) {
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentAsString(result)).contains(("inscription valid"));
	}

	private Result whenTheInscriptionIsSubmited(Inscription inscription) {
		JsonNode node = Json.toJson(inscription);
		FakeRequest fakeRequest = fakeRequest(POST, "/").withJsonBody(node);
		return routeAndCall(fakeRequest);
	}

	private Inscription givenIHaveAValidInscription() {
		Inscription inscription = new Inscription();
		inscription.nom = "Durand";
		inscription.prenom = "Fernand";
		inscription.email = "f.d@df.fr";
		inscription.acceptePourMailsOrganisation = true;
		inscription.acceptePourMailsSponsors = false;
		return inscription;
	}

}
