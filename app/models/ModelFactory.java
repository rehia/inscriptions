package models;

import helpers.StringFormatter;

public class ModelFactory {
	private static StringFormatter formatter = new StringFormatter();
	private final String inscritTicketId;
	private final String organisateurTicketId;
	private final String speakerTicketId;
	private final String studentTicketId;
	private final String vipTicketId;
	private final String earlyBirdTicketId;

	public ModelFactory(String inscritTicketId, String organisateurTicketId,
			String speakerTicketId, String studentTicketId, String vipTicketId,
			String earlyBirdTicketId) {
		this.inscritTicketId = inscritTicketId;
		this.organisateurTicketId = organisateurTicketId;
		this.speakerTicketId = speakerTicketId;
		this.studentTicketId = studentTicketId;
		this.vipTicketId = vipTicketId;
		this.earlyBirdTicketId = earlyBirdTicketId;
	}

	public Inscription createInscription(String nom, String prenom,
			String email, String barCode, String ticketId) {
		Inscription inscription = new Inscription();
		inscription.setNom(formatter.format(nom));
		inscription.setPrenom(formatter.format(prenom));
		inscription.setEmail(email);
		inscription.setBarCode(barCode);

		setCategorie(ticketId, inscription);
		return inscription;
	}

	protected void setCategorie(String ticketId, Inscription inscription) {
		if (ticketId != null && ticketId != "") {
			if (inscritTicketId.contains(ticketId) || ticketId.equals(earlyBirdTicketId))
				inscription.setCategorie(Inscription.INSCRIT);
			else if (ticketId.equals(organisateurTicketId))
				inscription.setCategorie(Inscription.ORGANISATEUR);
			else if (ticketId.equals(speakerTicketId))
				inscription.setCategorie(Inscription.SPEAKER);
			else if (ticketId.equals(studentTicketId))
				inscription.setCategorie(Inscription.STUDENT);
			else if (ticketId.equals(vipTicketId))
				inscription.setCategorie(Inscription.VIP);
		}
	}

	public Inscription createInscription(String nom, String prenom,
			String email, String barCode, String fonction, String entreprise,
			String ticketId) {
		Inscription inscription = createInscription(nom, prenom, email,
				barCode, ticketId);
		inscription.entreprise = entreprise;
		inscription.fonction = fonction;

		return inscription;
	}

}
