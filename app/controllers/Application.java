package controllers;

import javax.inject.Inject;

import models.Inscription;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import services.Repository;
import views.html.index;

public class Application extends Controller {
	
	final static Form<Inscription> inscriptionForm = form(Inscription.class);
	@Inject static Repository repository;
	
	@Transactional
	public static Result index() {
		return ok(index.render(inscriptionForm, ""));
  	}

	@Transactional
	public static Result submit() {
		Form<Inscription> filledForm = inscriptionForm.bindFromRequest();
		
		if(filledForm.hasErrors()) {
            return badRequest(index.render(filledForm, "inscription non valide : " + filledForm.errorsAsJson().toString()));
        } else {
        	Inscription inscription = filledForm.get();
			repository.save(inscription);
        	return ok(index.render(filledForm, "inscription validée"));
        }
	}
  
}