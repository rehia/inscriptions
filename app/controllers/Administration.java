package controllers;

import java.util.List;

import javax.inject.Inject;

import models.Inscription;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import static play.mvc.Results.*;

import services.Repository;
import views.html.admin;


public class Administration extends Controller {

	@Inject static Repository repository;
	
	@Transactional
	public static Result admin() {
		List<Inscription> inscriptions = repository.getInscriptions();
		return ok(admin.render(inscriptions));
  	}
}
