package module;

import java.net.MalformedURLException;

import javax.inject.Singleton;

import models.ModelFactory;

import play.Configuration;
import play.Play;
import services.EntityManagerFactory;
import services.EventbriteAccessor;
import services.ExternalSourceAccessor;
import services.HttpRequestSender;
import services.MailSender;
import services.MandrillAccessor;
import services.PlayJPAEntityManagerFactory;
import services.Repository;

import com.google.inject.Provides;

public class Dependencies {

	@Provides
	@Singleton
	public Repository makeRepository() throws MalformedURLException {
		return getRepository();
	}

	protected Repository getRepository() throws MalformedURLException {
		return new Repository(getEntityManagerFactory(), getExternalSourceAccessor(), getMailSender());
	}

	protected MailSender getMailSender() throws MalformedURLException {
		Configuration configuration = Play.application().configuration();
		return new MandrillAccessor(getRequestSender(),
				configuration.getString("mandrill.url"),
				configuration.getString("mandrill.key"));
	}

	protected ExternalSourceAccessor getExternalSourceAccessor() throws MalformedURLException {
		Configuration configuration = Play.application().configuration();
		ModelFactory modelFactory = getModelFactory();
		return new EventbriteAccessor(getRequestSender(),
				configuration.getString("eventbrite.url"),
				configuration.getString("eventbrite.applicationKey"),
				configuration.getString("eventbrite.userKey"),
				configuration.getString("eventbrite.eventId"),
				modelFactory);
	}

	protected ModelFactory getModelFactory() {
		Configuration configuration = Play.application().configuration();
		return new ModelFactory(configuration.getString("eventbrite.ticketId.inscrit"),
				configuration.getString("eventbrite.ticketId.organisateur"),
				configuration.getString("eventbrite.ticketId.speaker"),
				configuration.getString("eventbrite.ticketId.student"),
				configuration.getString("eventbrite.ticketId.vip"),
				configuration.getString("eventbrite.ticketId.earlyBird"));
	}

	private HttpRequestSender getRequestSender() {
		return new HttpRequestSender();
	}

	protected EntityManagerFactory getEntityManagerFactory() {
		return new PlayJPAEntityManagerFactory();
	}
}
