package models.mandrill;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class SendTemplateMessage {

	@JsonProperty("key")
	public String key;

	@JsonProperty("template_name")
	public String templateName;

	@JsonProperty("template_content")
	public List<Variable> templateContent;

	@JsonProperty("message")
	public Message message;
}
