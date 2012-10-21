package models;

import play.data.validation.Constraints.*;

public class Inscription {
	@Required public String nom;
	@Required public String prenom;
	@Required @Email public String email;
	public String twitter;
	public String entreprise;
	public String fonction;
	public String niveauAgile;
	@Required public Boolean acceptePourMailsSponsors;
	@Required public Boolean acceptePourMailsOrganisation;
	

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getEntreprise() {
		return entreprise;
	}
	public void setEntreprise(String entreprise) {
		this.entreprise = entreprise;
	}
	public String getFonction() {
		return fonction;
	}
	public void setFonction(String fonction) {
		this.fonction = fonction;
	}
	public String getNiveauAgile() {
		return niveauAgile;
	}
	public void setNiveauAgile(String niveauAgile) {
		this.niveauAgile = niveauAgile;
	}
	public Boolean getAcceptePourMailsSponsors() {
		return acceptePourMailsSponsors;
	}
	public void setAcceptePourMailsSponsors(Boolean acceptePourMailsSponsors) {
		this.acceptePourMailsSponsors = acceptePourMailsSponsors;
	}
	public Boolean getAcceptePourMailsOrganisation() {
		return acceptePourMailsOrganisation;
	}
	public void setAcceptePourMailsOrganisation(Boolean acceptePourMailsOrganisation) {
		this.acceptePourMailsOrganisation = acceptePourMailsOrganisation;
	}
}
