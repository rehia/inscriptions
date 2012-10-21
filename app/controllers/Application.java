package controllers;

import models.*;
import play.data.Form;
import play.data.validation.Validation;
import play.mvc.*;
import services.Repository;
import javax.inject.*;

import views.html.*;

public class Application extends Controller {
	
	final static Form<Inscription> inscriptionForm = form(Inscription.class);
	@Inject static Repository repository;
	
	public Application(Repository repository) {
	    this.repository = repository;
	  }
  
	public static Result index() {
		return ok(index.render(inscriptionForm, ""));
  	}

	public static Result submit() {
		Form<Inscription> filledForm = inscriptionForm.bindFromRequest();
		
		if(filledForm.hasErrors()) {
            return badRequest(index.render(filledForm, "inscription non valide"));
        } else {
        	return ok(index.render(filledForm, "inscription validée"));
        }
	}
  
}