package services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import models.Inscription;
import models.ModelFactory;
import models.eventbrite.Attendee;
import models.eventbrite.AttendeeElement;
import models.eventbrite.AttendeeMessage;
import play.Logger;

public class EventbriteAccessor implements ExternalSourceAccessor {
	
	private URL urlService;
	private HttpRequestSender requestSender;
	private ModelFactory modelFactory;
	
	public EventbriteAccessor(HttpRequestSender requestSender, String urlService, String applicationKey, String userKey, String eventId, ModelFactory modelFactory) throws MalformedURLException {
		
		this.requestSender = requestSender;
		
		String parameters = addParameter("", "app_key", applicationKey);
		parameters = addParameter(parameters, "user_key", userKey);
		parameters = addParameter(parameters, "id", eventId);
		
		if (parameters.length() > 0)
			urlService += "?" + parameters;
		
		this.urlService = new URL(urlService);
		this.modelFactory = modelFactory;
	}

	protected String addParameter(String parameters, String key, String value) {
		if(value != null && value != "") {
			if (parameters.length() > 0)
				parameters += "&";
			parameters += key + "=" + value;
		}
		return parameters;
	}
	
	public EventbriteAccessor() {}

	@Override
	public List<Inscription> getAllInscriptions() {
		try {
			return transformAttendeesToInscriptions(getAttendees());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	protected List<Inscription> transformAttendeesToInscriptions(String jsonString) {

		AttendeeMessage message = getAttendeeMessageFromJsonString(jsonString);
		
		return getInscriptionsFromAttendeeMessage(message);
	}

	protected List<Inscription> getInscriptionsFromAttendeeMessage(
			AttendeeMessage message) {
		List<Inscription> inscriptions = new ArrayList<Inscription>();
		for (Attendee attendee : message.attendees) {
			AttendeeElement element = attendee.inscription;
			inscriptions.add(
					this.modelFactory.createInscription(
							element.lastName,
							element.firstName,
							element.email,
							element.barCode,
							element.jobTitle,
							element.company,
							element.ticketId));
		}
		return inscriptions;
	}

	protected AttendeeMessage getAttendeeMessageFromJsonString(String attendees) {
		ObjectMapper mapper = new ObjectMapper();
		AttendeeMessage message = null;
		
		try {
			message = mapper.readValue(attendees, AttendeeMessage.class);
		} catch (IOException e) {
			throw new RuntimeException("url : " + urlService.toString() + "message in error : " + attendees, e);
		}
		return message;
	}

	public String getAttendees() throws IOException {
		return requestSender.sendGetRequest(this.urlService);
	}

}
