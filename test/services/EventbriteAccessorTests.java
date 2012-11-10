package services;

import static helpers.Helpers.fakeApplicationOverloaded;
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
	
	private static final String SIMPLE_INSCRIPTION_JSON = "{\"attendees\": [{\"attendee\": {\"first_name\": \"Thierry\", \"last_name\": \"Hazard\", \"order_type\": \"Free Order\", \"created\": \"2012-10-26 02:50:17\", \"order_id\": 119300000, \"amount_paid\": \"0.00\", \"barcode\": \"119309147152822000000\", \"modified\": \"2012-10-26 02:50:41\", \"answers\": [{\"answer\": {\"answer_text\": \"Novice\", \"question\": \"Niveau de connaissances agiles\", \"question_type\": \"multiple choice\", \"question_id\": 2913746}}, {\"answer\": {\"answer_text\": \"Novice\", \"question\": \"Niveau de pratiques agiles\", \"question_type\": \"multiple choice\", \"question_id\": 2913786}}], \"id\": 152820000, \"currency\": \"EUR\", \"affiliate\": \"\", \"ticket_id\": 15720000, \"event_id\": 4609749886, \"event_date\": \"\", \"discount\": \"\", \"email\": \"thierry.Hazard@lejerk.com\", \"quantity\": 1}}]}";
	private static final String INSCRIPTION_LIST_JSON = "{\"attendees\": [{\"attendee\":{\"first_name\": \"Thierry\",\"last_name\": \"Hazard\",\"barcode\": \"119309147152822000000\",\"email\": \"thierry.hazard@lejerk.com\"}},{\"attendee\":{\"first_name\": \"Gilbert\",\"last_name\": \"Montagné\",\"barcode\": \"119309147152822111111\",\"email\": \"gilbert@montagne.com\"}}]}";
	
	
	@Before
	public void setUp() throws MalformedURLException {
		try {
			eventbrite = spy(new EventbriteAccessor("http://localhost:9000/assets/eventbrite.json", "", "", ""));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void shouldTransformJsonToInscriptions() throws IOException {
		givenThereAreSomeInscriptions();
		
		List<Inscription> inscriptions = whenIGetTheseIncsriptions();
		
		thenAllInscriptionsAreGathered(inscriptions);
	}	

	private void givenThereAreSomeInscriptions() throws IOException {
		doReturn(INSCRIPTION_LIST_JSON).when(eventbrite).getAttendees();
	}

	private List<Inscription> whenIGetTheseIncsriptions() {
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
			eventbrite = spy(new EventbriteAccessor("http://localhost:9999/", "", "", ""));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}
