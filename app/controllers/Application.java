package controllers;

import models.*;
import play.data.Form;
import play.data.validation.Validation;
import play.mvc.*;
import services.Repository;
import javax.inject.*;

import views.html.*;

public class Application extends Controller {
	
	final Form<Inscription> inscriptionForm = form(Inscription.class);
	private Repository repository;
	
	@Inject public Application(Repository repository) {
	    this.repository = repository;
	  }
  
  public Result index() {
    return ok(index.render(inscriptionForm, ""));
  }

	public Result submit() {
		Form<Inscription> filledForm = inscriptionForm.bindFromRequest();
		
		if(filledForm.hasErrors()) {
            return badRequest(index.render(filledForm, "inscription non valide"));
        } else {
        	return ok(index.render(filledForm, "inscription validée"));
        }
	}
  
}