package models;

import org.junit.Test;
import static org.junit.Assert.*;

public class InscriptionTests {
	
	private ModelFactory createModelFactory() {
		return new ModelFactory("15723826", "16008859", "16057183", "16057319", "15929463", "17826293");
	}


	@Test
	public void shouldNotBeAnOrganizerIfNoCategory() {
		Inscription inscription = new Inscription();
		
		assertFalse(inscription.isOrganizer());
	}
	
	@Test
	public void shouldCreateAnInscriptionWithNullCategorie() {
		Inscription inscription = createModelFactory().createInscription("nom", "prenom", "email", "barCode", null);
		
		assertFalse(inscription.isOrganizer());
		assertFalse(inscription.isAttendee());
		assertFalse(inscription.isSpeaker());
	}
	
	@Test
	public void shouldCreateAnInscriptionWithEmptyCategorie() {
		Inscription inscription = createModelFactory().createInscription("nom", "prenom", "email", "barCode", "");
		
		assertFalse(inscription.isOrganizer());
		assertFalse(inscription.isAttendee());
		assertFalse(inscription.isSpeaker());
	}
}
