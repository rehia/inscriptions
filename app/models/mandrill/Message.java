package models.mandrill;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Message {

	@JsonProperty("subject")
	public String subject;

	@JsonProperty("from_email")
	public String fromEmail;

	@JsonProperty("from_name")
	public String fromName;

	@JsonProperty("to")
	public List<To> toEmails;

	@JsonProperty("global_merge_vars")
	public List<Variable> mergeVariables;

	@JsonProperty("attachments")	
	public List<Attachment> attachments;
}
