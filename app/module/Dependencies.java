package module;

import com.google.inject.Provides;
import javax.inject.Singleton;
import javax.persistence.EntityManager;

import play.Application;
import play.Play;
import play.db.jpa.JPA;

import com.typesafe.plugin.inject.InjectPlugin;

import services.EntityManagerFactory;
import services.PlayJPAEntityManagerFactory;
import services.Repository;

public class Dependencies {

	@Provides
	@Singleton
	public Repository makeRepository() {
		return getRepository();
	}

	protected Repository getRepository() {
		return new Repository(getEntityManagerFactory());
	}

	protected EntityManagerFactory getEntityManagerFactory() {
		return new PlayJPAEntityManagerFactory();
	}
}
