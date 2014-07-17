package services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import play.api.Logger;

import models.Inscription;

public class Repository {
	
	private EntityManagerFactory entityManagerFactory;
	private ExternalSourceAccessor externalSourceAccessor;
	private MailSender mailSender;

	public Repository(EntityManagerFactory entityManagerFactory, ExternalSourceAccessor externalSourceAccessor, MailSender mailSender) {
		this.entityManagerFactory = entityManagerFactory;
		this.externalSourceAccessor = externalSourceAccessor;
		this.mailSender = mailSender;
	}

	public void save(Inscription inscription) {
		EntityManager entityManager = entityManagerFactory.getEntityManager();
		entityManager.persist(inscription);
	}

	public List<Inscription> getInscriptions() {
		EntityManager entityManager = entityManagerFactory.getEntityManager();
		Query query = entityManager.createQuery("SELECT i FROM inscriptions i order by i.nom");
		return query.getResultList();
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

	public Inscription getInscriptionById(int inscriptionId) {
		Query query = entityManagerFactory.getEntityManager().createQuery("SELECT i FROM inscriptions i where i.id = :id");
		query.setParameter("id", inscriptionId);
		return (Inscription) query.getSingleResult();
	}

	public List<Inscription> getInscriptionsByCategory(String category) {
		Query query = entityManagerFactory.getEntityManager().createQuery("SELECT i FROM inscriptions i where i.categorie = :categorie order by i.nom");
		query.setParameter("categorie", category);
		return query.getResultList();
	}

	public List<String> getCategories() {
		ArrayList<String> categories = new ArrayList<String>();
		categories.add(Inscription.INSCRIT);
		categories.add(Inscription.ORGANISATEUR);
		categories.add(Inscription.SPEAKER);
		categories.add(Inscription.STUDENT);
		categories.add(Inscription.VIP);
		return categories;
	}

	public String sendBadgeToAttendee(Inscription inscription, String badge) {
		
		String message = mailSender.send(inscription, badge);
		inscription.setBadgeAsSent();
		play.Logger.info("badge envoyé à : " + inscription.getEmail());
		entityManagerFactory.getEntityManager().merge(inscription);
		return message;
	}

	public void deleteInscriptions(List<Inscription> inscriptions) {
		EntityManager entityManager = entityManagerFactory.getEntityManager();
		for (Inscription inscription : inscriptions) {
			entityManager.remove(inscription);
		}
	}

	

}


