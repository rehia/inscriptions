package controllers;

import static helpers.Helpers.fakeApplicationOverloaded;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.POST;
import static play.test.Helpers.GET;
import static play.test.Helpers.*;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsNot.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Inscription;

import org.codehaus.jackson.JsonNode;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;

import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

public class AdministrationTests {

	@Test
	public void shouldDisplayInscriptionsList() {
		givenThereAreSomeInscriptions();
		
		Result result = whenIArriveOnAdminMainPage();
		
		thenTheListOfInscriptionsIsDisplayed(result);
	}
	
	@Test
	public void shouldGenerateABadgeForAnInscription() {
		Inscription inscription = givenIHaveSelectedAnInscription();
		
		Result result = whenIGenerateABadge(inscription.getId());
		
		thenTheBadgeIsGenerated(result);
	}
	

	private void thenTheBadgeIsGenerated(Result result) {
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentAsBytes(result).length).isGreaterThan(0);
	}

	private Result whenIGenerateABadge(int idInscription) {
		Result result = null;

		// c'est moche, mais seule façon trouvée pour passer des données et éviter d'encapsuler tous les appels 
		final List<Object> arguments = new ArrayList();
		arguments.add(result);
		arguments.add("nameSelected_" + idInscription);

		running(fakeApplicationOverloaded(), new Runnable() {
			@Override
			public void run() {
				Map<String, String> map = new HashMap<String, String>();
				map.put((String) arguments.get(1), "on"); 
				FakeRequest fakeRequest = fakeRequest(POST, "/admin/badge")
						.withFormUrlEncodedBody(map);
				arguments.set(0, routeAndCall(fakeRequest));
			}
		});

		return (Result) arguments.get(0);
	}

	private Inscription givenIHaveSelectedAnInscription() {
		Inscription inscription = new Inscription();
		inscription.setNom("Durand");
		inscription.setPrenom("Fernand");
		inscription.setEmail("f.d@df.fr");
		return inscription;
	}

	private void thenTheListOfInscriptionsIsDisplayed(Result result) {
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentAsString(result)).contains(("Durand"));
		assertThat(contentAsString(result)).contains(("Dupont"));
	}

	private Result whenIArriveOnAdminMainPage() {
		Result result = null;

		// c'est moche, mais seule façon trouvée pour passer des données et éviter d'encapsuler tous les appels 
		final List<Object> arguments = new ArrayList();
		arguments.add(result);

		running(fakeApplicationOverloaded(), new Runnable() {
			@Override
			public void run() {
				FakeRequest fakeRequest = fakeRequest(GET, "/admin");
				arguments.set(0, routeAndCall(fakeRequest));
			}
		});

		return (Result) arguments.get(0);		
	}

	private void givenThereAreSomeInscriptions() {
			
	}

}
