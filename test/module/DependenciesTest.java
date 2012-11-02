package module;

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
		
		when(repository.getInscriptions()).thenReturn(getInscriptions());
		
		return repository;
	}

	private List<Inscription> getInscriptions() {
		List<Inscription> inscriptions = new ArrayList<Inscription>();
		
		Inscription durand = new Inscription();
		durand.setNom("Durand");
		durand.setPrenom("Fernand");
		durand.setEmail("f.d@df.fr");
		inscriptions.add(durand);

		Inscription dupont = new Inscription();
		dupont.setNom("Dupont");
		dupont.setPrenom("Bernard");
		dupont.setEmail("u_I@uj.fr");
		inscriptions.add(dupont);
		
		return inscriptions;
	}

}
