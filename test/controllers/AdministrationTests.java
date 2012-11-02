package controllers;

import static helpers.Helpers.fakeApplicationOverloaded;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.POST;
import static play.test.Helpers.GET;
import static play.test.Helpers.*;

import java.util.ArrayList;
import java.util.List;

import models.Inscription;

import org.codehaus.jackson.JsonNode;
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
		assertThat(contentType(result)).isEqualTo("application/pdf");
	}

	private Result whenIGenerateABadge(int idInscription) {
		Result result = null;

		// c'est moche, mais seule façon trouvée pour passer des données et éviter d'encapsuler tous les appels 
		final List<Object> arguments = new ArrayList();
		arguments.add(result);
		arguments.add(idInscription);

		running(fakeApplicationOverloaded(), new Runnable() {
			@Override
			public void run() {
				JsonNode node = Json.toJson((int) arguments.get(1));
				FakeRequest fakeRequest = fakeRequest(POST, "/admin/badge")
						.withJsonBody(node);
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
