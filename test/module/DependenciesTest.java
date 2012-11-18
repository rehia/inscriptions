package module;

import helpers.FakeDataProvider;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

import models.Inscription;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.inject.Provides;

import services.EntityManagerFactory;
import services.Repository;
import services.StubEntityManagerFactory;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class DependenciesTest extends Dependencies {
	
	@Override
	protected Repository getRepository() {
		Repository repository = mock(Repository.class);
		
		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				Inscription inscription = (Inscription) args[0];
				inscription.setId(1);
				return inscription;
			}
		}).when(repository).save(any(Inscription.class));
		
		when(repository.getInscriptions()).thenReturn(FakeDataProvider.getSomeExistingInscriptions());
		
		when(repository.updateInscriptions()).thenReturn(2);
		
		when(repository.getInscriptionById(12)).thenReturn(FakeDataProvider.getAnExistingInscription());
		
		when(repository.getInscriptionsByCategory(Inscription.ORGANISATEUR)).thenReturn(FakeDataProvider.getOrganizers());
		
		return repository;
	}

}
