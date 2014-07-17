package services;

import static helpers.PlayTestHelpers.fakeApplicationOverloaded;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static play.test.Helpers.PUT;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.routeAndCall;
import static play.test.Helpers.running;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import models.Inscription;
import models.ModelFactory;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.junit.Before;
import org.junit.Test;

import play.mvc.Result;
import play.test.FakeRequest;

public class EventbriteAccessorTests {

	private EventbriteAccessor eventbrite;
	
	private static final String ORGANIZER_TICKET_ID = "16008859";
	private static final String ATTENDEE_TICKET_ID = "15723826";
	private static final String SPEAKER_TICKET_ID = "16057183";
	private static final String STUDENT_TICKET_ID = "16057319";
	private static final String VIP_TICKET_ID = "15929463";
	private static final String EARLYBIRD_TICKET_ID = "17826293";
	
	private static final String SIMPLE_INSCRIPTION_JSON = "{\"attendees\": [{\"attendee\": {\"first_name\": \"Thierry\", \"last_name\": \"Hazard\", \"order_type\": \"Free Order\", \"created\": \"2012-10-26 02:50:17\", \"order_id\": 119300000, \"amount_paid\": \"0.00\", \"barcode\": \"119309147152822000000\", \"modified\": \"2012-10-26 02:50:41\", \"answers\": [{\"answer\": {\"answer_text\": \"Novice\", \"question\": \"Niveau de connaissances agiles\", \"question_type\": \"multiple choice\", \"question_id\": 2913746}}, {\"answer\": {\"answer_text\": \"Novice\", \"question\": \"Niveau de pratiques agiles\", \"question_type\": \"multiple choice\", \"question_id\": 2913786}}], \"id\": 152820000, \"currency\": \"EUR\", \"affiliate\": \"\", \"ticket_id\": " + ATTENDEE_TICKET_ID + " , \"event_id\": 4609749886, \"event_date\": \"\", \"discount\": \"\", \"email\": \"thierry.Hazard@lejerk.com\", \"quantity\": 1}}]}";
	private static final String INSCRIPTION_LIST_JSON = "{\"attendees\": [{\"attendee\":{\"first_name\": \"Thierry\",\"last_name\": \"hazard\",\"barcode\": \"119309147152822000000\",\"email\": \"thierry.hazard@lejerk.com\", \"ticket_id\": 15723826}},{\"attendee\":{\"first_name\": \"Gilbert\",\"last_name\": \"Montagné\",\"barcode\": \"119309147152822111111\",\"email\": \"gilbert@montagne.com\", \"ticket_id\": 15723826}}]}";
	private static final String ORGANIZER_JSON = "{\"attendees\": [{\"attendee\": {\"first_name\": \"Thierry\", \"last_name\": \"Hazard\", \"order_type\": \"Free Order\", \"created\": \"2012-10-26 02:50:17\", \"order_id\": 119300000, \"amount_paid\": \"0.00\", \"barcode\": \"119309147152822000000\", \"modified\": \"2012-10-26 02:50:41\", \"answers\": [{\"answer\": {\"answer_text\": \"Novice\", \"question\": \"Niveau de connaissances agiles\", \"question_type\": \"multiple choice\", \"question_id\": 2913746}}, {\"answer\": {\"answer_text\": \"Novice\", \"question\": \"Niveau de pratiques agiles\", \"question_type\": \"multiple choice\", \"question_id\": 2913786}}], \"id\": 152820000, \"currency\": \"EUR\", \"affiliate\": \"\", \"ticket_id\": " + ORGANIZER_TICKET_ID + ", \"event_id\": 4609749886, \"event_date\": \"\", \"discount\": \"\", \"email\": \"thierry.Hazard@lejerk.com\", \"quantity\": 1}}]}";
	private static final String SPEAKER_JSON = "{\"attendees\": [{\"attendee\": {\"first_name\": \"Thierry\", \"last_name\": \"Hazard\", \"order_type\": \"Free Order\", \"created\": \"2012-10-26 02:50:17\", \"order_id\": 119300000, \"amount_paid\": \"0.00\", \"barcode\": \"119309147152822000000\", \"modified\": \"2012-10-26 02:50:41\", \"answers\": [{\"answer\": {\"answer_text\": \"Novice\", \"question\": \"Niveau de connaissances agiles\", \"question_type\": \"multiple choice\", \"question_id\": 2913746}}, {\"answer\": {\"answer_text\": \"Novice\", \"question\": \"Niveau de pratiques agiles\", \"question_type\": \"multiple choice\", \"question_id\": 2913786}}], \"id\": 152820000, \"currency\": \"EUR\", \"affiliate\": \"\", \"ticket_id\": " + SPEAKER_TICKET_ID + ", \"event_id\": 4609749886, \"event_date\": \"\", \"discount\": \"\", \"email\": \"thierry.Hazard@lejerk.com\", \"quantity\": 1}}]}";
	private static final String STUDENT_JSON = "{\"attendees\": [{\"attendee\": {\"first_name\": \"Mas\", \"last_name\": \"Jeanne\", \"order_type\": \"Free Order\", \"created\": \"2012-10-26 02:50:17\", \"order_id\": 119300000, \"amount_paid\": \"0.00\", \"barcode\": \"119309147152822000000\", \"modified\": \"2012-10-26 02:50:41\", \"answers\": [{\"answer\": {\"answer_text\": \"Novice\", \"question\": \"Niveau de connaissances agiles\", \"question_type\": \"multiple choice\", \"question_id\": 2913746}}, {\"answer\": {\"answer_text\": \"Novice\", \"question\": \"Niveau de pratiques agiles\", \"question_type\": \"multiple choice\", \"question_id\": 2913786}}], \"id\": 152820000, \"currency\": \"EUR\", \"affiliate\": \"\", \"ticket_id\": " + STUDENT_TICKET_ID + ", \"event_id\": 4609749886, \"event_date\": \"\", \"discount\": \"\", \"email\": \"thierry.Hazard@lejerk.com\", \"quantity\": 1}}]}";
	private static final String VIP_JSON = "{\"attendees\": [{\"attendee\": {\"first_name\": \"Plastic\", \"last_name\": \"Bertrand\", \"order_type\": \"Free Order\", \"created\": \"2012-10-26 02:50:17\", \"order_id\": 119300000, \"amount_paid\": \"0.00\", \"barcode\": \"119309147152822000000\", \"modified\": \"2012-10-26 02:50:41\", \"answers\": [{\"answer\": {\"answer_text\": \"Novice\", \"question\": \"Niveau de connaissances agiles\", \"question_type\": \"multiple choice\", \"question_id\": 2913746}}, {\"answer\": {\"answer_text\": \"Novice\", \"question\": \"Niveau de pratiques agiles\", \"question_type\": \"multiple choice\", \"question_id\": 2913786}}], \"id\": 152820000, \"currency\": \"EUR\", \"affiliate\": \"\", \"ticket_id\": " + VIP_TICKET_ID + ", \"event_id\": 4609749886, \"event_date\": \"\", \"discount\": \"\", \"email\": \"thierry.Hazard@lejerk.com\", \"quantity\": 1}}]}";
	private static final String EARLYBIRD_JSON = "{\"attendees\": [{\"attendee\": {\"first_name\": \"Plastic\", \"last_name\": \"Bertrand\", \"order_type\": \"Free Order\", \"created\": \"2012-10-26 02:50:17\", \"order_id\": 119300000, \"amount_paid\": \"0.00\", \"barcode\": \"119309147152822000000\", \"modified\": \"2012-10-26 02:50:41\", \"answers\": [{\"answer\": {\"answer_text\": \"Novice\", \"question\": \"Niveau de connaissances agiles\", \"question_type\": \"multiple choice\", \"question_id\": 2913746}}, {\"answer\": {\"answer_text\": \"Novice\", \"question\": \"Niveau de pratiques agiles\", \"question_type\": \"multiple choice\", \"question_id\": 2913786}}], \"id\": 152820000, \"currency\": \"EUR\", \"affiliate\": \"\", \"ticket_id\": " + EARLYBIRD_TICKET_ID + ", \"event_id\": 4609749886, \"event_date\": \"\", \"discount\": \"\", \"email\": \"thierry.Hazard@lejerk.com\", \"quantity\": 1}}]}";
	
	@Before
	public void setUp() throws MalformedURLException {
		try {
			HttpRequestSender requestSender = new HttpRequestSender();
			eventbrite = spy(new EventbriteAccessor(requestSender, "http://localhost:9000/assets/eventbrite.json", "", "", "", getModelFactory()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ModelFactory getModelFactory() {
		return new ModelFactory(ATTENDEE_TICKET_ID + ",20376711", ORGANIZER_TICKET_ID, SPEAKER_TICKET_ID, STUDENT_TICKET_ID, VIP_TICKET_ID, EARLYBIRD_TICKET_ID);
	}
	
	@Test
	public void shouldTransformJsonToInscriptions() throws IOException {
		givenThereAreSomeInscriptions();
		
		List<Inscription> inscriptions = whenIGetTheInscriptions();
		
		thenAllInscriptionsAreGathered(inscriptions);
	}	

	private void givenThereAreSomeInscriptions() throws IOException {
		prepareEventbriteGetAttendees(INSCRIPTION_LIST_JSON);
	}

	private List<Inscription> whenIGetTheInscriptions() {
		return eventbrite.getAllInscriptions();
	}

	private void thenAllInscriptionsAreGathered(List<Inscription> inscriptions) {
		assertEquals(2, inscriptions.size());
		assertEquals("Hazard", inscriptions.get(0).getNom());
	}
	
	@Test
	public void shouldGetAttendeesString() {
		givenThereAreSomeAttendees();
		
		String attendeesString = whenIGetTheAttendees();
		
		thenTheAttendeesAreGathered(attendeesString);
	}

	private void givenThereAreSomeAttendees() {}

	private String whenIGetTheAttendees() {
		String attendees = null;

		// c'est moche, mais seule façon trouvée pour passer des données et éviter d'encapsuler tous les appels 
		final List<Object> arguments = new ArrayList();
		arguments.add(attendees);

		running(fakeApplicationOverloaded(), new Runnable() {
			@Override
			public void run() {
				try {
					arguments.set(0, eventbrite.getAttendees());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});

		return (String) arguments.get(0);
	}

	private void thenTheAttendeesAreGathered(String attendeesString) {
		assertTrue(attendeesString.contains("{\"attendees\":"));
	}
	
	@Test(expected=IOException.class)
	public void shouldThrowExceptionWhenSourceNotReachable() throws Throwable {
		givenEventbriteIsNotReachable();
				
		try {
			whenIGetTheAttendees();
		} catch (RuntimeException e) {
			throw e.getCause();
		}
	}

	private void givenEventbriteIsNotReachable() {
		try {
			eventbrite = spy(new EventbriteAccessor(new HttpRequestSender(), "http://localhost:9999/", "", "", "", getModelFactory()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Test
	public void shouldReturnAnOrganizerWhenSpecificTicketId() throws IOException {
		givenThereIsAnOrganizer();
		
		List<Inscription> inscriptions = whenIGetTheInscriptions();
		
		thenTheInscriptionIsAnOrganizer(inscriptions.get(0));		
		butNotAnAttendee(inscriptions.get(0));
	}

	private void butNotAnAttendee(Inscription inscription) {
		assertFalse(inscription.isAttendee());
	}

	private void givenThereIsAnOrganizer() throws IOException {
		prepareEventbriteGetAttendees(ORGANIZER_JSON);	
	}

	protected void prepareEventbriteGetAttendees(String json)
			throws IOException {
		doReturn(json).when(eventbrite).getAttendees();
	}

	private void thenTheInscriptionIsAnOrganizer(Inscription inscription) {
		assertTrue(inscription.isOrganizer());
	}
	
	@Test
	public void shouldReturnAnAttendeeWhenSpecificTicketId() throws IOException {
		givenThereIsAnAttendee();
		
		List<Inscription> inscriptions = whenIGetTheInscriptions();
		
		thenTheInscriptionIsAnAttendee(inscriptions.get(0));
		butNotAnOrganizer(inscriptions.get(0));
		andNotASpeaker(inscriptions.get(0));
	}

	private void andNotASpeaker(Inscription inscription) {
		assertFalse(inscription.isSpeaker());
	}

	private void givenThereIsAnAttendee() throws IOException {
		prepareEventbriteGetAttendees(SIMPLE_INSCRIPTION_JSON);	
	}

	private void thenTheInscriptionIsAnAttendee(Inscription inscription) {
		assertTrue(inscription.isAttendee());
	}

	private void butNotAnOrganizer(Inscription inscription) {
		assertFalse(inscription.isOrganizer());
	}
	
	@Test
	public void shouldReturnAnAttendeeWhenEarlyBirdTicketId() throws IOException {
		givenThereIsAnEarlyBird();
		
		List<Inscription> inscriptions = whenIGetTheInscriptions();
		
		thenTheInscriptionIsAnAttendee(inscriptions.get(0));
		butNotAnOrganizer(inscriptions.get(0));
		andNotASpeaker(inscriptions.get(0));
	}
	
	private void givenThereIsAnEarlyBird() throws IOException {
		prepareEventbriteGetAttendees(EARLYBIRD_JSON);
	}

	@Test
	public void shouldReturnASpeakerWhenSpecificTicketId() throws IOException {
		givenThereIsASpeaker();
		
		List<Inscription> inscriptions = whenIGetTheInscriptions();
		
		thenTheInscriptionIsASpeaker(inscriptions.get(0));
	}

	private void givenThereIsASpeaker() throws IOException {
		prepareEventbriteGetAttendees(SPEAKER_JSON);
	}

	private void thenTheInscriptionIsASpeaker(Inscription inscription) {
		assertTrue(inscription.isSpeaker());
	}
	
	@Test
	public void shouldReturnAStudentWhenSpecificTicketId() throws IOException {
		givenThereIsAStudent();
		
		List<Inscription> inscriptions = whenIGetTheInscriptions();
		
		thenTheInscriptionIsAStudent(inscriptions.get(0));
	}

	private void givenThereIsAStudent() throws IOException {
		prepareEventbriteGetAttendees(STUDENT_JSON);
	}

	private void thenTheInscriptionIsAStudent(Inscription inscription) {
		assertTrue(inscription.isStudent());
		assertTrue(inscription.isAttendee());
	}
	
	@Test
	public void shouldReturnAVIPWhenSpecificTicketId() throws IOException {
		givenThereIsAVIP();
		
		List<Inscription> inscriptions = whenIGetTheInscriptions();
		
		thenTheInscriptionIsAVIP(inscriptions.get(0));
	}

	private void givenThereIsAVIP() throws IOException {
		prepareEventbriteGetAttendees(VIP_JSON);
	}

	private void thenTheInscriptionIsAVIP(Inscription inscription) {
		assertTrue(inscription.isVIP());
		assertTrue(inscription.isAttendee());
	}
}
