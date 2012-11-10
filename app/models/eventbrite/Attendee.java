package models.eventbrite;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true) 
public class Attendee {
	  @JsonProperty("attendee")
	  public AttendeeElement inscription;
}
