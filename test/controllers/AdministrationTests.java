package controllers;

import static helpers.PlayTestHelpers.fakeApplicationOverloaded;
import static org.fest.assertions.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.SEE_OTHER;
import static play.test.Helpers.GET;
import static play.test.Helpers.POST;
import static play.test.Helpers.PUT;
import static play.test.Helpers.DELETE;
import static play.test.Helpers.contentAsBytes;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.routeAndCall;
import static play.test.Helpers.running;
import static play.test.Helpers.status;

import helpers.FakeDataProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Inscription;

import org.junit.Test;

import play.mvc.Result;
import play.test.FakeRequest;

public class AdministrationTests {

	@Test
	public void shouldDisplayInscriptionsList() {
		givenThereAreSomeInscriptions();

		Result result = whenIArriveOnAdminMainPage();

		thenTheListOfInscriptionsIsDisplayed(result);
	}

	private void givenThereAreSomeInscriptions() {

	}

	private Result whenIArriveOnAdminMainPage() {
		Result result = null;

		// c'est moche, mais seule façon trouvée pour passer des données et
		// éviter d'encapsuler tous les appels
		final List<Object> arguments = new ArrayList();
		arguments.add(result);

		running(fakeApplicationOverloaded(), new Runnable() {
			@Override
			public void run() {
				FakeRequest fakeRequest = fakeRequest(GET, "/admin").withSession("connectedUser", "Admin");
				arguments.set(0, routeAndCall(fakeRequest));
			}
		});

		return (Result) arguments.get(0);
	}

	private void thenTheListOfInscriptionsIsDisplayed(Result result) {
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentAsString(result)).contains(("Durand"));
		assertThat(contentAsString(result)).contains(("Dupont"));
	}

	@Test
	public void shouldGenerateABadgeForAnInscription() {
		Inscription inscription = givenIHaveSelectedAnInscription();

		Result result = whenIGenerateABadge(inscription.getId());

		thenTheBadgeIsGenerated(result);
	}

	private Inscription givenIHaveSelectedAnInscription() {
		Inscription inscription = FakeDataProvider.getAnExistingInscription();
		inscription.setId(12);
		return inscription;
	}

	private Result whenIGenerateABadge(int idInscription) {
		return postCheckedInscriptionTo(idInscription, "/admin/badge", POST);
	}

	protected Result postCheckedInscriptionTo(int idInscription,
			final String url, final String verb) {
		Result result = null;

		// c'est moche, mais seule façon trouvée pour passer des données et
		// éviter d'encapsuler tous les appels
		final List<Object> arguments = new ArrayList();
		arguments.add(result);
		arguments.add("nameSelected_" + idInscription);

		running(fakeApplicationOverloaded(), new Runnable() {
			@Override
			public void run() {
				Map<String, String> map = new HashMap<String, String>();
				map.put((String) arguments.get(1), "on");
				FakeRequest fakeRequest = fakeRequest(verb, url).withSession("connectedUser", "Admin")
						.withFormUrlEncodedBody(map);
				arguments.set(0, routeAndCall(fakeRequest));
			}
		});

		return (Result) arguments.get(0);
	}

	private void thenTheBadgeIsGenerated(Result result) {
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentAsBytes(result).length).isGreaterThan(0);
	}

	@Test
	public void shouldUpdateInscriptions() {
		givenThereAreNewInscriptions();

		Result result = whenIUpdateTheInscriptions();

		thenTheInscriptionsAreAdded(result);
	}

	private void givenThereAreNewInscriptions() {

	}

	private Result whenIUpdateTheInscriptions() {
		Result result = null;

		// c'est moche, mais seule façon trouvée pour passer des données et
		// éviter d'encapsuler tous les appels
		final List<Object> arguments = new ArrayList();
		arguments.add(result);

		running(fakeApplicationOverloaded(), new Runnable() {
			@Override
			public void run() {
				FakeRequest fakeRequest = fakeRequest(POST,
						"/admin/inscriptions").withSession("connectedUser", "Admin");
				arguments.set(0, routeAndCall(fakeRequest));
			}
		});

		return (Result) arguments.get(0);
	}

	private void thenTheInscriptionsAreAdded(Result result) {
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentAsString(result)).contains("2 inscriptions");
	}
	
	@Test
	public void shouldFilterInscriptionsOnOrganizers() {
		givenThereAreInscriptionsWithSomeOrganizers();
		
		Result result = whenIFilterTheListOnOrganizersOnly();
		
		thenOnlyTheOrganizersAreDisplayed(result);
	}

	private void givenThereAreInscriptionsWithSomeOrganizers() {		
	}

	private Result whenIFilterTheListOnOrganizersOnly() {
		Result result = null;

		// c'est moche, mais seule façon trouvée pour passer des données et
		// éviter d'encapsuler tous les appels
		final List<Object> arguments = new ArrayList();
		arguments.add(result);

		running(fakeApplicationOverloaded(), new Runnable() {
			@Override
			public void run() {
				FakeRequest fakeRequest = fakeRequest(GET,
						"/admin/inscriptions/organisateur").withSession("connectedUser", "Admin");
				arguments.set(0, routeAndCall(fakeRequest));
			}
		});

		return (Result) arguments.get(0);
	}

	private void thenOnlyTheOrganizersAreDisplayed(Result result) {
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentAsString(result)).contains("Goldman");
		assertThat(contentAsString(result)).doesNotContain("Dupont");
	}
	
	@Test
	public void shouldSendABadgeToAnAttendee() {
		int attendeeId = givenISelectAnAttendee();
		
		Result result = whenISendABadgeToTheAttendee(attendeeId);
		
		thenTheBadgeIsSent(result);
	}

	private int givenISelectAnAttendee() {
		return givenIHaveSelectedAnInscription().getId();
	}

	private Result whenISendABadgeToTheAttendee(int attendeeId) {
		return postCheckedInscriptionTo(attendeeId, "/admin/send", POST);
	}

	private void thenTheBadgeIsSent(Result result) {
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentAsString(result)).contains("badge envoyé");
	}
	
	@Test
	public void shouldNotSendABadgeToAnAttendeeIfAlreadySent() {
		int attendeeId = givenISelectAnAttendeeWhomBadgeHasAlreadyBeenSent();
		
		Result result = whenISendABadgeToTheAttendee(attendeeId);
		
		thenTheBadgeIsNotSent(result);
	}

	private int givenISelectAnAttendeeWhomBadgeHasAlreadyBeenSent() {
		return 13;
	}

	private void thenTheBadgeIsNotSent(Result result) {
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentAsString(result)).contains("badge déjà envoyé");
	}
	
	@Test
	public void shouldDeleteInscription() {
		Inscription inscription = givenIHaveSelectedAnInscription();
		
		Result result = whenIDeleteTheInscription(inscription);
		
		thenTheInscriptionIsDeleted(result);
	}

	private Result whenIDeleteTheInscription(Inscription inscription) {
		return postCheckedInscriptionTo(inscription.getId(), "/admin/inscriptions/delete", POST);
	}

	private void thenTheInscriptionIsDeleted(Result result) {
		assertThat(status(result)).isEqualTo(SEE_OTHER);
	}

}
