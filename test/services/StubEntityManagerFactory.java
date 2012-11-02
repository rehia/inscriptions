package services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import models.Inscription;
import static org.mockito.Mockito.*;

public class StubEntityManagerFactory implements EntityManagerFactory {

	private EntityManager entityManager;

	public StubEntityManagerFactory() {
		entityManager = mock(EntityManager.class);

		doAnswer(new Answer<Object>() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				Inscription inscription = (Inscription) args[0];
				inscription.setId(1);
				return inscription;
			}
		}).when(entityManager).persist(any(Inscription.class));		

		Query queryAll = mock(Query.class);
		when(entityManager.createQuery(anyString())).thenReturn(queryAll);
		when(queryAll.getResultList()).thenReturn(getInscriptions());
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
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
