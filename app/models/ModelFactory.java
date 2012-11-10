package models;

public class ModelFactory {

	public static Inscription createInscription(String nom, String prenom,
			String email, String barCode) {
		Inscription inscription = new Inscription();
		inscription.setNom(nom);
		inscription.setPrenom(prenom);
		inscription.setEmail(email);
		inscription.setBarCode(barCode);
		return inscription;
	}

}
