package models.mandrill;

import org.codehaus.jackson.annotate.JsonProperty;

public class Variable {

	@JsonProperty("name")
	public String name;
	
	@JsonProperty("content")
	public String content;
}
