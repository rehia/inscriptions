package services;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import helpers.FakeDataProvider;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import models.Inscription;
import models.ModelFactory;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

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
		when(entityManager.createQuery("SELECT i FROM inscriptions i")).thenReturn(queryAll);
		when(queryAll.getResultList()).thenReturn(FakeDataProvider.getSomeExistingInscriptions());
	}

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
