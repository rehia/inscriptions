package services;

import java.util.List;

import models.Inscription;


public interface ExternalSourceAccessor {
	
	public List<Inscription> getAllInscriptions();
}
