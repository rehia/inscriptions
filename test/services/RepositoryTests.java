package services;

import static org.junit.Assert.*;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.running;

import java.util.List;

import javax.persistence.EntityManager;

import models.Inscription;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class RepositoryTests {

	private Repository repository;
	private EntityManager entityManager;

	@Before
	public void setUp() throws Exception {
		EntityManagerFactory entityManagerFactory = new StubEntityManagerFactory();
		entityManager = entityManagerFactory.getEntityManager();

		repository = new Repository(entityManagerFactory);
	}

	@Test
	public void shouldSaveAnInscription() {
		Inscription inscription = givenIHaveAnInscription();

		whenISaveTheInscription(inscription);

		thenTheInscriptionIsSaved(inscription);
	}
	
	@Test
	public void shouldGetAListOfInscriptions() {
		givenThereAreAlreadySomeInscriptions();
		
		List<Inscription> inscriptions = whenIGetAListOfInscriptions();
		
		thenTheListContainAllInscriptions(inscriptions);
	}

	private void thenTheListContainAllInscriptions(List<Inscription> inscriptions) {
		assertEquals(2, inscriptions.size());
	}

	private List<Inscription> whenIGetAListOfInscriptions() {
		return repository.getInscriptions();
	}

	private void givenThereAreAlreadySomeInscriptions() {
		// TODO Auto-generated method stub
		
	}

	private void thenTheInscriptionIsSaved(Inscription inscription) {
		assertFalse(inscription.getId() == 0);
		verify(entityManager).persist(any(Inscription.class));
	}

	private void whenISaveTheInscription(Inscription inscription) {
		repository.save(inscription);
	}

	private Inscription givenIHaveAnInscription() {
		Inscription inscription = new Inscription();
		inscription.setNom("Durand");
		inscription.setPrenom("Fernand");
		inscription.setEmail("f.d@df.fr");
		return inscription;
	}

}
