package controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;

import models.Inscription;

import play.db.jpa.Transactional;
import play.mvc.Controller;
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
	public static Result generateBadge() {
		
		File generatedPDF = null;
		OutputStream out = null;
		try {
			File tempDirectory = new File("tmp");
			
			generatedPDF = File.createTempFile("badge", ".pdf", tempDirectory);
			out = new FileOutputStream(generatedPDF);
			PDF.toStream(template.render(), out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		return ok(generatedPDF.getName());
	}
}
