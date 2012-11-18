package helpers;

import java.util.ArrayList;
import java.util.List;

import models.Inscription;
import models.ModelFactory;

public class FakeDataProvider {

	public static Inscription getAnExistingInscription() {
		return ModelFactory.createInscription("Durand", "Fernand", "f.d@df.fr", "119309147152822123456", Inscription.INSCRIT);
	}

	public static List<Inscription> getANewInscription() {
		List<Inscription> inscriptions = new ArrayList<Inscription>();
		inscriptions.add(ModelFactory.createInscription("Hazard", "Thierry", "thierry.hazard@lejerk.com", "119309147152822000000", Inscription.INSCRIT));
		return inscriptions;
	}

	public static List<Inscription> getSomeNewInscriptions() {
		List<Inscription> inscriptions = getANewInscription();
		inscriptions.add(ModelFactory.createInscription("Montagné", "Gilbert", "gilbert@montagne.com", "119309147152822111111", Inscription.INSCRIT));
		return inscriptions;
	}

	public static List<Inscription> getSomeExistingInscriptions() {
		List<Inscription> inscriptions = new ArrayList<Inscription>();
		inscriptions.add(getAnExistingInscription());
		inscriptions.add(ModelFactory.createInscription("Dupont", "Bernard", "u_I@uj.fr", "119309147152822654321", Inscription.INSCRIT));
		inscriptions.addAll(getOrganizers());

		return inscriptions;
	}

	public static List<Inscription> getOrganizers() {
		List<Inscription> inscriptions = new ArrayList<Inscription>();
		inscriptions.add(ModelFactory.createInscription("Goldman", "Jean Jacques", "zokd@iy.fr", "119309147152822987654", Inscription.ORGANISATEUR));
		
		return inscriptions;
	}

}
