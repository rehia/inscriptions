package models.mandrill;

import org.codehaus.jackson.annotate.JsonProperty;

public class Attachment {

	@JsonProperty("type")
	public String type;

	@JsonProperty("name")
	public String name;

	@JsonProperty("content")
	public String content;
}
