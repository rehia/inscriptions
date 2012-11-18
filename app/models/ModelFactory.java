package models;

import helpers.StringFormatter;

public class ModelFactory {
	private static StringFormatter formatter = new StringFormatter();

	public static Inscription createInscription(String nom, String prenom,
			String email, String barCode, String ticketId) {
		Inscription inscription = new Inscription();
		inscription.setNom(formatter.format(nom));
		inscription.setPrenom(formatter.format(prenom));
		inscription.setEmail(email);
		inscription.setBarCode(barCode);
		
		setCategorie(ticketId, inscription);
		return inscription;
	}

	protected static void setCategorie(String ticketId, Inscription inscription) {
		if (ticketId != null) {
			if (ticketId.equals("15723826"))
				inscription.setCategorie(Inscription.INSCRIT);
			else if (ticketId.equals("16008859"))
				inscription.setCategorie(Inscription.ORGANISATEUR);
			else if (ticketId.equals("16057183"))
				inscription.setCategorie(Inscription.SPEAKER);
		}
	}

	public static Inscription createInscription(String nom,
			String prenom, String email, String barCode, String fonction,
			String entreprise, String ticketId) {
		Inscription inscription = createInscription(nom, prenom, email, barCode, ticketId);
		inscription.entreprise = entreprise;
		inscription.fonction = fonction;
		
		return inscription;
	}

}
