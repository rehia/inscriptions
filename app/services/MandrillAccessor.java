package services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import models.Inscription;
import models.mandrill.Attachment;
import models.mandrill.Message;
import models.mandrill.SendTemplateMessage;
import models.mandrill.To;
import models.mandrill.Variable;

public class MandrillAccessor implements MailSender {
	
	private HttpRequestSender requestSender;
	private URL urlService;
	private String mandrillKey;

	public MandrillAccessor(HttpRequestSender requestSender, String urlService, String mandrillKey) throws MalformedURLException {
		this.requestSender = requestSender;
		this.urlService = new URL(urlService);
		this.mandrillKey = mandrillKey;
	}

	@Override
	public String send(Inscription inscription, String badge) {
		
		String message;
		try {
			message = generateMessageToSend(inscription, badge);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return requestSender.sendPostRequest(this.urlService, message);
	}
	
	private String generateMessageToSend(Inscription inscription, String badge) throws JsonGenerationException, JsonMappingException, IOException {
		SendTemplateMessage templateMessage = new SendTemplateMessage();
		templateMessage.key = this.mandrillKey; 
		templateMessage.templateContent = new ArrayList<>();
		templateMessage.templateName = "EventBrite Agile Tour Montpellier"; //"Envoi Badge Erreur";
		
		templateMessage.message = new Message();
		templateMessage.message.fromEmail = "at-montpellier-contact@googlegroups.com";
		templateMessage.message.fromName = "Equipe Agile Tour Montpellier";
		templateMessage.message.subject = "[Agile Tour Montpellier 2013] Voici votre billet d'entrée... et votre badge !";
		//"[Agile Tour Montpellier 2012] Erreur dans l'envoi de votre ticket";
		
		templateMessage.message.toEmails = new ArrayList<>();
		To recipient = new To();
		recipient.email = inscription.getEmail();
		recipient.name = inscription.getPrenom() + " " + inscription.getNom();
		templateMessage.message.toEmails.add(recipient);
		
		templateMessage.message.mergeVariables = new ArrayList<>();
		Variable firstNameVariable = new Variable();
		firstNameVariable.name = "FNAME";
		firstNameVariable.content = inscription.getPrenom();
		Variable lastNameVariable = new Variable();
		lastNameVariable.name = "LNAME";
		lastNameVariable.content = inscription.getNom();
		templateMessage.message.mergeVariables.add(firstNameVariable);
		templateMessage.message.mergeVariables.add(lastNameVariable);
		
		templateMessage.message.attachments = new ArrayList<>();
		Attachment badgeAttached = new Attachment();
		badgeAttached.type = "application/pdf";
		badgeAttached.name = "Badge ATMTP2013 - " + recipient.name;
		badgeAttached.content = badge;
		templateMessage.message.attachments.add(badgeAttached);
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(templateMessage);
	}


}
