package models.eventbrite;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true) 
public class AttendeeElement {
	
	@JsonProperty("last_name")
	public String lastName;
	
	@JsonProperty("first_name")
	public String firstName;
	
	@JsonProperty("email")
	public String email;

	@JsonProperty("company")
	public String company;
	
	@JsonProperty("job_title")
	public String jobTitle;
	
	@JsonProperty("barcode")
	public String barCode;
	
	@JsonProperty("ticket_id")
	public String ticketId;
}
