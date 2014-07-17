package controllers;

import helpers.PDF;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.codec.binary.Base64;

import models.Inscription;
import models.ModelFactory;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Security;
import play.mvc.Http.Request;
import play.mvc.Result;
import services.Repository;
import views.html.admin;
import views.html.template;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;

@Security.Authenticated(Secured.class)
public class Administration extends Controller {

	@Inject static Repository repository;
	
	@Transactional
	public static Result admin() {
		List<Inscription> inscriptions = repository.getInscriptions();
		String message = "";
		session().put("currentCategory", "tous");
		return renderAdminView(inscriptions, message);
  	}

	protected static Status renderAdminView(List<Inscription> inscriptions,
			String message) {
		List<String> categories = repository.getCategories();
		return ok(admin.render(inscriptions, message, categories, session().get("currentCategory"), session().get("connectedUser")));
	}
	
	@Transactional
	public static Result generateBadge() throws DocumentException, IOException {
		
		byte[] finalBadge = generateAndMergeBadges(getSelectedInscriptionIds(request()));
		return ok(finalBadge).as("application/pdf");		
	}

	protected static byte[] generateAndMergeBadges(Set<Integer> selectedInscriptions)
			throws DocumentException, IOException {
		ByteArrayOutputStream finalOutput = new ByteArrayOutputStream();
		PdfCopyFields copy = new PdfCopyFields(finalOutput);
					
		for (Integer inscriptionId : selectedInscriptions) {
			byte[] badge = getSingleBadgeFromInscriptionId(inscriptionId);
			copy.addDocument(new PdfReader(badge));
		}
		
		copy.close();
						
		return finalOutput.toByteArray();
	}

	protected static byte[] getSingleBadgeFromInscriptionId(Integer inscriptionId) {
		Inscription inscription = repository.getInscriptionById(inscriptionId);
		return getSingleBadgeFromInscription(inscription);
	}

	protected static byte[] getSingleBadgeFromInscription(
			Inscription inscription) {
		return PDF.toBytes(template.render(inscription));
	}

	protected static Set<Integer> getSelectedInscriptionIds(Request request) {
		Set<Integer> selectedInscriptionIds = new HashSet<Integer>();
		
		for (String checkboxId : getCheckedCheckboxesId(request)) {
			selectedInscriptionIds.add(getInscriptionIdFromCheckboxId(checkboxId));
		}
		return selectedInscriptionIds;
	}

	protected static int getInscriptionIdFromCheckboxId(String checkboxId) {
		return Integer.parseInt(checkboxId.replaceAll("nameSelected_", ""));
	}

	protected static Set<String> getCheckedCheckboxesId(Request request) {
		return request.body().asFormUrlEncoded().keySet();
	}
	
	@Transactional
	public static Result updateInscriptions() {
		int inscriptionsAddedCount = repository.updateInscriptions();
		List<Inscription> inscriptions = repository.getInscriptions();
		String message = inscriptionsAddedCount + " inscriptions ajoutées";
		return renderAdminView(inscriptions, message);
	}
	
	@Transactional
	public static Result filterInscriptions(String category) {
		List<Inscription> inscriptions = repository.getInscriptionsByCategory(category);
		session().put("currentCategory", category);
		
		return renderAdminView(inscriptions, "");
	}
	
	@Transactional
	public static Result sendBadge() throws IOException {
		Inscription inscription = getSelectedInscription();
		
		if (inscription.badgeIsSent())
			return ok("badge déjà envoyé pour " + inscription.getEmail());

		byte[] badge = getSingleBadgeFromInscription(inscription);
		
		try {
			String message = repository.sendBadgeToAttendee(inscription, formatBadge(badge));
			return ok("badge envoyé pour " + inscription.getEmail());
		} catch (RuntimeException e) {
			play.Logger.error("erreur lors de l'envoi du badge à " + inscription.getEmail(), e);
			return badRequest("erreur : " + e.getMessage());
		}
		
	}

	private static String formatBadge(byte[] badge) {
		return Base64.encodeBase64String(badge);
	}

	protected static Inscription getSelectedInscription() {
		int inscriptionId = getSelectedInscriptionIds(request()).iterator().next();
		Inscription inscription = repository.getInscriptionById(inscriptionId);
		return inscription;
	}
	
	@Transactional
	public static Result deleteInscriptions() {
		List<Inscription> inscriptions = getSelectedInscriptions();
		
		repository.deleteInscriptions(inscriptions);
		
		return redirect(routes.Administration.admin());
	}

	private static List<Inscription> getSelectedInscriptions() {
		List<Inscription> inscriptions = new ArrayList<Inscription>();
		Set<Integer> inscriptionIds = getSelectedInscriptionIds(request());
		
		for(int inscriptionId : inscriptionIds) {
			inscriptions.add(repository.getInscriptionById(inscriptionId));
		}
		
		return inscriptions;
	}
}
