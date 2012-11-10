package services;

import javax.persistence.EntityManager;

import play.db.jpa.JPA;


public class PlayJPAEntityManagerFactory implements EntityManagerFactory {

	@Override
	public EntityManager getEntityManager() {
		return JPA.em();
	}

}
