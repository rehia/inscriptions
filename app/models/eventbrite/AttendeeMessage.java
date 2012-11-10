package models.eventbrite;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class AttendeeMessage {
		  @JsonProperty("attendees")
		  public List<Attendee> attendees;
}
