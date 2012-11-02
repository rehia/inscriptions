package services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.Inscription;

public class Repository {
	
	private EntityManagerFactory entityManagerFactory;

	public Repository(EntityManagerFactory entityManagerFactory) {
		this.entityManagerFactory = entityManagerFactory;
	}

	public void save(Inscription inscription) {
		EntityManager entityManager = entityManagerFactory.getEntityManager();
		entityManager.persist(inscription);
	}

	public List<Inscription> getInscriptions() {
		EntityManager entityManager = entityManagerFactory.getEntityManager();
		return entityManager.createQuery("SELECT i FROM inscriptions i").getResultList();
	}

}
