package module;

import java.net.MalformedURLException;

import javax.inject.Singleton;

import play.Configuration;
import play.Play;
import services.EntityManagerFactory;
import services.EventbriteAccessor;
import services.ExternalSourceAccessor;
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
		return new Repository(getEntityManagerFactory(), getExternalSourceAccessor());
	}

	protected ExternalSourceAccessor getExternalSourceAccessor() throws MalformedURLException {
		Configuration configuration = Play.application().configuration();
		return new EventbriteAccessor(configuration.getString("eventbrite.url"),
				configuration.getString("eventbrite.applicationKey"),
				configuration.getString("eventbrite.userKey"),
				configuration.getString("eventbrite.eventId"));
	}

	protected EntityManagerFactory getEntityManagerFactory() {
		return new PlayJPAEntityManagerFactory();
	}
}
