package controllers;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.codehaus.jackson.JsonNode;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopyFields;
import com.itextpdf.text.pdf.PdfReader;

import models.Inscription;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http.RequestBody;
import play.mvc.Result;
import static play.mvc.Results.*;

import services.Repository;
import views.html.admin;
import views.html.template;
import util.pdf.PDF;


public class Administration extends Controller {

	@Inject static Repository repository;
	
	@Transactional
	public static Result admin() {
		List<Inscription> inscriptions = repository.getInscriptions();
		return ok(admin.render(inscriptions));
  	}
	
	@Transactional
	public static Result generateBadge() throws DocumentException, IOException {
		Map<String, String[]> selectedInscriptions = request().body().asFormUrlEncoded();

		ByteArrayOutputStream finalOutput = new ByteArrayOutputStream();
		PdfCopyFields copy = new PdfCopyFields(finalOutput);
		
		for (String inscriptionId : selectedInscriptions.keySet()) {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			PDF.toStream(template.render(inscriptionId), output);
			copy.addDocument(new PdfReader(output.toByteArray()));
		}
		
		copy.close();
						
		return ok(finalOutput.toByteArray()).as("application/pdf");		
	}
}
