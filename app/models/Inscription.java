package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;

@Entity(name = "inscriptions")
@SequenceGenerator(name = "inscriptions_sequence", sequenceName = "inscriptions_sequence")
public class Inscription {
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inscriptions_sequence")
	private int id;
	
	@Required
	@Column(name = "nom", nullable = false, length = 50)
	public String nom;
	
	@Required
	@Column(name = "prenom", nullable = false, length = 50)
	public String prenom;
	
	@Required
	@Email
	@Column(name = "email", nullable = false, length = 255)
	public String email;
	
	@Column(name = "twitter", length = 50)
	public String twitter;
	
	@Column(name = "entreprise", length = 100)
	
	public String entreprise;
	
	@Column(name = "fonction", length = 100)
	public String fonction;
	
	@Column(name = "niveauagile", length = 15)
	public String niveauAgile;

	@Column(name = "barcode", length = 30)
	public String barCode;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id= id;
	}
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
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
}
