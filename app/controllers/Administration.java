package controllers;

import helpers.PDF;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import models.Inscription;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http.Request;
import play.mvc.Result;
import services.Repository;
import views.html.admin;
import views.html.template;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;


public class Administration extends Controller {

	@Inject static Repository repository;
	
	@Transactional
	public static Result admin() {
		List<Inscription> inscriptions = repository.getInscriptions();
		return ok(admin.render(inscriptions, ""));
  	}
	
	@Transactional
	public static Result generateBadge() throws DocumentException, IOException {
		
		byte[] finalBadge = generateAndMergeBadges(getSelectedInscriptionId(request()));
		return ok(finalBadge).as("application/pdf");		
	}

	protected static byte[] generateAndMergeBadges(Set<Integer> selectedInscriptions)
			throws DocumentException, IOException {
		ByteArrayOutputStream finalOutput = new ByteArrayOutputStream();
		PdfCopyFields copy = new PdfCopyFields(finalOutput);
		
		try {			
			for (Integer inscriptionId : selectedInscriptions) {
				copy.addDocument(generateSingleBadge(inscriptionId));
			}
		} finally {
			copy.close();
		}		
						
		return finalOutput.toByteArray();
	}

	protected static PdfReader generateSingleBadge(Integer inscriptionId)
			throws IOException {
		byte[] output = PDF.toBytes(template.render(inscriptionId));
		return new PdfReader(output);
	}

	protected static Set<Integer> getSelectedInscriptionId(Request request) {
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
		return ok(admin.render(inscriptions, inscriptionsAddedCount + " inscriptions ajoutées"));
	}
}
