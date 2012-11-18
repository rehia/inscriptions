package models;

import org.junit.Test;
import static org.junit.Assert.*;

public class InscriptionTests {
	
	@Test
	public void shouldNotBeAnOrganizerIfNoCategory() {
		Inscription inscription = new Inscription();
		
		assertFalse(inscription.isOrganizer());
	}
	
	@Test
	public void shouldCreateAnInscriptionWithNullCategorie() {
		Inscription inscription = ModelFactory.createInscription("nom", "prenom", "email", "barCode", null);
		
		assertFalse(inscription.isOrganizer());
		assertFalse(inscription.isAttendee());
		assertFalse(inscription.isSpeaker());
	}
	
	@Test
	public void shouldCreateAnInscriptionWithEmptyCategorie() {
		Inscription inscription = ModelFactory.createInscription("nom", "prenom", "email", "barCode", "");
		
		assertFalse(inscription.isOrganizer());
		assertFalse(inscription.isAttendee());
		assertFalse(inscription.isSpeaker());
	}
}
