package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.Inscription;

public class Repository {
	
	private EntityManagerFactory entityManagerFactory;
	private ExternalSourceAccessor externalSourceAccessor;

	public Repository(EntityManagerFactory entityManagerFactory, ExternalSourceAccessor externalSourceAccessor) {
		this.entityManagerFactory = entityManagerFactory;
		this.externalSourceAccessor = externalSourceAccessor;
	}

	public void save(Inscription inscription) {
		EntityManager entityManager = entityManagerFactory.getEntityManager();
		entityManager.persist(inscription);
	}

	public List<Inscription> getInscriptions() {
		EntityManager entityManager = entityManagerFactory.getEntityManager();
		return entityManager.createQuery("SELECT i FROM inscriptions i").getResultList();
	}

	public int updateInscriptions() {
		List<Inscription> allInscriptions = externalSourceAccessor.getAllInscriptions();
		int inscriptionsAddedCount = 0;
		
		for (Inscription inscription : allInscriptions) {
			if (!inscriptionAlreadyExists(inscription)) {
				save(inscription);
				inscriptionsAddedCount++;
			}
		}
		
		return inscriptionsAddedCount;
	}

	public boolean inscriptionAlreadyExists(Inscription inscription) {
		Query query = entityManagerFactory.getEntityManager().createQuery("SELECT i FROM inscriptions i where i.barCode = :barCode");
		query.setParameter("barCode", inscription.barCode);
		return query.getResultList().size() != 0;
	}

}


