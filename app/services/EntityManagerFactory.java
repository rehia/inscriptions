package services;

import javax.persistence.EntityManager;

public interface EntityManagerFactory {
	
	EntityManager getEntityManager();

}
