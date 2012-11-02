package controllers;

import models.*;
import play.data.Form;
import play.data.validation.Validation;
import play.db.jpa.Transactional;
import play.mvc.*;
import services.Repository;
import javax.inject.*;

import org.hibernate.TypeMismatchException;

import views.html.*;

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