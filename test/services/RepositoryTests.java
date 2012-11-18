package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import helpers.FakeDataProvider;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import models.Inscription;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class RepositoryTests {

	private Repository repository;
	private EntityManager entityManager;
	private ExternalSourceAccessor externalSourceAccessor;
	
	@Before
	public void setUp() throws Exception {
		EntityManagerFactory entityManagerFactory = new StubEntityManagerFactory();
		entityManager = entityManagerFactory.getEntityManager();
		externalSourceAccessor = mock(ExternalSourceAccessor.class);

		repository = spy(new Repository(entityManagerFactory, externalSourceAccessor));
	}

	@Test
	public void shouldSaveAnInscription() {
		Inscription inscription = givenIHaveAnInscription();

		whenISaveTheInscription(inscription);

		thenTheInscriptionIsSaved(inscription);
	}

	private Inscription givenIHaveAnInscription() {
		return FakeDataProvider.getAnExistingInscription();
	}

	private void whenISaveTheInscription(Inscription inscription) {
		repository.save(inscription);
	}

	private void thenTheInscriptionIsSaved(Inscription inscription) {
		assertFalse(inscription.getId() == 0);
		verify(entityManager).persist(any(Inscription.class));
	}
	
	@Test
	public void shouldGetAListOfInscriptions() {
		givenThereAreAlreadySomeInscriptions();
		
		List<Inscription> inscriptions = whenIGetAListOfInscriptions();
		
		thenTheListContainAllInscriptions(inscriptions);
	}

	private void givenThereAreAlreadySomeInscriptions() {}

	private List<Inscription> whenIGetAListOfInscriptions() {
		return repository.getInscriptions();
	}

	private void thenTheListContainAllInscriptions(List<Inscription> inscriptions) {
		assertEquals(3, inscriptions.size());
	}
	
	@Test
	public void shouldUpdateInscriptionsFromExternalSourceForOneInscription() {
		givenThereAreInscriptionsInExternalSource();
		andOneInscriptionHasNotBeenUploadedInTheLocalRepositoryYet();
		
		int inscriptionsAddedCount = whenIUpdateTheLocalRepository();
		
		thenTheInscriptionIsAdded(inscriptionsAddedCount);
	}

	private void givenThereAreInscriptionsInExternalSource() {}

	private void andOneInscriptionHasNotBeenUploadedInTheLocalRepositoryYet() {
		List<Inscription> inscriptions = FakeDataProvider.getANewInscription();
		when(externalSourceAccessor.getAllInscriptions()).thenReturn(inscriptions);
		
		for (Inscription inscription : inscriptions) {
			doReturn(false).when(repository).inscriptionAlreadyExists(inscription);
		}
	}

	private int whenIUpdateTheLocalRepository() {
		return repository.updateInscriptions();	
	}

	private void thenTheInscriptionIsAdded(int inscriptionsAddedCount) {
		ArgumentCaptor<Inscription> inscriptionToSave = ArgumentCaptor.forClass(Inscription.class);
		verify(entityManager).persist(inscriptionToSave.capture());
		assertEquals("Hazard", inscriptionToSave.getValue().getNom());
		
		assertEquals(1, inscriptionsAddedCount);
	}
	
	@Test public void inscriptionShouldNotExistWhenNotInLocalRepository() {
		Inscription inscription = givenIHaveAnInscriptionThatIsAlreadyInLocalRepository();
		
		Boolean inscriptionExists = whenIVerifyIfTheInscriptionExists(inscription);
		
		thenItsFalse(inscriptionExists);
	}
	
	private Inscription givenIHaveAnInscriptionThatIsAlreadyInLocalRepository() {
		mockQueryOnInscriptions(new ArrayList<Inscription>());
		
		return FakeDataProvider.getAnExistingInscription();
	}

	protected void mockQueryOnInscriptions(List<Inscription> inscriptionsInRepository) {
		Query queryAll = mock(Query.class);
		when(entityManager.createQuery(anyString())).thenReturn(queryAll);
		when(queryAll.getResultList()).thenReturn(inscriptionsInRepository);
		
		if (inscriptionsInRepository.size() == 0) {
			when(queryAll.getSingleResult()).thenThrow(NoResultException.class);
		} else {
			when(queryAll.getSingleResult()).thenReturn(inscriptionsInRepository.get(0));
		}
	}

	private Boolean whenIVerifyIfTheInscriptionExists(Inscription inscription) {
		return repository.inscriptionAlreadyExists(inscription);
	}

	private void thenItsFalse(Boolean inscriptionExists) {
		assertFalse(inscriptionExists);
	}

	@Test
	public void shouldUpdateInscriptionsFromExternalSourceForMultipleInscriptions() {
		givenThereAreInscriptionsInExternalSource();
		andSomeInscriptionsHaveNotBeenUploadedInTheLocalRepositoryYet();
		
		int inscriptionsAddedCount = whenIUpdateTheLocalRepository();
		
		thenTheInscriptionsAreAdded(inscriptionsAddedCount);
	}

	private void andSomeInscriptionsHaveNotBeenUploadedInTheLocalRepositoryYet() {
		List<Inscription> inscriptions = FakeDataProvider.getSomeNewInscriptions();
		when(externalSourceAccessor.getAllInscriptions()).thenReturn(inscriptions);
		
		for (Inscription inscription : inscriptions) {
			doReturn(false).when(repository).inscriptionAlreadyExists(inscription);
		}
	}

	private void thenTheInscriptionsAreAdded(int inscriptionsAddedCount) {
		ArgumentCaptor<Inscription> inscriptionToSave = ArgumentCaptor.forClass(Inscription.class);
		verify(entityManager, times(2)).persist(inscriptionToSave.capture());
		assertEquals("Montagné", inscriptionToSave.getValue().getNom());
		
		assertEquals(2, inscriptionsAddedCount);
	}

	@Test
	public void shouldNotUpdateInscriptionFromExternalSourceWhenAlreadyAdded() {
		givenThereAreInscriptionsInExternalSource();
		andTheseInscriptionsHaveAlreadyBeenAddedToLocalRepository();
		
		int inscriptionsAddedCount = whenIUpdateTheLocalRepository();
		
		thenTheInscriptionsAreNotAdded(inscriptionsAddedCount);
	}

	private void andTheseInscriptionsHaveAlreadyBeenAddedToLocalRepository() {
		Inscription inscription = FakeDataProvider.getAnExistingInscription();
		List<Inscription> inscriptions = new ArrayList<Inscription>();
		inscriptions.add(inscription);
		when(externalSourceAccessor.getAllInscriptions()).thenReturn(inscriptions);
		
		doReturn(true).when(repository).inscriptionAlreadyExists(inscription);
	}
	
	private void thenTheInscriptionsAreNotAdded(int inscriptionsAddedCount) {
		verify(entityManager, never()).persist(any(Inscription.class));
		assertEquals(0, inscriptionsAddedCount);
	}
	
	@Test
	public void shouldReturnAnInscriptionWithExistingId() {
		int inscriptionId = givenIHaveAnExistingId();
		
		Inscription inscription = whenIGetAnInscriptionFromId(inscriptionId);
		
		thenTheInscriptionIsValid(inscription);
	}

	private int givenIHaveAnExistingId() {	
		int idInscription = 12;	
		
		List<Inscription> inscriptionsInRepository = new ArrayList<Inscription>();
		Inscription inscription = FakeDataProvider.getAnExistingInscription();
		inscription.setId(idInscription);
		inscriptionsInRepository.add(inscription);
		mockQueryOnInscriptions(inscriptionsInRepository);
		
		return idInscription;
	}

	private Inscription whenIGetAnInscriptionFromId(int inscriptionId) {
		return repository.getInscriptionById(inscriptionId);
	}

	private void thenTheInscriptionIsValid(Inscription inscription) {
		assertNotNull(inscription);
		assertEquals("Durand", inscription.getNom());
	}
	
	@Test
	public void shouldGetOnlyOrganizersWhenFiltered() {
		givenThereAreOrganizers();
		
		List<Inscription> inscriptions = whenIGetAListOfOrganizers();
		
		thenTheListContainOnlyOrganizers(inscriptions);		
	}

	private void givenThereAreOrganizers() {
		mockQueryOnInscriptions(FakeDataProvider.getOrganizers());
	}

	private List<Inscription> whenIGetAListOfOrganizers() {
		return repository.getInscriptionsByCategory(Inscription.ORGANISATEUR);
	}

	private void thenTheListContainOnlyOrganizers(List<Inscription> inscriptions) {
		assertEquals(1, inscriptions.size());
		assertEquals("Goldman", inscriptions.get(0).getNom());
	}
	
	@Test
	public void shouldReturnAListOfCategories() {
		List<String> categories = repository.getCategories();
		
		assertEquals(3, categories.size());
	}
}
