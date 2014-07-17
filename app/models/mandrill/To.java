package models.mandrill;

import org.codehaus.jackson.annotate.JsonProperty;

public class To {
	@JsonProperty("email")
	public String email;

	@JsonProperty("name")
	public String name;
}
